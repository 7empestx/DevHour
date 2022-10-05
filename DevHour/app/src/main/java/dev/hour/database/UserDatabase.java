package dev.hour.database;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import dev.hour.contracts.UserContract;
import dev.hour.user.User;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.regions.Region;

public class UserDatabase implements UserContract.Database {

    private DynamoDbClient client       ;
    private String         tableName    ;

    /// -----------
    /// Constructor

    public UserDatabase(final String regionName, final String tableName) {

        // Create the client
        client = DynamoDbClient
                .builder()
                .region(Region.of(regionName))
                .build();



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

        keyMap.put(key, AttributeValue.builder().s(value).build());

        final GetItemRequest request = GetItemRequest.builder()
                .key(keyMap)
                .tableName(tableName)
                .build();

        final Collection<AttributeValue> response = this.client.getItem(request).item().values();

        return response.stream().collect(Collectors.toMap(AttributeValue::s, s->s));

    }

    @Override
    public UserContract.User getUser(final String id) {

        final UserContract.User user;

        if(client != null) {

            final Map<String, AttributeValue> userBlob = getItem("id", id);

            user = new User();

            user.setFirstName(Objects.requireNonNull(userBlob.get("first")).s());
            user.setLastName(Objects.requireNonNull(userBlob.get("last")).s());
            user.setLongitude(Objects.requireNonNull(userBlob.get("longitude")).s());
            user.setLatitude(Objects.requireNonNull(userBlob.get("latitude")).s());
            

        } else user = null;

        return user;

    }

    @Override
    public void updateUser(final UserContract.User user) {

    }

}
