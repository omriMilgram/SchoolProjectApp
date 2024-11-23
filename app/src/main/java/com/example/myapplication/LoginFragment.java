package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LoginFragment extends Fragment {

    // UI elements for the login screen
    EditText etEmailAddress;  // EditText for the user's email address
    EditText etPassword;      // EditText for the user's password
    Button button;            // Button to submit login
    Button btToRegister;      // Button to navigate to the registration screen
    Button btBackToMain;      // Button to go back to the main screen
    HelperDB helperDB;        // Database helper to interact with the database

    public LoginFragment() {
        // Required empty constructor for the fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize the HelperDB object to interact with the database
        helperDB = new HelperDB(getContext());

        // Find the UI components in the fragment layout
        etEmailAddress = view.findViewById(R.id.etEmailAddress);  // Link to the email input field
        etPassword = view.findViewById(R.id.etPassword);          // Link to the password input field
        button = view.findViewById(R.id.button);                  // Link to the login button
        btToRegister = view.findViewById(R.id.btToRegister);      // Link to the register button
        btBackToMain = view.findViewById(R.id.btBackToMain);      // Link to the back to main button

        // Register button click listener - navigate to RegisterFragment
        btToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace current fragment with RegisterFragment
                RegisterFragment registerFragment = new RegisterFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, registerFragment);
                transaction.addToBackStack(null); // Allows user to navigate back
                transaction.commit();
            }
        });

        // Login button click listener - verify user credentials
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailLogin = etEmailAddress.getText().toString();  // Get entered email
                String passwordLogin = etPassword.getText().toString();  // Get entered password

                // Fetch user details from the database based on email
                UserDetails user = helperDB.getUserByEmail(emailLogin);

                // Check if user exists and password matches
                if (user != null && user.getUserPwd().equals(passwordLogin)) {
                    // Successful login
                    Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                    // Add additional code to start another Fragment or Activity if needed
                } else {
                    // Invalid credentials
                    Toast.makeText(getActivity(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Back to main button click listener - close current activity and return to MainActivity
        btBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();  // Close the current activity and return to the main screen
            }
        });

        return view;  // Return the inflated view for this fragment
    }
}
