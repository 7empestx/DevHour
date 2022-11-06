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

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantListItemViewHolder> {
    private List<RestaurantContract.Restaurant> RestaurantLists;

    @NonNull
    @Override
    public RestaurantListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view,parent,false);
        return new RestaurantListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListItemViewHolder holder, int position) {
        final ViewGroup view = (ViewGroup) holder.itemView;
        final RestaurantContract.Restaurant restaurant = (RestaurantLists == null)?null : RestaurantLists.get(position);
        if(restaurant != null){
            for(int i = 0; i < view.getChildCount(); i++) {
                if(view.getChildAt(i).getId() == R.id.list_item_text_view_Food_Name){
                    final TextView Foodview = (TextView) view.getChildAt(i);
                    Foodview.setText(restaurant.getName()); //Binding of text box
                }

                if(view.getChildAt(i).getId() == R.id.list_item_text_view_Category_Name){
                    final TextView Foodview = (TextView) view.getChildAt(i);
                    Foodview.setText(restaurant.getName()); //Binding of text box
                }

                if(view.getChildAt(i).getId() == R.id.list_item_text_view_Mileage){
                    final TextView Foodview = (TextView) view.getChildAt(i);
                    Foodview.setText(restaurant.getName()); //Binding of text box
                }
                //obtain image after grabbing restaurant info

                if(view.getChildAt(i).getId() == R.id.list_item_image_view) {
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

    class RestaurantListItemViewHolder extends RecyclerView.ViewHolder{
     RestaurantListItemViewHolder(View view){
         super(view);
     }
    }
}
