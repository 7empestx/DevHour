package dev.hour.database;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dev.hour.contracts.MealContract;
import dev.hour.model.Meal;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

public class MealDatabase implements MealContract.Meal.Database {

    /// ---------------------
    /// Public Static Members

    public final static String ACCESS_KEY       = "ACCESS_KEY"      ;
    public final static String SECRET_KEY       = "SECRET_KEY"      ;
    public final static String SESSION_TOKEN    = "SESSION_TOKEN"   ;

    /// ----------------------
    /// Private Static Members

    private final static int BUFFER_SIZE = 4096;

    /// ---------------------
    /// Public Static Methods

    /**
     * Generates a semi-unique hexadecimal identifier based on the current time
     * @return [String] value of a semi-unique hexadecimal identifier
     */
    public static String GenerateId() {

        final long      time        = Calendar.getInstance().getTimeInMillis();
        final String    timeString  = String.valueOf(time);

        return Integer.toHexString(timeString.hashCode());

    }

    /// ---------------
    /// Private Members

    private DynamoDbClient                          client          ;
    private S3Client                                s3Client        ;
    private String                                  tableName       ;
    private String                                  bucketName      ;
    private String                                  region          ;
    private SdkHttpClient                           httpClient      ;
    private Map<String, AttributeValue>             response        ;
    private ResponseInputStream<GetObjectResponse>  objectResponse  ;

    /// ------------
    /// Constructing

    /**
     * Initializes the [MealDatabase] to its' default state.
     * @param region The [Region] corresponding the Restaurant resources
     * @param tableName The name of the Restaurant table
     * @param bucketName The name of the Restaurant bucket
     * @param httpClient The http client to perform the requests.
     */
    public MealDatabase(final String region, final String tableName, final String bucketName,
                              final SdkHttpClient httpClient){

        this.client     = null          ;
        this.tableName  = tableName     ;
        this.bucketName = bucketName    ;
        this.region     = region        ;
        this.httpClient = httpClient    ;

    }

    /// ---------------
    /// Private Methods

    /**
     * Retrieves a String value from the AttributeValue with the given key
     * @param item The item to extract the [String] from
     * @param key The [String] key corresponding to the value
     * @return [String] instance
     */
    private String getStringFrom(final Map<String, AttributeValue> item, final String key) {

        String result = "";

        if(item != null) {

            final AttributeValue attributeValue = item.get(key);

            if(attributeValue != null)
                result = attributeValue.s();

        }

        return result;

    }

    /**
     * Retrieves a List value from the AttributeValue with the given key
     * @param item The item to extract the [List] from
     * @param key The [String] key corresponding to the value
     * @return [List] instance
     */
    private List<AttributeValue> getListFrom(final Map<String, AttributeValue> item, final String key) {

        List<AttributeValue> result = new ArrayList<>();

        if(item != null) {

            final AttributeValue attributeValue = item.get(key);

            if(attributeValue != null)
                result = attributeValue.l();

        }

        return result;

    }

    /**
     * Creates an item that can update a DynamoDB table item.
     * @param data The item to set
     * @return Map<String, AttributeValue> with the given data.
     */
    private Map<String, AttributeValue> createItemFrom(final Map<String, Object> data) {

        final Map<String, AttributeValue> result;

        if(data != null) {

            result = new HashMap<>();

            for(Map.Entry<String, Object> entry: data.entrySet()) {

                final Object value = entry.getValue();
                AttributeValue attributeValue = null;

                if(value instanceof String)
                    attributeValue = AttributeValue.builder().s((String) value).build();

                else if(value instanceof List) {

                    final List<String> list = (List) value;
                    final List<AttributeValue> attributeValues = new ArrayList<>();

                    for(final String string: list)
                        attributeValues.add(AttributeValue.builder().s(string).build());

                    attributeValue = AttributeValue.builder().l(attributeValues).build();

                }

                if(attributeValue != null)
                    result.put(entry.getKey(), attributeValue);

            }


        } else result = new HashMap<>();

        return result;

    }

