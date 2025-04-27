package com.example.onlinecourseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI elements
        final EditText nameField = (EditText) findViewById(R.id.editText1);
        final EditText emailField = (EditText) findViewById(R.id.editText2);
        final EditText passwordField = (EditText) findViewById(R.id.editText3);
        final EditText confirmPasswordField = (EditText) findViewById(R.id.editText4);

        final TextView nameError = (TextView) findViewById(R.id.nameError);
        final TextView emailError = (TextView) findViewById(R.id.emailError);
        final TextView passwordError = (TextView) findViewById(R.id.passwordError);
        final TextView confirmPasswordError = (TextView) findViewById(R.id.confirmPasswordError);

        Button signUpButton = (Button) findViewById(R.id.button1);
        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Sign-up button click listener
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameField.getText().toString().trim();
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();
                String confirmPassword = confirmPasswordField.getText().toString().trim();
                boolean isValid = true;

                // Clear previous errors
                nameError.setText("");
                emailError.setText("");
                passwordError.setText("");
                confirmPasswordError.setText("");

                // Name validation
                if (name.isEmpty()) {
                    nameError.setText("Name is required");
                    isValid = false;
                }

                // Email validation
                if (email.isEmpty()) {
                    emailError.setText("Email is required");
                    isValid = false;
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailError.setText("Enter a valid email address");
                    isValid = false;
                }

                // Password validation
                if (password.isEmpty()) {
                    passwordError.setText("Password is required");
                    isValid = false;
                } else if (password.length() < 6) {
                    passwordError.setText("Password must be at least 6 characters");
                    isValid = false;
                }

                // Confirm password validation
                if (confirmPassword.isEmpty()) {
                    confirmPasswordError.setText("Confirm password is required");
                    isValid = false;
                } else if (!confirmPassword.equals(password)) {
                    confirmPasswordError.setText("Passwords do not match");
                    isValid = false;
                }

                // If input is valid, navigate to LoginActivity
                if (isValid) {
                    DatabaseHelper dbHelper = new DatabaseHelper(RegisterActivity.this);
                    boolean inserted = dbHelper.insertUser(name, email, password);
                    if (inserted) {
                        dbHelper.logAllUsers(); // This will log all users into Logcat
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
}
