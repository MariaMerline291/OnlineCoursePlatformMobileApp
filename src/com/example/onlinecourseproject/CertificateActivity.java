package com.example.onlinecourseproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CertificateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);

        TextView certificateText = (TextView) findViewById(R.id.certificate_text);

        String courseName = getIntent().getStringExtra("COURSE_NAME");
        String username = getIntent().getStringExtra("USERNAME");

        String certificateMessage = " Certificate of Completion\n\n" +
                                    "This certifies that " + "you" +
                                    " have successfully completed the course:\n\n" +
                                    "\"" + courseName + "\"";

        certificateText.setText(certificateMessage);
    }
}
