package dev.hour.fragment;

import android.annotation.SuppressLint;
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
import android.widget.ImageButton;

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

public class BusinessAddRestaurantFragment extends Fragment implements
        RestaurantContract.View, View.OnClickListener {

    /// --------------
    /// Static Members

    public final static String TAG = "BusinessAddRestaurantFragment";

    /// --------------
    /// Private Fields

    private RestaurantContract.Presenter.InteractionListener interactionListener    ;

    private final static Map<String, Object> data                   = new HashMap<>();
    private final static Map<String, Object> tags                   = new HashMap<>();
    private final static Map<String, Object> image                  = new HashMap<>();

    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle) {

        final View layout =
                layoutInflater.inflate(R.layout.fragment_business_add_restaurant, viewGroup, false);

        final View               confirmButton   = layout.findViewById(R.id.fragment_business_add_restaurant_confirm_button);
        final View               tagButton       = layout.findViewById(R.id.fragment_business_add_restaurant_tag_button);
        final AppCompatImageView image           = layout.findViewById(R.id.fragment_business_add_restaurant_image);
        final View               backButton      = layout.findViewById(R.id.fragment_business_add_restaurant_back_button);

        confirmButton.setOnClickListener(this);
        tagButton.setOnClickListener(this);
        image.setOnClickListener(this);
        backButton.setOnClickListener(this);

        final Object picture = BusinessAddRestaurantFragment.image.get("picture");

        if(picture != null) {

            final ByteArrayInputStream                  imageStream = (ByteArrayInputStream) picture;
            final Pair<ByteArrayInputStream, byte[]>    pair        = clone(imageStream);

            if(pair != null) {

                BusinessAddRestaurantFragment.image.put("picture", pair.first);

                final byte[] bytes = pair.second;

                if(bytes.length > 0) {

                    final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    final Resources resources = getContext().getResources();

                    final int width     = (int) (192 * resources.getDisplayMetrics().density);
                    final int height    = (int) (192 * resources.getDisplayMetrics().density);

                    image.setImageBitmap(Bitmap.createScaledBitmap(
                            bitmap, width, height, false));

                    image.setClipToOutline(true);

                }

            }

        }

        return layout;

    }

    public Pair<ByteArrayInputStream, byte[]> clone(final ByteArrayInputStream inputStream) {

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

    @Override
    public void onResume() {
        super.onResume();

        bindData();

    }

    @Override
    public void setRestaurants(List<RestaurantContract.Restaurant> restaurants) {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.fragment_business_add_restaurant_confirm_button:

                persistData();

                if(this.interactionListener != null)
                    this.interactionListener.onCreateRestaurantRequest(data);

                break;

            case R.id.fragment_business_add_restaurant_tag_button:

                persistData();

                tags.clear();

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

                if(this.interactionListener != null)
                    this.interactionListener.onShowBusinessRestaurantListRequest();

                break;

            default: break;

        }

    }

    public void persistData() {

        data.clear();

        final EditText restaurantName =
                this.requireView()
                        .findViewById(R.id.fragment_business_add_restaurant_name_input);

        final EditText address1 =
                this.requireView()
                        .findViewById(R.id.fragment_business_add_restaurant_address_input_1);

        final EditText address2 =
                this.requireView()
                        .findViewById(R.id.fragment_business_add_restaurant_address_input_2);

        data.put("name",            restaurantName.getText().toString());
        data.put("address1",        address1.getText().toString());
        data.put("address2",        address2.getText().toString());
        data.put("pricing",         "0");
        data.put("picture",         image.get("picture"));
        data.put("content_length",  image.get("content_length"));

        final List<String> tags = new ArrayList<>();

        for(final Map.Entry<String, Object> entry: this.tags.entrySet())
            tags.add((String) entry.getValue());

        data.put("tags", tags);

    }

    public void bindData() {

        final String name =     (data.get("name") == null     ||
                ((String) data.get("name")).isEmpty()) ?
                "Restaurant Name" : (String) data.get("name");
        final String address1 = (data.get("address1") == null ||
                ((String) data.get("address1")).isEmpty()) ?
                "Street Address"       : (String) data.get("address1");
        final String address2 = (data.get("address2") == null ||
                ((String) data.get("address2")).isEmpty()) ?
                "City State, Zip Code" : (String) data.get("address2");

        ((EditText) this.requireView()
                .findViewById(R.id.fragment_business_add_restaurant_name_input))
                .setHint(name);

        ((EditText) this.requireView()
                .findViewById(R.id.fragment_business_add_restaurant_address_input_1))
                .setHint(address1);

        ((EditText) this.requireView()
                .findViewById(R.id.fragment_business_add_restaurant_address_input_2))
                .setHint(address2);

    }

    public void setInteractionListener(RestaurantContract.Presenter.InteractionListener listener) {

        this.interactionListener = listener;

    }

}
