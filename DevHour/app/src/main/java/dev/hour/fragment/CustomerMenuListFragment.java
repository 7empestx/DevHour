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
import dev.hour.contracts.MealContract;
import dev.hour.view.list.CustomerMenuListAdapter;

/**
 * Basic Login [Fragment] that authenticates & creates users before interacting with the
 * rest of the application.
 *
 * @since 1.0.0.0
 */
public final class CustomerMenuListFragment extends Fragment implements MealContract.Menu.View {

    /// --------------
    /// Static Members

    public final static String TAG = "CustomerMenuListFragment";

    /// --------------
    /// Private Fields

    private CustomerMenuListAdapter customerMenuListAdapter;


    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {

        if (this.customerMenuListAdapter == null)
            this.customerMenuListAdapter = new CustomerMenuListAdapter();

        final View layout =
                layoutInflater.inflate(R.layout.fragment_customer_menu_list, viewGroup, false);
        final RecyclerView recyclerView =
                layout.findViewById(R.id.fragment_customer_menu_list_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.customerMenuListAdapter);

        return layout;

    }

    @Override
    public void setMenu(List<MealContract.Meal> meals) {

        if (this.customerMenuListAdapter == null)
            this.customerMenuListAdapter = new CustomerMenuListAdapter();

        this.customerMenuListAdapter.setMealsList(meals);

    }

}
