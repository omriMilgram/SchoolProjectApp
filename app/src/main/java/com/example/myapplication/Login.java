package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge experience to utilize full screen
        EdgeToEdge.enable(this);

        // Set the layout for the activity
        setContentView(R.layout.activity_login);

        // Handle window insets (system bars like status and navigation bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Get the insets for system bars (status bar, navigation bar)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Apply padding to the view to avoid overlap with system bars
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            // Return the insets to allow further handling if necessary
            return insets;
        });

        // Check if this is the first time the activity is created
        if (savedInstanceState == null) {

            // Begin a fragment transaction to replace the fragment container with the LoginFragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())  // Replace with LoginFragment
                    .commit();  // Commit the transaction to apply changes
        }
    }
}
