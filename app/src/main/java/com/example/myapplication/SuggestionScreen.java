package com.example.myapplication;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class SuggestionScreen extends AppCompatActivity {

    ImageView ivPhoto;
    Button btCamera, btSavePicture, btActivityList; // הוספת כפתור למעבר לרשימה
    ActivityResultLauncher<Intent> arSmall;
    Bitmap currentBitmap; // משתנה לשמירת התמונה שצולמה

    private static final int CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_screen);

        // חיבור ל-Views
        btCamera = findViewById(R.id.btButtonToTakeAPicture);
        btSavePicture = findViewById(R.id.btButtonSavePicture);
        btActivityList = findViewById(R.id.btButtonToActivityList); // כפתור למעבר לרשימה
        ivPhoto = findViewById(R.id.imageView);

        // רישום ה-ActivityResultLauncher
        arSmall = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null && data.getExtras() != null) {
                                currentBitmap = (Bitmap) data.getExtras().get("data");
                                ivPhoto.setImageBitmap(currentBitmap); // הצגת התמונה ב-ImageView
                            }
                        }
                    }
                });

        // פעולה בלחיצה על כפתור המצלמה
        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkCameraPermission()) {
                    // אם יש הרשאה, פתח את המצלמה
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    arSmall.launch(intent);
                } else {
                    // בקשת הרשאה
                    requestCameraPermission();
                }
            }
        });

        // פעולה בלחיצה על כפתור שמירת תמונה
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

        // פעולה בלחיצה על כפתור למעבר לרשימת הפעילויות
        btActivityList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuggestionScreen.this, ActivitiesRecyclerViewActivity.class);
                startActivity(intent);
            }
        });
    }

    // פונקציה לבדוק אם יש הרשאת מצלמה
    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    // בקשת הרשאת מצלמה
    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            // הצגת דיאלוג עם הסבר לפני בקשת הרשאה
            Toast.makeText(this, "Camera permission is required to take pictures", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }

    // טיפול בתוצאות בקשת הרשאה
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
                // פתח מצלמה אם ההרשאה התקבלה
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                arSmall.launch(intent);
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // פונקציה לשמירת תמונה בגלריה
    private void saveImageToGallery(Bitmap bitmap) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "captured_image_" + System.currentTimeMillis() + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp"); // נתיב שמירה מותאם אישית

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
