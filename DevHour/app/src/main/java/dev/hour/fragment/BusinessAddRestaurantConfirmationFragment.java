package dev.hour.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dev.hour.R;
import dev.hour.contracts.RestaurantContract;

import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;

public class BusinessAddRestaurantConfirmationFragment extends Fragment implements RestaurantContract.View, View.OnClickListener {

    /// --------------
    /// Static Members
    public final static String TAG = "BusinessAddRestaurantConfirmationFragment";

    /// --------------
    /// Private Fields

    private RestaurantContract.Presenter.InteractionListener interactionListener    ; //maybe
    private Map<String, Object>     export                  ;
    private MapView mapView;

    @Override
    public View onCreateView(final LayoutInflater layoutInflater, //why onCreateView and not onCreate?
                             final ViewGroup viewGroup, final Bundle bundle) {
        final View layout = layoutInflater.inflate(R.layout.fragment_business_add_restaurant_confirmation, viewGroup, false);
        final View confirmButton = layout.findViewById(R.id.fragment_business_add_restaurant_confirm_button); //dont know if needed yet lol

        mapView = layout.findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);

        //all examples use onCreate and have an extra dependency that allows mapbox.getinstance (maybe its something im missing)
        //because i see no instance of the mapbox token yet, soo i assume we'll need to use it.

        confirmButton.setOnClickListener(this);

        return mapView;
    }


    @Override
    public void setRestaurants(List<RestaurantContract.Restaurant> restaurants) {
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.fragment_business_add_restaurant_confirm_button:

                final EditText restaurantName =
                        this.requireView()
                                .findViewById(R.id.fragment_business_add_restaurant_confirmation_name);
                final EditText address1 =
                        this.requireView()
                                .findViewById(R.id.fragment_business_add_restaurant_confirmation_address_one);

                final EditText address2 =
                        this.requireView()
                                .findViewById(R.id.fragment_business_add_restaurant_confirmation_address_two);

                this.export.put("name", restaurantName.getText().toString());
                this.export.put("address1", address1.getText().toString());
                this.export.put("address2", address2.getText().toString());

                break;


            default: break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mapView != null)
            mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mapView != null)
            mapView.onStop();
    }

    public void onLowMemory() {
        super.onLowMemory();
        if(mapView != null)
            mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mapView != null)
            mapView.onDestroy();
    }


    public void setInteractionListener(RestaurantContract.Presenter.InteractionListener listener) { //uhh idk yet
        this.interactionListener = listener;
    }

    public void setExport(final Map<String, Object> export) {
        this.export = export;
    }
}
