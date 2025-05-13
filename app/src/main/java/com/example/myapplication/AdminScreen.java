package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * AdminScreen allows the admin to add new activities to Firebase Firestore.
 * The admin fills in various details and can save them to the database or return to the main screen.
 */
public class AdminScreen extends AppCompatActivity {

    EditText etActivityName, etLocation, etLocationLink, etPricesInArea, etTargetAudience, etAbout, etImageLink, etType;
    Button btSaveActivity, btBackToMain;

    /**
     * Initializes the screen and sets up click listeners for buttons.
     *
     * @param savedInstanceState Saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        // Find views in the layout
        etActivityName = findViewById(R.id.etActivityName);
        etLocation = findViewById(R.id.etLocation);
        etLocationLink = findViewById(R.id.etLocationLink);
        etPricesInArea = findViewById(R.id.etPricesInArea);
        etTargetAudience = findViewById(R.id.etTargetAudience);
        etAbout = findViewById(R.id.etAbout);
        etImageLink = findViewById(R.id.etImageLink);
        etType = findViewById(R.id.etType);
        btSaveActivity = findViewById(R.id.btSaveActivity);
        btBackToMain = findViewById(R.id.btBackToMain); // Find back button

        // Set click listener for save button
        btSaveActivity.setOnClickListener(v -> saveActivity());

        // Set click listener for back button
        btBackToMain.setOnClickListener(v -> finish());
    }

    /**
     * Collects the data from input fields and saves it to Firebase Firestore.
     * Displays a success message, plays a sound, clears the fields,
     * and navigates to the SuggestionScreen if successful.
     * Displays an error message if validation fails or saving fails.
     */
    private void saveActivity() {
        String ActivityName = etActivityName.getText().toString();
        String Location = etLocation.getText().toString();
        String LocationLink = etLocationLink.getText().toString();
        String PricesInArea = etPricesInArea.getText().toString();
        String TargetAudience = etTargetAudience.getText().toString();
        String about = etAbout.getText().toString();
        String imageLink = etImageLink.getText().toString();
        String type = etType.getText().toString();

        // Check if all fields are filled
        if (TextUtils.isEmpty(ActivityName) || TextUtils.isEmpty(Location) || TextUtils.isEmpty(LocationLink) ||
                TextUtils.isEmpty(PricesInArea) || TextUtils.isEmpty(TargetAudience) || TextUtils.isEmpty(about) ||
                TextUtils.isEmpty(imageLink) || TextUtils.isEmpty(type)) {

            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save data to Firebase Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> activityData = new HashMap<>();
        activityData.put("ActivityName", ActivityName);
        activityData.put("Location", Location);
        activityData.put("LocationLink", LocationLink);
        activityData.put("PricesInArea", PricesInArea);
        activityData.put("TargetAudience", TargetAudience);
        activityData.put("about", about);
        activityData.put("imageLink", imageLink);
        activityData.put("type", type);

        db.collection("Activities").add(activityData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AdminScreen.this, "Activity saved successfully!", Toast.LENGTH_SHORT).show();

                    // Play success sound
                    Intent soundIntent = new Intent(AdminScreen.this, SoundService.class);
                    startService(soundIntent);

                    clearFields();

                    // Go to SuggestionScreen after saving
                    Intent intent = new Intent(AdminScreen.this, SuggestionScreen.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(AdminScreen.this, "Error saving activity", Toast.LENGTH_SHORT).show());
    }

    /**
     * Clears all input fields after the activity is saved.
     */
    private void clearFields() {
        etActivityName.setText("");
        etLocation.setText("");
        etLocationLink.setText("");
        etPricesInArea.setText("");
        etTargetAudience.setText("");
        etAbout.setText("");
        etImageLink.setText("");
        etType.setText("");
    }
}
