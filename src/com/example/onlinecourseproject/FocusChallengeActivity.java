package com.example.onlinecourseproject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class FocusChallengeActivity extends Activity {

    Spinner timeSpinner;
    Button startButton;
    TextView timerDisplay, quoteText;
    RelativeLayout backgroundLayout;

    CountDownTimer countDownTimer;
    boolean isBlinking = false;

    String[] durations = {
            "Select Duration",
            "15 Seconds", "30 Seconds",
            "5 Minutes", "10 Minutes", "15 Minutes", "30 Minutes"
    };

    long selectedMillis = 0;

    String[] motivationalQuotes = {
            "Stay focused. Stay determined.",
            "Every second counts!",
            "Push through distractions.",
            "You’ve got this!",
            "Focus now, shine later.",
            "Make it happen!"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_challenge);

        timeSpinner = (Spinner) findViewById(R.id.time_spinner);
        startButton = (Button) findViewById(R.id.start_button);
        timerDisplay = (TextView) findViewById(R.id.timer_display);
        quoteText = (TextView) findViewById(R.id.quote_text);
        backgroundLayout = (RelativeLayout) findViewById(R.id.focus_layout);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, durations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(adapter);

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1: selectedMillis = 15 * 1000; break;
                    case 2: selectedMillis = 30 * 1000; break;
                    case 3: selectedMillis = 5 * 60 * 1000; break;
                    case 4: selectedMillis = 10 * 60 * 1000; break;
                    case 5: selectedMillis = 15 * 60 * 1000; break;
                    case 6: selectedMillis = 30 * 60 * 1000; break;
                    default: selectedMillis = 0; break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedMillis == 0) {
                    timerDisplay.setText("Please select a valid duration.");
                    return;
                }

                final Random rand = new Random();
                quoteText.setText(motivationalQuotes[rand.nextInt(motivationalQuotes.length)]);

                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }

                countDownTimer = new CountDownTimer(selectedMillis, 1000) {
                    public void onTick(long millisUntilFinished) {
                        int seconds = (int) (millisUntilFinished / 1000);
                        int minutes = seconds / 60;
                        int displaySeconds = seconds % 60;

                        timerDisplay.setText(String.format("%02d:%02d", minutes, displaySeconds));

                        if (seconds <= 5) {
                            blinkBackground();
                        }
                    }

                    public void onFinish() {
                        timerDisplay.setText("Congrats!");
                        quoteText.setText("You stayed focused ");
                        backgroundLayout.setBackgroundColor(Color.parseColor("#A5D6A7")); // Light green
                    }
                }.start();
            }
        });
    }

    private void blinkBackground() {
        if (!isBlinking) {
            isBlinking = true;
            new CountDownTimer(5000, 500) {
                boolean colorFlag = false;

                public void onTick(long millisUntilFinished) {
                    if (colorFlag) {
                        backgroundLayout.setBackgroundColor(Color.WHITE);
                    } else {
                        backgroundLayout.setBackgroundColor(Color.RED);
                    }
                    colorFlag = !colorFlag;
                }

                public void onFinish() {
                    backgroundLayout.setBackgroundColor(Color.WHITE);
                    isBlinking = false;
                }
            }.start();
        }
    }
}
