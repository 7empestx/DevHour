package dev.hour.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import dev.hour.R;
import dev.hour.contracts.AuthenticatorContract;

/**
 * Basic Login [Fragment] that authenticates & creates users before interacting with the
 * rest of the application.
 *
 * @since 1.0.0.0
 */
public final class LoginFragment extends Fragment implements AuthenticatorContract.View, OnClickListener {

    /// --------------
    /// Static Members

    public final static String TAG = "LoginFragment";

    /// --------------
    /// Private Fields

    private Snackbar        snackBar        ;
    private SignInListener  signInListener  ;

    /// --------
    /// Fragment

    /**
     * Invoked in the [Fragment]'s lifecycle when the [View] is to be created
     * @param layoutInflater The [LayoutInflater]
     * @param container The [ViewGroup] container
     * @param savedInstanceState The [Bundle] storing state data
     * @return [View]
     */
    public View onCreateView(final LayoutInflater layoutInflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {


        final View                layout              = layoutInflater.inflate(R.layout.fragment_login, container, false);
        final ConstraintLayout    mLayout             = layout.findViewById(R.id.fragment_login_layout);
        final TextView            createUserButton    = layout.findViewById(R.id.fragment_login_create_account);
        final TextView            resetButton         = layout.findViewById(R.id.fragment_login_reset);
        final TextView            signInButton        = layout.findViewById(R.id.fragment_login_sign_in_button);

        if (mLayout != null) mLayout.setOnClickListener(this);

        if (signInButton != null) signInButton.setOnClickListener(this);

        if (createUserButton != null) createUserButton.setOnClickListener(this);

        if (resetButton != null) resetButton.setOnClickListener(this);

        return layout;

    }

    /**
     * Invoked when the [UserLoginFragment] is to be destroyed.
     */
    public void onDestroy() {

        super.onDestroy();
        this.dispose();

    }

    /// --------------------
    /// View.OnClickListener

    /**
     * Invoked when the either the Create User or Sign-In Button has been clicked
     * @param view The [View] that has been clicked
     */
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {

        hideKeyboardFrom(this.getContext(), this.getView());

        if (this.snackBar != null) this.snackBar.dismiss();

        switch(view.getId()) {

            case R.id.fragment_login_sign_in_button:

                final Button signInButton =
                        this.requireView().findViewById(R.id.fragment_login_sign_in_button);

                signInButton.setEnabled(false);

                final Map<String, String> input = new HashMap<>();

                input.put("USERNAME", getUsernameOrEmail());
                input.put("PASSWORD", getPassword());

                /// Notify the listener
                if(this.signInListener != null)
                    this.signInListener.onReceivedSignInInput(input);

            case R.id.fragment_login_reset: break;
            case R.id.fragment_login_create_account:

                /// Notify the listener
                if(this.signInListener != null)
                    this.signInListener.onRequestSignUp();

            default:

        }

    }

    /// --------------------------
    /// AuthenticatorContract.View

    /**
     * Invoked when the user has successfully signed up
     */
    @Override
    public void onSignUp() { /* Empty */ }

    /**
     * Invoked when the user has successfully signed in
     */
    @Override
    public void onSignIn() {

        final Button signInButton = this.requireView().findViewById(R.id.fragment_login_sign_in_button);

        signInButton.setEnabled(true);

        if (this.snackBar != null)  this.snackBar.dismiss();

    }

    /**
     * Invoked when the user could not sign in
     */
    @Override
    public void onSignInFailed(final String message) {

        Button signInButton = this.requireView().findViewById(R.id.fragment_login_sign_in_button);

        signInButton.setEnabled(true);

        this.snackBar = Snackbar.make(this.requireView(), ("Error: " + message), 10000);

        this.snackBar.show();


    }

    /**
     * Invoked when the user could not sign up
     */
    @Override
    public void onSignUpFailed(final String message) { /* Empty */ }

    /**
     * Invoked when the user could not sign out
     */
    @Override
    public void onSignOutFailed(final String message) { /* Empty */ }

    /// --------------
    /// Public Methods

    /**
     * The Listener that receives callbacks when the user attempts to Sign in
     */
    public void setSignInListener(final SignInListener listener) {
        this.signInListener = listener;
    }

    /**
     * The Listener that receives callbacks when the user attempts to Sign Up
     */
    public void setSignUpListener(final SignUpListener listener) { /* Empty */ }

    /// ---------------
    /// Private Methods

    /**
     * Hides the keyboard from the user
     * @param context The current [Context]
     * @param view The view to hide the keyboard from
     */
    private void hideKeyboardFrom(final Context context, final View view) {

        if (context != null && view != null) {

            InputMethodManager inputMethodManager =
                    (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }

    }

    /**
     * Attempts to retrieve the email from the corresponding [EditText].
     * Guarantees a non-null [String] that is at least empty
     * @return valid or invalid [String]
     */
    private String getUsernameOrEmail() {

        String email = "";
        final View        view      = this.getView();
        final EditText    emailText = view != null ?
                (EditText)view.findViewById(R.id.fragment_login_email_input) : null;

        CharSequence text = (emailText != null ? emailText.getText() : null);

        if (text != null) email = text.toString();

        return email;

    }

    /**
     * Attempts to retrieve the email from the corresponding [EditText].
     * Guarantees a non-null [String] that is at least empty
     * @return valid or invalid [String]
     */
    private String getPassword() {

        final View      view            = this.getView();
        final EditText  passwordText    = view != null ? (EditText)view.findViewById(R.id.fragment_login_password_input) : null;

        String password = "";

        CharSequence text = (passwordText != null ? passwordText.getText() : null);

        if(text != null) password = text.toString();

        return password;
    }

    /**
     * Removes the handles from all of this activities' member variables
     */
    private void dispose() {

        if (this.snackBar != null)  this.snackBar.dismiss();

        this.snackBar           = null;
        this.signInListener     = null;

    }

}
