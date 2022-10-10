package dev.hour.database;

import java.util.List;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import dev.hour.contracts.RestaurantContract;
import dev.hour.restaurant.Restaurant;

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.regions.Region;


public class RestaurantDatabase implements RestaurantContract.Database {
    private DynamoDbAsyncClient client;
    private String tableName;

    public RestaurantDatabase(final String regionName, final String tableName){

        // Create client
        client = DynamoDbAsyncClient
                .builder()
                .region(Region.of(regionName))
                .build();

        // Set table name
        this.tableName = tableName;
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

        final Collection<AttributeValue> response = this.client.getItem(request).join().item().values();

        return response.stream().collect(Collectors.toMap(AttributeValue::s, s->s));

    }

    @Override
    public RestaurantContract.Restaurant getRestaurant(final String id) {

        final RestaurantContract.Restaurant restaurant;

        if(client != null){

            final Map<String, AttributeValue> restaurantBlob = getItem("id", id);

            restaurant =  new Restaurant();

            restaurant.setName(Objects.requireNonNull(restaurantBlob.get("name")).s());
            restaurant.setLongitude(Double.parseDouble(Objects.requireNonNull(restaurantBlob.get("longitude")).s()));
            restaurant.setLatitude(Double.parseDouble(Objects.requireNonNull(restaurantBlob.get("latitude")).s()));
            restaurant.setPricing(Integer.parseInt(Objects.requireNonNull(restaurantBlob.get("pricing")).s()));
        } else restaurant = null;

        return restaurant;
    }

    @Override
    public List<RestaurantContract.Restaurant> getRestaurantsFromRadiusLocation(final double longitude,
                                                                                final double latitude,
                                                                                final double radius) {
        //mercator projection
        //use scan function to query tables
        //TODO: implement query to find restaurants based off radius
        return null;
    }

}
