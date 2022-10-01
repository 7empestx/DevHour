package dev.hour.authenticator;

import android.content.Context;
import android.util.Log;

import dev.hour.contracts.AuthenticatorContract;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthChannelEventName;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.hub.HubChannel;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Authenticator implements AuthenticatorContract.Authenticator {

    /// --------------
    /// Private Fields

    private AuthenticatorContract.Authenticator.Listener listener   ;
    private Context                                      context    ;

    /// ------------
    /// Constructors

    public Authenticator(final Context context) {

        this.context = context;

    }

    @Override
    public void checkSession() {

        // Clear the DataStore when user signs in.
        final String signedInEventName = AuthChannelEventName.SIGNED_IN.toString();

        Amplify.Hub.subscribe(HubChannel.AUTH,
                anyAuthEvent -> signedInEventName.equals(anyAuthEvent.getName()),
                signedInEvent -> Amplify.DataStore.clear(
                        () -> Log.i("Amplify", "DataStore is cleared."),
                        failure -> Log.e("Amplify", "Failed to clear DataStore.")
                )
        );

        try {

            Amplify.addPlugin(new AWSApiPlugin()); // UNCOMMENT this line once backend is deployed
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(this.context);
            Log.i("Amplify", "Initialized Amplify");

        } catch (AmplifyException error) {

            Log.e("Amplify", "Could not initialize Amplify", error);

        }

        Amplify.Auth.fetchAuthSession(

                result ->   {

                    Log.i("Amplify", result.toString());

                    if(listener != null) {

                        listener.onAuthenticated("User Authenticated");

                    }

                },

                error -> {

                    Log.e("Amplify", error.toString());

                    if(listener != null) {

                        listener.onSignInFailed("Sign in failed");

                    }

                }

        );

    }

    @Override
    public void signUp(Map<String, String> input) {

        final List attr = new Vector();

        attr.add(new AuthUserAttribute(AuthUserAttributeKey.email(), input.get("email")));

        Amplify.Auth.signUp(
                input.get("email"),
                input.get("password"),
                AuthSignUpOptions.builder()
                        .userAttributes(attr).build(),
                result -> {

                    Log.i("Amplify Auth", "Result: " + result.toString());

                    if(listener != null) {

                        listener.onAuthenticated("User Authenticated");

                    }

                },

                error -> {

                    Log.e("Amplify Auth", "Sign up failed", error);

                    if(listener != null) {

                        listener.onSignUpFailed("Sign Up Failed");

                    }

                }
        );

    }

    @Override
    public void signIn(Map<String, String> input) {

        Amplify.Auth.signIn(
                input.get("email"),
                input.get("password"),
                result -> {
                    Log.i("Amplify Auth", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");

                        if(listener != null) {

                            listener.onAuthenticated("User Authenticated");

                        }

                    },

                error -> {

                    Log.e("Amplify Auth", error.toString());

                    if(listener != null) {

                        listener.onSignInFailed("Sign-in failed");

                    }

                }

        );

    }

    @Override
    public void signOut() {

        Amplify.Auth.signOut(
                () -> {

                    Log.i("Amplify Auth", "Signed out successfully");

                    if(listener != null) {

                        listener.onSignOut("User Signed out");

                    }

                },
                error -> {

                    Log.e("Amplify Auth", error.toString());

                    if(listener != null) {

                        listener.onSignOutFailed("Sign out failed");

                    }

                }

        );

    }

    @Override
    public void setListener(Listener listener) {

        this.listener = listener;

    }

}
