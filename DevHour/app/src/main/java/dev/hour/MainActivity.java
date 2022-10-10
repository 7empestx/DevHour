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

import dev.hour.authenticator.Authenticator;
import dev.hour.contracts.AuthenticatorContract;
import dev.hour.fragment.LoginFragment;
import dev.hour.fragment.SignUpFragment;
import dev.hour.presenter.AuthenticatorPresenter;
import dev.hour.view.Utilities;

public class MainActivity extends AppCompatActivity implements
        AuthenticatorContract.Presenter.InteractionListener,
        NavigationBarView.OnItemSelectedListener {

    /// --------------
    /// Private Fields

    private AuthenticatorContract.Presenter     authenticatorPresenter  ;
    private AuthenticatorContract.Authenticator authenticator           ;
    private Fragment                            lastFragment            ;

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

        authenticator           = new Authenticator(this);
        authenticatorPresenter  = new AuthenticatorPresenter();

        // Bind the model
        authenticatorPresenter.setAuthenticator(authenticator);
        authenticatorPresenter.setInteractionListener(this);

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

        showLoginFragment();

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

            case R.id.navigation_list: /// TODO: Show List Fragment
            case R.id.navigation_location: /// TODO: Show Map Fragment

        }

        return true;

    }

}