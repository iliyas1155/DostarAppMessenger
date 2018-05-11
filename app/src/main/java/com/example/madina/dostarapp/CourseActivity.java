package com.example.madina.dostarapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.madina.dostarapp.Items.Course;

import static com.example.madina.dostarapp.CoursesActivity.getSelectedCourse;

public class CourseActivity extends SampleActivity {

    TextView titleTextView, descTextView, textTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        getSupportActionBar().hide();

        titleTextView = findViewById(R.id.course_title);
        descTextView = findViewById(R.id.course_description);
        textTextView = findViewById(R.id.course_text);

        Course course = getSelectedCourse();

        titleTextView.setText(course.name);
        descTextView.setText(course.desc);
        textTextView.setText(course.text);
    }
}
