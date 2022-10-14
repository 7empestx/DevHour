package dev.hour.database;

import org.junit.Assert;
import org.junit.Test;

import dev.hour.contracts.UserContract;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;

public class UserDatabaseUnitTest {

    final String region     = "us-west-1"       ;
    final String tableName  = "user-test-table" ;

    @Test
    void UserDatabase_returnsUser() {

        final UserContract.Database userDatabase = new UserDatabase(region, tableName, UrlConnectionHttpClient.create());
        final UserContract.User user;

        final String id = "123456";

        user = userDatabase.getUser(id);

        Assert.assertNotNull(user);

    }


}
