package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Arrays;
import java.util.List;

public class RecyclerView extends AppCompatActivity {

    private androidx.recyclerview.widget.RecyclerView recyclerView;
    private ActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        // קישור ל-RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        // הגדרת LayoutManager עבור רשימה אנכית
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // יצירת רשימת פריטים לדוגמה
        List<String> activities = Arrays.asList("Beaches", "Cafes", "Ice cream shops", "Museums");

        // הגדרת ה-Adapter
        adapter = new ActivityAdapter(this, activities, position -> {
            String selectedActivity = activities.get(position);
            if (selectedActivity.equals("Beaches")) {
                Intent intent = new Intent(RecyclerView.this, ListOfBeaches.class);
                startActivity(intent);
            }
            if (selectedActivity.equals("Cafes")) {
                Intent intent = new Intent(RecyclerView.this, ListOfCafes.class);
                startActivity(intent);
            }
            if (selectedActivity.equals("Ice cream shops")) {
                Intent intent = new Intent(RecyclerView.this, ListOfIceCreamShops.class);
                startActivity(intent);
            }
            if (selectedActivity.equals("Museums")) {
                Intent intent = new Intent(RecyclerView.this, ListOfMuseums.class);
                startActivity(intent);
            }
            // ניתן להוסיף תנאים נוספים עבור פעילויות אחרות
        });

        // חיבור ה-Adapter ל-RecyclerView
        recyclerView.setAdapter(adapter);
    }
}