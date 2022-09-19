package dev.hour.contracts;

public interface AuthenticatorContract {

    interface Authenticator {

        void checkSession();

        interface Listener {

            void onAuthenticated(final String message);
            void onNotAuthenticated(final String message);

        }

    }

}