    /**
     * Creates a GetItemRequest to retrieve the item with the specified key-value pair.
     * @param key The value of the key
     * @param value The specific value to match
     * @return Map containing the requested data, if any
     */
    private Map<String, AttributeValue> getItem(final String key, final String value) {

        final Map<String, AttributeValue> keyMap = new HashMap<>();
        Map<String, AttributeValue> result;

        if(this.client != null) {

            keyMap.put(key, AttributeValue.builder().s(value).build());

            final GetItemRequest request = GetItemRequest.builder()
                    .key(keyMap)
                    .tableName(this.tableName)
                    .build();

            final MealDatabase database = this;

            final Thread thread = new Thread(() ->
                    database.response = database.client.getItem(request).item());

            try {

                thread.start();
                thread.join();

                result = response;

            } catch (final Exception exception) {

                Log.e("MealDatabase", exception.getMessage());
                result = new HashMap<>();

            }

        } else result = new HashMap<>();

        return result;

    }

    /**
     * Returns an S3 Object with the specified key (if any) as an [OutputStream] instance
     * @param key The key corresponding to the S3 Object
     * @return [OutputStream]
     */
    private OutputStream getObject(final String key) {

        // Create the resultant OutputStream
        final OutputStream result = new ByteArrayOutputStream();

        // If we have a client
        if(this.s3Client != null) {

            // Create the request
            final GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(this.bucketName)
                    .key(key)
                    .build();

            // Retrieve a handle to ourselves
            final MealDatabase database = this;


            try {

                // Create the request thread
                final Thread thread = new Thread(() ->
                        database.objectResponse = database.s3Client.getObject(request));

                // Perform the request
                thread.start();
                thread.join();

                // Create our buffer and bytes read
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytes;

                do {

                    // Attempt to read the bytes into the buffer
                    bytes = this.objectResponse.read(buffer);

                    // Break condition
                    if(bytes == -1) break;

                    // Write the result into the output stream
                    result.write(buffer, 0, bytes);

                } while(true);

            } catch(final Exception exception) {

                Log.e("MealDatabase", "Error" + exception.getMessage());

            }

        }

        // Return the result
        return result;

    }

    /**
     * Sets the given item with the given data onto the current table
     * @param item The item to write.
     */
    private void putItem(final Map<String, AttributeValue> item) {

        if(this.client != null) {

            final PutItemRequest request =
                    PutItemRequest.builder().tableName(this.tableName).item(item).build();

            this.client.putItem(request);

        }

    }

    /**
     * Puts the contents of the given [InputStream] into the specified S3 bucket with the given key
     * @param key The key to set to the S3 Object
     * @param inputStream The contents of the Object
     * @param length The length of the content
     */
    private void putObject(final String key, final InputStream inputStream, final long length) {

        if(this.s3Client != null) {

            // Create the request
            final PutObjectRequest request =
                    PutObjectRequest.builder().bucket(this.bucketName).key(key).build();

            // Upload the object
            this.s3Client.putObject(request, RequestBody.fromInputStream(inputStream, length));

            final S3Waiter waiter = this.s3Client.waiter();

            // Create the wait request
            final HeadObjectRequest waitRequest =
                    HeadObjectRequest.builder().bucket(this.bucketName).key(key).build();

            // Wait
            final WaiterResponse<HeadObjectResponse> response = waiter.waitUntilObjectExists(waitRequest);

        }

    }

    /**
     * Retrieves the [InputStream] containing the picture data of the meal, if any.
     * @param data The meal data
     * @return [InputStream] object containing the meal picture data.
     */
    private InputStream getMealPictureInputStreamFrom(final Map<String, Object> data) {

        final Object intermediate = data.get("picture")  ;

        InputStream   result = null;

        if(intermediate instanceof InputStream)
            result = (InputStream) intermediate;

        if(intermediate != null)
            data.remove("picture");

        return result;

    }

