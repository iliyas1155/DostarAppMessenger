package com.example.madina.dostarapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.madina.dostarapp.Items.Vacancy;
import com.example.madina.dostarapp.Utils.EmailSender;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import static com.example.madina.dostarapp.VacanciesActivity.getSelectedVacancy;
import static com.example.madina.dostarapp.VacanciesActivity.getSelectedVacancyId;

public class VacancyActivity extends SampleActivity {

    TextView titleTextView, descTextView;

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
        responeOnVacancy = findViewById(R.id.respond_button);

        vacancy = getSelectedVacancy();
        vacancyId = getSelectedVacancyId();
        db = FirebaseFirestore.getInstance();

        titleTextView.setText(vacancy.name);
        descTextView.setText(vacancy.desc);
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
            sendEmailToOwner();
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

    private void sendEmailToOwner() {
        final EmailSender sender = new EmailSender("iliyas1155@gmail.com", "keepcalm");
        try {
            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        sender.sendMail(vacancy.name, MainActivity.userProfile.getResume(), "iliyas1155@gmail.com", "iliyas@adr.irish");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            asyncTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setTitle(vacancy.name);
    }
}
