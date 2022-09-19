package dev.hour.contracts;

public interface AuthenticatorContract {

    interface Authenticator {

        void checkSession();
        void signUp(final String username, final String password,
                    final String firstName, final String lastName);
        void signIn(final String username, final String password);

    }

    interface Presenter {

        void setAuthenticator(Authenticator authenticator);
        void setAuthenticatorView(View view);
        void onAuthenticated(final String message);
        void onUnauthenticated(final String message);

    }

    interface View {

        interface Listener {

            void onReceivedSignInInput(final String username, final String password);
            void onReceivedSignUpInput(final String username, final String password,
                                       final String firstName, final String lastName);

        }

    }

}
