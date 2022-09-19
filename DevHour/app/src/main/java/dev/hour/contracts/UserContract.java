package dev.hour.contracts;

public interface UserContract {
    public interface Database {
        String getUserFirst();
        String getGetUserLast();
    }
    public interface Presenter {
        void setDatabase(Database d);
        void setView(View v);
    }
    public interface View {

    }
    public  interface  User {
        String getName();
        double getLongitude();
        double getLatitude();
    }
    public interface Meal {
        int getCalories();
    }

}
