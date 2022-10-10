package dev.hour.database;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import dev.hour.contracts.UserContract;
import dev.hour.user.User;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.regions.Region;

public class UserDatabase implements UserContract.Database {

    /// ---------------
    /// Private Members

    private DynamoDbAsyncClient client      ;
    private String              tableName   ;
    private String              region      ;

    /// -----------
    /// Constructor

    public UserDatabase(final String region, final String tableName) {

        this.client     = null      ;
        this.tableName  = tableName ;
        this.region     = region    ;

    }

    /// ---------------
    /// Private Methods

    /**
     * Creates a GetItemRequest to get the item  with the specified key, value pair.
     * @param key The value of the key
     * @param value The specific value to match
     * @return Map containing the requested data, if any
     */
    private Map<String, AttributeValue> getItem(final String key, final String value) {

        final Map<String, AttributeValue> keyMap = new HashMap<>();
        final Map<String, AttributeValue> result;

        if(client != null) {

            keyMap.put(key, AttributeValue.builder().s(value).build());

            final GetItemRequest request = GetItemRequest.builder()
                    .key(keyMap)
                    .tableName(tableName)
                    .build();

            final Collection<AttributeValue> response = this.client.getItem(request).join().item().values();

            result = response.stream().collect(Collectors.toMap(AttributeValue::s, s->s));

        } else result = new HashMap<>();

        return result;

    }

    /**
     * Sets the credentials required to build the DynamoDB client
     * @param credentials The credentials to set.
     */
    @Override
    public void setCredentials(final Map<String, String> credentials) {

        client = DynamoDbAsyncClient
                .builder()
                .region(Region.of(this.region))
                .credentialsProvider(() -> AwsBasicCredentials.create(
                        credentials.get("ACCESS_KEY"),
                        credentials.get("SECRET_KEY")))
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

            user.setLongitude(Double.parseDouble(Objects.requireNonNull(userBlob.get("longitude")).s()));
            user.setLatitude(Double.parseDouble(Objects.requireNonNull(userBlob.get("latitude")).s()));
            

        } else user = null;

        return user;

    }

    @Override
    public void updateUser(final UserContract.User user) {

    }

}
