package dev.hour.view.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.hour.R;
import dev.hour.contracts.MealContract;

/**
 * Business Menu Item Detail Ingredients list adapter. Defines the View Holder for the Business
 * Menu Item Detail Ingredients list.
 * @author Carlos L. Cuenca
 * @version 1.0.0
 */
public class BusinessMenuItemDetailListAdapter extends
        RecyclerView.Adapter<BusinessMenuItemDetailListAdapter.BusinessMenuItemDetailListItemViewHolder> {

    /// --------------
    /// Private Fields

    private List<MealContract.Ingredient> ingredientsList;

    @NonNull
    @Override
    public BusinessMenuItemDetailListAdapter.BusinessMenuItemDetailListItemViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_business_menu_item_detail_list_item,
                                parent,false);

        return new BusinessMenuItemDetailListAdapter.BusinessMenuItemDetailListItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BusinessMenuItemDetailListAdapter.BusinessMenuItemDetailListItemViewHolder holder, int position) {

        final ViewGroup view = (ViewGroup) holder.itemView;
        final MealContract.Ingredient ingredient = (this.ingredientsList == null) ? null : ingredientsList.get(position);

        if(ingredient != null){

            final TextView      title       =
                    view.findViewById(R.id.fragment_business_menu_list_item_detail_title);
            final ImageButton   image       =
                    view.findViewById(R.id.fragment_business_menu_list_item_detail_image);

            title.setText(ingredient.toString());

            // TODO: Image

        }

    }

    public void setIngredientsList(final List <MealContract.Ingredient> ingredients){

        this.ingredientsList = ingredients;

    }

    /**
     * Returns the amount of items in the list
     * @return int value of the amount of Restaurants in the list
     */
    @Override
    public int getItemCount() {

        return (this.ingredientsList == null) ? 0 : this.ingredientsList.size();

    }

    /// -------
    /// Classes

    class BusinessMenuItemDetailListItemViewHolder extends RecyclerView.ViewHolder{

        BusinessMenuItemDetailListItemViewHolder(View view){
            super(view);
        }

    }

}
