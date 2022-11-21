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
import dev.hour.contracts.RestaurantContract;
import dev.hour.model.Meal;
import dev.hour.view.list.BusinessMenuListAdapter;

public class BusinessMenuListFragment extends Fragment
        implements MealContract.Menu.View, View.OnClickListener, BusinessMenuListAdapter.Listener {

    /// --------------
    /// Static Members

    public final static String TAG = "BusinessMenuListFragment";

    /// --------------
    /// Private Fields

    private BusinessMenuListAdapter businessMenuListAdapter;
    private MealContract.Menu.Presenter.InteractionListener listener;


    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {

        if (this.businessMenuListAdapter == null)
            this.businessMenuListAdapter = new BusinessMenuListAdapter();

        final View layout =
                layoutInflater.inflate(R.layout.fragment_business_menu_list, viewGroup, false);
        final RecyclerView recyclerView =
                layout.findViewById(R.id.fragment_business_menu_list_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.businessMenuListAdapter);

        return layout;

    }

    @Override
    public void setMenu(List<MealContract.Meal> meals) {

        if (this.businessMenuListAdapter == null)
            this.businessMenuListAdapter = new BusinessMenuListAdapter();

        this.businessMenuListAdapter.setMealsList(meals);

    }

    public void setInteractionListener(final MealContract.Menu.Presenter.InteractionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClicked(MealContract.Meal meal) {

        if(this.listener != null)
            this.listener.onMealSelected(meal);

    }

    @Override
    public void onEditMealButtonClicked(MealContract.Meal meal) {


    }

    @Override
    public void onClick(View view) {

    }
}
