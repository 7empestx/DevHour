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
import dev.hour.view.list.BusinessRestaurantListAdapter;
import dev.hour.view.list.CustomerRestaurantListAdapter;

public class BusinessRestaurantListFragment extends Fragment implements RestaurantContract.View, View.OnClickListener {

    /// --------------
    /// Static Members

    public final static String TAG = "BusinessRestaurantListFragment";

    /// --------------
    /// Private Fields

    private BusinessRestaurantListAdapter                       businessRestaurantListAdapter   ;
    private RestaurantContract.Presenter.InteractionListener    listener                        ;


    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {

        if(this.businessRestaurantListAdapter == null)
            this.businessRestaurantListAdapter = new BusinessRestaurantListAdapter();

        final View          layout          =
                layoutInflater.inflate(R.layout.fragment_business_restaurant_list, viewGroup, false);
        final RecyclerView  recyclerView    =
                layout.findViewById(R.id.fragment_customer_restaurant_list_recycler_view);
        final View          floatingActionButton =
                layout.findViewById(R.id.fragment_business_restaurant_list_add_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.businessRestaurantListAdapter);

        floatingActionButton.setOnClickListener(this);

        return layout;

    }

    @Override
    public void setRestaurants(List<RestaurantContract.Restaurant> restaurants) {

        if(this.businessRestaurantListAdapter == null)
            this.businessRestaurantListAdapter = new BusinessRestaurantListAdapter();

        this.businessRestaurantListAdapter.setRestaurantLists(restaurants);

    }

    @Override
    public void onClick(View view) {

        final int id = view.getId();

        switch(id) {

            case R.id.fragment_business_restaurant_list_add_button:

                if(this.listener != null)
                    this.listener.onAddRestaurantRequest();

                break;

            default: break;

        }

    }

    public void setInteractionListener(final RestaurantContract.Presenter.InteractionListener listener) {

        this.listener = listener;

    }

}
