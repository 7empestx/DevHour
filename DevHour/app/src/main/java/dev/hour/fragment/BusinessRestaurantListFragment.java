package dev.hour.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dev.hour.R;
import dev.hour.view.list.RestaurantListAdapter;

public class BusinessRestaurantListFragment extends Fragment {

    public final static String TAG = "BusinessRestaurantListFragment";

    @Override
    public View onCreateView(LayoutInflater lf, ViewGroup vg, Bundle b){
        final View layout = (View) lf.inflate(R.layout.fragment_business_restaurant_list, vg, false);
        return layout;
    }
}
