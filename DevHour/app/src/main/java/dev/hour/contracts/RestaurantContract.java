package dev.hour.contracts;

import java.util.List;
import java.util.Map;

public interface RestaurantContract {
    
    interface Database {

        void setCredentials(final Map<String, String> credentials);
        void createRestaurant(final Map<String, Object> data, String ownerId);
        List<Restaurant> retrieveRestaurantsByTag(final String tag);
        List<Restaurant> retrieveRestaurantsByOwner(final String ownerId);
        Restaurant getRestaurant(String id);
        List<Restaurant> retrieveRestaurantsByLocation(final double longitude,
                                                       final double latitude,
                                                       final double radius);



    }

    interface Presenter {

        void setDatabase(final Database database);
        void setView(final View view);
        void createRestaurant(final Map<String, Object> data, final String ownerId);
        void setRestaurantsByTag(final String query);
        void setRestaurantsByOwner(final String owner);

        interface InteractionListener {

            void onAddRestaurantRequest();
            void onCreateRestaurantRequest(final Map<String, Object> data);
            void onShowBusinessRestaurantListRequest();
            void onShowBusinessAddRestaurantImageRequest();
            void onShowBusinessAddRestaurantTagRequest(final Map<String, String> tags);

        }

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
        String getAddress1();
        String getAddress2();

        void setId(final String id);
        void setName(final String name);
        void setPricing(final int pricing);
        void setAddress1(final String address1);
        void setAddress2(final String address2);
        void setLongitude(final double longitude);
        void setLatitude(final double latitude);

    }

}
