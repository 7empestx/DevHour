package dev.hour.view.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.hour.R;
import dev.hour.contracts.MealContract;

/**
 * Customer Menu list adapter. Defines the View Holder for the Customer
 * Menu list.
 * @author Yurii Bibik
 * @version 1.0.0
 */
public class CustomerMenuListAdapter extends
        RecyclerView.Adapter<CustomerMenuListAdapter.CustomerMenuListItemViewHolder> {

    /// --------------
    /// Private Fields

    private List<MealContract.Meal> mealsList;

    @NonNull
    @Override
    public CustomerMenuListAdapter.CustomerMenuListItemViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_customer_menu_list_item,
                                parent,false);

        return new CustomerMenuListAdapter.CustomerMenuListItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomerMenuListAdapter.CustomerMenuListItemViewHolder holder, int position) {

        final ViewGroup view = (ViewGroup) holder.itemView;
        final MealContract.Meal ingredient = (this.mealsList == null) ? null : mealsList.get(position);

        if(ingredient != null){

            final TextView      title       =
                    view.findViewById(R.id.fragment_customer_menu_list_item_title);
            final TextView      details1       =
                    view.findViewById(R.id.fragment_customer_menu_list_item_details_text_1);
            final TextView      details2       =
                    view.findViewById(R.id.fragment_customer_menu_list_item_details_text_2);

            title.setText(ingredient.toString());

            // TODO: Image, details1, details2

        }

    }

    public void setMealsList(final List <MealContract.Meal> meals){

        this.mealsList = meals;

    }

    /**
     * Returns the amount of items in the list
     * @return int value of the amount of Restaurants in the list
     */
    @Override
    public int getItemCount() {

        return (this.mealsList == null) ? 0 : this.mealsList.size();

    }

    /// -------
    /// Classes

    class CustomerMenuListItemViewHolder extends RecyclerView.ViewHolder{

        CustomerMenuListItemViewHolder(View view){
            super(view);
        }

    }

}
