package dev.hour.database;

import android.util.Log;

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
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;

public class RestaurantDatabase implements RestaurantContract.Database {

    /// ---------------
    /// Private Members

    private DynamoDbClient              client      ;
    private String                      tableName   ;
    private String                      region      ;
    private SdkHttpClient               httpClient  ;
    private Map<String, AttributeValue> response    ;

    /// ------------
    /// Constructing

    public RestaurantDatabase(final String region, final String tableName,
                              final SdkHttpClient httpClient){

        this.client     = null          ;
        this.tableName  = tableName     ;
        this.region     = region        ;
        this.httpClient = httpClient    ;

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
        Map<String, AttributeValue> result;

        if(client != null) {

            keyMap.put(key, AttributeValue.builder().s(value).build());

            final GetItemRequest request = GetItemRequest.builder()
                    .key(keyMap)
                    .tableName(tableName)
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
     * Sets the credentials required to build the DynamoDB client
     * @param credentials The credentials to set.
     */
    @Override
    public void setCredentials(final Map<String, String> credentials) {

        client = DynamoDbClient
                .builder()
                .region(Region.of(this.region))
                .httpClient(this.httpClient)
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
