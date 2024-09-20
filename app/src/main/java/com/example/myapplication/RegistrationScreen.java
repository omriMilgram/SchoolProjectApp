package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegistrationScreen extends AppCompatActivity {

    EditText etUserName;
    EditText etEmailAddress;
    EditText etPassword;
    EditText etReTypePassword;
    EditText etUserPhoneNumber;
    Button button2;
    Button btToLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_screen);

        etUserName = findViewById(R.id.etUserName);
        etEmailAddress = findViewById(R.id.etEmailAddress);
        etPassword = findViewById(R.id.etPassword);
        etReTypePassword = findViewById(R.id.etReTypePassword);
        etUserPhoneNumber = findViewById(R.id.etUserPhoneNumber);
        button2 = findViewById(R.id.button2);
        btToLogin = findViewById(R.id.btToLogin);

        UserDetails one = new UserDetails("omri", "0987654", "nmdhg@fdgdg", "0987654");

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUserName.getText().toString();
                String userPwd = etPassword.getText().toString();
                String userEmail = etEmailAddress.getText().toString();
                String userPhone = etUserPhoneNumber.getText().toString();

                one.setUserEmail(userEmail);
                one.setUserName(username);
                one.setUserPwd(userPwd);
                one.setUserPhone(userPhone);

                Intent intent = new Intent(RegistrationScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });

        btToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });




    }
}