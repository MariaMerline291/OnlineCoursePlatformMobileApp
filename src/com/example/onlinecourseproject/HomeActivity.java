package com.example.onlinecourseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;


public class HomeActivity extends Activity {

    EditText searchBar;
    ImageView cartIcon;
    ImageView[] courseImages;
    ImageView homeIcon, profileIcon, favoriteIcon, learningIcon; // Bottom Navigation Icons

    String[] courseTitles = {
            "Java Basics", "Python Mastery", "Web Dev", "AI & ML",
            "Cyber Security", "Android Dev", "Cloud Computing", "Blockchain", "Data Science"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        new android.app.AlertDialog.Builder(this)
        .setTitle(" Reminder")
        .setMessage("Don’t forget to complete your course today!")
        .setPositiveButton("Got it!", null)
        .show();

        
     // Get reference to your welcome TextView (make sure it has an ID like welcome_textview)
        TextView welcomeText = (TextView) findViewById(R.id.welcome_textview);

        // Get the username passed from LoginActivity
        final String username = getIntent().getStringExtra("USERNAME");

        if (username != null && !username.isEmpty()) {
            welcomeText.setText("Welcome, " + username + "!");
        }


        // Initialize UI elements (No re-declaration)
        final EditText    searchBar =(EditText) findViewById(R.id.search_bar);
        ImageView cartIcon =(ImageView) findViewById(R.id.cart_icon);

        // Initialize bottom navigation icons
        ImageView homeIcon = (ImageView)findViewById(R.id.home_icon);
        ImageView profileIcon =(ImageView) findViewById(R.id.profile_icon);
        ImageView favoriteIcon =(ImageView) findViewById(R.id.favorite_icon);
        ImageView learningIcon =(ImageView) findViewById(R.id.learning_icon);

        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || 
                    (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    
                    String query = searchBar.getText().toString().trim();
                    boolean courseFound = false;

                    for (String course : courseTitles) {
                        if (course.equalsIgnoreCase(query)) {  // Case-insensitive match
                            Intent intent = new Intent(HomeActivity.this, CourseDetailsActivity.class);
                            intent.putExtra("COURSE_NAME", course);
                            startActivity(intent);
                            courseFound = true;
                            break;
                        }
                    }

                    if (!courseFound) {
                        Toast.makeText(HomeActivity.this, "Course not found", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
                return false;
            }
        });

        
        // Click listener for cart
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));
            }
        });

        // Initialize course images array
        courseImages = new ImageView[]{
        		(ImageView) findViewById(R.id.course1), (ImageView)findViewById(R.id.course2),(ImageView) findViewById(R.id.course3),
        		(ImageView) findViewById(R.id.course4),(ImageView) findViewById(R.id.course5),(ImageView) findViewById(R.id.course6),
        		(ImageView)  findViewById(R.id.course7), (ImageView)findViewById(R.id.course8), (ImageView)findViewById(R.id.course9)
        };

        // Set click listeners for each course
        for (int i = 0; i < courseImages.length; i++) {
            final int index = i;
            courseImages[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, CourseDetailsActivity.class);
                    intent.putExtra("COURSE_NAME", courseTitles[index]);
                    startActivity(intent);
                }
            });
        }

        // Set bottom navigation click listeners
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Already on Home", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.focus_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to Focus Activity
                startActivity(new Intent(HomeActivity.this, FocusChallengeActivity.class));
            }
        });
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(HomeActivity.this, ProfileActivity.class);

                //  Get the extras received from LoginActivity
                String username = getIntent().getStringExtra("USERNAME");
                String email = getIntent().getStringExtra("EMAIL");

                //  Pass them to ProfileActivity
                profileIntent.putExtra("USERNAME", username);
                profileIntent.putExtra("EMAIL", email);

                startActivity(profileIntent);
            }
        });


        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CategoryActivity.class));
            }
        });

        learningIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MyLearningActivity.class);
                intent.putExtra("USERNAME", username);  // send username to MyLearningActivity
                startActivity(intent);
            }
        });

    }
}
