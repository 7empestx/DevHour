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
import dev.hour.contracts.UserContract;
import dev.hour.restaurant.Restaurant;
import dev.hour.view.list.RestaurantListAdapter;

public class RestaurantListFragment extends Fragment {
    private RestaurantListAdapter restaurantListAdapter;
    @Override
    public View onCreateView(LayoutInflater lf, ViewGroup vg, Bundle b){
        this.restaurantListAdapter = new RestaurantListAdapter();
        final ViewGroup layout = (ViewGroup) lf.inflate(R.layout.fragment_restaurant_list, vg, false);
        final RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.list_item_RV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(restaurantListAdapter);
        return layout;
    }

    public void setRestaurantList(List<RestaurantContract.Restaurant> list){
        if(restaurantListAdapter != null){
            this.restaurantListAdapter.setRestaurantLists(list);
        }

    }
}
