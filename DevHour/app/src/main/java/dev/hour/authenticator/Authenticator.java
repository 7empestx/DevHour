package dev.hour.authenticator;

import static software.amazon.awssdk.services.cognitoidentityprovider.model.AuthFlowType.USER_PASSWORD_AUTH;

import android.content.Context;
import android.util.Log;

import dev.hour.R;
import dev.hour.contracts.AuthenticatorContract;
import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.GlobalSignOutRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.InitiateAuthResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpResponse;

import java.util.Map;

public class Authenticator implements AuthenticatorContract.Authenticator {

    /// --------------
    /// Private Fields

    private AuthenticatorContract.Authenticator.Listener listener       ;
    private Context                                      context        ;
    private String                                       idToken        ;
    private String                                       accessToken    ;
    private String                                       refreshToken   ;
    private CognitoIdentityProviderClient                cognitoClient  ;

    /// ------------
    /// Constructors

    public Authenticator(final Context context) {

        this.context = context;
        cognitoClient = CognitoIdentityProviderClient.builder()
                .credentialsProvider(AnonymousCredentialsProvider.create())
                .httpClient(UrlConnectionHttpClient.create())
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
        try {
            SignUpResponse response = cognitoClient.signUp(signUpRequest);
            Log.i("Cognito", "Result: " + response.toString());
            if(listener != null) {
                listener.onAuthenticated("User Authenticated");
            }
        } catch (Exception e) {
            Log.e("Cognito", e.toString());
            if(listener != null) {
                listener.onSignUpFailed("Sign-up failed");
            }
        }
    }

    @Override
    public void signIn(Map<String, String> input) {
        InitiateAuthRequest authRequest = InitiateAuthRequest
                .builder()
                .authFlow(USER_PASSWORD_AUTH)
                .clientId(context.getString(R.string.client_id))
                .authParameters(input)
                .build();

        try {
            InitiateAuthResponse result = cognitoClient.initiateAuth(authRequest);

            idToken = result.authenticationResult().idToken();
            accessToken = result.authenticationResult().accessToken();
            refreshToken = result.authenticationResult().refreshToken();

            if(listener != null) {
                listener.onAuthenticated("User Authenticated");
            }
        } catch (Exception e) {
            Log.e("Cognito", e.toString());
            if(listener != null) {
                listener.onSignInFailed("Sign-in failed");
            }
        } finally {
            Log.i("Cognito", idToken != null ? "Sign in succeeded" : "Sign in not complete");
        }
    }

    @Override
    public void signOut() {
        GlobalSignOutRequest signOutRequest = GlobalSignOutRequest
                .builder()
                .accessToken(accessToken)
                .build();

        try {
            cognitoClient.globalSignOut(signOutRequest);
            Log.i("Cognito", "Signed out successfully");
            if(listener != null) {
                listener.onSignOut("User Signed out");
            }
        } catch (Exception e) {
            Log.e("Cognito", e.toString());
            if(listener != null) {
                listener.onSignOutFailed("Sign out failed");
            }
        } finally {
            Log.i("Cognito", idToken != null ? "Sign in succeeded" : "Sign in not complete");
        }
    }

    @Override
    public void setListener(Listener listener) {

        this.listener = listener;

    }

}
