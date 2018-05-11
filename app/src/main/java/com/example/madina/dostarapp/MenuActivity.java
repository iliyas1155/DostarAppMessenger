package com.example.madina.dostarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends SampleActivity {
    private Button toChat, toForum, toVacancies, toCourses, signOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toChat = findViewById(R.id.to_chat_button);
        toForum = findViewById(R.id.to_forum_button);
        toVacancies = findViewById(R.id.to_vacancies_button);
        toCourses = findViewById(R.id.to_courses_button);
        signOut = findViewById(R.id.sign_out_button);

        setOnClickListeners();
        if(MainActivity.ADMIN_EMAILS_LIST.contains(MainActivity.currentUser.getEmail())){
            MainActivity.isAdmin = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setSupportActionBarIcon();
    }

    private void setOnClickListeners() {
        toChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuActivity.this, ChatActivity.class);
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
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.signOut();
                finish();
            }
        });

    }
}
