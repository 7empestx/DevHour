package dev.hour.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.hour.R;
import dev.hour.contracts.RestaurantContract;

public class BusinessAddRestaurantFragment extends Fragment implements RestaurantContract.View {

    /// --------------
    /// Static Members

    public final static String TAG = "BusinessAddRestaurantFragment";

    /// --------------
    /// Private Fields

    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {

        final View          layout          =
                layoutInflater.inflate(R.layout.fragment_business_add_restaurant, viewGroup, false);

        return layout;

    }


    @Override
    public void setRestaurants(List<RestaurantContract.Restaurant> restaurants) {

    }
}