    /**
     * Binds a Meal instance with the pertinent data from the given Map
     * @param data The data to bind the Meal with
     * @return Meal instance
     */
    private Meal bindMealFrom(final Map<String, AttributeValue> data) {

        final Meal meal = new Meal();

        // Set the id
        meal.setId(getStringFrom(data, "id"));

        // Set the name
        meal.setName(getStringFrom(data, "name"));

        final String calorieString = getStringFrom(data, "calories");
        final int calories = (calorieString != null && !calorieString.isEmpty()) ? Integer.parseInt(calorieString) : 0;

        // Set the calories
        meal.setCalories(calories);

        final Map <String, AttributeValue>  tempMap         = Objects.requireNonNull(data.get("ingredients")).m();
        final Map <String, String>          ingredientsMap  = new HashMap<>();

        for (final Map.Entry<String, AttributeValue> entry: tempMap.entrySet())
            ingredientsMap.put(entry.getKey(), entry.getKey());

        // Set the ingredients
        meal.setIngredients(ingredientsMap);

        final String pictureId = getStringFrom(data, "picture_id");

        if((pictureId != null) && !(pictureId.isEmpty()))
            meal.setImageStream(getObject(pictureId));

        return meal;

    }

    /// ---------------------------
    /// MealContract.Meal.Database

    /**
     * Sets the credentials required to build the DynamoDB client and S3 client
     * @param credentials The credentials to set.
     */
    @Override
    public void setCredentials(final Map<String, String> credentials) {

        this.client = DynamoDbClient
                .builder()
                .region(Region.of(this.region))
                .httpClient(this.httpClient)
                .credentialsProvider(() -> AwsSessionCredentials.create(
                        Objects.requireNonNull(credentials.get(ACCESS_KEY)),
                        Objects.requireNonNull(credentials.get(SECRET_KEY)),
                        Objects.requireNonNull(credentials.get(SESSION_TOKEN))))
                .build();

        this.s3Client = S3Client
                .builder()
                .region(Region.of(this.region))
                .httpClient(this.httpClient)
                .credentialsProvider(() -> AwsSessionCredentials.create(
                        Objects.requireNonNull(credentials.get(ACCESS_KEY)),
                        Objects.requireNonNull(credentials.get(SECRET_KEY)),
                        Objects.requireNonNull(credentials.get(SESSION_TOKEN))))
                .build();

    }

    /**
     * Creates a Meal with the given data on the current database table. This method
     * will also upload a picture from the corresponding S3 bucket.
     * @param data The data to create the Meal entry with
     */
    @Override
    public void updateMeal(final Map<String, Object> data) {

        // Retrieve a handle to ourselves, the picture stream (if any), and create a picture id
        final MealDatabase    database      = this                                     ;
        final InputStream     pictureStream = getMealPictureInputStreamFrom(data)      ;

        data.putIfAbsent("id", GenerateId());

        try {

            if((pictureStream != null) && (pictureStream.available() > 0)) {

                final String mealPictureID = GenerateId();
                final long contentLength =
                        (data.get("content_length") == null) ? 0L : Long.parseLong(String.valueOf(data.get("content_length")));

                data.remove("content_length");

                data.putIfAbsent("picture_id", mealPictureID);

                final Thread uploadThread = new Thread(()->
                        database.putObject(mealPictureID, pictureStream, contentLength));

                uploadThread.start();
                uploadThread.join();

            }

        } catch (final Exception exception) {

            Log.e("UpdateMealDatabase", exception.getMessage());

        }

        try {
            final Thread thread = new Thread(() ->
                    database.putItem(createItemFrom(data)));

            thread.start();
            thread.join();

        } catch (final Exception exception) {

            Log.e("MealDatabase", exception.getMessage());

        }

    }

    /**
     * Returns a List of MealContract.Meals from the list of mealids
     * @param mealIds The meal id's of the MealContract.Meals to retrieve
     * @return List of MealContract.Meals
     */
    @Override
    public List<MealContract.Meal> getMealsFrom(final List<String> mealIds) {

        final List<MealContract.Meal> result = new ArrayList<>();

        if(mealIds != null)
            for(final String id: mealIds)
                result.add(bindMealFrom(getItem("id", id)));

        return result;

    }

    /**
     * Retrieves the meal corresponding with the given id, if any
     * @param id the meal id
     * @return MealContract.Meal instance
     */
    @Override
    public MealContract.Meal getMeal(final String id) {

        return bindMealFrom(getItem("id", id));

    }

}
