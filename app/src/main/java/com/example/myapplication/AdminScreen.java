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

public class AdminScreen extends AppCompatActivity {

    EditText etActivityName, etLocation, etLocationLink, etPricesInArea, etTargetAudience, etAbout, etImageLink, etType;
    Button btSaveActivity, btBackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        // מציאת רכיבים במסך
        etActivityName = findViewById(R.id.etActivityName);
        etLocation = findViewById(R.id.etLocation);
        etLocationLink = findViewById(R.id.etLocationLink);
        etPricesInArea = findViewById(R.id.etPricesInArea);
        etTargetAudience = findViewById(R.id.etTargetAudience);
        etAbout = findViewById(R.id.etAbout);
        etImageLink = findViewById(R.id.etImageLink);
        etType = findViewById(R.id.etType);
        btSaveActivity = findViewById(R.id.btSaveActivity);
        btBackToMain = findViewById(R.id.btBackToMain); // מציאת כפתור החזרה

        // לחיצה על שמירה
        btSaveActivity.setOnClickListener(v -> saveActivity());

        // לחיצה על חזרה
        btBackToMain.setOnClickListener(v -> finish());
    }

    private void saveActivity() {
        String ActivityName = etActivityName.getText().toString();
        String Location = etLocation.getText().toString();
        String LocationLink = etLocationLink.getText().toString();
        String PricesInArea = etPricesInArea.getText().toString();
        String TargetAudience = etTargetAudience.getText().toString();
        String about = etAbout.getText().toString();
        String imageLink = etImageLink.getText().toString();
        String type = etType.getText().toString();

        // בדיקה אם כל השדות מולאו
        if (TextUtils.isEmpty(ActivityName) || TextUtils.isEmpty(Location) || TextUtils.isEmpty(LocationLink) ||
                TextUtils.isEmpty(PricesInArea) || TextUtils.isEmpty(TargetAudience) || TextUtils.isEmpty(about) ||
                TextUtils.isEmpty(imageLink) || TextUtils.isEmpty(type)) {

            Toast.makeText(this, "אנא מלא את כל השדות", Toast.LENGTH_SHORT).show();
            return;
        }

        // שמירה ב-Firebase
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
                    Toast.makeText(AdminScreen.this, "הפעילות נשמרה בהצלחה!", Toast.LENGTH_SHORT).show();

                    // השמעת צליל הצלחה
                    Intent soundIntent = new Intent(AdminScreen.this, SoundService.class);
                    startService(soundIntent);

                    clearFields();

                    Intent intent = new Intent(AdminScreen.this, SuggestionScreen.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(AdminScreen.this, "שגיאה בשמירה", Toast.LENGTH_SHORT).show());
    }

        // פונקציה לנקות את השדות לאחר שמירה
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
