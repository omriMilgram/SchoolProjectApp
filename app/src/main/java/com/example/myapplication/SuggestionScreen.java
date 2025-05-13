package com.example.myapplication;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.OutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * SuggestionScreen is an Activity that provides the functionality to capture a photo,
 * save it to the gallery, display weather data, and navigate to the activities list.
 */
public class SuggestionScreen extends AppCompatActivity {

    private ImageView ivPhoto;
    private Button btCamera, btSavePicture, btActivityList, btButtonBackToLogin;
    private ActivityResultLauncher<Intent> arSmall;
    private Bitmap currentBitmap;
    private TextView tvWeather;

    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final String TAG = "TAG_SUGGESTION_SCREEN";
    private static final String API_KEY = "4af745b948b1c2b9184c06d8aa357ec0";

    /**
     * Initializes the activity and sets up the necessary views and listeners.
     * It also handles the setup of the camera and weather API calls.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_screen);

        // Initialize views
        btCamera = findViewById(R.id.btButtonToTakeAPicture);
        btSavePicture = findViewById(R.id.btButtonSavePicture);
        btActivityList = findViewById(R.id.btButtonToActivityList);
        ivPhoto = findViewById(R.id.imageView);
        tvWeather = findViewById(R.id.tvWeather);
        btButtonBackToLogin = findViewById(R.id.btButtonBackToLogin);

        // Set back button listener
        btButtonBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current screen
            }
        });

        // Register ActivityResultLauncher for camera intent
        arSmall = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.getExtras() != null) {
                                currentBitmap = (Bitmap) data.getExtras().get("data");
                                ivPhoto.setImageBitmap(currentBitmap); // Display photo
                            }
                        }
                    }
                });

        // Camera button click listener
        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCameraPermission()) {
                    // If permission granted, open camera
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    arSmall.launch(intent);
                } else {
                    // Request camera permission if not granted
                    requestCameraPermission();
                }
            }
        });

        // Save picture button click listener
        btSavePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentBitmap != null) {
                    saveImageToGallery(currentBitmap);
                } else {
                    Toast.makeText(SuggestionScreen.this, "No image to save", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Activity list button click listener
        btActivityList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuggestionScreen.this, ActivitiesRecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        // Fetch weather data from API
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<WeatherResponse> call = apiService.getWeatherData("Tel Aviv", "metric", API_KEY);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weather = response.body();
                    double temp = weather.getMain().getTemp();
                    String result = "Temp: " + temp + "°C";
                    tvWeather.setText(result);
                } else {
                    tvWeather.setText("Error retrieving data from server. Code:  " + response.code());
                    Log.e(TAG, "API Response Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                tvWeather.setText("API Response Error: " + t.getMessage());
                Log.e(TAG, "API Call Failure", t);
            }
        });
    }

    /**
     * Checks if the app has permission to access the camera.
     *
     * @return True if the app has permission, false otherwise.
     */
    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Requests permission to access the camera if it is not already granted.
     */
    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Toast.makeText(this, "Camera permission is required to take pictures", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }

    /**
     * Handles the result of the camera permission request.
     *
     * @param requestCode The request code passed in requestPermissions().
     * @param permissions The requested permissions.
     * @param grantResults The grant results for the corresponding permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
                // Open camera if permission is granted
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                arSmall.launch(intent);
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Saves the captured image to the device's gallery.
     *
     * @param bitmap The image to be saved.
     */
    private void saveImageToGallery(Bitmap bitmap) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "captured_image_" + System.currentTimeMillis() + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp");

        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (uri != null) {
            try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                Toast.makeText(this, "Image saved to gallery!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to save image.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
