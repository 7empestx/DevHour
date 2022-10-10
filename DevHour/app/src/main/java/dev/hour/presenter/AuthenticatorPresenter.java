package dev.hour.presenter;

import android.util.Log;

import java.util.Map;

import dev.hour.contracts.AuthenticatorContract;

public class AuthenticatorPresenter implements AuthenticatorContract.Presenter,
        AuthenticatorContract.View.SignUpListener,
        AuthenticatorContract.View.SignInListener,
        AuthenticatorContract.Authenticator.Listener {

    /// --------------
    /// Private Fields

    private AuthenticatorContract.Authenticator                 authenticator           ;
    private AuthenticatorContract.View                          view                    ;
    private AuthenticatorContract.Presenter.InteractionListener interactionListener     ;

    /// -------------------------------
    /// AuthenticatorContract.Presenter

    /**
     * Mutates the AuthenticatorPresenter's Authenticator*
     * @param authenticator The Authenticator to mutate to
     */
    @Override
    public void setAuthenticator(final AuthenticatorContract.Authenticator authenticator) {

        /// Simple setter
        this.authenticator = authenticator;

    }

    /**
     * Mutates the AuthenticatorPresenter's view
     * @param view The view that will display state mutations.
     */
    @Override
    public void setAuthenticatorView(final AuthenticatorContract.View view) {

        this.view = view;

    }

    /**
     * Set the listener that will receive call backs when user-authenticator interactions occur.
     * @param interactionListener The listener that will receive callbacks on user-authenticator
     *                            interactions.
     */
    @Override
    public void setInteractionListener(final InteractionListener interactionListener) {

        this.interactionListener = interactionListener;

    }

    /// -----------------------------------------
    /// AuthenticatorContract.View.SignUpListener

    /**
     * Invoked when some SignUp input has been received, passes the value to the
     * Authenticator
     * @param input The received input
     */
    @Override
    public void onReceivedSignUpInput(Map<String, String> input) {

        if(this.authenticator != null)
            this.authenticator.signUp(input);

    }

    /**
     * Invoked when the Sign-In functionality has been requested over the sign-up
     * functionality
     */
    @Override
    public void onRequestSignIn() {

        if(this.interactionListener != null)
            this.interactionListener.onSignInRequest();

    }

    /// -----------------------------------------
    /// AuthenticatorContract.View.SignInListener

    /**
     * Invoked when some SignIn input has been received, passes the value to the
     * Authenticator
     * @param input The received input
     */
    @Override
    public void onReceivedSignInInput(final Map<String, String> input) {

        if(this.authenticator != null)
            this.authenticator.signIn(input);

    }

    /**
     * Invoked when the Sign-Up functionality has been requested over the sign-in
     * functionality
     */
    @Override
    public void onRequestSignUp() {

        if(this.interactionListener != null)
            this.interactionListener.onSignUpRequest();

    }

    /// --------------------------------------------
    /// AuthenticatorContract.Authenticator.Listener

    /**
     * Invoked when the Sign-In request failed.
     * @param message The error message
     */
    @Override
    public void onSignInFailed(final String message) {

        if(this.view != null)
            this.view.onSignInFailed(message);

    }

    /**
     * Invoked when the Sign-Up request failed.
     * @param message The error message
     */
    @Override
    public void onSignUpFailed(final String message) {

        if(this.view != null)
            this.view.onSignUpFailed(message);

    }

    /**
     * Invoked when the Authenticator has failed to sign out the user
     * @param message The error message
     */
    @Override
    public void onSignOutFailed(final String message) {

        if(this.view != null)
            this.view.onSignOutFailed(message);

    }

    /**
     * Invoked when the Sign-In request succeeded.
     * @param credentials The credentials to pass to the interaction listener
     */
    @Override
    public void onAuthenticated(final Map<String, String> credentials) {

        if(this.view != null)
            this.view.onSignIn();

        if(this.interactionListener != null)
            this.interactionListener.onAuthenticated(credentials);

    }

    /**
     * Invoked when the user is not authenticated
     * @param message The error message
     */
    @Override
    public void onUnauthenticated(final String message) {

        if(this.interactionListener != null)
            this.interactionListener.onUnauthenticated(message);

    }

    /**
     * Invoked when the user has successfully signed out.
     * @param message the success message
     */
    @Override
    public void onSignOut(final String message) {

        if(this.interactionListener != null)
            this.interactionListener.onSignOut(message);

    }

    /**
     * Invoked when the user has successfully signed up.
     * @param message The success message
     */
    @Override
    public void onSignUp(final String message) {

        if(this.view != null)
            this.view.onSignUp();

        if(this.interactionListener != null)
            this.interactionListener.onSignUp(message);

    }

}
