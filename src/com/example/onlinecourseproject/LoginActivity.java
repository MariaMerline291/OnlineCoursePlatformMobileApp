package com.example.onlinecourseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        final EditText emailField = (EditText) findViewById(R.id.editText2);
        final EditText passwordField = (EditText) findViewById(R.id.editText1);
        final TextView emailError = (TextView) findViewById(R.id.emailError);
        final TextView passwordError = (TextView) findViewById(R.id.passwordError);
        Button loginButton = (Button) findViewById(R.id.button1);
        Button registerButton = (Button) findViewById(R.id.button2);

        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        
        // Login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();
                boolean isValid = true;

                // Clear previous error messages
                emailError.setText("");
                passwordError.setText("");

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

                // If input is valid, navigate to HomeActivity
                if (isValid && dbHelper.checkUser(email, password)) {
                    dbHelper.logAllUsers(); // Log current users (optional)

                    //  Get the username from database
                    String username = dbHelper.getUsernameByEmail(email);

                    //  Pass the username and email to HomeActivity
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("USERNAME", username); // Send username
                    intent.putExtra("EMAIL", email);       // Send email too
                    startActivity(intent);
                    finish();
                }

else {
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }


            }
        });

        // Sign-up button click listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
