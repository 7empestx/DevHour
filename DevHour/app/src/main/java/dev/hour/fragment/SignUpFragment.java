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

import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import dev.hour.R;
import dev.hour.contracts.AuthenticatorContract;

/**
 * Fragment that displays and handles a user's account creation.
 *
 * @since 1.0.0.0
 */
public final class SignUpFragment extends Fragment implements AuthenticatorContract.View, OnClickListener {

    /// --------------
    /// Static Members

    public final static String TAG = "SignUpFragment"    ;

    /// --------------
    /// Private Fields

    private Snackbar        snackBar        ;
    private SignUpListener  signUpListener  ;
<<<<<<< Updated upstream
=======
    private SignInListener  signInListener  ;
>>>>>>> Stashed changes

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

        final View                layout              =
                layoutInflater.inflate(R.layout.fragment_create_user, container, false);
        final Button              createAccountButton =
                layout.findViewById(R.id.fragment_sign_up_create_account);
        final View                createAccountClose  =
                layout.findViewById(R.id.fragment_sign_up_back_button);

        layout.setOnClickListener(this);

        if (createAccountButton != null) createAccountButton.setOnClickListener(this);

        if (createAccountClose != null) createAccountClose.setOnClickListener(this);

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
     * Invoked when the either the Back Button or Sign-Up Button has been clicked
     * @param view The [View] that has been clicked
     */
    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {

        hideKeyboardFrom(this.getContext(), this.getView());

        if (this.snackBar != null) this.snackBar.dismiss();

        switch(view.getId()) {

            case R.id.fragment_sign_up_create_account:

                final Button createAccountButton =
                        this.requireView().findViewById(R.id.fragment_sign_up_create_account);

                createAccountButton.setEnabled(false);

                final Map<String, String> input = new HashMap<>();

<<<<<<< Updated upstream
                input.put("username",   getUsername());
                input.put("password",   getPassword());
                input.put("email",      getEmail());
                input.put("name",       getFirstName() + getLastName());
                input.put("first",      getFirstName());
                input.put("last",       getLastName());

=======
                input.put("USERNAME", getEmail());
                input.put("PASSWORD", getPassword());
                input.put("FIRST", getFirstName());
                input.put("LAST", getLastName());

                /// Notify the listener
>>>>>>> Stashed changes
                if(this.signUpListener != null)
                    this.signUpListener.onReceivedSignUpInput(input);

            case R.id.fragment_sign_up_back_button:

<<<<<<< Updated upstream
=======
                /// Notify the listener
>>>>>>> Stashed changes
                if(this.signUpListener != null)
                    this.signUpListener.onRequestSignIn();

            default:

        }

    }

    /// --------------------------
    /// AuthenticatorContract.View

    /**
     * Invoked when the user has successfully signed up
     */
<<<<<<< Updated upstream
    @Override
    public void onSignUp() {
=======
    public void onUserSignedUp() {
>>>>>>> Stashed changes

        final Button signUpButton =
                this.requireView().findViewById(R.id.fragment_sign_up_create_account);

        signUpButton.setEnabled(true);

        if (this.snackBar != null)  this.snackBar.dismiss();

    }

    /**
<<<<<<< Updated upstream
     * Invoked when the user has successfully signed in
     */
    @Override
    public void onSignIn() { /* Empty */ }

    /**
     * Invoked when the user could not sign in
     */
    @Override
    public void onSignInFailed(final String message) { /* Empty */ }

    /**
     * Invoked when the user could not sign up
     */
    @Override
    public void onSignUpFailed(final String message) {
=======
     * Invoked when the user could not sign up
     */
    public void onUserSignUpFailed(final String errorString) {
>>>>>>> Stashed changes

        Button signUpButton = this.requireView().findViewById(R.id.fragment_sign_up_create_account);

        signUpButton.setEnabled(true);

<<<<<<< Updated upstream
        this.snackBar = Snackbar.make(this.requireView(), ("Error: " + message), 10000);

        this.snackBar.show();


    }

    /**
     * Invoked when the user could not sign out
     */
    @Override
    public void onSignOutFailed(final String message) { /* Empty */ }

=======
        this.snackBar = Snackbar.make(this.requireView(), ("Error: " + errorString), 10);

        this.snackBar.show();

    }

>>>>>>> Stashed changes
    /// --------------
    /// Public Methods

    /**
     * The Listener that receives callbacks when the user attempts to Sign in
     */
<<<<<<< Updated upstream
    public void setSignInListener(final SignInListener listener) { /* Empty */ }
=======
    public void setSignInListener(final SignInListener listener) {
        this.signInListener = listener;
    }
>>>>>>> Stashed changes

    /**
     * The Listener that receives callbacks when the user attempts to Sign Up
     */
    public void setSignUpListener(final SignUpListener listener) {
        this.signUpListener = listener;
    }

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
<<<<<<< Updated upstream
     * Attempts to retrieve the username from the corresponding [EditText].
     * Guarantees a non-null [String] that is at least empty
     * @return valid or invalid [String]
     */
    private String getUsername() {

        String email = "";

        final View        view      = this.getView();
        final EditText    usernameText = view != null ?
                (EditText)view.findViewById(R.id.fragment_sign_up_username_input) : null;

        CharSequence text = (usernameText != null ? usernameText.getText() : null);

        if (text != null) email = text.toString();

        return email;

    }

    /**
=======
>>>>>>> Stashed changes
     * Attempts to retrieve the email from the corresponding [EditText].
     * Guarantees a non-null [String] that is at least empty
     * @return valid or invalid [String]
     */
    private String getEmail() {

        String email = "";

        final View        view      = this.getView();
        final EditText    emailText = view != null ?
                (EditText)view.findViewById(R.id.fragment_sign_up_email_input) : null;

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

        final View      view         = this.getView();
        final EditText  passwordText = view != null ?
                (EditText)view.findViewById(R.id.fragment_sign_up_email_input) : null;

        String password = "";

        CharSequence text = (passwordText != null ? passwordText.getText() : null);

        if(text != null) password = text.toString();

        return password;
    }

    /**
     * Attempts to retrieve the first name from the corresponding [EditText].
     * Guarantees a non-null [String] that is at least empty
     * @return valid or invalid [String]
     */
    private String getFirstName() {

        String firstName = "";

        final View        view      = this.getView();
        final EditText    firstNameText = view != null ?
                (EditText) view.findViewById(R.id.fragment_sign_up_first_name_input) : null;

        CharSequence text = (firstNameText != null ? firstNameText.getText() : null);

        if (text != null) firstName = text.toString();

        return firstName;

    }

    /**
     * Attempts to retrieve the last name from the corresponding [EditText].
     * Guarantees a non-null [String] that is at least empty
     * @return valid or invalid [String]
     */
    private String getLastName() {

        final View      view                = this.getView();
        final EditText  lastNameEditText    = view != null ?
                (EditText) view.findViewById(R.id.fragment_sign_up_last_name_input) : null;

        String lastName = "";

        CharSequence text = (lastNameEditText != null ? lastNameEditText.getText() : null);

        if(text != null) lastName = text.toString();

        return lastName;
    }

    /**
     * Removes the handles from all of this activities' member variables
     */
    private void dispose() {

        if (this.snackBar != null)  this.snackBar.dismiss();

        this.snackBar           = null;
<<<<<<< Updated upstream
=======
        this.signInListener     = null;
>>>>>>> Stashed changes
        this.signUpListener     = null;

    }

}
