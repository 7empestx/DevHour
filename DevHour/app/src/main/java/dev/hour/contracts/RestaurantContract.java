package dev.hour.contracts;

public interface RestaurantContract {
    
    public interface Database {

        Restaurant getRestaurant(String id);
        Meal getMeal(String id); //retrieves from database

    }

    public interface Presenter {

        void setDatabase(Database d);
        void setView(View v);

    }

    public interface View {

    }

    public interface Restaurant {

        String getName();
        double getLongitude();
        double getLatitude();

    }
    public interface Meal {

        int getCalories();

    }

}
