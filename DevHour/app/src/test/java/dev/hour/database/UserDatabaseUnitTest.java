package dev.hour.database;

import org.junit.Assert;
import org.junit.Test;

import dev.hour.contracts.UserContract;

public class UserDatabaseUnitTest {


    @Test
    void UserDatabase_returnsUser() {

        final UserContract.Database userDatabase = new UserDatabase();
        final UserContract.User user;
        final UserDatabase userDatabase1 = new UserDatabase();
        user = userDatabase.getUser();

        Assert.assertNotNull(user);

    }


}
