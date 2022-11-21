package dev.hour.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
    private ListView                        listView            ;
    private ArrayAdapter<String>            adapter             ;
    // TODO: Populate list with more ingredients, potentially even just grab this from a table instead
    private String[] ingredientAndDietArray = {"Vegetarian",  "Halal", "Kosher","Vegan", "Dairy-Free",
            "Beef", "Gluten-Free", "Peanut-Free", "ShellFish-Free", "Fish-Free", "Banana",
            "Strawberry", "Pineapple", "Wheat", "Olive", "Onion", "Tomato", "Mushroom"};

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = layoutInflater.inflate(R.layout.fragment_user_profile, container, false);

        firstNameTextView   = layout.findViewById(R.id.fragment_profile_first_name);
        lastNameTextView    = layout.findViewById(R.id.fragment_profile_last_name);

        listView = layout.findViewById(R.id.fragment_profile_list_view);
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_multiple_choice, ingredientAndDietArray);
        listView.setAdapter(adapter);

        // Populate the name views for the current user
        userListener.onGetUserRequest();
        dietListener.onGetDietRequest(userId);

        initSearchWidget(layout);
        setUpOnclickListener();

        return layout;

    }

    private void setUpOnclickListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String selectedString = (String) (listView.getItemAtPosition(pos));

                // Todo: Send request to Diet table to remove or add diet preference for user, for this item
            }
        });

    }

    private void initSearchWidget(View layout) {

        SearchView searchView = (SearchView) layout.findViewById(R.id.fragment_profile_search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<String> filteredIngredientsAndDiets = new ArrayList<String>();
                for (String diet_preference : ingredientAndDietArray) {
                    if (diet_preference.toLowerCase().contains(s.toLowerCase())) {
                        filteredIngredientsAndDiets.add(diet_preference);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_list_item_multiple_choice,filteredIngredientsAndDiets);
                listView.setAdapter(adapter);

                return false;
            }
        });

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

    @Override
    public void setDiet(List<MealContract.Meal> meals) {

    }

    @Override
    public void setDietListener(MealContract.Diet.View.Listener listener) {
        this.dietListener = listener;
    }

    @Override
    public void onDisplayDietInfo(MealContract.Diet diet) {
        // TODO: Set checkboxes as selected for diet preferences set in given diet
    }
}