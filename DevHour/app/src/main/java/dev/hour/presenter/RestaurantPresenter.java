package dev.hour.presenter;

import java.util.List;

import dev.hour.contracts.RestaurantContract;

public class RestaurantPresenter implements RestaurantContract.Presenter {

    //Private Members
    private RestaurantContract.Database database;
    private RestaurantContract.View view;
    private List<RestaurantContract.Restaurant> resturants; //this is missing in the UML..?

    @Override
    public void setDatabase(RestaurantContract.Database database) {
        this.database = database;
    }

    @Override
    public void setView(RestaurantContract.View view) {

    }

    @Override
    public void invalidate() {
        if(view != null) {
            view.setRestaurants(resturants);
        }
    }
}
