package dev.hour.contracts;

import java.util.Map;

public interface UserContract {

    interface Database {

        void setCredentials(final Map<String, String> credentials);
        User getUser(final String id);
        void updateUser(final Map<String, String> data);

    }

    interface Presenter {

        void setDatabase(final Database database);
        void setView(final View view);
        void invalidate();
        void setUserLocation(final double longitude, final double latitude);
        void setUser(final String userId);
        UserContract.User getUser(final String userId);

    }

    interface View {

        void update(final User user);
        void remove(final User user);
        void clearUsers();
        void onDisplayUserInfo(final User user);
        void setUserListener(UserContract.View.Listener listener);


        interface Listener {

            void onGetUserRequest();

        }

    }

    interface  User {

        String getId();
        String getFirstName();
        String getLastName();
        String getType();
        double getLongitude();
        double getLatitude();

        void setFirstName(final String firstName);
        void setLastName(final String lastName);
        void setType(final String type);
        void setLongitude(final double longitude);
        void setLatitude(final double latitude);

    }

}
