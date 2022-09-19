package dev.hour.database;

import java.util.List;

import dev.hour.contracts.RestaurantContract;

public class RestaurantDatabase implements RestaurantContract.Database {


    @Override
    public RestaurantContract.Restaurant getRestaurant(String id) {
        return null;
    }

    @Override
    public List<RestaurantContract.Restaurant> getRestaurantsFromRadiusLocation(double longitude, double latitude, double radius) {
        return null;
    }
}
