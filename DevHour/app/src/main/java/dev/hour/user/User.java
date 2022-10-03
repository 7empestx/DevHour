package dev.hour.user;

import dev.hour.contracts.UserContract;

public class User implements UserContract.User {

    /// --------------
    /// Private Fields

    private String firstName    ;
    private String lastName     ;
    private double longitude    ;
    private double latitude     ;

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public void setFirstName(final String firstName) {

        this.firstName = firstName;

    }

    @Override
    public void setLastName(final String lastName) {

        this.lastName = lastName;

    }

    @Override
    public void setLongitude(final double longitude) {

        this.longitude = longitude;

    }

    @Override
    public void setLatitude(double latitude) {

        this.latitude = latitude;

    }

}
