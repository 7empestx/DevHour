package dev.hour.contracts;

import java.util.Map;

public interface UserContract {

    interface Database {

        void setCredentials(final Map<String, String> credentials);
        User getUser(final String id);
        void updateUser(final User user);

    }

    interface Presenter {

        void setDatabase(final Database database);
        void setView(final View view);
        void invalidate();
        void setUserLocation(final double longitude, final double latitude);

    }

    interface View {

        void update(final User user);
        void remove(final User user);
        void clearUsers();

    }

    interface  User {

        String getId();
        String getFirstName();
        String getLastName();
        double getLongitude();
        double getLatitude();

        void setFirstName(final String firstName);
        void setLastName(final String lastName);
        void setLongitude(final double longitude);
        void setLatitude(double latitude);

    }

}
