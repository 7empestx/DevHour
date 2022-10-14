package dev.hour.restaurant;

import dev.hour.contracts.MapObjectContract;
import dev.hour.contracts.RestaurantContract;

public class Restaurant implements RestaurantContract.Restaurant, MapObjectContract.MapObject {

    /// ---------------
    /// Private Members

    private String  id          ;
    private String  name        ;
    private double  longitude   ;
    private double  latitude    ;
    private int     pricing     ;

    /// -----------
    /// Constructor

    public Restaurant(final String id, final String name) {

        this.id     = id    ;
        this.name   = name  ;

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName(){
        return name;
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
    public int getPricing(){
        return pricing;
    }

    @Override
    public String setName(String name){
        this.name = name;
        return name;
    }

    @Override
    public double setLongitude(String longitude) {
        return 0;
    }

    @Override
    public double setLatitude(String latitude) {
        return 0;
    }

    @Override
    public int setPricing(String pricing) {
        return 0;
    }

}