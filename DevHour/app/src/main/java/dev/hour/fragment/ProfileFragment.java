package dev.hour.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import dev.hour.R;
import dev.hour.contracts.MealContract;
import dev.hour.contracts.UserContract;

/**
 * Basic User Profile [Fragment] that allows a user to update their settings
 * and dietary preference filters.
 */
public class ProfileFragment extends Fragment implements
        UserContract.View,
        MealContract.Diet.View,
        OnClickListener {

    /// --------------
    /// Static Members

    public final static String TAG = "ProfileFragment";

    /// --------------
    /// Private Members

    private TextView                        firstNameTextView   ;
    private TextView                        lastNameTextView    ;
    private String                          userId              ;
    private UserContract.View.Listener      userListener        ;
    private MealContract.Diet.View.Listener dietListener        ;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = layoutInflater.inflate(R.layout.fragment_profile, container, false);

        firstNameTextView = layout.findViewById(R.id.fragment_profile_first_name);
        lastNameTextView = layout.findViewById(R.id.fragment_profile_last_name);

        // Populate the name views for the current user
        userListener.onGetUserRequest();

        // Set the diet filter switches to the correct state for the current user
        dietListener.onGetDietRequest(userId);

        return layout;

    }

    @Override
    public void setDiet(MealContract.Diet diet) {

    }

    @Override
    public void onDisplayDietInfo(MealContract.Diet diet) {

    }

    @Override
    public void update(UserContract.User user) {

    }

    @Override
    public void remove(UserContract.User user) {

    }

    @Override
    public void clearUsers() {

    }

    @Override
    public void setUserListener(UserContract.View.Listener listener) {
        this.userListener = listener;
    }

    @Override
    public void setDietListener(MealContract.Diet.View.Listener listener) {
        this.dietListener = listener;
    }

    /**
     * Invoked when the save changes Button has been clicked
     * @param view The [View] that has been clicked
     */
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDisplayUserInfo(UserContract.User user) {

        this.firstNameTextView.setText(user.getFirstName());
        this.lastNameTextView.setText(user.getLastName());
        this.userId = user.getId();

    }
}