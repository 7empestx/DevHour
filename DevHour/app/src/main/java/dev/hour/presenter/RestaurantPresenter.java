package dev.hour.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.hour.contracts.RestaurantContract;

public class RestaurantPresenter implements RestaurantContract.Presenter {

    /// --------------
    /// Private Fields

    private RestaurantContract.Database         database    ;
    private RestaurantContract.View             view        ;
    private List<RestaurantContract.Restaurant> restaurants ;

    /// ----------------------------
    /// RestaurantContract.Presenter

    /**
     * Sets the [RestaurantContract.Database] instance
     * @param database The [RestaurantContract.Database] instance
     */
    @Override
    public void setDatabase(RestaurantContract.Database database) {

        this.database = database;

    }

    /**
     * Sets the [RestaurantContract.View] instance.
     * @param view The [RestaurantContract.View] instance.
     */
    @Override
    public void setView(RestaurantContract.View view) {

        // Set the view
        this.view = view;

        if(this.view != null)
            this.view.setRestaurants(this.restaurants);

    }

    /**
     * Invokes the [RestaurantContract.Database] to create a restaurant with the given data.
     * @param data The Restautant data.
     * @param ownerId The Restaurant owner id
     */
    @Override
    public void updateRestaurant(final Map<String, Object> data, final String ownerId) {

        if(this.database != null)
            this.database.updateRestaurant(data, ownerId);

    }

    /**
     * Sets the Restaurant list by tag
     * @param tag The [String] tag to set the Restaurant lists from
     */
    @Override
    public void setRestaurantsByTag(final String tag) {

        if (this.database != null){

            final List<RestaurantContract.Restaurant> restaurants =
                    this.database.retrieveRestaurantsByTag(tag);

            if (this.view != null)
                this.view.setRestaurants(restaurants);

        }

    }

    /**
     * Sets the Restaurant list by owner
     * @param ownerId The owner id to set the Restaurant lists
     */
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
