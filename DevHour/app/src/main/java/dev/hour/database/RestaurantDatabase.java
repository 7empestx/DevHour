package dev.hour.database;


import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import dev.hour.contracts.RestaurantContract;
import dev.hour.model.Restaurant;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

public class RestaurantDatabase implements RestaurantContract.Database {

    /// ---------------------
    /// Public Static Members

    public final static String ACCESS_KEY       = "ACCESS_KEY"      ;
    public final static String SECRET_KEY       = "SECRET_KEY"      ;
    public final static String SESSION_TOKEN    = "SESSION_TOKEN"   ;

    /// ----------------------
    /// Private Static Members

    private final static int BUFFER_SIZE        = 4096              ;

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
     * Initializes the [RestaurantDatabase] to its' default state.
     * @param region The [Region] corresponding the Restaurant resources
     * @param tableName The name of the Restaurant table
     * @param bucketName The name of the Restaurant bucket
     * @param httpClient The http client to perform the requests.
     */
    public RestaurantDatabase(final String region, final String tableName, final String bucketName,
                              final SdkHttpClient httpClient) {

        this.client     = null          ;
        this.s3Client   = null          ;
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

                else if(value instanceof List)
                    attributeValue = AttributeValue.builder().l((List) value).build();

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

            final RestaurantDatabase database = this;

            final Thread thread = new Thread(() ->
                    database.response = database.client.getItem(request).item());

            try {

                thread.start();
                thread.join();

                result = response;

            } catch (final Exception exception) {

                Log.e("RestaurantDatabase", exception.getMessage());
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
        if((this.s3Client != null) && (key != null) && (!key.isEmpty())) {

            // Create the request
            final GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(this.bucketName)
                    .key(key)
                    .build();

            // Retrieve a handle to ourselves
            final RestaurantDatabase database = this;

            // Create the request thread
            final Thread thread = new Thread(() -> {

                try {

                    database.objectResponse = database.s3Client.getObject(request);

                } catch(final Exception exception) {

                    Log.e("RestaurantDatabase", exception.getMessage());

                }

            });

            try {

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

                Log.e("RestaurantDatabase", exception.getMessage());

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
     * Retrieves the [InputStream] containing the picture data of the restaurant, if any.
     * @param data The restaurant data
     * @return [InputStream] object containing the restaurant picture data.
     */
    private InputStream getRestaurantPictureInputStreamFrom(final Map<String, Object> data) {

        final Object intermediate = data.get("picture")  ;

        InputStream   result = null;

        if(intermediate instanceof InputStream)
            result = (InputStream) intermediate;

        if(intermediate != null)
            data.remove("picture");

        return result;

    }

    /**
     * Binds a Restaurant instance with the pertinent data from the given Map
     * @param data The data to bind the Restaurant with
     * @return Restaurant instance
     */
    private Restaurant bindRestaurantFrom(final Map<String, AttributeValue> data) {

        final Restaurant restaurant = new Restaurant();

        // Set the id
        restaurant.setId(getStringFrom(data, "id"));

        // Set the name
        restaurant.setName(getStringFrom(data, "name"));

        // Set the address
        restaurant.setAddress1(getStringFrom(data, "address1"));

        // Set the other address
        restaurant.setAddress1(getStringFrom(data, "address2"));

        // Set the menu id
        restaurant.setMenuId(getStringFrom(data, "menu_id"));

        final String longitude = getStringFrom(data, "longitude");
        final String latitude  = getStringFrom(data, "latitude");

        // Set the longitude
        restaurant.setLongitude(Double.parseDouble((longitude != null && !longitude.isEmpty()) ? longitude : "0"));

        // Set the latitude
        restaurant.setLatitude(Double.parseDouble((longitude != null && !latitude.isEmpty()) ? latitude : "0"));

        final String pictureId = getStringFrom(data, "picture_id");

        if((pictureId != null) && !(pictureId.isEmpty()))
            restaurant.setImageStream(getObject(pictureId));

        return restaurant;

    }

    /// ---------------------------
    /// RestaurantContract.Database

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
     * Updates/Creates a Restaurant with the given data on the current database table. This method
     * will also upload a picture from the corresponding S3 bucket.
     * @param data The data to create the Restaurant entry with
     * @param ownerId The owner of the restaurant
     */
    @Override
    public void updateRestaurant(final Map<String, Object> data, final String ownerId) {

        // Retrieve a handle to ourselves, the picture stream (if any), and create a picture id
        final RestaurantDatabase    database            = this                                     ;
        final InputStream           pictureStream       = getRestaurantPictureInputStreamFrom(data);

        String restaurantPictureId = GenerateId();
        long   contentLength        = (pictureStream != null) ? (Long) data.get("content_length") : 0L;

        // Generate an id for the restaurant
        data.put("id", GenerateId());
        data.put("picture_id", restaurantPictureId);
        data.put("restaurant_owner", ownerId);
        data.put("menu_id", GenerateId());

        // Remove the content length
        data.remove("content_length");

        final Thread thread = new Thread(() ->
                database.putItem(createItemFrom(data)));

        try {

            thread.start();
            thread.join();

        } catch (final Exception exception) {

            Log.e("RestaurantDatabase", exception.getMessage());

        }

        if(pictureStream != null) {

            final Thread uploadThread = new Thread(() ->
                    database.putObject(restaurantPictureId, pictureStream, contentLength));

            try {

                thread.start();
                thread.join();
                uploadThread.start();
                uploadThread.join();

            } catch (final Exception exception) {

                Log.e("RestaurantDatabase", exception.getMessage());

            }

        }

    }

    /**
     * Retrieves the Restaurants that contain the given tag in their list of tags*
     * @param tag The [String] tag to search
     * @return [List] of restaurants that contain the tag
     */
    @Override
    public List<RestaurantContract.Restaurant> retrieveRestaurantsByTag(final String tag) {

        final List<RestaurantContract.Restaurant> results                     = new ArrayList<>();
        final Map<String, AttributeValue>         expressionAttributeValues   = new HashMap<String, AttributeValue>();

        expressionAttributeValues.put(":tags", AttributeValue
                .builder()
                .s(tag)
                .build());

        final ScanRequest scanRequest = ScanRequest
                .builder()
                .tableName(this.tableName)
                .filterExpression("contains(tags, :tags)")
                .expressionAttributeValues(expressionAttributeValues)
                .build();

        final Thread thread = new Thread(() -> {

            try {

                final ScanResponse scanResponse = client.scan(scanRequest);

                for (final Map<String, AttributeValue> item : scanResponse.items())
                    results.add(bindRestaurantFrom(item));

            } catch (final Exception exception) {

                Log.e("RestaurantDatabase", exception.toString());

            }

        });

        try {

            thread.start();
            thread.join();

        } catch (final Exception exception) {

            Log.e("RestaurantDatabase", exception.getMessage());

        }

        return results;
    }

    /**
     * Retrieves the Restaurants that match with the owner id*
     * @param ownerId The id of the owner
     * @return [List] of restaurants
     */
    @Override
    public List<RestaurantContract.Restaurant> retrieveRestaurantsByOwner(final String ownerId) {

        final List<RestaurantContract.Restaurant>   results                     = new ArrayList<>();
        final Map<String, AttributeValue>           expressionAttributeValues   = new HashMap<>();

        expressionAttributeValues.put(":owner", AttributeValue
                .builder()
                .s(ownerId)
                .build());

        final ScanRequest scanRequest = ScanRequest
                .builder()
                .tableName(this.tableName)
                .filterExpression("restaurant_owner = :owner")
                .expressionAttributeValues(expressionAttributeValues)
                .build();

        final Thread thread = new Thread(() -> {

            try {

                final ScanResponse scanResponse = client.scan(scanRequest);

                for (final Map<String, AttributeValue> item : scanResponse.items())
                    results.add(bindRestaurantFrom(item));

            } catch (final Exception exception) {

                Log.e("RestaurantDatabase", exception.toString());

            }

        });

        try {

            thread.start();
            thread.join();

        } catch (final Exception exception) {

            Log.e("RestaurantDatabase", exception.getMessage());

        }

        return results;
    }
    
    /**
     * Returns a list of RestaurantContract.Restaurants that are within the specified radius.
     * @param longitude The center longitude
     * @param latitude The center latitude
     * @param radius The radius
     * @return List of Restaurants.
     */
    @Override
    public List<RestaurantContract.Restaurant> retrieveRestaurantsByLocation(final double longitude,
                                                                             final double latitude,
                                                                             final double radius) {
        //mercator projection
        //use scan function to query tables
        //TODO: implement query to find restaurants based off radius
        return null;
    }

    /**
     * Retrieves the restaurant corresponding with the given id, if any
     * @param id the restaurant id
     * @return RestaurantContract.Restaurant instance
     */
    @Override
    public RestaurantContract.Restaurant getRestaurant(final String id) {

        return bindRestaurantFrom(getItem("id", id));

    }

}
