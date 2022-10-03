package dev.hour.contracts;

import java.util.List;
import java.util.Map;

public interface UserContract {

    interface Database {

        User getUser();
        void updateUser(final User user);

    }

    interface Presenter {

        void setDatabase(final Database database);
        void setView(final View view);
        void invalidate();
        void setUserLocation(final double longitude, final double latitude);

    }

    interface View {

        void setUser(final User user);

    }

    interface  User {

        String getFirstName();
        String getLastName();
        double getLongitude();
        double getLatitude();

    }

}
