package dev.hour.database;

import android.util.Log;
import android.webkit.ConsoleMessage;

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
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;


public class MenuDatabase implements MealContract.Menu.Database {

    /// ---------------------
    /// Public Static Members

    public final static String ACCESS_KEY       = "ACCESS_KEY"      ;
    public final static String SECRET_KEY       = "SECRET_KEY"      ;
    public final static String SESSION_TOKEN    = "SESSION_TOKEN"   ;

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

    public MenuDatabase(final String region, final String tableName, final String bucketName,
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

            final MenuDatabase database = this;

            final Thread thread = new Thread(() ->
                    database.response = database.client.getItem(request).item());

            try {

                thread.start();
                thread.join();

                result = response;

            } catch (final Exception exception) {

                Log.e("MenuDatabase", exception.getMessage());
                result = new HashMap<>();

            }

        } else result = new HashMap<>();

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
     * Sets the credentials required to build the DynamoDB client and S3 client
     * @param credentials The credentials to set.
     */
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


    }

    @Override
    public List<MealContract.Meal> getMenu(String menuId){
        return toMeals(getItem("id", menuId));
    }

    private List<MealContract.Meal> toMeals(final Map<String, AttributeValue> data){

        List<MealContract.Meal> res = new ArrayList<>();

        List<AttributeValue> objs = Objects.requireNonNull(data.get("meals")).l();

        for(AttributeValue v: objs){

            Map<String, AttributeValue> mealMap = v.m();

            String calories = Objects.requireNonNull(mealMap.get("calories")).s();
            String name = Objects.requireNonNull(mealMap.get("name")).s();

            Meal meal = new Meal();

            meal.setName(name);
            meal.setCalories(Integer.parseInt(calories));

            res.add(meal);
        }

        return res;
    }


}
