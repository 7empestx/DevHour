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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import dev.hour.R;
import dev.hour.contracts.MealContract;
import dev.hour.contracts.UserContract;
import dev.hour.model.DietPreferenceItem;
import dev.hour.model.DietPreferenceItemViewHolder;

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
    private ArrayAdapter<DietPreferenceItem> adapter            ;
    private DietPreferenceItem[]            dietPrefs           ;
    private Set<String>                     user_allergens      ;
    private Set<String>                     user_diets          ;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = layoutInflater.inflate(R.layout.fragment_user_profile, container, false);

        firstNameTextView   = layout.findViewById(R.id.fragment_profile_first_name);
        lastNameTextView    = layout.findViewById(R.id.fragment_profile_last_name);

        listView = layout.findViewById(R.id.fragment_profile_list_view);

        dietPrefs = (DietPreferenceItem[]) onRetainCustomNonConfigurationInstance();
        if (dietPrefs == null) {
            dietPrefs = new DietPreferenceItem[] {
                    new DietPreferenceItem("Vegetarian", true), new DietPreferenceItem("Vegan", true),
                    new DietPreferenceItem("Kosher", true), new DietPreferenceItem("Halal", true),
                    new DietPreferenceItem("Dairy-Free", false), new DietPreferenceItem("Beef", false),
                    new DietPreferenceItem("Peanut-Free", false), new DietPreferenceItem("Gluten-Free", false),
                    new DietPreferenceItem("Nut-Free", false), new DietPreferenceItem("Fish-Free", false),
                    new DietPreferenceItem("ShellFish-Free", false), new DietPreferenceItem("Banana-Free", false),
                    new DietPreferenceItem("Pineapple-Free", false), new DietPreferenceItem("Strawberry-Free", false),
                    new DietPreferenceItem("Tomato-Free", false), new DietPreferenceItem("Olive-Free", false),
                    new DietPreferenceItem("Wheat-Free", false), new DietPreferenceItem("Onion-Free", false),
            };
        }
        initSearchWidget(layout);
        setUpOnclickListener();
        userListener.onGetUserRequest();

        ArrayList<DietPreferenceItem> dietPrefList = new ArrayList<DietPreferenceItem>();
        dietPrefList.addAll( Arrays.asList(dietPrefs) );

        adapter = new DietPreferenceArrayAdapter(getContext(), dietPrefList);
        listView.setAdapter(adapter);
        dietListener.onGetDietRequest(userId);

        return layout;

    }

    private void setUpOnclickListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                // Toggle the item object and checkbox view
                DietPreferenceItem dietPreferenceItem = adapter.getItem(pos);
                dietPreferenceItem.toggleChecked();
                DietPreferenceItemViewHolder viewHolder = (DietPreferenceItemViewHolder) view.getTag();
                viewHolder.getCheckBox().setChecked(dietPreferenceItem.isChecked());

                // Update user preference lists locally with addition or removal of preference
                // and send off request to update the table to match the local state.
                Map<String, Object> data = new HashMap<>();
                if (dietPreferenceItem.isChecked()) {
                    if (dietPreferenceItem.isAllergen()) {
                        user_allergens.add(dietPreferenceItem.getName());
                    } else {
                        user_diets.add(dietPreferenceItem.getName());
                    }
                } else {
                    if (dietPreferenceItem.isAllergen()) {
                        user_allergens.remove(dietPreferenceItem.getName());
                    } else {
                        user_diets.remove(dietPreferenceItem.getName());
                    }
                }
                data.put("id", userId);
                data.put("allergens", new ArrayList<>(user_allergens));
                data.put("diets", new ArrayList<>(user_diets));

                dietListener.onUpdateDietRequest(data);
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
                ArrayList<DietPreferenceItem> filteredIngredientsAndDiets = new ArrayList<DietPreferenceItem>();
                for (DietPreferenceItem dietPref : dietPrefs) {
                    if (dietPref.getName().toLowerCase().contains(s.toLowerCase())) {
                        filteredIngredientsAndDiets.add(dietPref);
                    }
                }

                DietPreferenceArrayAdapter adapter = new DietPreferenceArrayAdapter(getContext(), filteredIngredientsAndDiets);
                listView.setAdapter(adapter);

                return false;
            }
        });

    }

    public Object onRetainCustomNonConfigurationInstance () {
        return dietPrefs ;
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
        user_allergens = new HashSet<>(diet.getAllergens());
        user_diets = new HashSet<>(diet.getDiets());

        // Set the checkboxes to match user's saved diet preferences.
        for (int i = 0; i < listView.getCount(); i++) {
            DietPreferenceItem item = (DietPreferenceItem) listView.getItemAtPosition(i);
            if (user_allergens.contains(item.getName())) {
                item.toggleChecked();
                item.setAsAllergen();
            } else if (user_diets.contains(item.getName())) {
                item.toggleChecked();
                item.setAsDiet();
            }
        }
    }

    // Called after attempt to update diet preference for the user.
    public void onUpdateDietRequest() {
        // TODO: Reset checkbox if request was not successfully written to table

    }
}