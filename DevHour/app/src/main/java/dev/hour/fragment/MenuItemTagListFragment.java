package dev.hour.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dev.hour.R;


public class MenuItemTagListFragment extends Fragment {


    /// --------------
    /// Static Members

    public final static String TAG = "MenuItemTagListFragment";

    /// --------------
    /// Private Fields



    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {

        final View layout =
                layoutInflater.inflate(R.layout.fragment_business_restaurant_list, viewGroup, false);
        final RecyclerView recyclerView =
                layout.findViewById(R.id.fragment_customer_restaurant_list_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return layout;

    }



}