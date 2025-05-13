package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Login activity that handles the login screen layout and initializes the {@link LoginFragment}.
 * This activity also enables full edge-to-edge screen usage and manages system UI insets
 * (such as the status and navigation bars) to avoid overlapping the UI content.
 */
public class Login extends AppCompatActivity {

    /**
     * Called when the activity is starting. This is where most initialization should go.
     * This method sets the content view, handles edge-to-edge layout, and loads the {@link LoginFragment}.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge layout to use the full screen area
        EdgeToEdge.enable(this);

        // Set the layout for this activity
        setContentView(R.layout.activity_login);

        // Apply window insets (status/navigation bars) padding to the root view
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Extract system bar insets
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Apply padding to the view to prevent overlap
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Load the LoginFragment if this is the first time the activity is created
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())  // Replace container with fragment
                    .commit();  // Commit the transaction
        }
    }
}
