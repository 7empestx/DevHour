package dev.hour.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.hour.MainActivity;
import dev.hour.R;
import dev.hour.contracts.MealContract;
import dev.hour.contracts.RestaurantContract;
import dev.hour.view.list.CustomerRestaurantListAdapter;

public class CustomerRestaurantListFragment extends Fragment implements RestaurantContract.View {

    public final static String TAG = "CustomerRestaurantListFragment";

    private CustomerRestaurantListAdapter customerRestaurantListAdapter;

    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle){

        if (this.customerRestaurantListAdapter == null)
            this.customerRestaurantListAdapter = new CustomerRestaurantListAdapter();

        final View layout =
                layoutInflater.inflate(R.layout.fragment_customer_restaurant_list, viewGroup, false);
        final RecyclerView recyclerView =
                layout.findViewById(R.id.fragment_customer_restaurant_list_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.customerRestaurantListAdapter);

        return layout;
    }


    @Override
    public void setRestaurants(List<RestaurantContract.Restaurant> restaurants) {
        if(customerRestaurantListAdapter == null){
            this.customerRestaurantListAdapter = new CustomerRestaurantListAdapter();
        }
        this.customerRestaurantListAdapter.setRestaurantLists(restaurants);
    }
}
