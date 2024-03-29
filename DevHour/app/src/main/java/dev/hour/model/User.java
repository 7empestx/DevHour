package dev.hour.model;

import dev.hour.contracts.MapObjectContract;
import dev.hour.contracts.UserContract;

public class User implements UserContract.User, MapObjectContract.MapObject {

    /// --------------
    /// Private Fields

    private String id           ;
    private String firstName    ;
    private String lastName     ;
    private String type         ;
    private double longitude    ;
    private double latitude     ;

    public User(final String id, final String firstName, final String lastName) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;

    }

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
    public String getType() {

        return this.type;

    }

    @Override
    public String getId() {

        return this.id;

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
    public void setType(final String type) {

        this.type = type;

    }

    @Override
    public void setLatitude(final double latitude) {

        this.latitude = latitude;

    }

}
