package com.example.madina.dostarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends SampleActivity {
    private Button toCalendar, toForum, toVacancies, toCourses, toProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toCalendar = findViewById(R.id.to_calendar_button);
        toForum = findViewById(R.id.to_forum_button);
        toVacancies = findViewById(R.id.to_vacancies_button);
        toCourses = findViewById(R.id.to_courses_button);
        toProfile = findViewById(R.id.to_profile_button);

        setOnClickListeners();
        if(MainActivity.ADMIN_EMAILS_LIST.contains(MainActivity.currentUser.getEmail())){
            MainActivity.isAdmin = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setSupportActionBarIcon();
        getSupportActionBar().setTitle(getString(R.string.home));
    }

    private void setOnClickListeners() {
        toCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuActivity.this, CalendarActivity.class);
                MenuActivity.this.startActivity(myIntent);
            }
        });
        toForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuActivity.this, ForumActivity.class);
                MenuActivity.this.startActivity(myIntent);
            }
        });
        toVacancies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuActivity.this, VacanciesActivity.class);
                MenuActivity.this.startActivity(myIntent);
            }
        });
        toCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuActivity.this, CoursesActivity.class);
                MenuActivity.this.startActivity(myIntent);
            }
        });
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MenuActivity.this, ProfileActiviry.class);
                MenuActivity.this.startActivity(myIntent);
            }
        });

    }
}
