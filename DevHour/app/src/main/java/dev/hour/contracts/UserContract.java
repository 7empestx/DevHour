package dev.hour.contracts;

import java.util.List;

public interface UserContract {
    public interface Database {
        User getUser();
    }
    public interface Presenter {
        void setDatabase(Database d);
        void setView(View v);
    }
    public interface View {

    }
    public  interface  User {
        String getFirstName();
        String getLastName();
        double getLongitude();
        double getLatitude();
    }

}
