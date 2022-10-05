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
    public void setName(String name){
        this.name = name;
    }

    @Override
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    @Override
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    @Override
    public void setPricing(int pricing){
        this.pricing = pricing;
    }

}