package com.example.myapplication;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class RegisterFragment extends Fragment {

    EditText etUserName;
    EditText etEmailAddress;
    EditText etPassword;
    EditText etReTypePassword;
    EditText etUserPhoneNumber;
    Button Register;
    Button btToLogin;

    HelperDB helperDB;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        helperDB = new HelperDB(getContext());

        etUserName = view.findViewById(R.id.etUserName);
        etEmailAddress = view.findViewById(R.id.etEmailAddress);
        etPassword = view.findViewById(R.id.etPassword);
        etReTypePassword = view.findViewById(R.id.etReTypePassword);
        etUserPhoneNumber = view.findViewById(R.id.etUserPhoneNumber);
        Register = view.findViewById(R.id.Register);
        btToLogin = view.findViewById(R.id.btToLogin);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUserName.getText().toString().trim();
                String userEmail = etEmailAddress.getText().toString().trim();
                String userPwd = etPassword.getText().toString().trim();
                String reTypePwd = etReTypePassword.getText().toString().trim();
                String userPhone = etUserPhoneNumber.getText().toString().trim();

                // Check for empty fields
                if (username.isEmpty() || userEmail.isEmpty() || userPwd.isEmpty() || reTypePwd.isEmpty() || userPhone.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check for valid email
                if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    Toast.makeText(getContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if passwords match
                if (!userPwd.equals(reTypePwd)) {
                    Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save user
                helperDB.insertUser(username, userEmail, userPwd, userPhone);
                helperDB.fetchData();

                // Navigate to login
                LoginFragment loginFragment = new LoginFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, loginFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment loginFragment = new LoginFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, loginFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
