package com.example.onlinecourseproject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProfileActivity extends Activity {
	

    boolean isDarkMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final TextView usernameText = (TextView) findViewById(R.id.profile_username);
        final TextView emailText = (TextView) findViewById(R.id.profile_email);
        final TextView titleText = (TextView) findViewById(R.id.profile_title);
        final Button darkModeBtn = (Button) findViewById(R.id.dark_mode_toggle);
        final RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.profile_root);
        final LinearLayout card = (LinearLayout) findViewById(R.id.profile_card);

        // Get the data passed from HomeActivity
        String username = getIntent().getStringExtra("USERNAME");
        String email = getIntent().getStringExtra("EMAIL");

        // Set the values to TextViews
        usernameText.setText(" Username: " + username);
        emailText.setText(" Email: " + email);

        // Toggle Dark Mode
        darkModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDarkMode) {
                    rootLayout.setBackgroundColor(Color.parseColor("#121212")); // Dark background
                    card.setBackgroundColor(Color.parseColor("#1E1E1E"));
                    usernameText.setTextColor(Color.WHITE);
                    emailText.setTextColor(Color.WHITE);
                    titleText.setTextColor(Color.parseColor("#80D8FF")); // Accent
                    darkModeBtn.setText("Light Mode");
                    darkModeBtn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    darkModeBtn.setTextColor(Color.parseColor("#000000"));
                    isDarkMode = true;
                } else {
                    rootLayout.setBackgroundColor(Color.parseColor("#F5F5F5")); // Light background
                    card.setBackgroundColor(Color.WHITE);
                    usernameText.setTextColor(Color.BLACK);
                    emailText.setTextColor(Color.BLACK);
                    titleText.setTextColor(Color.parseColor("#3F51B5"));
                    darkModeBtn.setText("Dark Mode");
                    darkModeBtn.setBackgroundColor(Color.parseColor("#212121"));
                    darkModeBtn.setTextColor(Color.WHITE);
                    isDarkMode = false;
                }
            }
        });
    }
}
