package dev.hour.contracts;

import java.util.List;

public interface RestaurantContract {
    
    public interface Database {

        Restaurant getRestaurant(String id);
        Meal getMeal(String id); //retrieves from database

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
        List getMeals();

        }
    public interface Meal {

        int getCalories();
        String getName();
        List getIngredients();

    }

    public interface Ingredient{
        int getCalories();
        String getName();
        
    }

}
