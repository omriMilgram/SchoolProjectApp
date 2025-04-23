package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class API_For_Check extends AppCompatActivity {

    private static final String TAG = "API_For_Check";
    private static final String API_KEY = "4af745b948b1c2b9184c06d8aa357ec0"; // שים כאן את המפתח שלך

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_for_check);

        textView = findViewById(R.id.textView2);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<WeatherResponse> call = apiService.getWeatherData("Tel Aviv", "metric", API_KEY);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weather = response.body();
                    double temp = weather.getMain().getTemp(); // רק הטמפרטורה
                    String result = "טמפרטורה: " + temp + "°C"; // הצגת הטמפרטורה בלבד
                    textView.setText(result);
                } else {
                    textView.setText("שגיאה בנתונים מהשרת. קוד: " + response.code());
                    Log.e(TAG, "API Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                textView.setText("כשל בחיבור לשרת: " + t.getMessage());
                Log.e(TAG, "API Call Failure", t);
            }
        });
    }
}
