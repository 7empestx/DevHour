package dev.hour.contracts;

import java.util.List;

public interface RestaurantContract {
    
    interface Database {

        Restaurant getRestaurant(String id);
        List<Restaurant> getRestaurantsFromRadiusLocation(final double longitude,
                                                          final double latitude,
                                                          final double radius);

    }

    interface Presenter {

        void setDatabase(final Database database);
        void setView(final View view);
        void invalidate();

    }

    interface View {

        void setRestaurants(final List <Restaurant> restaurants);

    }

    interface Restaurant {

        String getName();
        double getLongitude();
        double getLatitude();
        int getPricing();

        String setName();
        double setLongitude();
        double setLatitude();
        int setPricing();

    }

}
