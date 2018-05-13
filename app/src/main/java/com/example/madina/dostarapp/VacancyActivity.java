package com.example.madina.dostarapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madina.dostarapp.Items.Vacancy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

import static com.example.madina.dostarapp.VacanciesActivity.getSelectedVacancy;
import static com.example.madina.dostarapp.VacanciesActivity.getSelectedVacancyId;

public class VacancyActivity extends SampleActivity {

    TextView titleTextView, descTextView, coursesTextView;

    Button responeOnVacancy;
    Vacancy vacancy;
    String vacancyId;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancy);

        titleTextView = findViewById(R.id.vacancy_title);
        descTextView = findViewById(R.id.vacancy_description);
        coursesTextView = findViewById(R.id.vacancy_contacts);
        responeOnVacancy = findViewById(R.id.respond_button);

        vacancy = getSelectedVacancy();
        vacancyId = getSelectedVacancyId();
        db = FirebaseFirestore.getInstance();

        titleTextView.setText(vacancy.name);
        descTextView.setText(vacancy.desc);
        coursesTextView.setText(vacancy.contacts);
        responeOnVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                respondOnVacancy();
            }
        });
    }

    private void respondOnVacancy(){
        boolean userAdded = vacancy.addResponding(MainActivity.currentUser.getUid());
        if(userAdded) {
            showProgressDialog();
            db.collection(VacanciesActivity.COLLECTION_NAME).document(vacancyId)
                    .set(vacancy, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            hideProgressDialog();
                            showToastMessage(getString(R.string.respond_success));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            hideProgressDialog();
                            showToastMessage(getString(R.string.respond_failure));
                        }
                    });
        }else{
            showToastMessage(getString(R.string.respond_already_done));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setTitle(vacancy.name);
    }
}
