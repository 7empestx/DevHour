package dev.hour.database;

import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dev.hour.contracts.MealContract;
import dev.hour.model.Meal;
import dev.hour.model.Menu;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

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
    private String                                  tableName       ;
    private String                                  region          ;
    private SdkHttpClient                           httpClient      ;
    private Map<String, AttributeValue>             response        ;

    /// ------------
    /// Constructing

    /**
     * Initializes the [MenuDatabase] to its' default state.
     * @param region The [Region] corresponding the Menu resources
     * @param tableName The name of the Menu table
     * @param httpClient The http client to perform the requests.
     */
    public MenuDatabase(final String region, final String tableName, final SdkHttpClient httpClient){

        this.client     = null          ;
        this.tableName  = tableName     ;
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

        if((this.client != null) && (key != null) && (!key.isEmpty()) &&
                (value != null) && (!value.isEmpty())) {

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
     * Binds a Menu instance with the pertinent data from the given Map
     * @param data The data to bind the Menu with
     * @return Menu instance
     */
    private Menu bindMenuFrom(final Map<String, AttributeValue> data) {

        final Menu                  menu    = new Menu();
        final List<AttributeValue>  meals   = getListFrom(data, "meals");
        final List<String>          result  = new ArrayList<>();

        menu.setId(getStringFrom(data, "id"));

        for(final AttributeValue attributeValue: meals)
            result.add(attributeValue.s());

        menu.setMealIds(result);

        return menu;
    }

    /// --------------------------
    /// MealContract.Menu.Database

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

    }

    /**
     * Creates a Menu with the given data on the current database table.
     * @param data The data to create the Menu entry with
     */
    @Override
    public void updateMenu(final Map<String, Object> data) {

        // Retrieve a handle to ourselves
        final MenuDatabase  database = this;

        // Define the thread
        final Thread thread = new Thread(() ->
                database.putItem(createItemFrom(data)));

        try {

            // Create it
            thread.start();
            thread.join();

        } catch (final Exception exception) {

            Log.e("MenuDatabase", exception.getMessage());

        }

    }

    /**
     * Retrieves the menu corresponding with the given id, if any
     * @param id the menu id
     * @return List of meals
     */
    @Override
    public MealContract.Menu getMenu(final String id) {

        return bindMenuFrom(getItem("id", id));

    }

}
