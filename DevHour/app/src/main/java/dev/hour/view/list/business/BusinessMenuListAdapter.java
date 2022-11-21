package dev.hour.view.list.business;

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

    /**
     * Attempts to fit the given text into the [TextView] limited by the given bounds.*
     * @param textView The [TextView] to fit the text
     * @param otherView The [View] to relate the dimensions to
     * @param text The [String] value to fit.
     */
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

            final View          layout      =
                    view.findViewById(R.id.fragment_business_menu_list_item_content);
            final TextView      title       =
                    view.findViewById(R.id.fragment_business_menu_list_item_title);
            final ImageButton   image       =
                    view.findViewById(R.id.fragment_business_menu_list_item_image);
            final TextView      details1    =
                    view.findViewById(R.id.fragment_business_menu_list_item_details_text_1);
            final TextView      details2    =
                    view.findViewById(R.id.fragment_business_menu_list_item_details_text_2);
            final ImageView     editButton  =
                    view.findViewById(R.id.fragment_business_menu_list_item_edit_button);

            final Bitmap menuImage = getImageFor(meal);

            if(menuImage != null) {

                image.setImageBitmap(Bitmap.createScaledBitmap(
                        menuImage, 192, 192, false));

                image.setClipToOutline(true);

            }

            title.setText(meal.getName());
            details2.setText(meal.getCalories() + " Calories");

            final String ingredientsList = getIngredientsList(meal);

            holder.itemView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {

                    FitTextInsideTextView(details1, layout, ingredientsList);

                    holder.itemView.getViewTreeObserver().removeOnPreDrawListener(this);

                    return true;

                }
                
            });

            holder.itemView.setTag(String.valueOf(position));
            editButton.setTag(String.valueOf(position));

            holder.itemView.setTranslationX(0.0f);

        }

    }

    /**
     * Sets the list of Meals presented by the BusinessMenuListAdapter
     */
    public void setMealsList(final List <MealContract.Meal> meals){

        this.mealsList = meals;

        if(this.images == null)
            this.images = new HashMap<>();

        else this.images.clear();

        notifyDataSetChanged();

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
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        int position;

        switch(v.getId()) {

            case R.id.fragment_business_menu_list_item_edit_button:

                position = Integer.parseInt(v.getTag().toString());

                if(this.listener != null)
                    this.listener.onEditMealButtonClicked(this.mealsList.get(position));

                break;

            default:

                position = Integer.parseInt(v.getTag().toString());

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

    /**
     * Sets the [Listener] that will receive callbacks on user interaction
     * @param listener The [Listener] that will receive callbacks on user interaction
     */
    public void setListener(final BusinessMenuListAdapter.Listener listener) {

        this.listener = listener;

    }

    /**
     * Retrieves the Ingredients as a list
     * @param meal The meal to retrieve the ingredients from
     * @return String value of the ingredients list
     */
    private String getIngredientsList(final MealContract.Meal meal) {

        final StringBuilder builder = new StringBuilder();
        int index = 0;
        int size = meal.getIngredients().size();

        for(final Map.Entry<String, String> entry: meal.getIngredients().entrySet()) {

            builder.append(entry.getValue());

            if(index < (size - 1))
                builder.append(", ");

        }

        return builder.toString();

    }

    /// ----------
    /// Interfaces

    /**
     * Defines callbacks that are invoked on user interaction
     */
    public interface Listener {

        void onEditMealButtonClicked(final MealContract.Meal meal);
        void onItemClicked(final MealContract.Meal meal);

    }

    /// -------
    /// Classes

    /**
     * The [ViewHolder] instance that holds the view to be bound.
     */
    class BusinessMenuListItemViewHolder extends RecyclerView.ViewHolder{

        BusinessMenuListItemViewHolder(View view){
            super(view);
        }

    }

}
