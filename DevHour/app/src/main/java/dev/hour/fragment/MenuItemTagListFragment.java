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
import dev.hour.view.list.BusinessMenuListAdapter;

public class MenuItemTagListFragment extends Fragment implements MealContract.Menu.View{

        /// --------------
        /// Static Members

        public final static String TAG = "MenuItemTagListFragment";

        /// --------------
        /// Private Fields

        private BusinessMenuListAdapter MenuItemTagListAdapter;


    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {

        if (this.MenuItemTagListAdapter == null)
            this.MenuItemTagListAdapter = new BusinessMenuListAdapter();

        final View layout =
                layoutInflater.inflate(R.layout.fragment_business_restaurant_list, viewGroup, false);
        final RecyclerView recyclerView =
                layout.findViewById(R.id.fragment_customer_restaurant_list_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.MenuItemTagListAdapter);

        return layout;

    }

    @Override
    public void setMenu(List<MealContract.Meal> meals) {

        if (this.MenuItemTagListAdapter == null)
            this.MenuItemTagListAdapter = new BusinessMenuListAdapter();

        this.MenuItemTagListAdapter.setMealsList(meals);

    }

}
