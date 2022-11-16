package dev.hour.database;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import dev.hour.contracts.UserContract;
import dev.hour.user.User;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

public class UserDatabase implements UserContract.Database {

    /// ---------------
    /// Private Members

    private DynamoDbClient              client      ;
    private String                      tableName   ;
    private String                      region      ;
    private Map<String, AttributeValue> response    ;
    private SdkHttpClient               httpClient  ;

    /// -----------
    /// Constructor

    public UserDatabase(final String region, final String tableName, final SdkHttpClient httpClient) {

        this.client     = null       ;
        this.tableName  = tableName  ;
        this.region     = region     ;
        this.httpClient = httpClient ;

    }

    /// ---------------
    /// Private Methods

    /**
     * Creates an item that can update a DynamoDB table item.
     * @param data The item to set
     * @return Map<String, AttributeValue> with the given data.
     */
    private Map<String, AttributeValue> createItemFrom(final Map<String, String> data) {

        final Map<String, AttributeValue> result;

        if(data != null) {

            result = new HashMap<>();

            for(Map.Entry<String, String> entry: data.entrySet()) {

                final AttributeValue attributeValue = AttributeValue.builder().s(entry.getValue()).build();

                result.put(entry.getKey(), attributeValue);

            }


        } else result = new HashMap<>();

        return result;

    }

    /**
     * Creates a GetItemRequest to get the item  with the specified key, value pair.
     * @param key The value of the key
     * @param value The specific value to match
     * @return Map containing the requested data, if any
     */
    private Map<String, AttributeValue> getItem(final String key, final String value) {

        final Map<String, AttributeValue> keyMap = new HashMap<>();
        Map<String, AttributeValue> result;

        if(client != null) {

            keyMap.put(key, AttributeValue.builder().s(value).build());

            final GetItemRequest request = GetItemRequest.builder()
                    .key(keyMap)
                    .tableName(tableName)
                    .build();

            final UserDatabase database = this;

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
     * Sets the given item with the given data
     * @param item The item to write.
     */
    private void putItem(final Map<String, AttributeValue> item) {

        if(this.client != null) {

            final PutItemRequest request =
                    PutItemRequest.builder().item(item).tableName(this.tableName).build();

            this.client.putItem(request);

        }

    }

    /**
     * Sets the credentials required to build the DynamoDB client
     * @param credentials The credentials to set.
     */
    @Override
    public void setCredentials(final Map<String, String> credentials) {

        client = DynamoDbClient
                .builder()
                .region(Region.of(this.region))
                .httpClient(this.httpClient)
                .credentialsProvider(() -> AwsSessionCredentials.create(
                        credentials.get("ACCESS_KEY"),
                        credentials.get("SECRET_KEY"),
                        credentials.get("SESSION_TOKEN")))
                .build();

    }

    /**
     * Retrieves the user corresponding with the given id.
     * @param id the user id
     * @return RestaurantContract.Restaurant instance
     */
    @Override
    public UserContract.User getUser(final String id) {

        final UserContract.User user;

        if(client != null) {

            final Map<String, AttributeValue> userBlob = getItem("id", id);

            final String firstName = Objects.requireNonNull(userBlob.get("first")).s();
            final String lastName  = Objects.requireNonNull(userBlob.get("last")).s();

            user = new User(id, firstName, lastName);

            user.setFirstName(Objects.requireNonNull(userBlob.get("first")).s());
            user.setLastName(Objects.requireNonNull(userBlob.get("last")).s());
            user.setType(Objects.requireNonNull(userBlob.get("type")).s());
            user.setLongitude(Double.parseDouble(Objects.requireNonNull(userBlob.get("longitude")).n()));
            user.setLatitude(Double.parseDouble(Objects.requireNonNull(userBlob.get("latitude")).n()));
        } else user = null;

        return user;

    }

    @Override
    public void updateUser(final Map<String, String> data) {

        final UserDatabase database = this;

        final Thread thread = new Thread(() -> database.putItem(createItemFrom(data)));

        try {

            thread.start();
            thread.join();

        } catch (final Exception exception) {

            Log.e("UserDatabase", exception.getMessage());

        }

    }

}
