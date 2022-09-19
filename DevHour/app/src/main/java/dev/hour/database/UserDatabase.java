package dev.hour.database;

import dev.hour.contracts.UserContract;

public class UserDatabase implements UserContract.Database{
    @Override
    public UserContract.User getUser() {
        return null;
    }

    @Override
    public void updateUser(UserContract.User user) {

    }
}
