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

public class ListOfBeaches extends AppCompatActivity {

    ArrayList<NameActivity> beaches;
    ListView lvBeaches;
    ArrayAdapter<NameActivity> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_of_beaches);

        // קישור ל-ListView
        lvBeaches = findViewById(R.id.lvBeaches);
        fillData();

        // הגדרת ה-Adapter והצגת הנתונים
        adapter = new ArrayAdapter<NameActivity>(this, android.R.layout.simple_list_item_1, beaches);
        lvBeaches.setAdapter(adapter);

        // טיפול ב-insets עבור עיצוב edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.list_of_beaches), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // קישור לכפתור החזרה
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            // חזרה למסך הקודם (RecyclerView)
            Intent intent = new Intent(ListOfBeaches.this, ActivitiesRecyclerViewActivity.class);
            startActivity(intent);
        });
    }

    public void fillData(){
        beaches = new ArrayList<>();
        beaches.add(new NameActivity("Gordon Beach"));
        beaches.add(new NameActivity("Hilton Beach"));
        beaches.add(new NameActivity("Frishman Beach"));
        beaches.add(new NameActivity("Dolfinarium Beach"));
        beaches.add(new NameActivity("Jerusalem Beach"));
    }
}
