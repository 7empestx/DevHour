package dev.hour.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.hour.R;
import dev.hour.contracts.RestaurantContract;

public class BusinessAddRestaurantFragment extends Fragment implements
        RestaurantContract.View, View.OnClickListener {

    /// --------------
    /// Static Members

    public final static String TAG = "BusinessAddRestaurantFragment";

    /// --------------
    /// Private Fields

    private RestaurantContract.Presenter.InteractionListener interactionListener    ;
    private Map<String, String>                              tags                   ;

    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {

        tags = new HashMap<>();

        final View layout =
                layoutInflater.inflate(R.layout.fragment_business_add_restaurant, viewGroup, false);

        final View confirmButton = layout.findViewById(R.id.fragment_business_add_restaurant_confirm_button);
        final View tagButton     = layout.findViewById(R.id.fragment_business_add_restaurant_tag_button);
        final View image         = layout.findViewById(R.id.fragment_business_add_restaurant_image);
        final View backButton    = layout.findViewById(R.id.fragment_business_add_restaurant_back_button);

        confirmButton.setOnClickListener(this);
        tagButton.setOnClickListener(this);
        image.setOnClickListener(this);
        backButton.setOnClickListener(this);

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
                                .findViewById(R.id.fragment_business_add_restaurant_name_input);

                final EditText address1 =
                        this.requireView()
                                .findViewById(R.id.fragment_business_add_restaurant_address_input_1);

                final EditText address2 =
                        this.requireView()
                                .findViewById(R.id.fragment_business_add_restaurant_address_input_2);

                data.put("name",        restaurantName.getText().toString());
                data.put("address1",    address1.getText().toString());
                data.put("address2",    address2.getText().toString());

                final List<String> tags = new ArrayList<>();

                for(final Map.Entry<String, String> entry: this.tags.entrySet())
                    tags.add(entry.getValue());

                data.put("tags", tags);

                if(this.interactionListener != null)
                    this.interactionListener.onCreateRestaurantRequest(data);

                break;

            case R.id.fragment_business_add_restaurant_tag_button:

                if(this.interactionListener != null) {

                    if(this.tags != null)
                        this.tags = new HashMap<>();

                    this.interactionListener.onShowTagRequest(this.tags);

                }

                break;

            case R.id.fragment_business_add_restaurant_image:

                if(this.interactionListener != null)
                    this.interactionListener.onShowBusinessAddImageRequest();

                break;

            case R.id.fragment_business_add_restaurant_back_button:

                if(this.interactionListener != null)
                    this.interactionListener.onShowBusinessRestaurantListRequest();

                break;

            default: break;

        }

    }

    public void setInteractionListener(RestaurantContract.Presenter.InteractionListener listener) {

        this.interactionListener = listener;

    }

}
