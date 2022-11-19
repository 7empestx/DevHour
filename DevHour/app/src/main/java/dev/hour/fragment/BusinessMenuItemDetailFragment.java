package dev.hour.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import dev.hour.R;
import dev.hour.contracts.AuthenticatorContract;
import dev.hour.contracts.MealContract;

public class BusinessMenuItemDetailFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "BusinessMenuItemDetail";

    /// --------------
    /// Private Fields

    @Override
    public View onCreateView(LayoutInflater lf, ViewGroup vg, Bundle b){
        final View layout = (View) lf.inflate(R.layout.fragment_business_menu_item_detail, vg, false);
        return layout;
    }

    /// --------------------
    /// View.OnClickListener

    /**
     * Invoked when the either the Add Ingredient, Tag, or Confirm Button has been clicked
     * @param view The [View] that has been clicked
     */
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {

        final String input = "Text from the text box";

        switch(view.getId()) {

            case R.id.fragment_business_menu_item_detail_ingredient_button:

                final Button signInButton =
                        this.requireView().findViewById(R.id.fragment_business_menu_item_detail_ingredient_button);

                signInButton.setEnabled(false);

                break;
            case R.id.fragment_business_menu_item_detail_tag_button:

                final Button tagButton =
                        this.requireView().findViewById(R.id.fragment_business_menu_item_detail_tag_button);

                tagButton.setEnabled(false);

                break;

            case R.id.fragment_business_menu_item_detail_confirm_button:

                final Button confirmButton =
                        this.requireView().findViewById(R.id.fragment_business_menu_item_detail_confirm_button);

                confirmButton.setEnabled(false);


                break;
            default:

        }

    }

}
