package dev.hour.contracts;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface RestaurantContract {
    
    interface Database {

        void setCredentials(final Map<String, String> credentials);
        void updateRestaurant(final Map<String, Object> data, String ownerId);
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
        void updateRestaurant(final Map<String, Object> data, final String ownerId);
        void setRestaurantsByTag(final String query);
        void setRestaurantsByOwner(final String owner);

        interface InteractionListener {

            void onUpdateRestaurantRequest(final Restaurant restaurant);
            void onShowBusinessRestaurantListRequest();
            void onCreateRestaurantRequest(final Map<String, Object> data);
            void onShowBusinessAddRestaurantImageRequest(final Map<String, Object> export);
            void onShowBusinessAddRestaurantTagRequest(final Map<String, Object> export);
            void onRestaurantSelected(final Restaurant restaurant);

        }

    }

    interface View {

        void setRestaurants(final List<Restaurant> restaurants);

    }

    interface Restaurant {

        String getId();
        String getName();
        String getOwnerId();
        String getPictureId();
        double getLongitude();
        double getLatitude();
        int getPricing();
        String getMenuId();
        String getAddress1();
        String getAddress2();
        OutputStream getImageStream();

        void setId(final String id);
        void setName(final String name);
        void setPricing(final int pricing);
        void setOwnerId(final String id);
        void setPictureId(final String id);
        void setMenuId(final String menuId);
        void setAddress1(final String address1);
        void setAddress2(final String address2);
        void setLongitude(final double longitude);
        void setLatitude(final double latitude);
        void setImageStream(final OutputStream imageStream);

    }

}
