package dev.hour.restaurant;

import dev.hour.contracts.RestaurantContract;

public class Restaurant implements RestaurantContract.Restaurant{

    private String name;
    private double longitude;
    private double latitude;
    private int pricing;

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