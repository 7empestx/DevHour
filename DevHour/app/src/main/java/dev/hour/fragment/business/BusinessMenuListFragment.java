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
import dev.hour.contracts.MealContract;
import dev.hour.view.list.business.BusinessMenuListAdapter;

public class BusinessMenuListFragment extends Fragment
        implements MealContract.Menu.View, View.OnClickListener, BusinessMenuListAdapter.Listener {

    /// --------------
    /// Static Members

    public final static String TAG = "BusinessMenuListFragment";

    /// --------------
    /// Private Fields

    private BusinessMenuListAdapter                         businessMenuListAdapter ;
    private MealContract.Menu.Presenter.InteractionListener listener                ;

    /**
     * Invoked when the [BusinessMenuListFragment] should create its' view. Inflates the
     * view and any persist state
     * @param layoutInflater The [LayoutInflater] responsible for inflating the view
     * @param viewGroup The parent
     * @param bundle SavedInstanceState
     * @return [View] instance
     */
    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {

        if (this.businessMenuListAdapter == null)
            this.businessMenuListAdapter = new BusinessMenuListAdapter();

        final View layout =
                layoutInflater.inflate(R.layout.fragment_business_menu_list, viewGroup, false);
        final RecyclerView recyclerView =
                layout.findViewById(R.id.fragment_business_menu_list_recycler_view);
        final View          floatingActionButton =
                layout.findViewById(R.id.fragment_business_menu_list_fab);
        final View          backButton =
                layout.findViewById(R.id.fragment_business_menu_list_back_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.businessMenuListAdapter);

        floatingActionButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

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

            case R.id.fragment_business_menu_list_fab:

                if(this.listener != null)
                    this.listener.onUpdateMealRequest(null);

                break;

            case R.id.fragment_business_menu_list_back_button:

                if(this.listener != null)
                    this.listener.onCloseBusinessMenuRequest();

                break;

            default: break;

        }
    }

    /// --------------------------------------
    /// BusinessMenuItemListAdapter.Listener

    /**
     * Invoked when a list item's edit button has been clicked by the user.
     * @param meal The [MealContract.Meal] instance corresponding to the list item
     */
    @Override
    public void onEditMealButtonClicked(MealContract.Meal meal) {

        if(this.listener != null)
            this.listener.onUpdateMealRequest(meal);

    }

    /**
     * Invoked when a list item has been clicked by the user.
     * @param meal The [MealContract.Meal] instance corresponding to the list item
     */
    @Override
    public void onItemClicked(MealContract.Meal meal) {

        if(this.listener != null)
            this.listener.onBusinessMealSelected(meal);

    }

    /// -----------------------
    /// MealContract.View

    /**
     * Sets the [MealContract.Meal] list from the given List
     * @param meals The [MealContract.Meal] list to set.
     */
    @Override
    public void setMenu(List<MealContract.Meal> meals) {

        if (this.businessMenuListAdapter == null)
            this.businessMenuListAdapter = new BusinessMenuListAdapter();

        this.businessMenuListAdapter.setMealsList(meals);

    }

    /// --------------
    /// Public Methods

    /**
     * Sets the Interaction listener that will receive callbacks on user interactions.
     * @param listener The listener that will receive callbacks on user interactions.
     */
    public void setInteractionListener(final MealContract.Menu.Presenter.InteractionListener listener) {

        this.listener = listener;

    }

}
