package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvTitle;
    ImageView ivMonkey;
    Button btButtonToLogin;
    TextView tvCountdownMain;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        ivMonkey = findViewById(R.id.ivMonkey);
        btButtonToLogin = findViewById(R.id.btButtonToLogin);
        tvCountdownMain = findViewById(R.id.tvCountdownMain);



        btButtonToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                startActivity(intent);
            }
        });

        CountDownTimer countDownTimer = new CountDownTimer(7000 + 100, 1000) {
            @Override
            public void onTick(long l) {
                Log.i("TAG", "onTick: Seconds until finished" + l/1000);
                String stCountdown = l/1000 + "";
                tvCountdownMain.setText("Start in: " + stCountdown + " seconds");
            }

            @Override
            public void onFinish() {
                tvCountdownMain.setText("done!");
                btButtonToLogin.callOnClick();
            }
        }.start();
    }


}