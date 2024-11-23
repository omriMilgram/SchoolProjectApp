package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class RegisterFragment extends Fragment {

    // UI elements for the registration screen
    EditText etUserName;          // EditText for the user's name
    EditText etEmailAddress;      // EditText for the user's email address
    EditText etPassword;          // EditText for the user's password
    EditText etReTypePassword;    // EditText for re-typing the password
    EditText etUserPhoneNumber;   // EditText for the user's phone number
    Button button2;               // Button to submit registration
    Button btToLogin;             // Button to navigate to the login screen

    HelperDB helperDB;            // Database helper for interacting with the database

    public RegisterFragment() {
        // Required empty constructor for the fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Initialize the HelperDB object for database operations
        helperDB = new HelperDB(getContext());

        // Initialize the UI components (Views)
        etUserName = view.findViewById(R.id.etUserName);                // User's name input field
        etEmailAddress = view.findViewById(R.id.etEmailAddress);        // User's email input field
        etPassword = view.findViewById(R.id.etPassword);                // User's password input field
        etReTypePassword = view.findViewById(R.id.etReTypePassword);    // Re-typing password input field
        etUserPhoneNumber = view.findViewById(R.id.etUserPhoneNumber);  // User's phone number input field
        button2 = view.findViewById(R.id.button2);                      // Register button
        btToLogin = view.findViewById(R.id.btToLogin);                  // Button to navigate to login screen

        // Register button click listener - save user to the database and navigate to login
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUserName.getText().toString();          // Get entered username
                String userPwd = etPassword.getText().toString();           // Get entered password
                String userEmail = etEmailAddress.getText().toString();     // Get entered email address
                String userPhone = etUserPhoneNumber.getText().toString();  // Get entered phone number

                // Save the user information to the database
                helperDB.insertUser(username, userEmail, userPwd, userPhone);

                helperDB.fetchData();  // Display data in the log or update the UI

                // After registration, navigate to the LoginFragment
                LoginFragment loginFragment = new LoginFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, loginFragment);
                transaction.addToBackStack(null); // Allows going back
                transaction.commit();
            }
        });

        // Back to login button click listener - navigate to the LoginFragment
        btToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the LoginFragment
                LoginFragment loginFragment = new LoginFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, loginFragment);
                transaction.addToBackStack(null); // Allows going back
                transaction.commit();
            }
        });

        return view; // Return the inflated view for this fragment
    }
}
