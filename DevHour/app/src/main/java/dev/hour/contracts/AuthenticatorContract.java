package dev.hour.contracts;

public interface AuthenticatorContract {

    interface Authenticator {

        void checkSession();
        void signUp(final String username, final String password,
                    final String firstName, final String lastName);
        void signIn(final String username, final String password);

        interface Listener {

            void onSignInFailed(final String message);
            void onSignUpFailed(final String message);
            void onAuthenticated(final String message);
            void onUnauthenticated(final String message);

        }

    }

    interface Presenter {

        void setAuthenticator(Authenticator authenticator);
        void setAuthenticatorView(View view);

    }

    interface View {

        interface SignUpListener {

            void onReceivedSignUpInput(final String username, final String password,
                                       final String firstName, final String lastName);
            void onRequestSignIn();

        }

        interface SignInListener {

            void onReceivedSignInInput(final String username, final String password);
            void onRequestSignUp();

        }

    }

}
