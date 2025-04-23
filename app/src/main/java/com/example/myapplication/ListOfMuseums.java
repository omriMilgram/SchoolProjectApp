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

public class ListOfMuseums extends AppCompatActivity {

    ArrayList<NameActivity> museums;
    ListView lvMuseums;
    ArrayAdapter<NameActivity> adapter;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_of_museums);  // Make sure this layout exists

        // Link the ListView
        lvMuseums = findViewById(R.id.lvMuseums);  // Check that this ID exists in the XML

        // Fill data and set up the adapter
        fillData();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, museums);
        lvMuseums.setAdapter(adapter);

        // Handle insets for edge-to-edge design
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // קישור לכפתור החזרה
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            // חזרה למסך הקודם (המסך בו היה ה-RecyclerView)
            Intent intent = new Intent(ListOfMuseums.this, ActivitiesRecyclerViewActivity.class);
            startActivity(intent);
        });
    }

    public void fillData() {
        museums = new ArrayList<>();
        museums.add(new NameActivity("Israel Museum"));
        museums.add(new NameActivity("Tel Aviv Museum of Art"));
        museums.add(new NameActivity("Lehi Museum"));
    }
}
