package dev.hour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.hour.authenticator.Authenticator;
import dev.hour.contracts.AuthenticatorContract;
import dev.hour.contracts.MealContract;
import dev.hour.contracts.RestaurantContract;
import dev.hour.contracts.UserContract;
import dev.hour.database.MealDatabase;
import dev.hour.database.MenuDatabase;
import dev.hour.database.RestaurantDatabase;
import dev.hour.database.UserDatabase;
import dev.hour.database.DietDatabase;
import dev.hour.fragment.business.BusinessUpdateRestaurantFragment;
import dev.hour.fragment.BusinessUpdateMenuItemFragment;
import dev.hour.fragment.business.BusinessMenuListFragment;
import dev.hour.fragment.general.LoginFragment;
import dev.hour.fragment.MapFragment;
import dev.hour.fragment.ProfileFragment;
import dev.hour.fragment.CustomerRestaurantListFragment;
import dev.hour.fragment.general.SignUpFragment;
import dev.hour.fragment.business.BusinessRestaurantListFragment;
import dev.hour.fragment.general.AddPictureFragment;
import dev.hour.fragment.general.TagFragment;
import dev.hour.fragment.BusinessAddRestaurantConfirmationFragment;
import dev.hour.presenter.AuthenticatorPresenter;
import dev.hour.presenter.MealPresenter;
import dev.hour.presenter.MenuPresenter;
import dev.hour.presenter.RestaurantPresenter;
import dev.hour.presenter.UserPresenter;
import dev.hour.presenter.DietPresenter;
import dev.hour.view.MapView;
import dev.hour.view.Utilities;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;

