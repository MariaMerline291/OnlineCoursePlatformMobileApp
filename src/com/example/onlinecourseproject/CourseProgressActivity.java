package com.example.onlinecourseproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import java.util.HashMap;

public class CourseProgressActivity extends Activity {

    LinearLayout lecturesLayout;
    Button saveProgressButton;
    String courseName;
    static HashMap<String, Boolean[]> lectureStatus = new HashMap<String, Boolean[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_progress);

        lecturesLayout = (LinearLayout) findViewById(R.id.lectures_layout);
        saveProgressButton = (Button) findViewById(R.id.save_progress_button);

        courseName = getIntent().getStringExtra("COURSE_NAME");
        final Boolean[] completedLectures;
        if (lectureStatus.containsKey(courseName)) {
            completedLectures = lectureStatus.get(courseName);
        } else {
            completedLectures = new Boolean[10]; // Default: all false
            for (int i = 0; i < 10; i++) {
                completedLectures[i] = false;
            }
        }

        for (int i = 0; i < 10; i++) {
            final CheckBox checkBox = new CheckBox(this);
            checkBox.setText("Lecture " + (i + 1));
            checkBox.setChecked(completedLectures[i] != null && completedLectures[i]);
            final int finalI = i;
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    completedLectures[finalI] = checkBox.isChecked();
                }
            });

        }

        saveProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lectureStatus.put(courseName, completedLectures);
                updateProgress();
                finish();
            }
        });

    }

    private void updateProgress() {
        Boolean[] completedLectures = lectureStatus.get(courseName);
        int completedCount = 0;
        for (boolean isChecked : completedLectures) {
            if (isChecked) completedCount++;
        }
        int progress = (completedCount * 100) / 10;
        MyLearningActivity.courseProgress.put(courseName, progress);
    }
}
