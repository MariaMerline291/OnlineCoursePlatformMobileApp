package com.example.onlinecourseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreen extends Activity {

    private static final int SPLASH_TIME_OUT = 2500; // Increased for better UX

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize views
        ImageView logo = (ImageView) findViewById(R.id.logo);
        TextView welcomeText = (TextView) findViewById(R.id.welcomeText);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Fade-in animation for logo and text
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(1000);
        logo.startAnimation(fadeIn);
        
     // Fade-in for welcome text (slightly delayed for nice effect)
        Animation fadeInText = new AlphaAnimation(0, 1);
        fadeInText.setStartOffset(500); // Start after logo begins
        fadeInText.setDuration(1500);
        welcomeText.startAnimation(fadeInText); // Correct animation for text

        // Delayed transition to MainActivity
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
