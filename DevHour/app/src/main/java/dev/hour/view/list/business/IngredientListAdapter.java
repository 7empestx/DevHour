package dev.hour.view.list.business;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.hour.R;

/**
 * Ingredients list adapter. Defines the View Holder for the Ingredient list
 * @author Carlos L. Cuenca
 * @version 1.0.0
 */

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientListItemViewHolder> {

    /// --------------
    /// Private Fields

    private List<String> ingredientsList = new ArrayList<>();

    @NonNull
    @Override
    public IngredientListAdapter.IngredientListItemViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_ingredient_list_item,
                                parent,false);

        return new IngredientListAdapter.IngredientListItemViewHolder(view);

    }

    /**
     * Invoked when the [IngredientListItemViewHolder] is to be bound to the view.
     * @param holder The [IngredientListItemViewHolder]
     * @param position The position of the ingredient in the collection
     */
    @Override
    public void onBindViewHolder(@NonNull IngredientListAdapter.IngredientListItemViewHolder holder, int position) {

        final ViewGroup view    = (ViewGroup) holder.itemView   ;
        final String    ingredient     = ingredientsList.get(position)        ;

        if(ingredient != null){

            final TextView title = view.findViewById(R.id.fragment_ingredient_list_item_title);

            title.setText(ingredient);

        }

    }

    /**
     * Adds the ingredient to the List of ingredients
     * @param ingredient The ingredient to add
     */
    @SuppressLint("NotifyDataSetChanged")
    public void addIngredient(final String ingredient) {

        this.ingredientsList.add(ingredient);

        notifyDataSetChanged();

    }

    /**
     * Retrieves the [String] ingredients in the list
     * @return List ingredients
     */
    public List<String> getIngredients() {

        return this.ingredientsList;

    }

    /**
     * Returns the amount of ingredients in the list
     * @return int value of the amount of Restaurants in the list
     */
    @Override
    public int getItemCount() {

        return this.ingredientsList.size();

    }

    /// -------
    /// Classes

    /**
     * The [ViewHolder] instance that holds the view to be bound.
     */
    class IngredientListItemViewHolder extends RecyclerView.ViewHolder {

        IngredientListItemViewHolder(View view){
            super(view);
        }

    }

}
