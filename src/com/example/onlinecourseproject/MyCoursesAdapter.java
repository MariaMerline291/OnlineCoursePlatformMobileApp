package com.example.onlinecourseproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MyCoursesAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> courses;
    private HashMap<String, Integer> courseProgress;

    public MyCoursesAdapter(Context context, ArrayList<String> courses, HashMap<String, Integer> courseProgress) {
        this.context = context;
        this.courses = courses;
        this.courseProgress = courseProgress;
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int position) {
        return courses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.course_item, null);
        }

        TextView courseTitle = (TextView) convertView.findViewById(R.id.course_title);
        TextView progressText = (TextView) convertView.findViewById(R.id.course_progress);
        Button startCourseButton = (Button) convertView.findViewById(R.id.start_course_button);
        
        Button rateCourseButton = (Button) convertView.findViewById(R.id.rate_course_button); // add this line
        Button viewCertificateButton = (Button) convertView.findViewById(R.id.view_certificate_button);

        final String courseName = courses.get(position);
        int progress = courseProgress.containsKey(courseName) ? courseProgress.get(courseName) : 0;

        courseTitle.setText(courseName);
        progressText.setText("Progress: " + progress + "%");

        startCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseContentActivity.class);
                intent.putExtra("COURSE_NAME", courseName);
                context.startActivity(intent);
            }
        });

        if (progress == 100) {
            viewCertificateButton.setVisibility(View.VISIBLE);
            rateCourseButton.setVisibility(View.VISIBLE);
            viewCertificateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CertificateActivity.class);
                    intent.putExtra("COURSE_NAME", courseName);

                    // Get username from MyLearningActivity
                    if (context instanceof MyLearningActivity) {
                        String username = ((MyLearningActivity) context).getUsername();
                        intent.putExtra("USERNAME", username);
                    }

                    context.startActivity(intent);
                }
            });
            // Rate Course button click
            rateCourseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRatingDialog(courseName); // custom method
                }
            });
        } else {
            viewCertificateButton.setVisibility(View.GONE);
            rateCourseButton.setVisibility(View.GONE);
        }


        return convertView;
    }
    
    private void showRatingDialog(final String courseName) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_rate_course, null);

        final RatingBar ratingBar = (RatingBar) dialogView.findViewById(R.id.rating_bar);
        final EditText feedbackEditText = (EditText) dialogView.findViewById(R.id.feedback_edittext);
        Button submitButton = (Button) dialogView.findViewById(R.id.submit_rating_button);

        final android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(context)
                .setTitle("Rate Course: " + courseName)
                .setView(dialogView)
                .create();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                String feedback = feedbackEditText.getText().toString();

                // Save rating and feedback using SharedPreferences
                SharedPreferences prefs = context.getSharedPreferences("CourseRatings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putFloat(courseName + "_rating", rating);
                editor.putString(courseName + "_feedback", feedback);
                editor.apply();

                Toast.makeText(context, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
