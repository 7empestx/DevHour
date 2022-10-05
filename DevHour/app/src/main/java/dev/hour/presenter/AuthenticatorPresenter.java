package dev.hour.presenter;

import java.util.Map;

import dev.hour.contracts.AuthenticatorContract;

public class AuthenticatorPresenter implements AuthenticatorContract.Presenter,
        AuthenticatorContract.View.SignUpListener,
        AuthenticatorContract.View.SignInListener,
        AuthenticatorContract.Authenticator.Listener {

    /// --------------
    /// Private Fields

    private AuthenticatorContract.Authenticator authenticator   ;
    private AuthenticatorContract.View          view            ;

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
     * Invoked when the Sign-In funtionality has been requested over the sign-up
     * functionality
     */
    @Override
    public void onRequestSignIn() {

        /// TODO

    }

    /// -----------------------------------------
    /// AuthenticatorContract.View.SignInListener

    /**
     * Invoked when some SignIn input has been received, passes the value to the
     * Authenticator
     * @param input The received input
     */
    @Override
    public void onReceivedSignInInput(Map<String, String> input) {

        if(this.authenticator != null)
            this.authenticator.signIn(input);

    }

    /**
     * Invoked when the Sign-Up functionality has been requested over the sign-in
     * functionality
     */
    @Override
    public void onRequestSignUp() {

        /// TODO

    }

    /// --------------------------------------------
    /// AuthenticatorContract.Authenticator.Listener

    /**
     * Invoked when the Sign-In request failed.
     * @param message The error message
     */
    @Override
    public void onSignInFailed(final String message) {

        /// TODO Callback to the login view here

    }

    /**
     * Invoked when the Sign-Up request failed.
     * @param message The error message
     */
    @Override
    public void onSignUpFailed(String message) {

        /// TODO Callback to the fragment controller (Activity) here
    }

    /**
     * Invoked when the Sign-In request succeeded.
     * @param message The success message
     */
    @Override
    public void onAuthenticated(String message) {

        /// TODO Callback to the view here

    }

    /**
     * Invoked when the Sign-Out request succeeded.
     * @param message The success message
     */
    @Override
    public void onUnauthenticated(String message) {

        /// TODO Callback to the fragment controller (Activity) here

    }

}