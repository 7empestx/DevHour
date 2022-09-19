package dev.hour.contracts;

import java.util.List;

public interface UserContract {

    interface Database {

        User getUser();
        void updateUser(User user);

    }

    interface Presenter {

        void setDatabase(Database d);
        void setView(View v);
        void invalidate();
        void setUserLocation(double longitude, double latitude);

    }

    interface View {

        void setUser(User user);

    }

    interface  User {

        String getFirstName();
        String getLastName();
        double getLongitude();
        double getLatitude();
        List<MealContract.Diet> getDiet();
        List<MealContract.Ingredient> getAllergens();

    }

}
