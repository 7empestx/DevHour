package dev.hour.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dev.hour.MainActivity;
import dev.hour.R;
import dev.hour.view.list.CustomerRestaurantListAdapter;

public class GodModeFragment extends Fragment {
    public final static String TAG = "GodModeFragment";

    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {
        final View layout =
                layoutInflater.inflate(R.layout.fragment_god_mode, viewGroup, false);
        final View businessButton = layout.findViewById(R.id.fragment_business_restaurant_list);
        final View customerButton = layout.findViewById(R.id.fragment_customer_restaurant_list);

        businessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).navigateToBusinessRestaurantList();
            }
        });

        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).navigateToCustomerRestaurantList();
            }
        });

        return layout;
    }

}
