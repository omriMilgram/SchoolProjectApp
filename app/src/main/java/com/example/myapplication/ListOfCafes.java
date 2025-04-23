package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ListOfCafes extends AppCompatActivity {

    ArrayList<NameActivity> cafes;
    ListView lvCafes;
    ArrayAdapter<NameActivity> adapter;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_of_cafes);

        // קישור ל-ListView
        lvCafes = findViewById(R.id.lvCafes);

        // Fill data and set up the adapter
        fillData();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cafes);
        lvCafes.setAdapter(adapter);

        // טיפול ב-insets עבור עיצוב edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // קישור לכפתור החזרה
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            // חזרה למסך הקודם (RecyclerView)
            Intent intent = new Intent(ListOfCafes.this, ActivitiesRecyclerViewActivity.class);
            startActivity(intent);
        });
    }

    public void fillData() {
        cafes = new ArrayList<>();
        cafes.add(new NameActivity("Cafe Aroma"));
        cafes.add(new NameActivity("Cafe Cafe"));
        cafes.add(new NameActivity("Arcaffe"));
        cafes.add(new NameActivity("Landwer Cafe"));
        cafes.add(new NameActivity("Cafe Greg"));
    }
}
