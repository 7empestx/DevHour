package dev.hour.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

import dev.hour.R;
import dev.hour.contracts.MealContract;
import dev.hour.view.list.BusinessMenuItemDetailListAdapter;

public class BusinessMenuItemDetailFragment extends Fragment implements View.OnClickListener{
    public final static String TAG = "BusinessMenuItemDetail";

    /// --------------
    /// Private Fields

    private BusinessMenuItemDetailListAdapter businessMenuItemDetailListAdapter;
    private MealContract.Menu.Presenter.InteractionListener listener;

    @Override
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup viewGroup, final Bundle bundle){

        if (this.businessMenuItemDetailListAdapter == null)
            this.businessMenuItemDetailListAdapter = new BusinessMenuItemDetailListAdapter();

        final View layout =
                layoutInflater.inflate(R.layout.fragment_business_menu_item_detail, viewGroup, false);
        final RecyclerView recyclerView =
                layout.findViewById(R.id.fragment_business_menu_item_detail_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.businessMenuItemDetailListAdapter);

        return layout;
    }

    public void setInteractionListener(final MealContract.Menu.Presenter.InteractionListener listener) {
        this.listener = listener;
    }

    /// --------------------
    /// View.OnClickListener

    /**
     * Invoked when the either the Add Ingredient, Tag, or Confirm Button has been clicked
     * @param view The [View] that has been clicked
     */
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {

        Map <String, Object> input;

        switch(view.getId()) {

            case R.id.fragment_business_menu_item_detail_meal_image:

                this.listener.onEditPicture();
                break;
            case R.id.fragment_business_menu_item_detail_ingredient_button:

                this.listener.onAddIngredientButton();
                break;
            case R.id.fragment_business_menu_item_detail_tag_button:

                this.listener.onTagButton();
                break;

            case R.id.fragment_business_menu_item_detail_confirm_button:

                EditText editText = this.getView().findViewById(R.id.fragment_business_menu_item_detail_name_input);
                input = new HashMap<>();
                input.put("name", editText.getText().toString());

                this.listener.onConfirmButton(input);
                break;
            default:

        }

    }

}
