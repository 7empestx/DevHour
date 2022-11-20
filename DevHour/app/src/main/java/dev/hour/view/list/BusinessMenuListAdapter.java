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
import dev.hour.contracts.MealContract;
import dev.hour.contracts.RestaurantContract;

/**
 * Business Menu list adapter. Defines the View Holder for the Business
 * Menu list.
 * @author Carlos L. Cuenca
 * @version 1.0.0
 */
public class BusinessMenuListAdapter extends
        RecyclerView.Adapter<BusinessMenuListAdapter.BusinessMenuListItemViewHolder>
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

    private List<MealContract.Meal> mealsList;
    private Map<String, Bitmap>     images;
    private Listener                listener;

    /**
     * Invoked when a view holder is to be created. Inflates a new Listener, and assigns it
     * to the created [BusinessMenuListItemViewHolder]
     * @param parent The Listener's parent
     * @param viewType The view type
     * @return [BusinessMenuListItemViewHolder] instance
     */
    @NonNull
    @Override
    public BusinessMenuListAdapter.BusinessMenuListItemViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_business_menu_list_item,
                                parent,false);

        return new BusinessMenuListAdapter.BusinessMenuListItemViewHolder(view);

    }

    /**
     * Invoked when the [BusinessRestaurantListItemViewHolder] is to be bound to the view.
     * @param holder The [BusinessRestaurantListItemViewHolder]
     * @param position The position of the [Restaurant] in the collection
     */
    @Override
    public void onBindViewHolder(@NonNull BusinessMenuListAdapter.BusinessMenuListItemViewHolder holder, int position) {

        final ViewGroup view = (ViewGroup) holder.itemView;
        final MealContract.Meal meal = (this.mealsList == null) ? null : mealsList.get(position);

        if(meal != null){

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

            final Bitmap MenuImage = getImageFor(meal);

            if(MenuImage != null)
                image.setImageBitmap(Bitmap.createScaledBitmap(
                        MenuImage, image.getMeasuredWidth(), image.getMeasuredHeight(), false));

            holder.itemView.setTag(String.valueOf(position));
            editButton.setTag(String.valueOf(position));

            holder.itemView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    FitTextInsideTextView(title, holder.itemView, meal.getName());
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
                    this.listener.onAddButtonClicked(this.mealsList.get(position));

                break;

            default:

                position = Integer.valueOf(v.getTag().toString());

                if(this.listener != null)
                    this.listener.onItemClicked(this.mealsList.get(position));

                break;
        }

    }

    /// ---------------
    /// Private Methods

    /**
     * Retrieves the image for the [Restaurant]. If no image exists, this will create and cache it.
     * @param meal The restaurant to retrieve the image
     * @return Bitmap or null
     */
    private Bitmap getImageFor(final MealContract.Meal meal) {

        Bitmap bitmap = this.images.get(meal.getName());

        if(bitmap == null) {

            final ByteArrayOutputStream imageStream = (ByteArrayOutputStream) meal.getImageStream();
            final byte[]                bytes       = imageStream.toByteArray();

            if(bytes.length > 0) {

                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                this.images.put(meal.getName(), bitmap);

            }

        }

        return bitmap;

    }

    public void setMenuLists(final List <MealContract.Meal> Lists){

        this.mealsList = Lists;

        if(this.images == null)
            this.images = new HashMap<>();

        else this.images.clear();

    }

    public void setListener(final BusinessMenuListAdapter.Listener listener) {

        this.listener = listener;

    }

    /// ----------
    /// Interfaces


    public interface Listener {

        void onAddButtonClicked(final MealContract.Meal meal);
        void onItemClicked(final MealContract.Meal meal);


    }

    /// -------
    /// Classes

    class BusinessMenuListItemViewHolder extends RecyclerView.ViewHolder{

        BusinessMenuListItemViewHolder(View view){
            super(view);
        }

    }

}