public class MainActivity extends AppCompatActivity implements
        AuthenticatorContract.Presenter.InteractionListener,
        RestaurantContract.Presenter.InteractionListener,
        NavigationBarView.OnItemSelectedListener,
        MealContract.Menu.Presenter.InteractionListener,
        AddPictureFragment.Listener,
        TagFragment.Listener,
        MapView.SearchListener {
    //scope......
    static {
        System.setProperty(
                "javax.xml.stream.XMLInputFactory",
                "com.fasterxml.aalto.stax.InputFactoryImpl"
        );
        System.setProperty(
                "javax.xml.stream.XMLOutputFactory",
                "com.fasterxml.aalto.stax.OutputFactoryImpl"
        );
        System.setProperty(
                "javax.xml.stream.XMLEventFactory",
                "com.fasterxml.aalto.stax.EventFactoryImpl"
        );
    }
    /// --------------
    /// Private Fields

    private AuthenticatorContract.Presenter     authenticatorPresenter  ;
    private AuthenticatorContract.Authenticator authenticator           ;
    private UserContract.Presenter              userPresenter           ;
    private UserContract.Database               userDatabase            ;
    private MealContract.Meal.Database          mealDatabase            ;
    private MealContract.Meal.Presenter         mealPresenter           ;
    private MealContract.Diet.Presenter         dietPresenter           ;
    private MealContract.Diet.Database          dietDatabase            ;
    private MealContract.Menu.Presenter         menuPresenter           ;
    private MealContract.Menu.Database          menuDatabase            ;
    private RestaurantContract.Presenter        restaurantPresenter     ;
    private RestaurantContract.Database         restaurantDatabase      ;
    private Fragment                            lastFragment            ;
    private SdkHttpClient                       httpClient              ;
    private String                              userId                  ;
    private LocationRequest                     locationRequest         ;
    private LocationManager                     locationManager         ;

    /// ------------------
    /// Activity Lifecycle

    /**
     * Invoked when the Activity is being created
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize and hide the bottom navigation bar
        initializeBottomNavigation();
        hideBottomNavigationBar();

        // Initialize the http client
        this.httpClient = UrlConnectionHttpClient.create();

        // Set up the authenticator
        authenticator           = new Authenticator(
                this.getString(R.string.account),
                this.getString(R.string.region),
                this.getString(R.string.client_id),
                this.getString(R.string.identity_pool_id),
                this.getString(R.string.provider_name),
                this.getString(R.string.auth_endpoint),
                this.httpClient);
        authenticatorPresenter  = new AuthenticatorPresenter();

        // Set up the user presenter
        userPresenter           = new UserPresenter();
        userDatabase            = new UserDatabase(
                this.getString(R.string.region), this.getString(R.string.user_table_name), this.httpClient);

        // Set up the restaurant presenter
        restaurantPresenter     = new RestaurantPresenter();
        restaurantDatabase      = new RestaurantDatabase(
                this.getString(R.string.region), this.getString(R.string.restaurant_table_name), this.getString(R.string.restaurant_bucket_name), this.httpClient);

        // Set up the menu presenter
        menuPresenter           = new MenuPresenter();
        menuDatabase            = new MenuDatabase(
                this.getString(R.string.region), this.getString(R.string.menu_table_name), this.httpClient);

        // Set up the user presenter
        dietPresenter           = new DietPresenter();
        dietDatabase            = new DietDatabase(
                this.getString(R.string.region), this.getString(R.string.diet_table_name), this.httpClient);

        // Set up the Meal presenter
        mealPresenter           = new MealPresenter();
        mealDatabase            = new MealDatabase(
                this.getString(R.string.region), this.getString(R.string.meal_table_name), this.getString(R.string.meal_bucket_name), this.httpClient);
        
        // Bind the model
        authenticatorPresenter.setAuthenticator(authenticator);
        authenticatorPresenter.setInteractionListener(this);

        userPresenter.setDatabase(userDatabase);
        restaurantPresenter.setDatabase(restaurantDatabase);
        dietPresenter.setDatabase(dietDatabase);
        menuPresenter.setDatabase(menuDatabase);
        mealPresenter.setDatabase(mealDatabase);

        // Bind the presenter
        authenticator.setListener((AuthenticatorContract.Authenticator.Listener) authenticatorPresenter);
        // Retrieve the most recent Fragment and show the login screen
        bindRecentFragment();

    }

    /**
     * Invoked when the Activity is being resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();

        if((this.userId == null) || this.userId.isEmpty())
            this.authenticator.checkSession();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case AddPictureFragment.STORAGE_PERMISSION_REQUEST:

                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    final Fragment fragment =
                            getSupportFragmentManager().findFragmentByTag(AddPictureFragment.TAG);

                    if (fragment instanceof AddPictureFragment)
                        ((AddPictureFragment) fragment).storagePermissionsGranted();

                }

                break;
            case 420:
                if(grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Activity", "420 Granted");
                }

            default:
                break;

        }

    }

    public void checkLocationPermissions() {
        if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_DENIED) {

            requestPermissions(
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION },
                    420);

        } else if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            Log.e("Activity","Permissions Granted");

            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.FUSED_PROVIDER, 500, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    userPresenter.setUserLocation(location.getLongitude(), location.getLatitude());
                    Log.e("Activity","onLocationChanged()");
                }
            });
        }
    }

    /// ---------------------------------------------------
    /// AuthenticatorContract.Presenter.InteractionListener

    /**
     * Invoked when the User has requested to sign in.
     */
    @Override
    public void onSignInRequest() {

        showLoginFragment();

    }

    /**
     * Invoked when the user has requested to sign up
     */
    @Override
    public void onSignUpRequest() {

        showSignUpFragment();

    }

    /**
     * Invoked when the Sign-In request succeeded.
     * @param credentials The credentials to pass to the interaction listener
     */
    @Override
    public void onAuthenticated(final Map<String, String> credentials, final String userId) {

        // Provisional
        this.userId = userId;

        // Set the credentials
        this.userDatabase.setCredentials(credentials);
        this.dietDatabase.setCredentials(credentials);
        this.restaurantDatabase.setCredentials(credentials);
        this.menuDatabase.setCredentials(credentials);
        this.mealDatabase.setCredentials(credentials);
        this.userPresenter.setUser(userId);

        // Retrieve the user
        final UserContract.User user = this.userPresenter.getUser(this.userId)    ;

        if(user != null) {

            if (user.getType().equals("customer")) {
                checkLocationPermissions();
                showMapFragment();
            }

            else {

                this.restaurantPresenter.setRestaurantsByOwner(this.userId);

                //showBusinessRestaurantListFragment();
                showMapFragment();
                checkLocationPermissions();
            }
        }
    }

    /**
     * Invoked when the user is not authenticated
     * @param message The error message
     */
    @Override
    public void onUnauthenticated(final String message) {

        showLoginFragment();

    }

    /**
     * Invoked when the user has successfully signed out.
     * @param message the success message
     */
    @Override
    public void onSignOut(final String message) {

        showLoginFragment();

    }

    /**
     * Invoked when the user has successfully signed up.
     * @param data The user data to update
     */
    @Override
    public void onSignUp(final Map<String, String> data, final Map<String, String> credentials) {

        if(this.userDatabase != null) {

            this.userDatabase.setCredentials(credentials);
            this.userDatabase.updateUser(data);

        }

        showLoginFragment();

    }

    /// ---------------------------------------------------
    /// RestaurantContract.Presenter.InteractionListener

    @Override
    public void onUpdateRestaurantRequest(final RestaurantContract.Restaurant restaurant) {

        showBusinessUpdateRestaurantFragment(restaurant);

    }

    @Override
    public void onShowBusinessRestaurantListRequest() {

        showBusinessRestaurantListFragment();

    }

    @Override
    public void onCreateRestaurantRequest(final Map<String, Object> data) {

        if(data != null) {

            this.restaurantPresenter.updateRestaurant(data, this.userId);

            final Map<String, Object> menuData = new HashMap<>();

            // Set the menu data
            menuData.put("id", data.get("menu_id"));
            menuData.put("meal_ids", new ArrayList<>());

            this.menuPresenter.updateMenu(menuData);
            this.restaurantPresenter.setRestaurantsByOwner(this.userId);

        }

        showBusinessRestaurantListFragment();

    }

    @Override
    public void onShowBusinessAddRestaurantImageRequest(final Map<String, Object> export) {

        final FragmentManager fragmentManager = getSupportFragmentManager();

        showAddPictureFragment(export,
                fragmentManager.findFragmentByTag(BusinessUpdateRestaurantFragment.TAG));

    }

    @Override
    public void onShowBusinessAddRestaurantTagRequest(final Map<String, Object> export) {

        final FragmentManager fragmentManager = getSupportFragmentManager();

        showTagFragment(export,
                fragmentManager.findFragmentByTag(BusinessUpdateRestaurantFragment.TAG));

    }

    @Override
    public void onRestaurantSelected(final RestaurantContract.Restaurant restaurant) {

        if(this.menuPresenter != null) {

            // Retrieve the meal ids
            final List<String> mealIds = this.menuPresenter.getMealIdsForMenu(restaurant.getMenuId());

            if(this.mealDatabase != null) {

                final List<MealContract.Meal> meals = this.mealDatabase.getMealsFrom(mealIds);

                this.menuPresenter.setMeals(meals);

            }

        }

        showBusinessMenuListFragment();

    }

    @Override
    public void onShowMenuRequest() {

        showBusinessMenuListFragment();

    }

    @Override
    public void onUpdateMealRequest(MealContract.Meal meal) {

        showBusinessUpdateMenuItemFragment();

    }

    @Override
    public void onCloseBusinessMenuRequest() {

        showBusinessRestaurantListFragment();

    }

    @Override
    public void onBusinessMealSelected(MealContract.Meal meal) {

        /// TODO: Show MealDetailFragment

    }

    /// ---------------------------
    /// AddPictureFragment.Listener

    @Override
    public void onAddPictureReceived(final Object requestor) {

        if(requestor instanceof BusinessUpdateRestaurantFragment)
            showBusinessUpdateRestaurantFragment(null);

    }

    @Override
    public void onAddPictureCancelled(final Object requestor) {

        if(requestor instanceof BusinessUpdateRestaurantFragment)
            showBusinessUpdateRestaurantFragment(null);

    }

    @Override
    public void onTagReceived(Object requestor) {

        if(requestor instanceof BusinessUpdateRestaurantFragment)
            showBusinessUpdateRestaurantFragment(null);

    }

    @Override
    public void onTagCancelled(Object requestor) {

        if(requestor instanceof BusinessUpdateRestaurantFragment)
            showBusinessUpdateRestaurantFragment(null);

    }

    /// ----------------------
    /// MapView.SearchListener

    @Override
    public void onSearch(final String query) {

        restaurantPresenter.setRestaurantsByTag(query);

    }

    @Override
    public void onTextChange(final String query) {


    }

    /// ---------------
    /// Private Methods

    /**
     * Searches through the possible fragments to see if there is one currently shown.
     * This method is primarily used to bind to a main Fragment when the activity is created
     * in case a fragment instance was re-created from a previous state
     */
    private void bindRecentFragment() {

        final FragmentManager   fragmentManager = getSupportFragmentManager();

        Fragment          fragment        = fragmentManager.findFragmentByTag(LoginFragment.TAG);

        if(fragment == null) {

            fragment = fragmentManager.findFragmentByTag(LoginFragment.TAG);

            if(fragment != null)
                lastFragment = fragment;

        }

        if(fragment == null) {

            fragment = fragmentManager.findFragmentByTag(SignUpFragment.TAG);

            if(fragment != null)
                lastFragment = fragment;

        }

        if(fragment == null) {

            fragment = fragmentManager.findFragmentByTag(MapFragment.TAG);

            if(fragment != null)
                lastFragment = fragment;

        }

        if(fragment == null) {

            fragment = fragmentManager.findFragmentByTag(ProfileFragment.TAG);

            if(fragment != null)
                lastFragment = fragment;

        }

        if(fragment == null) {

            fragment = fragmentManager.findFragmentByTag(CustomerRestaurantListFragment.TAG);

            if(fragment != null)
                lastFragment = fragment;

        }

        if(fragment == null) {

            fragment = fragmentManager.findFragmentByTag(BusinessUpdateRestaurantFragment.TAG);

            if(fragment != null)
                lastFragment = fragment;

        }

        if(fragment == null) {

            fragment = fragmentManager.findFragmentByTag(BusinessUpdateMenuItemFragment.TAG);

            if(fragment != null)
                lastFragment = fragment;

        }

        if(fragment == null) {

            fragment = fragmentManager.findFragmentByTag(BusinessMenuListFragment.TAG);

            if(fragment != null)
                lastFragment = fragment;

        }

        if(fragment == null) {

            fragment = fragmentManager.findFragmentByTag(BusinessRestaurantListFragment.TAG);

            if(fragment != null)
                lastFragment = fragment;

        }

    }

    private void showBusinessMenuListFragment() {

        final FragmentManager       fragmentManager = getSupportFragmentManager();
        final FragmentTransaction   transaction     = fragmentManager.beginTransaction();

        Fragment fragment =
                fragmentManager.findFragmentByTag(BusinessMenuListFragment.TAG);

        if(fragment == null) {

            fragment = new BusinessMenuListFragment();
            transaction.add(R.id.activity_main, fragment, BusinessMenuListFragment.TAG);

            menuPresenter.setView((MealContract.Menu.View) fragment);

            ((BusinessMenuListFragment) fragment).setInteractionListener(this);

        } else if(fragment.isAdded()) {

        }

        transaction
                .setCustomAnimations(R.anim.fragment_enter_from_left, R.anim.fragment_exit_to_right);

        transaction.show(fragment);

        if(lastFragment != null && lastFragment != fragment) transaction.remove(lastFragment);

        lastFragment = fragment;

        hideBottomNavigationBar();

        transaction.commit();
        fragmentManager.executePendingTransactions();

    }

    private void showBusinessRestaurantListFragment() {

        final FragmentManager       fragmentManager = getSupportFragmentManager();
        final FragmentTransaction   transaction     = fragmentManager.beginTransaction();

        Fragment fragment =
                fragmentManager.findFragmentByTag(BusinessRestaurantListFragment.TAG);

        if(fragment == null) {

            fragment = new BusinessRestaurantListFragment();
            transaction.add(R.id.activity_main, fragment, BusinessRestaurantListFragment.TAG);

            restaurantPresenter.setView((RestaurantContract.View) fragment);

            ((BusinessRestaurantListFragment) fragment).setInteractionListener(this);

        } else if(fragment.isAdded()) {

            ((BusinessRestaurantListFragment) fragment).setInteractionListener(this);

            restaurantPresenter.setView((RestaurantContract.View) fragment);

        }

        transaction
                .setCustomAnimations(R.anim.fragment_enter_from_left, R.anim.fragment_exit_to_right);

        transaction.show(fragment);

        if(lastFragment != null && lastFragment != fragment) transaction.remove(lastFragment);

        lastFragment = fragment;

        hideBottomNavigationBar();

        transaction.commit();
        fragmentManager.executePendingTransactions();

    }

    private void showBusinessUpdateRestaurantFragment(final RestaurantContract.Restaurant restaurant) {

        final FragmentManager       fragmentManager = getSupportFragmentManager();
        final FragmentTransaction   transaction     = fragmentManager.beginTransaction();

        Fragment fragment =
                fragmentManager.findFragmentByTag(BusinessUpdateRestaurantFragment.TAG);

        if(fragment == null) {

            fragment = new BusinessUpdateRestaurantFragment();
            transaction.add(R.id.activity_main, fragment, BusinessUpdateRestaurantFragment.TAG);

            ((BusinessUpdateRestaurantFragment) fragment).setInteractionListener(this);
            ((BusinessUpdateRestaurantFragment) fragment).setRestaurant(restaurant);

        } else if(fragment.isAdded()) {

            ((BusinessUpdateRestaurantFragment) fragment).setInteractionListener(this);
            ((BusinessUpdateRestaurantFragment) fragment).setRestaurant(restaurant);

        }

        if((lastFragment instanceof AddPictureFragment) || (lastFragment instanceof TagFragment))
            transaction
                    .setCustomAnimations(R.anim.fragment_enter_from_left, R.anim.fragment_exit_to_right);

        else
            transaction
                .setCustomAnimations(R.anim.fragment_enter_from_right, R.anim.fragment_exit_to_left);

        transaction.show(fragment);

        if(lastFragment != null && lastFragment != fragment) transaction.remove(lastFragment);

        lastFragment = fragment;

        hideBottomNavigationBar();

        transaction.commit();
        fragmentManager.executePendingTransactions();

    }

    private void showBusinessUpdateMenuItemFragment() {

        final FragmentManager       fragmentManager = getSupportFragmentManager();
        final FragmentTransaction   transaction     = fragmentManager.beginTransaction();

        Fragment fragment =
                fragmentManager.findFragmentByTag(BusinessUpdateMenuItemFragment.TAG);

        if(fragment == null) {

            fragment = new BusinessUpdateMenuItemFragment();
            transaction.add(R.id.activity_main, fragment, BusinessUpdateMenuItemFragment.TAG);

        } else if(fragment.isAdded()) {

        }

        transaction
                .setCustomAnimations(R.anim.fragment_enter_from_left, R.anim.fragment_exit_to_right);

        transaction.show(fragment);

        if(lastFragment != null && lastFragment != fragment) transaction.remove(lastFragment);

        lastFragment = fragment;

        hideBottomNavigationBar();

        transaction.commit();
        fragmentManager.executePendingTransactions();

    }

    private void showBusinessAddRestaurantConfirmationFragment(final Map<String, Object> export) {
        final FragmentManager       fragmentManager = getSupportFragmentManager();
        final FragmentTransaction   transaction     = fragmentManager.beginTransaction();

        Fragment fragment =
                fragmentManager.findFragmentByTag(BusinessAddRestaurantConfirmationFragment.TAG);

        if(fragment == null) {
            fragment = new BusinessAddRestaurantConfirmationFragment();
            transaction.add(R.id.activity_main, fragment, BusinessAddRestaurantConfirmationFragment.TAG);

            ((BusinessAddRestaurantConfirmationFragment) fragment).setInteractionListener(this);
            ((BusinessAddRestaurantConfirmationFragment) fragment).setExport(export);
        } else if(fragment.isAdded()) {
            ((BusinessAddRestaurantConfirmationFragment) fragment).setInteractionListener(this);
            ((BusinessAddRestaurantConfirmationFragment) fragment).setExport(export);
        }

        transaction
                .setCustomAnimations(R.anim.fragment_enter_from_right, R.anim.fragment_exit_to_left);
        if(lastFragment != null && lastFragment != fragment) transaction.remove(lastFragment);

        lastFragment = fragment;

        hideBottomNavigationBar();

        transaction.commit();
        fragmentManager.executePendingTransactions();

    }

    private void showAddPictureFragment(final Map<String, Object> export, final Object requestor) {

        final FragmentManager       fragmentManager = getSupportFragmentManager();
        final FragmentTransaction   transaction     = fragmentManager.beginTransaction();

        Fragment fragment =
                fragmentManager.findFragmentByTag(AddPictureFragment.TAG);

        if(fragment == null) {

            fragment = new AddPictureFragment();
            transaction.add(R.id.activity_main, fragment, AddPictureFragment.TAG);

            ((AddPictureFragment) fragment).setListener(this, requestor);
            ((AddPictureFragment) fragment).setExport(export);

        } else if(fragment.isAdded()) {

            ((AddPictureFragment) fragment).setListener(this, requestor);
            ((AddPictureFragment) fragment).setExport(export);

        }

        transaction
                .setCustomAnimations(R.anim.fragment_enter_from_right, R.anim.fragment_exit_to_left);

        transaction.show(fragment);

        if(lastFragment != null && lastFragment != fragment) transaction.remove(lastFragment);

        lastFragment = fragment;

        hideBottomNavigationBar();

        transaction.commit();
        fragmentManager.executePendingTransactions();

    }

    private void showTagFragment(final Map<String, Object> export, final Object requestor) {

        final FragmentManager       fragmentManager = getSupportFragmentManager();
        final FragmentTransaction   transaction     = fragmentManager.beginTransaction();

        Fragment fragment =
                fragmentManager.findFragmentByTag(TagFragment.TAG);

        if(fragment == null) {

            fragment = new TagFragment();
            transaction.add(R.id.activity_main, fragment, AddPictureFragment.TAG);

            ((TagFragment) fragment).setListener(this, requestor);
            ((TagFragment) fragment).setExport(export);

        } else if(fragment.isAdded()) {

            ((TagFragment) fragment).setListener(this, requestor);
            ((TagFragment) fragment).setExport(export);

        }

        transaction
                .setCustomAnimations(R.anim.fragment_enter_from_right, R.anim.fragment_exit_to_left);

        transaction.show(fragment);

        if(lastFragment != null && lastFragment != fragment) transaction.remove(lastFragment);

        lastFragment = fragment;

        hideBottomNavigationBar();

        transaction.commit();
        fragmentManager.executePendingTransactions();

    }

    /**
     * Shows the login fragment to the user
     */
    private void showLoginFragment() {

        final FragmentManager       fragmentManager = getSupportFragmentManager();
        final FragmentTransaction   transaction     = fragmentManager.beginTransaction();

        Fragment fragment =
                fragmentManager.findFragmentByTag(LoginFragment.TAG);

        if(fragment == null) {

            fragment = new LoginFragment();
            ((AuthenticatorContract.View)fragment)
                    .setSignUpListener((AuthenticatorContract.View.SignUpListener) authenticatorPresenter);
            ((AuthenticatorContract.View)fragment)
                    .setSignInListener((AuthenticatorContract.View.SignInListener) authenticatorPresenter);

            authenticatorPresenter.setAuthenticatorView((AuthenticatorContract.View) fragment);

            transaction.add(R.id.activity_main, fragment, LoginFragment.TAG);

        } else if(fragment.isAdded()) {
            ((AuthenticatorContract.View)fragment)
                    .setSignUpListener((AuthenticatorContract.View.SignUpListener) authenticatorPresenter);
            ((AuthenticatorContract.View)fragment)
                    .setSignInListener((AuthenticatorContract.View.SignInListener) authenticatorPresenter);

            authenticatorPresenter.setAuthenticatorView((AuthenticatorContract.View) fragment);

        }

        transaction
                .setCustomAnimations(R.anim.fragment_enter_from_left, R.anim.fragment_exit_to_right);

        transaction.show(fragment);

        if(lastFragment != null && lastFragment != fragment) transaction.remove(lastFragment);

        lastFragment = fragment;

        hideBottomNavigationBar();

        transaction.commit();
        fragmentManager.executePendingTransactions();

    }

    /**
     * Shows the Profile fragment to the user
     */
    private void showProfileFragment() {

        final FragmentManager       fragmentManager = getSupportFragmentManager();
        final FragmentTransaction   transaction     = fragmentManager.beginTransaction();

        Fragment fragment =
                fragmentManager.findFragmentByTag(ProfileFragment.TAG);

        if(fragment == null) {

            fragment = new ProfileFragment();
            ((UserContract.View)fragment)
                    .setUserListener((UserContract.View.Listener) userPresenter);
            ((MealContract.Diet.View)fragment)
                    .setDietListener((MealContract.Diet.View.Listener) dietPresenter);
            userPresenter.setView((UserContract.View) fragment);

            dietPresenter.setView((MealContract.Diet.View) fragment);

            transaction.add(R.id.activity_main, fragment, ProfileFragment.TAG);

        } else if(fragment.isAdded()) {
            ((UserContract.View)fragment)
                    .setUserListener((UserContract.View.Listener) userPresenter);
            ((MealContract.Diet.View)fragment)
                    .setDietListener((MealContract.Diet.View.Listener) dietPresenter);

            userPresenter.setView((UserContract.View) fragment);
            dietPresenter.setView((MealContract.Diet.View) fragment);

        }

        transaction
                .setCustomAnimations(R.anim.fragment_enter_from_right, R.anim.fragment_exit_to_left);

        transaction.show(fragment);

        if(lastFragment != null && lastFragment != fragment) transaction.remove(lastFragment);

        lastFragment = fragment;

        showBottomNavigationBar();
        transaction.commit();
        fragmentManager.executePendingTransactions();

    }

    /**
     * Shows the login fragment to the user
     */
    private void showSignUpFragment() {

        final FragmentManager       fragmentManager = getSupportFragmentManager();
        final FragmentTransaction   transaction     = fragmentManager.beginTransaction();

        Fragment fragment =
                fragmentManager.findFragmentByTag(SignUpFragment.TAG);

        if(fragment == null) {

            fragment = new SignUpFragment();
            ((AuthenticatorContract.View) fragment)
                    .setSignUpListener((AuthenticatorContract.View.SignUpListener) authenticatorPresenter);
            ((AuthenticatorContract.View) fragment)
                    .setSignInListener((AuthenticatorContract.View.SignInListener) authenticatorPresenter);

            authenticatorPresenter.setAuthenticatorView((AuthenticatorContract.View) fragment);

            transaction.add(R.id.activity_main, fragment, SignUpFragment.TAG);

        } else if(fragment.isAdded()) {

            ((AuthenticatorContract.View) fragment)
                    .setSignUpListener((AuthenticatorContract.View.SignUpListener) authenticatorPresenter);
            ((AuthenticatorContract.View) fragment)
                    .setSignInListener((AuthenticatorContract.View.SignInListener) authenticatorPresenter);

            authenticatorPresenter.setAuthenticatorView((AuthenticatorContract.View) fragment);

        }

        transaction
                .setCustomAnimations(R.anim.fragment_enter_from_right, R.anim.fragment_exit_to_left);

        transaction.show(fragment);

        if(lastFragment != null && lastFragment != fragment) transaction.remove(lastFragment);

        lastFragment = fragment;

        hideBottomNavigationBar();
        transaction.commit();
        fragmentManager.executePendingTransactions();

    }

    /**
     * Shows the map fragment to the user
     */
    private void showMapFragment() {

        final FragmentManager       fragmentManager = getSupportFragmentManager();
        final FragmentTransaction   transaction     = fragmentManager.beginTransaction();

        Fragment fragment =
                fragmentManager.findFragmentByTag(MapFragment.TAG);

        if(fragment == null) {

            fragment = new MapFragment();

            userPresenter.setView((UserContract.View) fragment);
            restaurantPresenter.setView((RestaurantContract.View) fragment);

            transaction.add(R.id.activity_main, fragment, MapFragment.TAG);

        } else if(fragment.isAdded()) {

            userPresenter.setView((UserContract.View) fragment);
            restaurantPresenter.setView((RestaurantContract.View) fragment);

        }
        ((MapFragment)fragment).setSearchListener(this);
        transaction
                .setCustomAnimations(R.anim.fragment_enter_from_right, R.anim.fragment_exit_to_left);

        transaction.show(fragment);

        if(lastFragment != null && lastFragment != fragment) transaction.remove(lastFragment);

        lastFragment = fragment;

        showBottomNavigationBar();
        transaction.commit();
        fragmentManager.executePendingTransactions();

    }

    private void showCustomerRestaurantListFragment() {

        final FragmentManager       fragmentManager = getSupportFragmentManager();
        final FragmentTransaction   transaction     = fragmentManager.beginTransaction();

        Fragment fragment =
                fragmentManager.findFragmentByTag(CustomerRestaurantListFragment.TAG);

        if(fragment == null) {

            fragment = new CustomerRestaurantListFragment();
            transaction.add(R.id.activity_main, fragment, CustomerRestaurantListFragment.TAG);

            ((CustomerRestaurantListFragment) fragment).setInteractionListener(this);

        } else if(fragment.isAdded()) {

        }

        transaction
                .setCustomAnimations(R.anim.fragment_enter_from_left, R.anim.fragment_exit_to_right);

        transaction.show(fragment);

        if(lastFragment != null && lastFragment != fragment) transaction.remove(lastFragment);

        lastFragment = fragment;

        transaction.commit();
        fragmentManager.executePendingTransactions();
    }

    /**
     * Initializes the bottom navigation bar
     */
    private void initializeBottomNavigation() {

        NavigationBarView bottomNavigationView = findViewById(R.id.activity_map_keyed_map_bottom_navigation);

        if(bottomNavigationView != null) {

            bottomNavigationView.setItemIconTintList(null);
            bottomNavigationView.setOnItemSelectedListener(this);

        }

    }

    /**
     * Hides the bottom navigation bar if it is not currently hidden
     */
    private void hideBottomNavigationBar() {

        final NavigationBarView navigationBar =
                findViewById(R.id.activity_map_keyed_map_bottom_navigation);

        final int   height          = navigationBar.getMeasuredHeight();
        final float translationY    = navigationBar.getTranslationY();

        if(translationY < height && navigationBar.getVisibility() == View.VISIBLE) {

            final AnimatorSet    hideAnimator   = new AnimatorSet();
            final List<Animator> animators      = new ArrayList<>(
                    Utilities.floatTranslationOffsetAnimatorCollection(
                    navigationBar, 0.0f, (float) height, 300));

            hideAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    navigationBar.setVisibility(View.GONE);

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            hideAnimator.playTogether(animators);
            hideAnimator.start();

        }

    }

    /**
     * Shows the bottom navigation bar if it is hidden
     */
    private void showBottomNavigationBar() {

        final NavigationBarView navigationBar =
                findViewById(R.id.activity_map_keyed_map_bottom_navigation);

        final float translationY    = navigationBar.getTranslationY();

        if(translationY != 0.0f && navigationBar.getVisibility() == View.GONE) {

            final AnimatorSet    showAnimator   = new AnimatorSet();
            final List<Animator> animators      = new ArrayList<>(
                    Utilities.floatTranslationOffsetAnimatorCollection(
                            navigationBar, 0.0f, -translationY, 300));

            showAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                    navigationBar.setVisibility(View.VISIBLE);

                }

                @Override
                public void onAnimationEnd(Animator animator) {


                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }

            });

            showAnimator.playTogether(animators);
            showAnimator.start();

        } else if(navigationBar.getVisibility() == View.GONE) {

            navigationBar.setVisibility(View.VISIBLE);

        }

    }

    /**
     * Invoked when one of the bottom navigation buttons has been pressed by the user.
     * @param item The bottom navigation item that was pressed
     * @return flag indicating if the interaction was handled.
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.navigation_list: showCustomerRestaurantListFragment(); break;
            case R.id.navigation_location: showMapFragment(); break;
            case R.id.profile: showProfileFragment(); break;
        }
        return true;
    }


    private void showCustomerRestaurantListFragment() {

        final FragmentManager       fragmentManager = getSupportFragmentManager();
        final FragmentTransaction   transaction     = fragmentManager.beginTransaction();

        Fragment fragment =
                fragmentManager.findFragmentByTag(CustomerRestaurantListFragment.TAG);

        if(fragment == null) {

            fragment = new CustomerRestaurantListFragment();
            transaction.add(R.id.activity_main, fragment, CustomerRestaurantListFragment.TAG);
            restaurantPresenter.setView((RestaurantContract.View) fragment);

            //((CustomerRestaurantListFragment) fragment).setInteractionListener(this);

        } else if(fragment.isAdded()) {
            restaurantPresenter.setView((RestaurantContract.View) fragment);
        }

        transaction
                .setCustomAnimations(R.anim.fragment_enter_from_left, R.anim.fragment_exit_to_right);

        transaction.show(fragment);

        if(lastFragment != null && lastFragment != fragment) transaction.remove(lastFragment);

        lastFragment = fragment;

        transaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    public void onShowMenuRequest() {

    }

    @Override
    public void onShowBusinessAddMenuMeal(Map<String, Object> export) {

    }

    @Override
    public void onShowBusinessAddMenuAddTag(Map<String, Object> export) {

    }

    @Override
    public void onMealSelected(MealContract.Meal meal) {

    }

    @Override
    public void onEditPicture() {

    }

    @Override
    public void onAddIngredientButton() {

    }

    @Override
    public void onTagButton() {

    }

    @Override
    public void onConfirmButton(Map<String, Object> input) {

    }

    /// ---------------------------
    /// AddPictureFragment.Listener

    @Override
    public void onAddPictureReceived(final Object requestor) {

        if(requestor instanceof BusinessUpdateRestaurantFragment)
            showBusinessAddRestaurantFragment(null);

    }

    @Override
    public void onAddPictureCancelled(final Object requestor) {

        if(requestor instanceof BusinessUpdateRestaurantFragment)
            showBusinessAddRestaurantFragment(null);

    }
}