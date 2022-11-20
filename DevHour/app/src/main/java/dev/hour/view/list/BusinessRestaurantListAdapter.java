package dev.hour.view.list;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private List<RestaurantContract.Restaurant>         restaurantList  ;
    private Map<String, Bitmap>                         images          ;
    private Listener                                    listener        ;

    /**
     * Invoked when a view holder is to be created. Inflates a new Listener, and assigns it
     * to the created [BusinessRestaurantListItemViewHolder]
     * @param parent The Listener's parent
     * @param viewType The view type
     * @return [BusinessRestaurantListItemViewHolder] instance
     */
    @NonNull
    @Override
    public BusinessRestaurantListAdapter.BusinessRestaurantListItemViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_business_restaurant_list_item,
                                parent,false);

        final ImageView editButton  =
                view.findViewById(R.id.fragment_business_restaurant_list_item_edit_button);

        view.setOnClickListener(this);
        editButton.setOnClickListener(this);

        return new BusinessRestaurantListAdapter.BusinessRestaurantListItemViewHolder(view);

    }

    /**
     * Invoked when the [BusinessRestaurantListItemViewHolder] is to be bound to the view.
     * @param holder The [BusinessRestaurantListItemViewHolder]
     * @param position The position of the [Restaurant] in the collection
     */
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
            final ImageView     editButton  =
                    view.findViewById(R.id.fragment_business_restaurant_list_item_edit_button);

            final Bitmap restaurantImage = getImageFor(restaurant);

            if(restaurantImage != null)
                image.setImageBitmap(Bitmap.createScaledBitmap(
                        restaurantImage, image.getMeasuredWidth(), image.getMeasuredHeight(), false));

            holder.itemView.setTag(String.valueOf(position));
            editButton.setTag(String.valueOf(position));

            holder.itemView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    FitTextInsideTextView(title, holder.itemView, restaurant.getName());
                    FitTextInsideTextView(address1, holder.itemView, restaurant.getAddress1());
                    FitTextInsideTextView(address2, holder.itemView, restaurant.getAddress2());

                    holder.itemView.getViewTreeObserver().removeOnPreDrawListener(this);

                    return true;

                }
            });

            holder.itemView.setTranslationX(0.0f);

        }

    }

    /**
     * Returns the amount of items in the list
     * @return int value of the amount of Restaurants in the list
     */
    @Override
    public int getItemCount() {

        return (this.restaurantList == null) ? 0 : this.restaurantList.size();

    }

    /**
     * Invoked when a [BusinessRestaurantListItemViewHolder] [View] has been clicked.
     * Notifies the [Listener] of the interaction
     * @param v The [Alert] list item [View]
     */
    @Override
    public void onClick(View v) {

        int position;

        switch(v.getId()) {

            case R.id.fragment_business_restaurant_list_item_edit_button:

                position = Integer.valueOf(v.getTag().toString());

                if(this.listener != null)
                    this.listener.onEditButtonClicked(this.restaurantList.get(position));

                break;

            default:

                position = Integer.valueOf(v.getTag().toString());

                if(this.listener != null)
                    this.listener.onItemClicked(this.restaurantList.get(position));

                break;
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

    public void setRestaurantLists(final List <RestaurantContract.Restaurant> Lists){

        this.restaurantList = Lists;

        if(this.images == null)
            this.images = new HashMap<>();

        else this.images.clear();

    }

    public void setListener(final Listener listener) {

        this.listener = listener;

    }

    /// ----------
    /// Interfaces

    public interface Listener {

        void onEditButtonClicked(final RestaurantContract.Restaurant restaurant);
        void onItemClicked(final RestaurantContract.Restaurant restaurant);

    }

    /// -------
    /// Classes

    class BusinessRestaurantListItemViewHolder extends RecyclerView.ViewHolder{

        BusinessRestaurantListItemViewHolder(View view){
            super(view);
        }

    }

}
