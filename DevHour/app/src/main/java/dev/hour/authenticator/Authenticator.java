package dev.hour.authenticator;

import static software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType.USER_PASSWORD_AUTH;

import android.content.Context;
import android.util.Log;

import dev.hour.R;
import dev.hour.contracts.AuthenticatorContract;
import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentity.model.CognitoIdentityProvider;
import software.amazon.awssdk.services.cognitoidentity.model.Credentials;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderAsyncClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GlobalSignOutRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GlobalSignOutResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Authenticator implements AuthenticatorContract.Authenticator {

    /// --------------
    /// Private Fields

    private AuthenticatorContract.Authenticator.Listener listener       ;
    private Context                                      context        ;
    private String                                       idToken        ;
    private String                                       accessToken    ;
    private String                                       refreshToken   ;
    private CognitoIdentityProviderAsyncClient           cognitoClient  ;

    /// ------------
    /// Constructors

    public Authenticator(final Context context) {

        this.context = context;
        cognitoClient = CognitoIdentityProviderAsyncClient.builder()
                .credentialsProvider(AnonymousCredentialsProvider.create())
                .region(Region.of(context.getString(R.string.region)))
                .build();
    }

    @Override
    public void checkSession() {

    }

    @Override
    public void signUp(Map<String, String> input) {
        SignUpRequest signUpRequest = SignUpRequest
                .builder()
                .username(input.get("USERNAME"))
                .password(input.get("PASSWORD"))
                .clientId(context.getString(R.string.client_id))
                .userAttributes(AttributeType
                        .builder()
                        .name("name")
                        .value(input.get("name"))
                        .build())
                .build();

        CompletableFuture<SignUpResponse> response = cognitoClient.signUp(signUpRequest);

        response.whenComplete((result, e) -> {
            if (result != null) {
                Log.i("Cognito", "Result: " + response.toString());
                if(listener != null) {
                    listener.onAuthenticated("User Authenticated");
                }
            } else {
                Log.e("Cognito", e.toString());
                if(listener != null) {
                    listener.onSignUpFailed("Sign-up failed");
                }
            }
            Log.i("Cognito", idToken != null ? "Sign in succeeded" : "Sign in not complete");
        });
    }

    @Override
    public void signIn(Map<String, String> input) {
        InitiateAuthRequest authRequest = InitiateAuthRequest
                .builder()
                .authFlow(USER_PASSWORD_AUTH)
                .clientId(context.getString(R.string.client_id))
                .authParameters(input)
                .build();

        CompletableFuture<InitiateAuthResponse> response = cognitoClient.initiateAuth(authRequest);

        response.whenComplete((result, e) -> {
            if (result != null) {
                idToken = result.authenticationResult().idToken();
                accessToken = result.authenticationResult().accessToken();
                refreshToken = result.authenticationResult().refreshToken();
                if(listener != null) {
                    listener.onAuthenticated("User Authenticated");
                }
            } else {
                Log.e("Cognito", e.toString());
                if(listener != null) {
                    listener.onSignInFailed("Sign-in failed");
                }
            }
            Log.i("Cognito", idToken != null ? "Sign in succeeded" : "Sign in not complete");
        });

    }

    @Override
    public void signOut() {
        GlobalSignOutRequest signOutRequest = GlobalSignOutRequest
                .builder()
                .accessToken(accessToken)
                .build();

        CompletableFuture<GlobalSignOutResponse> response = cognitoClient.globalSignOut(signOutRequest);

        response.whenComplete((result, e) -> {
            if (result != null) {
                Log.i("Cognito", "Signed out successfully");
                if(listener != null) {
                    listener.onSignOut("User Signed out");
                }
            } else {
                Log.e("Cognito", e.toString());
                if(listener != null) {
                    listener.onSignOutFailed("Sign out failed");
                }
            }
            Log.i("Cognito", idToken != null ? "Sign out succeeded" : "Sign out not complete");
        });
    }

    @Override
    public void setListener(Listener listener) {

        this.listener = listener;

    }

}
