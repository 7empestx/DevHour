package dev.hour.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.hour.R;
import dev.hour.contracts.MealContract;
import dev.hour.view.list.business.IngredientListAdapter;

public class BusinessUpdateMenuItemFragment extends Fragment implements View.OnClickListener{

    /// ---------------------
    /// Public Static Members

    public final static String TAG = "BusinessUpdateMenuItemFragment";

    /// ----------------------
    /// Private Static Members

    private final static Map<String, Object>    data                = new HashMap<>()   ;
    private final static Map<String, Object>    tags                = new HashMap<>()   ;
    private final static Map<String, Object>    ingredients         = new HashMap<>()   ;
    private final static Map<String, Object>    image               = new HashMap<>()   ;
    private final static int                    STANDARD_WIDTH      = 192               ;
    private final static int                    STANDARD_HEIGHT     = 192               ;

    /// --------------
    /// Private Fields

    private MealContract.Menu.Presenter.InteractionListener listener                            ;
    private IngredientListAdapter                           ingredientListAdapter               ;
    private MealContract.Meal                               meal                                ;

    /// --------
    /// Fragment

    /**
     * Invoked when the [BusinessUpdateMenuItemFragment] should create its' view. Inflates the
     * view and any persist state
     * @param layoutInflater The [LayoutInflater] responsible for inflating the view
     * @param viewGroup The parent
     * @param bundle SavedInstanceState
     * @return [View] instance
     */
    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {

        if (this.ingredientListAdapter == null)
            this.ingredientListAdapter = new IngredientListAdapter();

        final View layout =
                layoutInflater.inflate(R.layout.fragment_business_update_menu_item, viewGroup, false);
        final RecyclerView       recyclerView     =
                layout.findViewById(R.id.fragment_business_update_menu_item_recycler_view);
        final View               confirmButton    =
                layout.findViewById(R.id.fragment_business_update_menu_item_confirm_button);
        final View               tagButton        =
                layout.findViewById(R.id.fragment_business_update_menu_item_tag_button);
        final View               ingredientButton =
                layout.findViewById(R.id.fragment_business_update_menu_item_ingredient_button);
        final AppCompatImageView image            =
                layout.findViewById(R.id.fragment_business_update_menu_item_meal_image);
        final View               backButton       =
                layout.findViewById(R.id.fragment_business_update_menu_item_back_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.ingredientListAdapter);

        confirmButton.setOnClickListener(this);
        tagButton.setOnClickListener(this);
        ingredientButton.setOnClickListener(this);
        image.setOnClickListener(this);
        backButton.setOnClickListener(this);

        return layout;

    }

    /**
     * Invoked when the fragment should resume. Binds any persist data to the edit texts.
     */
    @Override
    public void onResume() {
        super.onResume();

        bindData();

        final Object picture = (data.get("picture") == null) ?
                BusinessUpdateMenuItemFragment.image.get("picture") : data.get("picture");

        if(picture != null) {

            final AppCompatImageView                    image       =
                    getView().findViewById(R.id.fragment_business_update_menu_item_meal_image);

            final ByteArrayInputStream imageStream = (ByteArrayInputStream) picture;
            final Pair<ByteArrayInputStream, byte[]> pair        = clone(imageStream);

            if(pair != null) {

                BusinessUpdateMenuItemFragment.image.put("picture", pair.first);

                final byte[]    bytes   = pair.second;
                final Context context = getContext();

                if((bytes.length > 0) && (context != null)) {

                    final Bitmap bitmap = BitmapFactory.decodeByteArray(pair.second, 0, bytes.length);

                    final Resources resources = context.getResources();

                    final int width     =
                            (int) (STANDARD_WIDTH * resources.getDisplayMetrics().density);
                    final int height    =
                            (int) (STANDARD_HEIGHT * resources.getDisplayMetrics().density);

                    image.setImageBitmap(Bitmap.createScaledBitmap(
                            bitmap, width, height, false));

                    image.setClipToOutline(true);

                }

            }

        }

    }

    /// --------------------
    /// View.OnClickListener

