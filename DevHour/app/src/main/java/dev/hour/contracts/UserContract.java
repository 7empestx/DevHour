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
}
