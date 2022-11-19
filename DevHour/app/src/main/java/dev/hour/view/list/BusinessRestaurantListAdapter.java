package dev.hour.view.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.hour.R;
import dev.hour.contracts.RestaurantContract;

/**
 * Business Restaurant list adapter. Defines the View Holder for the Business Restaurant list.
 * @author Carlos L. Cuenca
 * @version 1.0.0
 */
public class BusinessRestaurantListAdapter extends
        RecyclerView.Adapter<BusinessRestaurantListAdapter.BusinessRestaurantListItemViewHolder>
        implements View.OnClickListener {

    /// --------------
    /// Private Fields

    private List<RestaurantContract.Restaurant> restaurantList  ;

    @NonNull
    @Override
    public BusinessRestaurantListAdapter.BusinessRestaurantListItemViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_business_restaurant_list_item,
                                parent,false);

        final ImageView     editButton  =
                view.findViewById(R.id.fragment_business_restaurant_list_item_edit_button);

        /// TODO: Set OnClickListener here

        return new BusinessRestaurantListAdapter.BusinessRestaurantListItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BusinessRestaurantListAdapter.BusinessRestaurantListItemViewHolder holder, int position) {

        final ViewGroup view = (ViewGroup) holder.itemView;
        final RestaurantContract.Restaurant restaurant = (this.restaurantList == null) ? null : restaurantList.get(position);

        if(restaurant != null){

            final TextView      title       =
                    view.findViewById(R.id.fragment_business_restaurant_list_item_title);
            final ImageButton   image       =
                    view.findViewById(R.id.fragment_business_restaurant_list_item_image);
            final TextView      address1    =
                    view.findViewById(R.id.fragment_business_restaurant_list_item_address_line_1);
            final TextView      address2    =
                    view.findViewById(R.id.fragment_business_restaurant_list_item_address_line_2);

            title.setText(restaurant.getName());
            address1.setText(restaurant.getAddress1());
            address2.setText(restaurant.getAddress2());

            // TODO: Set Image

        }

    }

    public void setRestaurantLists(final List <RestaurantContract.Restaurant> Lists){

        this.restaurantList = Lists;

    }

    /**
     * Returns the amount of items in the list
     * @return int value of the amount of Restaurants in the list
     */
    @Override
    public int getItemCount() {

        return (this.restaurantList == null) ? 0 : this.restaurantList.size();

    }

    @Override
    public void onClick(View v) {

    }

    /// -------
    /// Classes

    class BusinessRestaurantListItemViewHolder extends RecyclerView.ViewHolder{

        BusinessRestaurantListItemViewHolder(View view){
            super(view);
        }

    }

}
