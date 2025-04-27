package com.example.onlinecourseproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CategoryActivity extends Activity {

    GridLayout courseGrid;
    ImageView cartIcon;
    Button btnAll, btnProgramming, btnSecurity, btnData, btnMobile;

    String[] allCourses = {
            "Java Basics", "Python Mastery", "Web Dev", "AI & ML",
            "Cyber Security", "Android Dev", "Cloud Computing", "Blockchain", "Data Science"
    };

    String[] courseCategories = {
            "Programming", "Programming", "Programming", "Data",
            "Security", "Mobile", "Data", "Data", "Data"
    };

    int[] courseImages = {
            R.id.cat_course1, R.id.cat_course2, R.id.cat_course3,
            R.id.cat_course4, R.id.cat_course5, R.id.cat_course6,
            R.id.cat_course7, R.id.cat_course8, R.id.cat_course9
    };

    ImageView[] courseImageViews;
    TextView[] courseNameViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Category Buttons
        btnAll = (Button) findViewById(R.id.btnAll);
        btnProgramming = (Button) findViewById(R.id.btnProgramming);
        btnSecurity = (Button) findViewById(R.id.btnSecurity);
        btnData = (Button) findViewById(R.id.btnData);
        btnMobile = (Button) findViewById(R.id.btnMobile);

        // Cart Icon
        cartIcon = (ImageView) findViewById(R.id.cart_icon);
        courseGrid = (GridLayout) findViewById(R.id.course_grid);

        courseImageViews = new ImageView[]{
                (ImageView) findViewById(R.id.cat_course1), (ImageView) findViewById(R.id.cat_course2), (ImageView) findViewById(R.id.cat_course3),
                (ImageView) findViewById(R.id.cat_course4), (ImageView) findViewById(R.id.cat_course5), (ImageView) findViewById(R.id.cat_course6),
                (ImageView) findViewById(R.id.cat_course7), (ImageView) findViewById(R.id.cat_course8), (ImageView) findViewById(R.id.cat_course9)
        };
        
        courseNameViews = new TextView[]{
                (TextView) findViewById(R.id.course_name1),
                (TextView) findViewById(R.id.course_name2),
                (TextView) findViewById(R.id.course_name3),
                (TextView) findViewById(R.id.course_name4),
                (TextView) findViewById(R.id.course_name5),
                (TextView) findViewById(R.id.course_name6),
                (TextView) findViewById(R.id.course_name7),
                (TextView) findViewById(R.id.course_name8),
                (TextView) findViewById(R.id.course_name9)
        };



        // Click to go to Cart
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        // Set default view - show all courses
        filterCourses("All");

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterCourses("All");
            }
        });

        btnProgramming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterCourses("Programming");
            }
        });

        btnSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterCourses("Security");
            }
        });

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterCourses("Data");
            }
        });

        btnMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterCourses("Mobile");
            }
        });

    }

    private void filterCourses(String category) {
        int visibleIndex = 0;

        for (int i = 0; i < allCourses.length; i++) {
            if (category.equals("All") || courseCategories[i].equalsIgnoreCase(category)) {

                final String courseName = allCourses[i]; //  Define this first

                courseImageViews[visibleIndex].setVisibility(View.VISIBLE);
                courseNameViews[visibleIndex].setVisibility(View.VISIBLE);
                courseNameViews[visibleIndex].setText(courseName); //  Now it's defined and can be used

                courseImageViews[visibleIndex].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CategoryActivity.this, CourseDetailsActivity.class);
                        intent.putExtra("COURSE_NAME", courseName);
                        startActivity(intent);
                    }
                });

                visibleIndex++;
            }
        }

        // Hide remaining image & text slots
        for (int i = visibleIndex; i < courseImageViews.length; i++) {
            courseImageViews[i].setVisibility(View.GONE);
            courseNameViews[i].setVisibility(View.GONE);
        }
    }

}
