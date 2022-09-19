package dev.hour.contracts;

public interface RestaurantContract {
    public interface Database {

    }
    public interface Presenter {
        void setDatabase(Database d);
        void setView(View v);
    }
    public interface View {

    }
}
