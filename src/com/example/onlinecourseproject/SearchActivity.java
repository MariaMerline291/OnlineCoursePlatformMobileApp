package com.example.onlinecourseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends Activity {

	EditText searchInput;
    LinearLayout searchResultsLayout;
    
    // Dummy list of courses (replace with real data)
    private List<String> courseTitles = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize UI elements (Don't re-declare them)
        EditText searchInput = (EditText)findViewById(R.id.searchInput);
        LinearLayout searchResultsLayout = (LinearLayout)findViewById(R.id.searchResultsLayout);

        // Add some course data (Replace with real course data)
        courseTitles.add("Java Basics");
        courseTitles.add("Python Mastery");
        courseTitles.add("Web Dev");
        courseTitles.add("AI & ML");
        courseTitles.add("Cyber Security");
        courseTitles.add("Android Dev");
        courseTitles.add("Cloud Computing");
        courseTitles.add("Blockchain");
        courseTitles.add("Data Science");
        

     
        // Search bar text change listener
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCourses(s.toString()); // Call filter method
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }

    // Helper method to filter and display courses
    private void filterCourses(String query) {
        searchResultsLayout.removeAllViews(); // Clear previous results

        for (final String title : courseTitles) { // 'final' prevents error inside inner class
            if (title.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                TextView courseView = new TextView(this);
                courseView.setText(title);
                courseView.setTextSize(18);
                courseView.setPadding(16, 16, 16, 16);
                courseView.setClickable(true);

                // Use an anonymous inner class instead of lambda
                courseView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this, CourseDetailsActivity.class);
                        intent.putExtra("COURSE_NAME", title); // Send course name
                        startActivity(intent);
                    }
                });

                searchResultsLayout.addView(courseView);
            }
        }

        // Show "No Results" message if no course is found
        if (searchResultsLayout.getChildCount() == 0) {
            TextView noResultsView = new TextView(this);
            noResultsView.setText("No results found.");
            noResultsView.setTextSize(16);
            noResultsView.setPadding(16, 16, 16, 16);
            searchResultsLayout.addView(noResultsView);
        }
    }
}
