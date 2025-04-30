package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declaring variables
    TextView tvTitle;         // Title TextView
    ImageView backgroundImageView;       // Background ImageView
    Button btButtonToLogin;   // Button for login navigation

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        tvTitle = findViewById(R.id.tvTitle);
        backgroundImageView = findViewById(R.id.backgroundImageView);
        btButtonToLogin = findViewById(R.id.btButtonToLogin);
        context = this;

        // Action for the login button
        btButtonToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
