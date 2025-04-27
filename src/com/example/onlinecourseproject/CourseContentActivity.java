package com.example.onlinecourseproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;
import java.util.HashSet;
import java.util.Set;

public class CourseContentActivity extends Activity {
    private String courseName;
    private LinearLayout lectureList;
    private Button saveProgressButton;
    private Set<String> completedLectures;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);

        courseName = getIntent().getStringExtra("COURSE_NAME");
        lectureList = (LinearLayout) findViewById(R.id.lecture_list);
        saveProgressButton = (Button) findViewById(R.id.save_progress_button);

        prefs = getSharedPreferences("CourseProgress", MODE_PRIVATE);
        completedLectures = prefs.getStringSet(courseName, new HashSet<String>());

        // Add 10 lectures with checkboxes and video players
        for (int i = 1; i <= 10; i++) {
            LinearLayout lectureItem = new LinearLayout(this);
            lectureItem.setOrientation(LinearLayout.VERTICAL);

            CheckBox checkBox = new CheckBox(this);
            checkBox.setText("Lecture " + i);
            checkBox.setChecked(completedLectures.contains("Lecture " + i));
            lectureItem.addView(checkBox);

         // ImageView for each lecture instead of VideoView
            final ImageView lectureImage = new ImageView(this);
            lectureImage.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 400)); // You can adjust height
            lectureImage.setImageResource(R.drawable.code); // replace 'sample_image' with your image name
            lectureImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            lectureImage.setVisibility(View.GONE);

            // Show/hide image on checkbox click
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lectureImage.setVisibility(lectureImage.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                }
            });

            lectureItem.addView(lectureImage);
            
         // Notes Button
         // Notes Button
            Button notesButton = new Button(this);
            notesButton.setText("Add Notes");

            // Layout parameters with margins
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 30, 0, 0); // top margin for spacing
            notesButton.setLayoutParams(params);

            // Styling
            notesButton.setAllCaps(false); // Keep text normal
            notesButton.setTextColor(Color.WHITE); // Text color
            notesButton.setTextSize(16); // Text size
            notesButton.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD); // Font

            // Background color (teal-ish green)
            notesButton.setBackgroundColor(Color.parseColor("#388E3C")); // Deep green shade

            // Padding (in pixels, so convert dp to px)
            int padding = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics());
            notesButton.setPadding(padding, padding, padding, padding);

            
            

            // Pass unique ID: courseName + Lecture number
            final int lectureNumber = i;
            notesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CourseContentActivity.this, NotesActivity.class);
                    intent.putExtra("COURSE_NAME", courseName + "_Lecture" + lectureNumber);  // makes notes unique per lecture
                    startActivity(intent);
                }
            });

            lectureItem.addView(notesButton);


            lectureList.addView(lectureItem);
        }

        saveProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProgress();
            }
        });
  }

    private void saveProgress() {
        int checkedCount = 0;
        completedLectures.clear();

        for (int i = 0; i < lectureList.getChildCount(); i++) {
            LinearLayout lectureItem = (LinearLayout) lectureList.getChildAt(i);
            CheckBox checkBox = (CheckBox) lectureItem.getChildAt(0);
            if (checkBox.isChecked()) {
                completedLectures.add(checkBox.getText().toString());
                checkedCount++;
            }
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(courseName, completedLectures);
        editor.apply();

        int progress = (checkedCount * 100) / 10;
        MyLearningActivity.courseProgress.put(courseName, progress);

        Toast.makeText(this, "Progress Saved!", Toast.LENGTH_SHORT).show();
    }
}
