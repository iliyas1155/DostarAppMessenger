package com.example.madina.dostarapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.madina.dostarapp.Items.Vacancy;

import static com.example.madina.dostarapp.VacanciesActivity.getSelectedVacancy;

public class VacancyActivity extends SampleActivity {

    Vacancy vacancy;
    TextView titleTextView, descTextView, coursesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy);

        titleTextView = findViewById(R.id.vacancy_title);
        descTextView = findViewById(R.id.vacancy_description);
        coursesTextView = findViewById(R.id.vacancy_contacts);

        vacancy = getSelectedVacancy();

        titleTextView.setText(vacancy.name);
        descTextView.setText(vacancy.desc);
        coursesTextView.setText(vacancy.contacts);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setTitle(vacancy.name);
    }
}
