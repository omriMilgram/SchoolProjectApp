package com.example.myapplication;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * InfoScreen displays detailed information about a selected activity.
 * The user can view description, location, image, and mark the activity as visited.
 */
public class InfoScreen extends AppCompatActivity {

    FirebaseFirestore db;
    TextView tvActivityName, tvActivityType, tvActivityLocation, tvActivityPrice, tvActivityAudience, tvActivityMoreInfo;
    ImageView ivActivityImage;
    Button btnBack, btnVisitedStatus;

    /**
     * Initializes the UI and loads activity details from Firestore.
     * Allows the user to mark the activity as visited, return to the previous screen,
     * and open the activity's location link in a browser.
     *
     * @param savedInstanceState Saved state of the activity, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_screen);

        // Get the activity name from the intent
        String activityName = getIntent().getStringExtra("activityName");
        if (activityName == null) {
            Toast.makeText(this, "Activity not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize views
        tvActivityName = findViewById(R.id.tvActivityName);
        tvActivityType = findViewById(R.id.tvActivityType);
        tvActivityLocation = findViewById(R.id.tvActivityLocation);
        tvActivityLocation.setPaintFlags(tvActivityLocation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvActivityPrice = findViewById(R.id.tvActivityPrice);
        tvActivityAudience = findViewById(R.id.tvActivityAudience);
        tvActivityMoreInfo = findViewById(R.id.tvActivityMoreInfo);
        ivActivityImage = findViewById(R.id.ivActivityImage);
        btnBack = findViewById(R.id.btnBack);
        btnVisitedStatus = findViewById(R.id.btnVisitedStatus);

        // Load visited status from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("VisitedPrefs", MODE_PRIVATE);
        boolean visited = prefs.getBoolean(activityName, false);
        updateVisitedButton(btnVisitedStatus, visited);

        // Toggle visited status on button click
        btnVisitedStatus.setOnClickListener(v -> {
            boolean newStatus = !prefs.getBoolean(activityName, false);
            prefs.edit().putBoolean(activityName, newStatus).apply();
            updateVisitedButton(btnVisitedStatus, newStatus);

            if (newStatus) {
                Intent soundIntent = new Intent(this, SoundService.class);
                startService(soundIntent);
            }
        });

        // Back button closes the activity
        btnBack.setOnClickListener(view -> finish());

        // Fetch data from Firestore based on the activity name
        db = FirebaseFirestore.getInstance();
        db.collection("Activities")
                .whereEqualTo("ActivityName", activityName)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);

                        ActivitiesFeatures data = new ActivitiesFeatures(document.getString("ActivityName"),
                                document.getString("Location"),
                                document.getString("LocationLink"),
                                document.getString("PricesInArea"),
                                document.getString("TargetAudience"),
                                document.getString("about"),
                                document.getString("imageLink"),
                                document.getString("type"));

                        // Set UI elements with fetched data
                        tvActivityName.setText(data.getActivityName());
                        tvActivityType.setText("Type: " + data.getType());
                        tvActivityLocation.setText("Location: " + data.getLocation());
                        tvActivityPrice.setText("Price: " + data.getPricesInArea());
                        tvActivityAudience.setText("Target audience: " + data.getTargetAudience());
                        tvActivityMoreInfo.setText(data.getAbout());

                        // Open location link on click
                        final String locationLink = data.getLocationLink();
                        if (locationLink != null && !locationLink.isEmpty()) {
                            tvActivityLocation.setOnClickListener(view -> {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(locationLink));
                                startActivity(browserIntent);
                            });
                        }

                        // Load image with Glide or set default image
                        String imageUrl = data.getImageLink();
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Glide.with(this)
                                    .load(imageUrl)
                                    .placeholder(R.drawable.logoapp)
                                    .error(R.drawable.logoapp)
                                    .into(ivActivityImage);
                        } else {
                            ivActivityImage.setImageResource(R.drawable.logoapp);
                        }
                    } else {
                        Toast.makeText(this, "Activity not found in database", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    /**
     * Updates the "Visited" button UI based on the current status.
     *
     * @param button  The button to update.
     * @param visited True if the activity is marked as visited; false otherwise.
     */
    private void updateVisitedButton(Button button, boolean visited) {
        if (visited) {
            button.setText("Visited");
            button.setBackgroundTintList(getResources().getColorStateList(android.R.color.holo_red_dark));
        } else {
            button.setText("Mark as Visited");
            button.setBackgroundTintList(getResources().getColorStateList(android.R.color.darker_gray));
        }
    }
}
