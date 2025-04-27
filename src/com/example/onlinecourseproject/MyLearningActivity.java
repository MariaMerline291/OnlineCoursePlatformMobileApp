package com.example.onlinecourseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MyLearningActivity extends Activity {
	
	private String username;


    ListView myCoursesListView;
    static ArrayList<String> purchasedCourses = new ArrayList<String>();
    static HashMap<String, Integer> courseProgress = new HashMap<String, Integer>();
    MyCoursesAdapter adapter;  // Use custom adapter instead of ArrayAdapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_learning);
        username = getIntent().getStringExtra("USERNAME"); // received here

        myCoursesListView = (ListView) findViewById(R.id.my_courses_list);

        // Retrieve purchased courses from Intent
        Intent intent = getIntent();
        if (intent.hasExtra("MY_COURSES")) {
            ArrayList<String> newCourses = intent.getStringArrayListExtra("MY_COURSES");
            if (newCourses != null) {
                addPurchasedCourses(newCourses);
            }
        }

        // Use MyCoursesAdapter instead of simple ArrayAdapter
        adapter = new MyCoursesAdapter(this, purchasedCourses, courseProgress);
        myCoursesListView.setAdapter(adapter);
        
        findViewById(R.id.profile_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to Profile Activity
                startActivity(new Intent(MyLearningActivity.this, ProfileActivity.class));
            }
        });
        
        
        findViewById(R.id.focus_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to Focus Activity
                startActivity(new Intent(MyLearningActivity.this, FocusChallengeActivity.class));
            }
        });
        
        findViewById(R.id.home_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to Home Activity
                startActivity(new Intent(MyLearningActivity.this, HomeActivity.class));
            }
        });

        findViewById(R.id.favorite_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to Favorites Activity
                startActivity(new Intent(MyLearningActivity.this, CategoryActivity.class));
            }
        });

        findViewById(R.id.learning_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // You're already here — maybe toast
                Toast.makeText(MyLearningActivity.this, "You're on Learning!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String getUsername() {
        return username;
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged(); // Refresh progress when returning from CourseContentActivity
    }

    // Store purchased courses
    public static void addPurchasedCourses(ArrayList<String> courses) {
        for (String course : courses) {
            if (!purchasedCourses.contains(course)) { // Avoid duplicates
                purchasedCourses.add(course);
                if (!courseProgress.containsKey(course)) {
                    courseProgress.put(course, 0); // Initialize progress to 0%
                }
            }
        }
    }
}
