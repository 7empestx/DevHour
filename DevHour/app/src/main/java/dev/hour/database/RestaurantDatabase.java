package dev.hour.database;

import java.util.List;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import dev.hour.contracts.RestaurantContract;
import dev.hour.restaurant.Restaurant;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.regions.Region;

public class RestaurantDatabase implements RestaurantContract.Database {

    /// ---------------
    /// Private Members

    private DynamoDbAsyncClient client      ;
    private String              tableName   ;
    private String              region      ;

    /// ------------
    /// Constructing

    public RestaurantDatabase(final String region, final String tableName){

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
     * Retrieves the restaurant corresponding with the given id.
     * @param id the restaurant id
     * @return RestaurantContract.Restaurant instance
     */
    @Override
    public RestaurantContract.Restaurant getRestaurant(final String id) {

        final RestaurantContract.Restaurant restaurant;

        if(client != null){

            final Map<String, AttributeValue> restaurantBlob = getItem("id", id);
            final String name = Objects.requireNonNull(restaurantBlob.get("name")).s();

            restaurant =  new Restaurant(id, name);

            restaurant.setLongitude(
                    String.valueOf(Double.parseDouble(
                            Objects.requireNonNull(restaurantBlob.get("longitude")).s())));
            restaurant.setLatitude(
                    String.valueOf(Double.parseDouble(
                            Objects.requireNonNull(restaurantBlob.get("latitude")).s())));
            restaurant.setPricing(
                    String.valueOf(Integer.parseInt(
                            Objects.requireNonNull(restaurantBlob.get("pricing")).s())));

        } else restaurant = null;

        return restaurant;
    }

    /**
     * Returns a list of RestaurantContract.Restaurants that are within the specified radius.
     * @param longitude The center longitude
     * @param latitude The center latitude
     * @param radius The radius
     * @return List of Restaurants.
     */
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
