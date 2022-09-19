package dev.hour.contracts;

import java.util.List;

public interface RestaurantContract {
    
    public interface Database {

        Restaurant getRestaurant(String id);

    }

    public interface Presenter {

        void setDatabase(Database d);
        void setView(View v);

        void invalidate();
    }

    public interface View {

        void setRestaurants(List <Restaurant> restaurants);
    }

    public interface Restaurant {

        String getName();
        double getLongitude();
        double getLatitude();
        int getPricing();
        List<MealContract.Meal> getMeals();
        MealContract.Meal getMeal(String id);
    }


}
