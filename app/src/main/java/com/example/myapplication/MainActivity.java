package com.example.myapplication;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // Declaring variables
    TextView tvTitle;         // Title TextView
    ImageView backgroundImageView;       // Background ImageView
    Button btButtonToLogin;   // Button for login navigation

    Context context;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        tvTitle = findViewById(R.id.tvTitle); // Make sure 'tvTitle' exists in XML
        backgroundImageView = findViewById(R.id.backgroundImageView); // Make sure 'backgroundImageView' exists in XML
        btButtonToLogin = findViewById(R.id.btButtonToLogin); // Make sure 'btButtonToLogin' exists in XML
        context = this;

        // Action for the login button
        btButtonToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        // Show dialog to ask user's permission for notifications
        showNotificationPermissionDialog();
    }

    // Function to show dialog for notification permission
    private void showNotificationPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Allow Notifications");
        builder.setMessage("Would you like to allow daily notifications from this app?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // If user agrees, schedule the daily alarm
                scheduleDailyAlarm();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // If user declines, show a message or do nothing
                dialog.dismiss();
            }
        });
        builder.setCancelable(false); // Prevent dialog dismissal on outside touch
        builder.show();
    }

    // Schedule a daily alarm at 8 AM
    private void scheduleDailyAlarm() {
        createNotificationChannel();
        message = "Good morning! What are we doing today?";

        // Create intent to send to the BroadcastReceiver
        Intent notificationIntent = new Intent(context, ScheduleBroadCastReceiver.class);
        notificationIntent.putExtra("message", message);

        // Use a unique request code for the alarm
        int requestCode = (int) System.currentTimeMillis();

        // Create a unique PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                requestCode,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE
        );

        // Get the AlarmManager system service
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            // Set the alarm to repeat daily at 8 AM
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8); // Set hour to 8 AM
            calendar.set(Calendar.MINUTE, 0); // Set minute to 0
            calendar.set(Calendar.SECOND, 0); // Set second to 0

            // If the time has already passed for today, schedule for tomorrow
            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                calendar.add(Calendar.DATE, 1); // Schedule for the next day
            }

            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, // Repeat every 24 hours
                    pendingIntent
            );
        }
    }

    // Create a notification channel for alarms
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NotificationChannel";
            String description = "Channel for daily notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notifyChannel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
