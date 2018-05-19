package com.example.madina.dostarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.madina.dostarapp.MainActivity.ADMINS_COLLECTION;

public class MenuActivity extends SampleActivity {
    private Button toCalendar, toForum, toVacancies, toCourses, toProfile;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Log.d("MenuActivity", "start onCreate()");
        db = FirebaseFirestore.getInstance();

        toCalendar = findViewById(R.id.to_calendar_button);
        toForum = findViewById(R.id.to_forum_button);
        toVacancies = findViewById(R.id.to_vacancies_button);
        toCourses = findViewById(R.id.to_courses_button);
        toProfile = findViewById(R.id.to_profile_button);

        setOnClickListeners();
        Log.d("MenuActivity", "end onCreate()");
    }

    @Override
    protected void onStart() {
        Log.d("MenuActivity", "start onStart()");
        Log.d("MenuActivity", "start super onStart()");
        super.onStart();
        Log.d("MenuActivity", "end super onStart()");
        setSupportActionBarIcon();
        Log.d("MenuActivity", "end setSupportActionBarIcon()");

        getAdmins();
        Log.d("MenuActivity", "end onStart()");
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
                Intent myIntent = new Intent(MenuActivity.this, ForumRublickActivity.class);
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

    public void getAdmins(){
        showProgressDialog();
        db.collection(MainActivity.ADMINS_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            MainActivity.ADMIN_EMAILS_LIST = new ArrayList();
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("MenuActivity", document.getId() + " => " + document.getData());
                                String email = (String) document.getData().get("email");
                                MainActivity.ADMIN_EMAILS_LIST.add(email);

                                if(MainActivity.currentUser.getEmail().equals(email)){
                                    MainActivity.isAdmin = true;
                                }
                            }

                            hideProgressDialog();
                        } else {
                            Log.w("MenuActivity", "Error getting admins.", task.getException());
                            hideProgressDialog();
                        }
                    }
                });
    }
}
