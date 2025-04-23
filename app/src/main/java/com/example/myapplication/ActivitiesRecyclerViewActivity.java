package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesRecyclerViewActivity extends AppCompatActivity {

    EditText nameSearch, genreSearch;
    Context context;
    Button ByName, ByGenre, buttonShowAll;

    RecyclerView ActivitiesListRV;
    ActivityAdapter adapter;
    List<ActivitiesFeatures> ActivityList;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        context = this;
        db = FirebaseFirestore.getInstance();

        ByName = findViewById(R.id.buttonSearchByName);
        ByGenre = findViewById(R.id.buttonSearchByGenre);
        nameSearch = findViewById(R.id.editTextSearchByName);
        genreSearch = findViewById(R.id.editTextSearchByGenre);
        buttonShowAll = findViewById(R.id.buttonShowAll);

        ActivitiesListRV = findViewById(R.id.recyclerViewActivitiesList);
        ActivityList = new ArrayList<>();
        adapter = new ActivityAdapter(ActivityList);
        ActivitiesListRV.setHasFixedSize(true);
        ActivitiesListRV.setLayoutManager(new LinearLayoutManager(this));
        ActivitiesListRV.setAdapter(adapter);


        getAll();

        ByName.setOnClickListener(view -> {
            String name = nameSearch.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(context, "Search can't be empty", Toast.LENGTH_SHORT).show();
            } else {
                filterByName(name);
            }
        });


        ByGenre.setOnClickListener(view -> {
            String genre = genreSearch.getText().toString();
            if (genre.isEmpty()) {
                Toast.makeText(context, "Search can't be empty", Toast.LENGTH_SHORT).show();
            } else {
                filterByGenre(genre);
            }
        });

        buttonShowAll.setOnClickListener(v -> adapter.updateData(ActivityList));
    }

    public void getAll() {
        db.collection("Activities")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot snapshot = task.getResult();

                        if (snapshot == null || snapshot.isEmpty()) {
                            Toast.makeText(context, "No activities found", Toast.LENGTH_SHORT).show();
                            ActivityList.clear();
                            adapter.updateData(ActivityList);
                            return;
                        }

                        ActivityList.clear();

                        for (QueryDocumentSnapshot document : snapshot) {
                            String name = document.getString("ActivityName");
                            String location = document.getString("Location");
                            String prices = document.getString("PricesInArea");
                            String target = document.getString("TargetAudience");
                            String about = document.getString("about");
                            String type = document.getString("type");

                            if (name == null) name = "No name";
                            if (location == null) location = "Unknown location";
                            if (prices == null) prices = "Unknown prices";
                            if (target == null) target = "Unknown audience";
                            if (about == null) about = "";
                            if (type == null) type = "Other";

                            ActivitiesFeatures activity = new ActivitiesFeatures(name, location, prices, target, about, type);
                            ActivityList.add(activity);
                        }

                        adapter.updateData(ActivityList);
                    } else {
                        Toast.makeText(context, "Failed to load activities: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("FIREBASE", "Error loading activities", task.getException());
                    }
                })
                .addOnFailureListener(e -> Log.e("FIREBASE", "Firestore get() failed: ", e));
    }

    public void filterByName(String name) {
        List<ActivitiesFeatures> res = new ArrayList<>();
        for (ActivitiesFeatures activity : ActivityList) {
            if (activity.getActivityName().equalsIgnoreCase(name)) {
                res.add(activity);
            }
        }
        adapter.updateData(res);
    }

    public void filterByGenre(String genre) {
        List<ActivitiesFeatures> res = new ArrayList<>();
        for (ActivitiesFeatures activity : ActivityList) {
            if (activity.getType().equalsIgnoreCase(genre)) {
                res.add(activity);
            }
        }
        adapter.updateData(res);
    }
}
