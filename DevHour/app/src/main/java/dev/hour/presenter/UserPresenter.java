package dev.hour.presenter;

import dev.hour.contracts.UserContract;

public class UserPresenter implements UserContract.Presenter, UserContract.View.Listener {

    /// ---------------
    /// Private Members

    private UserContract.Database   database    ;
    private UserContract.User       user        ;
    private UserContract.View       view        ;
    @Override
    public void setDatabase(UserContract.Database database) {

        this.database = database;

    }

    @Override
    public void setView(UserContract.View view) {
        this.view = view;
    }

    @Override
    public void invalidate() {
        if(view!=null){
            view.update(user);
        }
    }

    @Override
    public void setUserLocation(double longitude, double latitude) {

        if(this.user != null) {

            this.user.setLatitude(latitude);
            this.user.setLongitude(longitude);

        }

        if(this.view != null)
            this.view.update(this.user);
    }

    @Override
    public void setUser(final String userId) {
        this.user = database.getUser(userId);
    }

    @Override
    public UserContract.User getUser(final String userId) {

        if((this.user == null) || (!this.user.getId().equals(userId))) {

            this.user = this.database.getUser(userId);

        }

        return this.user;

    }

    @Override
    public void onGetUserRequest() {
        view.onDisplayUserInfo(this.user);
    }
}
