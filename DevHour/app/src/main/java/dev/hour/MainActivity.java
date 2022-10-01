package dev.hour;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dev.hour.authenticator.Authenticator;
import dev.hour.contracts.AuthenticatorContract;

public class MainActivity extends AppCompatActivity {

    private AuthenticatorContract.Authenticator authenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authenticator = new Authenticator(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(authenticator != null) {

            authenticator.checkSession();

        }

    }

}