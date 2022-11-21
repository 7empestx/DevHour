package dev.hour.fragment.business;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import dev.hour.R;
import dev.hour.contracts.MealContract;
import dev.hour.contracts.RestaurantContract;

public class BusinessMenuDetailFragment {

    //set meal
    //then bind view

    /// ---------------------
    /// Public Static Members

    public final static String TAG = "BusinessMenuDetailFragment";

    /// Private Static Members
    Private final static
    private final static int                    STANDARD_WIDTH      = 192               ;
    private final static int                    STANDARD_HEIGHT     = 192               ;

    /// --------------
    /// Private Fields

    private MealContract.Menu.Presenter.InteractionListener interactionListener ;
    private MealContract.Menu                    menuDetails             ;

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

    @Override
    public void onResume() {
        super.onResume();

        bindData();

        final Object picture = (data.get("picture") == null) ?
                BusinessMenuDetailFragment.image.get("picture") : data.get("picture");

        if(picture != null) {

            final AppCompatImageView                    image       =
                    getView().findViewById(R.id.fragment_business_add_restaurant_image);

            final ByteArrayInputStream imageStream = (ByteArrayInputStream) picture;
            final Pair<ByteArrayInputStream, byte[]> pair        = clone(imageStream);

            if(pair != null) {

                BusinessUpdateRestaurantFragment.image.put("picture", pair.first);

                final byte[]    bytes   = pair.second;
                final Context context = getContext();

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

    private void bindData() {

        final String name       = (String) data.get("name")     ;
        final String ingredient =

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
