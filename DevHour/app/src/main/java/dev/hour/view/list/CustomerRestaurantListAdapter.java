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

public class CustomerRestaurantListAdapter extends RecyclerView.Adapter<CustomerRestaurantListAdapter.CustomerRestaurantListItemViewHolder> {
    private List<RestaurantContract.Restaurant> RestaurantLists;

    @NonNull
    @Override
    public CustomerRestaurantListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_customer_restaurant_list_item,parent,false);
        return new CustomerRestaurantListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerRestaurantListItemViewHolder holder, int position) {
        final ViewGroup view = (ViewGroup) holder.itemView;
        final RestaurantContract.Restaurant restaurant = (RestaurantLists == null)?null : RestaurantLists.get(position);
        if(restaurant != null){
            for(int i = 0; i < view.getChildCount(); i++) {
                if(view.getChildAt(i).getId() == R.id.fragment_customer_restaurant_list_item_title){
                    final TextView Foodview = (TextView) view.getChildAt(i);
                    Foodview.setText(restaurant.getName()); //Binding of text box
                }

                if(view.getChildAt(i).getId() == R.id.fragment_customer_restaurant_list_item_address_line_1){
                    final TextView Foodview = (TextView) view.getChildAt(i);
                    Foodview.setText(restaurant.getName()); //Binding of text box
                }

                if(view.getChildAt(i).getId() == R.id.fragment_customer_restaurant_list_item_distance){
                    final TextView Foodview = (TextView) view.getChildAt(i);
                    Foodview.setText(restaurant.getName()); //Binding of text box
                }
                //obtain image after grabbing restaurant info

                if(view.getChildAt(i).getId() == R.id.fragment_customer_restaurant_list_item_image) {
                    /*
                    final TextView Foodview = (TextView) view.getChildAt(i);
                    Foodview.setText(restaurant.getName()); //Binding of image box
                     delete comment later for when we have images populated
                     */
                }
            }
        }
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
