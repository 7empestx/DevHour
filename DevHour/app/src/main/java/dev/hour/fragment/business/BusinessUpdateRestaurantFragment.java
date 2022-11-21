package dev.hour.fragment.business;

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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.hour.R;
import dev.hour.contracts.RestaurantContract;

public class BusinessUpdateRestaurantFragment extends Fragment implements View.OnClickListener {

    /// ---------------------
    /// Public Static Members

    public final static String TAG = "BusinessAddRestaurantFragment";

    /// ----------------------
    /// Private Static Members

    private final static Map<String, Object>    data                = new HashMap<>()   ;
    private final static Map<String, Object>    tags                = new HashMap<>()   ;
    private final static Map<String, Object>    image               = new HashMap<>()   ;
    private final static int                    STANDARD_WIDTH      = 192               ;
    private final static int                    STANDARD_HEIGHT     = 192               ;

    /// --------------
    /// Private Fields

    private RestaurantContract.Presenter.InteractionListener interactionListener    ;
    private RestaurantContract.Restaurant                    restaurant             ;

    /// --------
    /// Fragment

    /**
     * Invoked when the [BusinessAddRestaurantFragment] should create its' view. Inflates the
     * view and any persist state
     * @param layoutInflater The [LayoutInflater] responsible for inflating the view
     * @param viewGroup The parent
     * @param bundle SavedInstanceState
     * @return [View] instance
     */
    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {

        final View layout =
                layoutInflater.inflate(R.layout.fragment_business_update_restaurant, viewGroup, false);

        final View               confirmButton   = layout.findViewById(R.id.fragment_business_add_restaurant_confirm_button);
        final View               tagButton       = layout.findViewById(R.id.fragment_business_add_restaurant_tag_button);
        final AppCompatImageView image           = layout.findViewById(R.id.fragment_business_add_restaurant_image);
        final View               backButton      = layout.findViewById(R.id.fragment_business_add_restaurant_back_button);

        confirmButton.setOnClickListener(this);
        tagButton.setOnClickListener(this);
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
                BusinessUpdateRestaurantFragment.image.get("picture") : data.get("picture");

        if(picture != null) {

            final AppCompatImageView                    image       =
                    getView().findViewById(R.id.fragment_business_add_restaurant_image);
            
            final ByteArrayInputStream                  imageStream = (ByteArrayInputStream) picture;
            final Pair<ByteArrayInputStream, byte[]>    pair        = clone(imageStream);

            if(pair != null) {

                BusinessUpdateRestaurantFragment.image.put("picture", pair.first);

                final byte[]    bytes   = pair.second;
                final Context   context = getContext();

                if((bytes.length > 0) && (context != null)) {

                    final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

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
     * Invoked when a [View] of interest is clicked by the user. Invokes the corresponding
     * callbacks depending on the [View] that was clicked
     * @param view The [View] instance that was clicked
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.fragment_business_add_restaurant_confirm_button:

                persistData();

                if(this.interactionListener != null)
                    this.interactionListener.onCreateRestaurantRequest(data);

                image.clear();
                tags.clear();
                data.clear();

                this.restaurant = null;

                break;

            case R.id.fragment_business_add_restaurant_tag_button:

                persistData();

                if(this.interactionListener != null)
                    this.interactionListener.onShowBusinessAddRestaurantTagRequest(tags);

                break;

            case R.id.fragment_business_add_restaurant_image:

                persistData();

                image.clear();

                if(this.interactionListener != null)
                    this.interactionListener.onShowBusinessAddRestaurantImageRequest(image);

                break;

            case R.id.fragment_business_add_restaurant_back_button:

                data.clear();
                image.clear();
                tags.clear();

                this.restaurant = null;

                if(this.interactionListener != null)
                    this.interactionListener.onShowBusinessRestaurantListRequest();

                break;

            default: break;

        }

    }

    /// --------------
    /// Public Methods

    /**
     * Sets the [RestaurantContract.Presenter.InteractionListener] that will receive callbacks
     * corresponding to user interaction.
     * @param listener The [RestaurantContract.Presenter.InteractionListener] to set
     */
    public void setInteractionListener(RestaurantContract.Presenter.InteractionListener listener) {

        this.interactionListener = listener;

    }

    /**
     * Sets the RestaurantContract.Restaurant instance to the given RestaurantContract.Restaurant
     * instance if not null. This will bind the persist data of the given restaurant.
     * @param restaurant The [RestaurantContract.Restaurant] instance to set.
     */
    public void setRestaurant(final RestaurantContract.Restaurant restaurant) {

        if(restaurant != null) {

            this.restaurant = restaurant;

            image.clear();

            data.put("id", this.restaurant.getId());
            data.put("name", this.restaurant.getName());
            data.put("address1", this.restaurant.getAddress1());
            data.put("address2", this.restaurant.getAddress2());
            data.put("menu_id", this.restaurant.getMenuId());
            data.put("pricing", this.restaurant.getPricing());
            data.put("longitude", String.valueOf(this.restaurant.getLongitude()));
            data.put("latitude", String.valueOf(this.restaurant.getLatitude()));
            data.put("picture", toInputStream((ByteArrayOutputStream) this.restaurant.getImageStream()));
            data.put("picture_id", this.restaurant.getPictureId());
            data.put("restaurant_owner", this.restaurant.getOwnerId());
            tags.put("tags", this.restaurant.getTags());

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

        final EditText restaurantName =
                this.requireView()
                        .findViewById(R.id.fragment_business_add_restaurant_name_input);

        final EditText address1 =
                this.requireView()
                        .findViewById(R.id.fragment_business_add_restaurant_address_input_1);

        final EditText address2 =
                this.requireView()
                        .findViewById(R.id.fragment_business_add_restaurant_address_input_2);

        image.putIfAbsent("picture", data.get("picture"));

        final ByteArrayInputStream imageStream = (ByteArrayInputStream) image.get("picture");

        if(imageStream != null)
            image.put("content_length", imageStream.available());

        data.put("name",            restaurantName.getText().toString());
        data.put("address1",        address1.getText().toString());
        data.put("address2",        address2.getText().toString());
        data.put("pricing",         "0");

        final Object retrieved = BusinessUpdateRestaurantFragment.tags.get("tags");

        if(retrieved != null)
            data.put("tags", retrieved);

        else data.put("tags", new ArrayList<>());

    }

    /**
     * Binds any persist data to the layout
     */
    private void bindData() {

        final String name       = (String) data.get("name")     ;
        final String address1   = (String) data.get("address1") ;
        final String address2   = (String) data.get("address2") ;

        if((name != null) && (!name.isEmpty()))
            ((EditText) this.requireView()
                .findViewById(R.id.fragment_business_add_restaurant_name_input))
                .setText(name);

        if((address1 != null) && (!address1.isEmpty()))
            ((EditText) this.requireView()
                .findViewById(R.id.fragment_business_add_restaurant_address_input_1))
                .setText(address1);

        if((address2 != null) && (!address2.isEmpty()))
            ((EditText) this.requireView()
                .findViewById(R.id.fragment_business_add_restaurant_address_input_2))
                .setText(address2);

    }

}
