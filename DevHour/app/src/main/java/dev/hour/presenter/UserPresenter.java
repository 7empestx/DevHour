package dev.hour.presenter;

import dev.hour.contracts.UserContract;

public class UserPresenter implements UserContract.Presenter {

    /// ---------------
    /// Private Members

    private UserContract.Database database;

    @Override
    public void setDatabase(UserContract.Database database) {

        this.database = database;

    }

    @Override
    public void setView(UserContract.View view) {

    }

    @Override
    public void invalidate() {

    }

    @Override
    public void setUserLocation(double longitude, double latitude) {

    }

}
