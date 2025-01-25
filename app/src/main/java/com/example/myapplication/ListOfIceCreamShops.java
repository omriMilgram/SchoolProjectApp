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

public class ListOfIceCreamShops extends AppCompatActivity {

    ArrayList<NameActivity> iceCreamShops;
    ListView lvIceCreamShops;
    ArrayAdapter<NameActivity> adapter;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_ice_cream_shops);

        // קישור ל-ListView
        lvIceCreamShops = findViewById(R.id.lvIceCreamShops);

        // Fill data and set up the adapter
        fillData();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, iceCreamShops);
        lvIceCreamShops.setAdapter(adapter);

        // טיפול ב-insets עבור עיצוב edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // קישור לכפתור החזרה
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            // חזרה למסך הקודם
            Intent intent = new Intent(ListOfIceCreamShops.this, RecyclerView.class);
            startActivity(intent);
        });
    }

    public void fillData() {
        iceCreamShops = new ArrayList<>();
        iceCreamShops.add(new NameActivity("Vanilla Ice Cream Shop"));
        iceCreamShops.add(new NameActivity("Golada"));
        iceCreamShops.add(new NameActivity("Ice Cream Kingdom"));
    }
}
