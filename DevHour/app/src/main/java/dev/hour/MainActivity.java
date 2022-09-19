package dev.hour;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}