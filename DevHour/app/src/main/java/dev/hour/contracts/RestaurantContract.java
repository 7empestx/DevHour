package dev.hour.contracts;

import java.util.List;
import java.util.Map;

public interface RestaurantContract {
    
    interface Database {

        void setCredentials(final Map<String, String> credentials);
        Restaurant getRestaurant(String id);
        List<Restaurant> getRestaurantsFromRadiusLocation(final double longitude,
                                                          final double latitude,
                                                          final double radius);

        List<Restaurant> search(String query);
    }

    interface Presenter {

        void setDatabase(final Database database);
        void setView(final View view);
        void invalidate();
        void search(String query);

    }

    interface View {

        void setRestaurants(final List <Restaurant> restaurants);

    }

    interface Restaurant {

        String getId();
        String getName();
        double getLongitude();
        double getLatitude();
        int getPricing();

        String setName(String name);
        double setLongitude(String longitude);
        double setLatitude(String latitude);
        int setPricing(String pricing);

    }

}
