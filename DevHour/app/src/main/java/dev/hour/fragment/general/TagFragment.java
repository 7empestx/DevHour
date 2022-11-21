package dev.hour.fragment.general;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import dev.hour.R;

public class TagFragment  extends Fragment implements View.OnClickListener{

    /// ---------------------
    /// Public Static Members

    public final static String TAG = "TagFragment"  ;

    /// ---------------
    /// Private Members

    private Listener            listener    ;
    private Object              requestor   ;
    private Map<String, Object> export      ;

    /// --------
    /// Fragment

    /**
     * Invoked when the [TagFragment] should create its' view. Inflates the
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
                layoutInflater.inflate(R.layout.fragment_tag, viewGroup, false);

        //if(this.businessRestaurantListAdapter == null)
            //this.businessRestaurantListAdapter = new BusinessRestaurantListAdapter();

        final RecyclerView recyclerView    =
                layout.findViewById(R.id.fragment_tag_recycler_view);
        final View          addTagButton =
                layout.findViewById(R.id.fragment_tag_button);
        final View          confirmButton =
                layout.findViewById(R.id.fragment_tag_confirm_button);
        final View          backButton =
                layout.findViewById(R.id.fragment_tag_back_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setAdapter(this.businessRestaurantListAdapter);

        addTagButton.setOnClickListener(this);

        return layout;

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

            case R.id.fragment_add_picture_upload_picture_button:

                break;

            case R.id.fragment_add_picture_save_picture_button:

                break;

            case R.id.fragment_add_picture_back_button:

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

    /// ---------------
    /// Private Methods

    /// ----------
    /// Interfaces

    /**
     * Defines callbacks to allow generic classes to request an image.
     */
    public interface Listener {

        void onTagReceived(final Object requestor);
        void onTagCancelled(final Object requestor);

    }

}
