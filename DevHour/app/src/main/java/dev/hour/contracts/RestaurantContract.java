package dev.hour.contracts;

public interface RestaurantContract {
    public interface Database {
    Meal getMeal(); //retrieves from database 
    }
    public interface Presenter {
        void setDatabase(Database d);
        void setView(View v);
    }
    public interface View {

    }
    public interface Meal {
        Integer Calories();
    }
}
