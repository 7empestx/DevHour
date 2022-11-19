package dev.hour.restaurant;

import java.io.OutputStream;

import dev.hour.contracts.MapObjectContract;
import dev.hour.contracts.RestaurantContract;

public class Restaurant implements RestaurantContract.Restaurant, MapObjectContract.MapObject {

    /// ---------------
    /// Private Members

    private String          id          ;
    private String          name        ;
    private int             pricing     ;
    private String          address1    ;
    private String          address2    ;
    private String          menuId      ;
    private double          longitude   ;
    private double          latitude    ;
    private OutputStream    imageStream ;

    /// -----------
    /// Constructor

    public Restaurant(final String id, final String name) {

        this.id     = id    ;
        this.name   = name  ;

    }

    public Restaurant() { /* Empty */ }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public int getPricing(){
        return pricing;
    }

    @Override
    public String getMenuId() {

        return this.menuId;

    }

    @Override
    public String getAddress1() {

        return this.address1;

    }

    @Override
    public String getAddress2() {

        return this.address2;

    }
    
    @Override
    public double getLongitude(){
        return longitude;
    }

    @Override
    public double getLatitude(){
        return latitude;
    }

    @Override
    public OutputStream getImageStream() {

       return this.imageStream;

    }

    @Override
    public void setId(final String id) {

        this.id = id;

    }
    
    @Override
    public void setName(final String name){

        this.name = name;

    }

    @Override
    public void setPricing(final int pricing) {

        this.pricing = pricing;

    }

    @Override
    public void setMenuId(final String menuId) {

        this.menuId = menuId;

    }

    @Override
    public void setAddress1(final String address1) {

        this.address1 = address1;

    }

    @Override
    public void setAddress2(final String address2) {

        this.address2 = address2;

    }
    
    @Override
    public void setLongitude(final double longitude) {

        this.longitude = longitude;

    }

    @Override
    public void setLatitude(final double latitude) {

        this.latitude = latitude;

    }

    @Override
    public void setImageStream(final OutputStream imageStream) {

        this.imageStream = imageStream;

    }

}