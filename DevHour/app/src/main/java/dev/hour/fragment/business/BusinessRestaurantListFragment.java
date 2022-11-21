package dev.hour.fragment.business;

import android.annotation.SuppressLint;
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
import dev.hour.view.list.business.BusinessRestaurantListAdapter;

public class BusinessRestaurantListFragment extends Fragment
        implements RestaurantContract.View, View.OnClickListener, BusinessRestaurantListAdapter.Listener {

    /// --------------
    /// Static Members

    public final static String TAG = "BusinessRestaurantListFragment";

    /// --------------
    /// Private Fields

    private BusinessRestaurantListAdapter                       businessRestaurantListAdapter   ;
    private RestaurantContract.Presenter.InteractionListener    listener                        ;

    /// --------
    /// Fragment

    /**
     * Invoked when the [BusinessRestaurantListFragment] should create its' view. Inflates the
     * view and any persist state
     * @param layoutInflater The [LayoutInflater] responsible for inflating the view
     * @param viewGroup The parent
     * @param bundle SavedInstanceState
     * @return [View] instance
     */
    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {

        if(this.businessRestaurantListAdapter == null)
            this.businessRestaurantListAdapter = new BusinessRestaurantListAdapter();

        final View          layout          =
                layoutInflater.inflate(R.layout.fragment_business_restaurant_list, viewGroup, false);
        final RecyclerView  recyclerView    =
                layout.findViewById(R.id.fragment_business_restaurant_list_recycler_view);
        final View          floatingActionButton =
                layout.findViewById(R.id.fragment_business_restaurant_list_add_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.businessRestaurantListAdapter);

        this.businessRestaurantListAdapter.setListener(this);
        floatingActionButton.setOnClickListener(this);

        return layout;

    }
    /// --------------------
    /// View.OnClickListener

    /**
     * Invoked when a [View] of interest is clicked by the user. Invokes the corresponding
     * callbacks depending on the [View] that was clicked
     * @param view The [View] instance that was clicked
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        final int id = view.getId();

        switch(id) {

            case R.id.fragment_business_restaurant_list_add_button:

                if(this.listener != null)
                    this.listener.onUpdateRestaurantRequest(null);

                break;

            default: break;

        }

    }

    /// --------------------------------------
    /// BusinessRestaurantListAdapter.Listener

    /**
     * Invoked when a list item's edit button has been clicked by the user.
     * @param restaurant The [RestaurantContract.Restaurant] instance corresponding to the list item
     */
    @Override
    public void onEditButtonClicked(final RestaurantContract.Restaurant restaurant) {

        if(this.listener != null)
            this.listener.onUpdateRestaurantRequest(restaurant);

    }

    /**
     * Invoked when a list item has been clicked by the user.
     * @param restaurant The [RestaurantContract.Restaurant] instance corresponding to the list item
     */
    @Override
    public void onItemClicked(RestaurantContract.Restaurant restaurant) {

        if(this.listener != null)
            this.listener.onRestaurantSelected(restaurant);

    }

    /// -----------------------
    /// RestaurantContract.View

    /**
     * Sets the [RestaurantContract.Restaurant] list from the given List
     * @param restaurants The [RestaurantContract.Restaurant] list to set.
     */
    @Override
    public void setRestaurants(List<RestaurantContract.Restaurant> restaurants) {

        if(this.businessRestaurantListAdapter == null)
            this.businessRestaurantListAdapter = new BusinessRestaurantListAdapter();

        this.businessRestaurantListAdapter.setRestaurantLists(restaurants);

    }

    /// --------------
    /// Public Methods

    /**
     * Sets the Interaction listener that will receive callbacks on user interactions.
     * @param listener The listener that will receive callbacks on user interactions.
     */
    public void setInteractionListener(final RestaurantContract.Presenter.InteractionListener listener) {

        this.listener = listener;

    }

}
