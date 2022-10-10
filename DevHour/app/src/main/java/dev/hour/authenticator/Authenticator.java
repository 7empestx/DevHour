package dev.hour.authenticator;

import static software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType.USER_PASSWORD_AUTH;

import android.util.Log;

import dev.hour.contracts.AuthenticatorContract;
import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentity.CognitoIdentityClient;
import software.amazon.awssdk.services.cognitoidentity.model.Credentials;
import software.amazon.awssdk.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import software.amazon.awssdk.services.cognitoidentity.model.GetIdRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderAsyncClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GlobalSignOutRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GlobalSignOutResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Authenticator implements AuthenticatorContract.Authenticator {

    /// --------------
    /// Private Fields

    private CognitoIdentityProviderAsyncClient           cognitoClient  ;
    private AuthenticatorContract.Authenticator.Listener listener       ;
    private Credentials                                  credentials    ;
    private String                                       idToken        ;
    private String                                       accessToken    ;
    private final String                                 accountId      ;
    private final String                                 region         ;
    private final String                                 authEndpoint   ;
    private final String                                 identityPoolId ;
    private final String                                 clientId       ;

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
                         final String identityPoolId, final String authEndpoint) {

        this.accountId      = accountId         ;
        this.region         = region            ;
        this.clientId       = clientId          ;
        this.identityPoolId = identityPoolId    ;
        this.authEndpoint   = authEndpoint      ;
        this.cognitoClient  = CognitoIdentityProviderAsyncClient.builder()
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

        final SignUpRequest signUpRequest = SignUpRequest
                .builder()
                .username(input.get("username"))
                .password(input.get("password"))
                .clientId(clientId)
                .userAttributes(AttributeType
                        .builder()
                        .name("name")
                        .value(input.get("name"))
                        .build())
                .build();

        final CompletableFuture<SignUpResponse> response = cognitoClient.signUp(signUpRequest);

        response.whenComplete((result, e) -> {

            if (result != null) {

                Log.i("Cognito", "Result: " + response);

                if(this.listener != null)
                    this.listener.onSignUp("Sign Up Succeeded");

            } else {

                Log.e("Cognito", e.toString());

                if(listener != null)
                    listener.onSignUpFailed("Sign-up failed");

            }

            Log.i("Cognito", idToken != null ? "Sign in succeeded" : "Sign in not complete");

        });

    }

    /**
     * Signs the User in using the specified fields
     * @param input The fields to sign the user in.
     */
    @Override
    public void signIn(final Map<String, String> input) {

        final InitiateAuthRequest authRequest = InitiateAuthRequest
                .builder()
                .authFlow(USER_PASSWORD_AUTH)
                .clientId(this.clientId)
                .authParameters(input)
                .build();

        final CompletableFuture<InitiateAuthResponse> response =
                this.cognitoClient.initiateAuth(authRequest);

        response.whenComplete((result, e) -> {

            Log.e("Authenticator", "Result" + result);
            if (result != null) {

                idToken         = result.authenticationResult().idToken();
                accessToken     = result.authenticationResult().accessToken();

                Log.i("Authenticator", "Creating Client");
                final CognitoIdentityClient client = CognitoIdentityClient.builder()
                        .region(Region.of(this.region))
                        .build();

                final Map<String, String> logins = new HashMap<>();

                Log.i("Authenticator", "Creating logins");
                logins.put(authEndpoint + "/" + identityPoolId, idToken);

                Log.i("Authenticator", "Getting Credentials");
                this.credentials =
                        getCredentialsForIdentity(client, logins, identityPoolId, accountId);

                Log.i("Authenticator", "REtrieved Credentials");
                if(listener != null) {

                    final Map<String, String> credentials = new HashMap<>();

                    credentials.put("ACCESS_KEY", this.credentials.accessKeyId());
                    credentials.put("SECRET_KEY", this.credentials.secretKey());

                    Log.i("Authenticator", "Notifying Listener");
                    listener.onAuthenticated(credentials);

                }

            } else {

                Log.e("Cognito", e.toString());

                if(listener != null)
                    listener.onSignInFailed("Sign-in failed");

            }

            Log.i("Cognito", idToken != null ? "Sign in succeeded" : "Sign in not complete");

        });

    }

    /**
     * Signs the user out.
     */
    @Override
    public void signOut() {

        final GlobalSignOutRequest signOutRequest = GlobalSignOutRequest
                .builder()
                .accessToken(accessToken)
                .build();

        final CompletableFuture<GlobalSignOutResponse> response =
                cognitoClient.globalSignOut(signOutRequest);

        response.whenComplete((result, e) -> {

            if (result != null) {

                Log.i("Cognito", "Signed out successfully");

                if(listener != null)
                    listener.onSignOut("User Signed out");

            } else {

                Log.e("Cognito", e.toString());

                if(listener != null)
                    listener.onSignOutFailed("Sign out failed");

            }

            Log.i("Cognito", idToken != null ? "Sign out succeeded" : "Sign out not complete");

        });

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
