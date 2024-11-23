package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {

    EditText etEmailAddress;
    EditText etPassword;
    Button button;
    Button btToRegister;
    Button btBackToMain;
    HelperDB helperDB = new HelperDB(this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_SHORT).show();

        etEmailAddress = findViewById(R.id.etEmailAddress);
        etPassword = findViewById(R.id.etPassword);
        button = findViewById(R.id.button);
        btToRegister = findViewById(R.id.btToRegister);
        btBackToMain = findViewById(R.id.btBackToMain);

        btToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, RegistrationScreen.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailLogin = etEmailAddress.getText().toString();
                String passwordLogin = etPassword.getText().toString();

                // Retrieve user data from the database based on email
                UserDetails user = helperDB.getUserByEmail(emailLogin);

                if (user != null && user.getUserPwd().equals(passwordLogin)) {
                    // Login successful
                    Toast.makeText(LoginScreen.this, "Login successful", Toast.LENGTH_SHORT).show();
                    // Start the next activity or perform other actions
                } else {
                    // Login failed
                    Toast.makeText(LoginScreen.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}