package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitiesRecyclerViewActivity extends AppCompatActivity {

    EditText nameSearch, genreSearch;
    Context context;
    Button searchByNameButton, buttonSearchByType, showAllButton, btBackToSuggestion;
    TextView textViewWeather;
    RecyclerView activitiesRecyclerView;
    ActivityAdapter adapter;
    List<ActivitiesFeatures> data = new ArrayList<>();
    FirebaseFirestore db;

    private static final String TAG = "API_For_Check";
    private static final String API_KEY = "4af745b948b1c2b9184c06d8aa357ec0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        context = this;
        db = FirebaseFirestore.getInstance();

        searchByNameButton = findViewById(R.id.buttonSearchByName);
        buttonSearchByType = findViewById(R.id.buttonSearchByType);
        showAllButton = findViewById(R.id.buttonShowAll);
        nameSearch = findViewById(R.id.editTextSearchByName);
        genreSearch = findViewById(R.id.editTextSearchByType);
        activitiesRecyclerView = findViewById(R.id.recyclerViewActivitiesList);
        textViewWeather = findViewById(R.id.textViewWeather);
        btBackToSuggestion = findViewById(R.id.btBackToSuggestion);

        activitiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ActivityAdapter(this, data);
        activitiesRecyclerView.setAdapter(adapter);

        makeData();

        searchByNameButton.setOnClickListener(v -> searchByName());
        buttonSearchByType.setOnClickListener(v -> searchByGenre());
        showAllButton.setOnClickListener(v -> showAllActivities());
        btBackToSuggestion.setOnClickListener(v -> finish());

        // Weather API
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<WeatherResponse> call = apiService.getWeatherData("Tel Aviv", "metric", API_KEY);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weather = response.body();
                    double temp = weather.getMain().getTemp();
                    String result = "Temp: " + temp + "°C";
                    textViewWeather.setText(result);
                } else {
                    textViewWeather.setText("Error retrieving data. Code: " + response.code());
                    Log.e(TAG, "API Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                textViewWeather.setText("API Error: " + t.getMessage());
                Log.e(TAG, "API Call Failure", t);
            }
        });
    }

    public void makeData() {
        db.collection("Activities")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        data.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, "Loaded activity: " + document.getData());
                            try {
                                ActivitiesFeatures activity = document.toObject(ActivitiesFeatures.class);
                                data.add(activity);
                            } catch (Exception e) {
                                Log.e(TAG, "Error converting document: " + e.getMessage());
                            }
                        }
                        adapter.updateData(data);
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                        Toast.makeText(context, "Error loading activities", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void searchByName() {
        String nameQuery = nameSearch.getText().toString().trim();
        if (!nameQuery.isEmpty()) {
            db.collection("Activities")
                    .whereEqualTo("ActivityName", nameQuery)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            data.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    ActivitiesFeatures activity = document.toObject(ActivitiesFeatures.class);
                                    data.add(activity);
                                } catch (Exception e) {
                                    Log.e(TAG, "Error converting document: " + e.getMessage());
                                }
                            }
                            adapter.updateData(data);
                        } else {
                            Log.w(TAG, "Error searching by name.", task.getException());
                            Toast.makeText(context, "Error searching by name", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show();
        }
    }

    public void searchByGenre() {
        String genreQuery = genreSearch.getText().toString().trim();
        if (!genreQuery.isEmpty()) {
            db.collection("Activities")
                    .whereEqualTo("type", genreQuery)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            data.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    ActivitiesFeatures activity = document.toObject(ActivitiesFeatures.class);
                                    data.add(activity);
                                } catch (Exception e) {
                                    Log.e(TAG, "Error converting document: " + e.getMessage());
                                }
                            }
                            adapter.updateData(data);
                        } else {
                            Log.w(TAG, "Error searching by genre.", task.getException());
                            Toast.makeText(context, "Error searching by genre", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(context, "Please enter a genre", Toast.LENGTH_SHORT).show();
        }
    }

    public void showAllActivities() {
        makeData(); // Reload data from the database
    }
}
