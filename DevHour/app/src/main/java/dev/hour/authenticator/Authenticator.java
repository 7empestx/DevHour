package dev.hour.authenticator;

import static software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType.USER_PASSWORD_AUTH;

import android.util.Log;

import dev.hour.contracts.AuthenticatorContract;
import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentity.CognitoIdentityClient;
import software.amazon.awssdk.services.cognitoidentity.model.CognitoIdentityProvider;
import software.amazon.awssdk.services.cognitoidentity.model.Credentials;
import software.amazon.awssdk.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import software.amazon.awssdk.services.cognitoidentity.model.GetIdRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderAsyncClient;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GlobalSignOutRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GlobalSignOutResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpResponse;
import software.amazon.awssdk.utils.internal.SystemSettingUtilsTestBackdoor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Authenticator implements AuthenticatorContract.Authenticator {

    /// --------------
    /// Private Fields

    private CognitoIdentityProviderClient                cognitoClient  ;
    private AuthenticatorContract.Authenticator.Listener listener       ;
    private Credentials                                  credentials    ;
    private String                                       idToken        ;
    private String                                       accessToken    ;
    private final String                                 accountId      ;
    private final String                                 region         ;
    private final String                                 clientId       ;
    private final String                                 identityPoolId ;
    private final String                                 providerName   ;
    private final String                                 authEndpoint   ;
    private final SdkHttpClient                          httpClient     ;

    /// ------------
    /// Constructors

    /**
     * Initializes the Authenticator to its' default state.
     * @param accountId The AWS account id
     * @param region The AWS region of the identity pool
     * @param clientId The client Id
     * @param identityPoolId The identity pool id
     * @param authEndpoint The Authentication endpoint
     */
    public Authenticator(final String accountId, final String region, final String clientId,
                         final String identityPoolId, final String providerName,
                         final String authEndpoint, final SdkHttpClient httpClient) {

        this.accountId      = accountId         ;
        this.region         = region            ;
        this.clientId       = clientId          ;
        this.identityPoolId = identityPoolId    ;
        this.authEndpoint   = authEndpoint      ;
        this.providerName   = providerName      ;
        this.httpClient     = httpClient        ;
        this.cognitoClient  = CognitoIdentityProviderClient.builder()
                .httpClient(this.httpClient)
                .credentialsProvider(AnonymousCredentialsProvider.create())
                .region(Region.of(region))
                .build();

    }

    /**
     * Returns the Identity Id for the given Id token.
     * @param logins The logins corresponding to the identity pool
     * @return identity id that corresponds to the id token.
     */
    public String getIdentityId(final CognitoIdentityClient client,
                                final Map<String, String> logins,
                                final String identityPoolId,
                                final String accountId) {

        final GetIdRequest request = GetIdRequest.builder()
                .identityPoolId(identityPoolId)
                .accountId(accountId)
                .logins(logins)
                .build();

        return client.getId(request).identityId();

    }

    /**
     * Returns the Credentials corresponding to the specified logins.
     * @param client The client to make the request
     * @param logins The logins
     * @param identityPoolId The identity pool id
     * @param accountId The account id
     * @return Credentials
     */
    public Credentials getCredentialsForIdentity(final CognitoIdentityClient client,
                                                 final Map<String, String> logins,
                                                 final String identityPoolId,
                                                 final String accountId) {

        final GetCredentialsForIdentityRequest request = GetCredentialsForIdentityRequest.builder()
                .identityId(getIdentityId(client, logins, identityPoolId, accountId))
                .logins(logins)
                .build();

        return client.getCredentialsForIdentity(request).credentials();

    }

    /**
     * Returns a flag indicating if the user is authenticated.
     */
    @Override
    public void checkSession() {

        if(this.idToken == null || this.credentials == null) {

            if(this.listener != null)
                this.listener.onUnauthenticated("User is not authenticated");

        } else {

            final Map<String, String> credentials = new HashMap<>();

            credentials.put("ACCESS_KEY", this.credentials.accessKeyId());
            credentials.put("SECRET_KEY", this.credentials.secretKey());

            this.listener.onAuthenticated(credentials);

        }

    }

    /**
     * Signs the user up to the service.
     * @param input The user input data
     */
    @Override
    public void signUp(final Map<String, String> input) {

        final Authenticator authenticator = this;

        final Thread thread = new Thread(() -> {

            final SignUpRequest signUpRequest = SignUpRequest
                    .builder()
                    .username(input.get("username"))
                    .password(input.get("password"))
                    .clientId(authenticator.clientId)
                    .userAttributes(AttributeType
                            .builder()
                            .name("name")
                            .value(input.get("name"))
                            .build())
                    .userAttributes(AttributeType
                            .builder()
                            .name("first")
                            .value(input.get("first"))
                            .build())
                    .userAttributes(AttributeType
                            .builder()
                            .name("last")
                            .value(input.get("last"))
                            .build())
                    .userAttributes(AttributeType
                            .builder()
                            .name("email")
                            .value(input.get("email"))
                            .build())
                    .build();

            try {

                authenticator.cognitoClient.signUp(signUpRequest);

            } catch (final Exception exception) {

                Log.e("Cognito", exception.toString());

            }

        });

        try {

            thread.start();
            thread.join();

            if(this.listener != null)
                this.listener.onSignUp(input);

        } catch (final Exception exception) {

            if(listener != null)
                listener.onSignUpFailed("Sign-up failed");

        }

    }

    /**
     * Signs the User in using the specified fields
     * @param input The fields to sign the user in.
     */
    @Override
    public void signIn(final Map<String, String> input) {

        final Authenticator authenticator = this;

        final Thread thread = new Thread(() -> {

            final InitiateAuthRequest authRequest = InitiateAuthRequest
                    .builder()
                    .authFlow(USER_PASSWORD_AUTH)
                    .clientId(authenticator.clientId)
                    .authParameters(input)
                    .build();

            try {

                final InitiateAuthResponse response =
                        authenticator.cognitoClient.initiateAuth(authRequest);

                authenticator.idToken       = response.authenticationResult().idToken();
                authenticator.accessToken   = response.authenticationResult().accessToken();

                final CognitoIdentityClient client = CognitoIdentityClient.builder()
                        .httpClient(authenticator.httpClient)
                        .region(Region.of(authenticator.region))
                        .credentialsProvider(AnonymousCredentialsProvider.create())
                        .build();

                final Map<String, String> logins = new HashMap<>();

                logins.put( authEndpoint + "/" + this.providerName, idToken);

                authenticator.credentials =
                        getCredentialsForIdentity(client, logins, identityPoolId, accountId);

            } catch (final Exception exception) {

                Log.e("Authenticator", exception.toString());

            }

        });

        try {

            thread.start();
            thread.join();

            if(listener != null) {

                final Map<String, String> credentials = new HashMap<>();

                credentials.put("ACCESS_KEY", this.credentials.accessKeyId());
                credentials.put("SECRET_KEY", this.credentials.secretKey());

                this.listener.onAuthenticated(credentials);

            }

        } catch (final Exception exception) {

            Log.e("Authenticator", exception.getMessage());

            if(this.listener != null)
                this.listener.onSignInFailed("Sign-in failed");

        }

    }

    /**
     * Signs the user out.
     */
    @Override
    public void signOut() {

        final Authenticator authenticator = this;

        final Thread thread = new Thread(() -> {

            final GlobalSignOutRequest signOutRequest = GlobalSignOutRequest
                    .builder()
                    .accessToken(authenticator.accessToken)
                    .build();

            try {

                authenticator.cognitoClient.globalSignOut(signOutRequest);

            } catch (final Exception exception) {

                Log.e("Authenticator", exception.getMessage());

            }

        });

        try {

            thread.start();
            thread.join();

            if(authenticator.listener != null)
                authenticator.listener.onSignOut("User Signed out");

        } catch (final Exception exception) {

            Log.e("Authenticator", exception.getMessage());

            if(authenticator.listener != null)
                authenticator.listener.onSignOutFailed("Sign out failed");

        }

    }

    /**
     * Sets the Listener that will receive that callbacks on authentication events.
     * @param listener The listener that will receive callbacks
     */
    @Override
    public void setListener(final Listener listener) {

        this.listener = listener;

        checkSession();

    }

}
