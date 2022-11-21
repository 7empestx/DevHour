package dev.hour.view.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.hour.R;
import dev.hour.contracts.RestaurantContract;

public class CustomerRestaurantListAdapter extends
        RecyclerView.Adapter<CustomerRestaurantListAdapter.CustomerRestaurantListItemViewHolder> {


    private List<RestaurantContract.Restaurant> RestaurantLists;

    @NonNull
    @Override
    public CustomerRestaurantListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_customer_restaurant_list_item,parent,false);
        return new CustomerRestaurantListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerRestaurantListItemViewHolder holder, int position) {

        final ViewGroup                     view        = (ViewGroup) holder.itemView;
        final RestaurantContract.Restaurant restaurant  = (RestaurantLists == null) ? null : RestaurantLists.get(position);

        final TextView title    = view.findViewById(R.id.fragment_customer_restaurant_list_item_title);
        final TextView address1 = view.findViewById(R.id.fragment_customer_restaurant_list_item_address_line_1);
        final TextView address2 = view.findViewById(R.id.fragment_customer_restaurant_list_item_address_line_2);

        title.setText(restaurant.getName());

    }

    public void setRestaurantLists(List <RestaurantContract.Restaurant> Lists){
        this.RestaurantLists = Lists;

    }


    @Override
    public int getItemCount() {
        return (this.RestaurantLists == null)?0: this.RestaurantLists.size();
    }

    class CustomerRestaurantListItemViewHolder extends RecyclerView.ViewHolder{

        CustomerRestaurantListItemViewHolder(View view){
            super(view);
        }

    }

}
