package dev.hour.view.list;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.hour.R;
import dev.hour.contracts.RestaurantContract;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class CustomerRestaurantListAdapter extends
        RecyclerView.Adapter<CustomerRestaurantListAdapter.CustomerRestaurantListItemViewHolder> {

    /// ----------------------
    /// Private Static Methods

    private static void FitTextInsideTextView(final TextView textView, final View otherView, final String text) {

        int end = 0;
        @SuppressLint("Range") int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(WRAP_CONTENT, View.MeasureSpec.UNSPECIFIED);
        @SuppressLint("Range") int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(WRAP_CONTENT, View.MeasureSpec.UNSPECIFIED);

        textView.setText(text);
        textView.measure(widthMeasureSpec, heightMeasureSpec);

        int bounds = otherView.getLeft() + otherView.getMeasuredWidth();

        if(textView.getLeft() + textView.getMeasuredHeight() >= bounds) {

            while(otherView.getMeasuredWidth() > 0) {

                textView.setText(text.substring(0, end) + "...");
                textView.measure(widthMeasureSpec, heightMeasureSpec);

                if(textView.getLeft() + textView.getMeasuredWidth() >= bounds && end > 0) {

                    textView.setText(text.substring(0, end - 1) + "...");
                    textView.measure(widthMeasureSpec, heightMeasureSpec);
                    break;
                }
                end++;
            }
        }
    }

    /// --------------
    /// Private Fields
    private List<RestaurantContract.Restaurant> RestaurantLists;
    private Map<String, Bitmap>                 images         ;


    @NonNull
    @Override
    public CustomerRestaurantListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_customer_restaurant_list_item,
                                parent,false);

        return new CustomerRestaurantListAdapter.CustomerRestaurantListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerRestaurantListItemViewHolder holder, int position) {
        final ViewGroup                     view        = (ViewGroup) holder.itemView;
        final RestaurantContract.Restaurant restaurant  = (RestaurantLists == null) ? null : RestaurantLists.get(position);

        if(restaurant != null) {
            final TextView title    = view.findViewById(R.id.fragment_customer_restaurant_list_item_title);
            final TextView address1 = view.findViewById(R.id.fragment_customer_restaurant_list_item_address_line_1);
            final TextView address2 = view.findViewById(R.id.fragment_customer_restaurant_list_item_address_line_2);
            final ImageButton image       =
                    view.findViewById(R.id.fragment_customer_restaurant_list_item_image);

            title.setText(restaurant.getName());
            address1.setText(restaurant.getAddress1());
            address2.setText(restaurant.getAddress2());

            final Bitmap restaurantImage = getImageFor(restaurant);

            if(restaurantImage != null) {

                image.setImageBitmap(Bitmap.createScaledBitmap(
                        restaurantImage, 192, 192, false));

                image.setClipToOutline(true);

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setRestaurantLists(List <RestaurantContract.Restaurant> lists){
        this.RestaurantLists = lists;

        notifyDataSetChanged();
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

    /// ---------------
    /// Private Methods

    /**
     * Retrieves the image for the [Restaurant]. If no image exists, this will create and cache it.
     * @param restaurant The restaurant to retrieve the image
     * @return Bitmap or null
     */
    private Bitmap getImageFor(final RestaurantContract.Restaurant restaurant) {

        if (this.images == null) {
            this.images = new HashMap<>();
        }

        Bitmap bitmap = this.images.get(restaurant.getId());

        if(bitmap == null) {

            final ByteArrayOutputStream imageStream = (ByteArrayOutputStream) restaurant.getImageStream();

            if(imageStream != null) {

                final byte[] bytes = imageStream.toByteArray();

                if (bytes.length > 0) {

                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    this.images.put(restaurant.getId(), bitmap);

                }

            }

        }

        return bitmap;

    }


}
