package dev.hour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.hour.authenticator.Authenticator;
import dev.hour.contracts.AuthenticatorContract;
import dev.hour.contracts.RestaurantContract;
import dev.hour.contracts.UserContract;
import dev.hour.database.RestaurantDatabase;
import dev.hour.database.UserDatabase;
import dev.hour.fragment.LoginFragment;
import dev.hour.fragment.MapFragment;
import dev.hour.fragment.SignUpFragment;
import dev.hour.presenter.AuthenticatorPresenter;
import dev.hour.presenter.RestaurantPresenter;
import dev.hour.presenter.UserPresenter;
import dev.hour.view.Utilities;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;

public class MainActivity extends AppCompatActivity implements
        AuthenticatorContract.Presenter.InteractionListener,
        NavigationBarView.OnItemSelectedListener {

    /// --------------
    /// Private Fields

    private AuthenticatorContract.Presenter     authenticatorPresenter  ;
    private AuthenticatorContract.Authenticator authenticator           ;
    private UserContract.Presenter              userPresenter           ;
    private UserContract.Database               userDatabase            ;
    private RestaurantContract.Presenter        restaurantPresenter     ;
    private RestaurantContract.Database         restaurantDatabase      ;
    private Fragment                            lastFragment            ;
    private SdkHttpClient                       httpClient              ;

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
                this.getString(R.string.region), this.getString(R.string.restaurant_table_name), this.httpClient);

        // Bind the model
        authenticatorPresenter.setAuthenticator(authenticator);
        authenticatorPresenter.setInteractionListener(this);

        userPresenter.setDatabase(userDatabase);
        restaurantPresenter.setDatabase(restaurantDatabase);

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

        // Check if we're authenticated
        this.authenticator.checkSession();

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
    public void onAuthenticated(final Map<String, String> credentials, String userId) {

        // Set the credentials
        this.userDatabase.setCredentials(credentials);
        this.restaurantDatabase.setCredentials(credentials);
        this.userPresenter.setUser(userId);

        showMapFragment();

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
    public void onSignUp(final Map<String, String> data) {

        if(this.userDatabase != null)
            this.userDatabase.updateUser(data);

        showLoginFragment();

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

        if(fragment != null)
            lastFragment = fragment;


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

            case R.id.navigation_list: break; /// TODO: Show List Fragment
            case R.id.navigation_location: showMapFragment(); break;

        }

        return true;

    }

}