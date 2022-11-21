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

public class BusinessAddRestaurantConfirmationFragment extends Fragment implements RestaurantContract.View, View.OnClickListener {

    /// --------------
    /// Static Members
    public final static String TAG = "BusinessAddRestaurantConfirmationFragment";

    /// --------------
    /// Private Fields

    private RestaurantContract.Presenter.InteractionListener interactionListener    ;
    private final static Map<String, Object>                              tags                   = new HashMap<>();
    private final static Map<String, Object>                              image                  = new HashMap<>();

    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {
        final View layout = layoutInflater.inflate(R.layout.fragment_business_add_restaurant_confirmation, viewGroup, false);

        final View confirmButton = layout.findViewById(R.id.fragment_business_add_restaurant_confirm_button);

        confirmButton.setOnClickListener(this);

        return layout;
    }


    @Override
    public void setRestaurants(List<RestaurantContract.Restaurant> restaurants) {
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.fragment_business_add_restaurant_confirm_button:
                final Map<String, Object> data = new HashMap<>();

                final EditText restaurantName =
                        this.requireView()
                                .findViewById(R.id.fragment_business_add_restaurant_confirmation_name);
                final EditText address1 =
                        this.requireView()
                                .findViewById(R.id.fragment_business_add_restaurant_confirmation_address_one);

                final EditText address2 =
                        this.requireView()
                                .findViewById(R.id.fragment_business_add_restaurant_confirmation_address_two);

                break;

            default: break;
        }
    }

    public void setInteractionListener(RestaurantContract.Presenter.InteractionListener listener) { //uhh idk yet
        this.interactionListener = listener;
    }

}
