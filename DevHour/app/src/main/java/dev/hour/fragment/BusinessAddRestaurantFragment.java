package dev.hour.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.hour.R;
import dev.hour.contracts.RestaurantContract;

public class BusinessAddRestaurantFragment extends Fragment implements RestaurantContract.View, View.OnClickListener {

    /// --------------
    /// Static Members

    public final static String TAG = "BusinessAddRestaurantFragment";

    /// --------------
    /// Private Fields

    private RestaurantContract.Presenter.InteractionListener interactionListener;

    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {

        final View          layout          =
                layoutInflater.inflate(R.layout.fragment_business_add_restaurant, viewGroup, false);

        final View confirmButton = layout.findViewById(R.id.fragment_business_add_restaurant_confirm_button);
        final View tagButton     = layout.findViewById(R.id.fragment_business_add_restaurant_tag_button);

        confirmButton.setOnClickListener(this);
        tagButton.setOnClickListener(this);

        return layout;

    }

    @Override
    public void setRestaurants(List<RestaurantContract.Restaurant> restaurants) {

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.fragment_business_add_restaurant_confirm_button:

                final Map<String, String> data = new HashMap<>();

                /// TODO: Fill in data

                if(this.interactionListener != null)
                    this.interactionListener.onCreateRestaurantRequest(data);

                break;

            case R.id.fragment_business_add_restaurant_tag_button:

                break;

            default: break;

        }

    }

    public void setInteractionListener(RestaurantContract.Presenter.InteractionListener listener) {

        this.interactionListener = listener;

    }

}
