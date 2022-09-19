package dev.hour.contracts;

import java.util.List;

public interface RestaurantContract {
    
    interface Database {

        Restaurant getRestaurant(String id);
        List<Restaurant> getRestaurantsFromRadiusLocation(double longitude,
                                                          double latitude,
                                                          double radius);

    }

    interface Presenter {

        void setDatabase(Database d);
        void setView(View v);
        void invalidate();

    }

    interface View {

        void setRestaurants(List <Restaurant> restaurants);

    }

    interface Restaurant {

        String getName();
        double getLongitude();
        double getLatitude();
        int getPricing();
        List<MealContract.Meal> getMeals();
        MealContract.Meal getMeal(String id);

    }

}
