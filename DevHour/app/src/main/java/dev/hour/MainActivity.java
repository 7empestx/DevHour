package dev.hour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import dev.hour.contracts.AuthenticatorContract;
import dev.hour.fragment.LoginFragment;
import dev.hour.presenter.AuthenticatorPresenter;

public class MainActivity extends AppCompatActivity {

    /// --------------
    /// Private Fields

    private Fragment lastFragment;
    private AuthenticatorPresenter authenticatorPresenter;

    /// ------------------
    /// Activity Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /// Create the Presenter
        authenticatorPresenter = new AuthenticatorPresenter();

        // Retrieve the most recent Fragment and show the login screen
        bindRecentFragment();
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

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment fragment = fragmentManager.findFragmentByTag(LoginFragment.TAG);

        if (fragment != null)
            lastFragment = fragment;

    }

    /**
     * Shows the login fragment to the user
     */
    private void showLoginFragment() {

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        Fragment fragment =
                fragmentManager.findFragmentByTag(LoginFragment.TAG);

        if (fragment == null) {

            fragment = new LoginFragment();

            authenticatorPresenter.setAuthenticatorView((AuthenticatorContract.View) fragment);

            transaction.add(R.id.activity_main, fragment, LoginFragment.TAG);

        } else if (fragment.isAdded()) {

            authenticatorPresenter.setAuthenticatorView((AuthenticatorContract.View) fragment);

        }

        transaction.show(fragment);

        if (lastFragment != null && lastFragment != fragment) transaction.remove(lastFragment);

        lastFragment = fragment;

        transaction.commit();
        fragmentManager.executePendingTransactions();

    }

}