package com.example.onlinecourseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CourseDetailsActivity extends Activity {

     ImageView courseImage;
     TextView courseTitle, courseDescription;
     Button enrollButton;
     String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        ImageView courseImage =(ImageView) findViewById(R.id.course_image);
        TextView courseTitle = (TextView)findViewById(R.id.course_title);
        TextView courseDescription =(TextView) findViewById(R.id.course_description);
        Button enrollButton =(Button) findViewById(R.id.enroll_button);

        // Get the course name from the intent
        courseName = getIntent().getStringExtra("COURSE_NAME");

        // Set course details dynamically
        courseTitle.setText(courseName);
        courseDescription.setText("Learn " + courseName + " with expert instructors and hands-on projects.");

        enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity.addToCart(courseName, R.drawable.code); // Change as per your image
                Toast.makeText(CourseDetailsActivity.this, "Added to Cart: " + courseName, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CourseDetailsActivity.this, CartActivity.class));
            }
        });


    }
}
