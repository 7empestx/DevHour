package dev.hour.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.List;

import dev.hour.contracts.RestaurantContract;
import dev.hour.contracts.UserContract;
import dev.hour.view.MapView;

public class MapFragment extends Fragment implements
        UserContract.View,
        RestaurantContract.View {

    /// --------------
    /// Static Members

    public final static String TAG = "MapFragment";

    /// ---------------
    /// Private Members

    private MapView mapView;
    private UserContract.View.Listener listener;
    private MapView.SearchListener searchListener;

    /// ------------------
    /// Fragment Lifecycle

    /**
     * Invoked when the MapFragment's View is about to be created.
     * @param layoutInflater The layout inflator responsible for creating the view
     * @param container The fragment's view container
     * @param savedInstanceState The saved instance state, if any
     * @return Inflated MapView
     */
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        mapView = new MapView(getContext());
        this.mapView.setSearchListener(searchListener);

        return mapView;

    }

    /**
     * Invoked when the MapFragment has started
     */
    @Override
    public void onStart() {
        super.onStart();

        if(mapView != null)
            mapView.onStart();

    }

    /**
     * Invoked when the MapFragment is stopping
     */
    @Override
    public void onStop() {
        super.onStop();

        if(mapView != null)
            mapView.onStop();

    }

    /**
     * Invoked when the MapFragment is low on memory
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if(mapView != null)
            mapView.onLowMemory();

    }

    /**
     * Invoked when the MapFragment is about to be destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mapView != null)
            mapView.onDestroy();

    }

    /**
     * Invoked when the UserContract.View should update the specified user
     * @param user The UserContract.User to update
     */
    @Override
    public void update(UserContract.User user) {

        if(this.mapView != null)
            this.mapView.remove(user);

    }

    /**
     * Invoked when the UserContract.View should remove the specified user
     * @param user The UserContract.User to remove
     */
    @Override
    public void remove(final UserContract.User user) {

        if(this.mapView != null)
            this.mapView.remove(user);

    }

    /**
     * Invoked when the UserContract.View should clear all users
     */
    @Override
    public void clearUsers() {

        if(this.mapView != null)
            this.mapView.clearUsers();

    }

    @Override
    public void onDisplayUserInfo(UserContract.User user) {

    }

    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Invoked when the RestaurantContract.Restaurant should update the
     * given Restaurants
     * @param restaurants The list of Restaurants to update.
     */
    @Override
    public void setRestaurants(List<RestaurantContract.Restaurant> restaurants) {

    }

    public void setSearchListener(MapView.SearchListener searchListener){
        this.searchListener = searchListener;
        if (this.mapView != null){
            this.mapView.setSearchListener(searchListener);
        }
    }
}
