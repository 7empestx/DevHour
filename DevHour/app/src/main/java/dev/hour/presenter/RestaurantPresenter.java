package dev.hour.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.hour.contracts.RestaurantContract;

public class RestaurantPresenter implements RestaurantContract.Presenter {

    private RestaurantContract.Database         database    ;
    private RestaurantContract.View             view        ;
    private List<RestaurantContract.Restaurant> restaurants ;

    @Override
    public void setDatabase(RestaurantContract.Database database) {
        this.database = database;
    }

    @Override
    public void setView(RestaurantContract.View view) {

        // Set the view
        this.view = view;

        if(this.view != null)
            this.view.setRestaurants(this.restaurants);

    }

    @Override
    public void setRestaurantsByTag(final String query) {
        if (this.database != null){
            List<RestaurantContract.Restaurant> restaurants= this.database.retrieveRestaurantsByTag(query);
            if (this.view != null){
                this.view.setRestaurants(restaurants);
            }
        }
    }

    @Override
    public void createRestaurant(final Map<String, Object> data, final String ownerId) {

        if(this.database != null)
            this.database.createRestaurant(data, ownerId);

    }

    @Override
    public void setRestaurantsByOwner(final String ownerId) {

        // Retrieve the restaurants by owner id
        this.restaurants = (this.database != null) ?
                this.database.retrieveRestaurantsByOwner(ownerId) : new ArrayList<>();

        // Set the restaurants if possible
        if(this.view != null)
            this.view.setRestaurants(this.restaurants);

    }

}
