package dev.hour.database;

import java.util.List;

import dev.hour.contracts.RestaurantContract;

public class RestaurantDatabase implements RestaurantContract.Database {

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
