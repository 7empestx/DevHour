package dev.hour.database;

import java.util.List;

import dev.hour.contracts.RestaurantContract;

public class RestaurantDatabase implements RestaurantContract.Database {

    private DynamoDbClient client;
    private String tableName;

    public RestaurantDatabase(final String regionName, final String tableName){

        // Create client
        client = DynamoDbClient
                .builder()
                .region(Region.of(regionName))
                .build();

        // Set table name
        this.tableName = tableName;
    }

    @Override
    public RestaurantContract.Restaurant getRestaurant(final String id) {
        return null;
    }

    @Override
    public List<RestaurantContract.Restaurant> getRestaurantsFromRadiusLocation(final double longitude,
                                                                                final double latitude,
                                                                                final double radius) {
        return null;
    }

}
