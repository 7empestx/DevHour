package dev.hour.fragment.general;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import dev.hour.R;
import dev.hour.view.list.business.IngredientListAdapter;

public class IngredientFragment  extends Fragment implements View.OnClickListener{

    /// ---------------------
    /// Public Static Members

    public final static String INGREDIENT = "IngredientFragment"  ;

    /// ---------------
    /// Private Members

    private Listener            listener        ;
    private Object              requestor       ;
    private Map<String, Object> export          ;
    private IngredientListAdapter      ingredientListAdapter  ;

    /// --------
    /// Fragment

    /**
     * Invoked when the [IngredientFragment] should create its' view. Inflates the
     * view and any persist state
     * @param layoutInflater The [LayoutInflater] responsible for inflating the view
     * @param viewGroup The parent
     * @param savedInstanceState SavedInstanceState
     * @return [View] instance
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup,
                             final Bundle savedInstanceState) {

        final View layout               =
                layoutInflater.inflate(R.layout.fragment_ingredient, viewGroup, false);

        if(this.ingredientListAdapter == null)
            this.ingredientListAdapter = new IngredientListAdapter();

        final RecyclerView recyclerView    =
                layout.findViewById(R.id.fragment_ingredient_recycler_view);
        final View          addIngredientButton =
                layout.findViewById(R.id.fragment_ingredient_button);
        final View          confirmButton =
                layout.findViewById(R.id.fragment_ingredient_confirm_button);
        final View          backButton =
                layout.findViewById(R.id.fragment_ingredient_back_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(this.ingredientListAdapter);

        confirmButton.setOnClickListener(this);
        addIngredientButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        return layout;

    }

    /**
     * Invoked when the IngredientFragment is to be resumed
     */
    @Override
    public void onResume() {
        super.onResume();

        if((this.export != null) && (this.export.get("ingredients") != null)) {

            final List<String> ingredients = (List) this.export.get("ingredients");

            for(final String ingredient: ingredients)
                this.ingredientListAdapter.addIngredient(ingredient);

        }

    }

    /**
     * Invoked when a [View] of interest is clicked by the user. Invokes the corresponding
     * callbacks depending on the [View] that was clicked
     * @param view The [View] instance that was clicked
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(final View view) {

        final int id = view.getId();

        switch(id) {

            case R.id.fragment_ingredient_button:

                final String ingredient =
                        ((EditText) getView().findViewById(R.id.fragment_ingredient_input))
                                .getText().toString();

                if(this.ingredientListAdapter != null)
                    this.ingredientListAdapter.addIngredient(ingredient);

                break;

            case R.id.fragment_ingredient_confirm_button:

                if(this.export != null)
                    this.export.put("ingredients", this.ingredientListAdapter.getIngredients());

                if(this.listener != null)
                    this.listener.onIngredientReceived(this.requestor);

                break;

            case R.id.fragment_ingredient_back_button:

                this.export.clear();

                if(this.listener != null)
                    this.listener.onIngredientCancelled(this.requestor);

                break;

        }

    }

    /// --------------
    /// Public Methods

    /**
     * Sets the [Listener] that will receive callbacks when the user interacts with the [Fragment]
     * @param listener The [Listener] that will receive callbacks when the user interacts with the
     *                 fragment
     * @param requestor The instance that requested the [Fragment]
     */
    public void setListener(final Listener listener, final Object requestor) {

        this.listener   = listener;
        this.requestor  = requestor;

    }

    /**
     * Sets the [Map] export object. The selected image will be placed in the given object
     * to be retrieved by the requestor
     * @param export The [Map] instance that holds the image and content length
     */
    public void setExport(final Map<String, Object> export) {

        this.export = export;

    }

    /// ----------
    /// Interfaces

    /**
     * Defines callbacks to allow generic classes to request an image.
     */
    public interface Listener {

        void onIngredientReceived(final Object requestor);
        void onIngredientCancelled(final Object requestor);

    }

}
