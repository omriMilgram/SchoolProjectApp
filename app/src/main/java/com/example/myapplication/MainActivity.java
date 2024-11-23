package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Declaring variables to link the Views (UI components) to the variables in the code
    TextView tvTitle;         // Variable representing the TextView for the title text
    ImageView ivMonkey;      // Variable representing the ImageView for the monkey image
    Button btButtonToLogin;  // Variable representing the button to navigate to the login screen

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setting the layout for the activity
        setContentView(R.layout.activity_main);

        // Linking the variables to the corresponding Views in the XML layout
        tvTitle = findViewById(R.id.tvTitle);               // Link to the TextView for the title
        ivMonkey = findViewById(R.id.ivMonkey);             // Link to the ImageView for the monkey image
        btButtonToLogin = findViewById(R.id.btButtonToLogin); // Link to the button for navigation

        // Defining the action for the login button
        btButtonToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating an Intent to navigate to the Login screen
                Intent intent = new Intent(MainActivity.this, Login.class);
                // Starting the activity and navigating to the login screen
                startActivity(intent);
            }
        });
    }
}
