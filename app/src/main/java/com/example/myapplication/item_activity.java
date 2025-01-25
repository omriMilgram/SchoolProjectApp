package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class item_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);  // Ensure this points to the correct layout file

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);  // Make sure recyclerView exists in your XML layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // List of activities
        List<String> activities = Arrays.asList("חופים", "בתי קפה", "גלידריות", "מוזאונים");

        // Set up the adapter
        ActivityAdapter adapter = new ActivityAdapter(this, activities, position -> {
            String selectedActivity = activities.get(position);
            if (selectedActivity.equals("חופים")) {
                // Open ListOfBeaches activity
                Intent intent = new Intent(item_activity.this, ListOfBeaches.class);
                startActivity(intent);
            }
            // Add additional actions for other activities if needed
        });

        recyclerView.setAdapter(adapter);
    }
}
