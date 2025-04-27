package com.example.onlinecourseproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NotesActivity extends Activity {

    private EditText notesEditText;
    private Button saveNotesButton;
    private SharedPreferences prefs;
    private String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notesEditText = (EditText) findViewById(R.id.notes_edittext);
        saveNotesButton = (Button) findViewById(R.id.save_notes_button);

        courseName = getIntent().getStringExtra("COURSE_NAME");
        prefs = getSharedPreferences("CourseNotes", MODE_PRIVATE);

        // Load saved note (if any)
        String savedNote = prefs.getString(courseName, "");
        notesEditText.setText(savedNote);

        saveNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notes = notesEditText.getText().toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(courseName, notes);
                editor.apply();

                Toast.makeText(NotesActivity.this, "Notes Saved!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
