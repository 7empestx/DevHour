package dev.hour.contracts;

import android.view.View;

import java.util.Map;

import dev.hour.view.UserDotView;

public interface AuthenticatorContract {

    interface Authenticator {

        void checkSession();
        void signUp(final Map<String, String> input);
        void signIn(final Map<String, String> input);
        void signOut();
        void setListener(final Listener listener);

        interface Listener {

            void onSignOut(final String message);
            void onSignUp(final Map<String, String> input, final Map<String, String> credentials);
            void onSignInFailed(final String message);
            void onSignUpFailed(final String message);
            void onSignOutFailed(final String message);
            void onAuthenticated(final Map<String, String> credentials, String userId);
            void onUnauthenticated(final String message);

        }

    }

    interface Presenter {

        void setAuthenticator(Authenticator authenticator);
        void setAuthenticatorView(View view);
        void setInteractionListener(final InteractionListener interactionListener);

        interface InteractionListener {

            void onSignInRequest();
            void onSignUpRequest();
            void onAuthenticated(final Map<String, String> credentials, String userId);
            void onUnauthenticated(final String message);
            void onSignOut(final String message);
            void onSignUp(final Map<String, String> input, final Map<String, String> credentials);

        }

    }

    interface View {

        interface SignUpListener {

            void onReceivedSignUpInput(final Map<String, String> input);
            void onRequestSignIn();

        }

        interface SignInListener {

            void onReceivedSignInInput(final Map<String, String> input);
            void onRequestSignUp();

        }

        void onSignUp();
        void onSignIn();
        void setSignInListener(final SignInListener signInListener);
        void setSignUpListener(final SignUpListener signUpListener);
        void onSignInFailed(final String message);
        void onSignUpFailed(final String message);
        void onSignOutFailed(final String message);

    }

}