    /**
     * Invoked when the either the Add Ingredient, Tag, or Confirm Button has been clicked
     * @param view The [View] that has been clicked
     */
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch(view.getId()) {

            case R.id.fragment_business_update_menu_item_confirm_button:

                persistData();

                if(this.listener != null)
                    this.listener.onCreateMealRequest(data);

                image.clear();
                tags.clear();
                data.clear();

                this.meal = null;

                break;

            case R.id.fragment_business_update_menu_item_tag_button:

                persistData();

                if(this.listener != null)
                    this.listener.onAddMenuItemTagRequest(tags);

                break;

            case R.id.fragment_business_update_menu_item_ingredient_button:

                persistData();

                if(this.listener != null)
                    this.listener.onAddMenuItemIngredientRequest(ingredients);

                break;

            case R.id.fragment_business_update_menu_item_meal_image:

                persistData();

                if(this.listener != null)
                    this.listener.onAddMenuItemImageRequest(image);

                break;

            case R.id.fragment_business_update_menu_item_back_button:

                data.clear();
                image.clear();
                tags.clear();

                this.meal = null;

                if(this.listener != null)
                    this.listener.onShowMenuRequest();

                break;

            default: break;

        }

    }

    /**
     * Sets the [RestaurantContract.Presenter.InteractionListener] that will receive callbacks
     * corresponding to user interaction.
     * @param listener The [RestaurantContract.Presenter.InteractionListener] to set
     */
    public void setInteractionListener(final MealContract.Menu.Presenter.InteractionListener listener) {

        this.listener = listener;

    }

    /**
     * Sets the RestaurantContract.Restaurant instance to the given RestaurantContract.Restaurant
     * instance if not null. This will bind the persist data of the given restaurant.
     * @param meal The [RestaurantContract.Restaurant] instance to set.
     */
    public void setMeal(final MealContract.Meal meal) {

        if(meal != null) {

            this.meal = meal;

            data.put("id", this.meal.getId());
            data.put("name", this.meal.getName());
            data.put("calories", String.valueOf(this.meal.getCalories()));
            data.put("picture", toInputStream((ByteArrayOutputStream) this.meal.getImageStream()));

            image.put("picture", data.get("picture"));
            tags.put("tags", this.meal.getTags());
            ingredients.put("ingredients", this.meal.getIngredients());

        }

    }

    /// ---------------
    /// Private Methods

    /**
     * Clones the given [ByteArrayInputStream] and returns a [Pair] containing a copy of the
     * bytes contained in the [ByteArrayInputStream] and the [ByteArrayInputStream]
     * @param inputStream The input stream to copy
     * @return [Pair] with a [ByteArrayInputStream] and byte array
     */
    private Pair<ByteArrayInputStream, byte[]> clone(final ByteArrayInputStream inputStream) {

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytes;

        Pair<ByteArrayInputStream, byte[]> result = null;

        try {

            do {

                bytes = inputStream.read(buffer);

                if (bytes == -1) break;

                outputStream.write(buffer, 0, bytes);

            } while (true);

            byte[] copy1 = outputStream.toByteArray();
            byte[] copy2 = outputStream.toByteArray();

            result = new Pair<>(new ByteArrayInputStream(copy1), copy2);

            outputStream.flush();
            outputStream.close();

        } catch(final Exception exception) {

            Log.e(TAG, exception.getMessage());

        }

        return result;

    }

    /**
     * Converts the given [ByteArrayOutputStream] to a copy [ByteArrayInputStream]
     * @param outputStream The [ByteArrayOutputStream] to copy
     * @return [ByteArrayInputStream]
     */
    private ByteArrayInputStream toInputStream(final ByteArrayOutputStream outputStream) {

        ByteArrayInputStream result = null;

        if(outputStream != null)
            result = new ByteArrayInputStream(outputStream.toByteArray());

        return result;

    }

    /**
     * Saves the [EditText] and image data, if any, to persist across tear-down.
     */
    private void persistData() {

        final EditText mealName =
                this.requireView()
                        .findViewById(R.id.fragment_business_update_menu_item_name_input);

        final ByteArrayInputStream imageStream = (ByteArrayInputStream) image.get("picture");

        if((data.get("picture") == null) && (imageStream != null)) {
            data.put("picture", image.get("picture"));
            data.put("content_length", imageStream.available());
        }
        else if(data.get("picture") != null) {
            image.putIfAbsent("picture", data.get("picture"));
            image.putIfAbsent("content_length", ((ByteArrayInputStream)data.get("picture")).available());
        }

        data.put("name",     mealName.getText().toString());
        data.put("pricing",  "0");

        final Object retrieved = BusinessUpdateMenuItemFragment.tags.get("tags");

        if(retrieved != null)
            data.put("tags", retrieved);

        else data.put("tags", new ArrayList<>());

        final Object retrievedIngredients =
                BusinessUpdateMenuItemFragment.ingredients.get("ingredients");

        if(retrievedIngredients != null)
            data.put("ingredients", retrievedIngredients);

        else data.put("ingredients", new ArrayList<>());

    }

    /**
     * Binds any persist data to the layout
     */
    private void bindData() {

        final String name = (String) data.get("name")     ;

        if((name != null) && (!name.isEmpty()))
            ((EditText) this.requireView()
                    .findViewById(R.id.fragment_business_update_menu_item_name_input))
                    .setText(name);

        final Object list = BusinessUpdateMenuItemFragment.ingredients.get("ingredients");

        if((list != null) && (this.ingredientListAdapter != null)) {

        }

    }

}
