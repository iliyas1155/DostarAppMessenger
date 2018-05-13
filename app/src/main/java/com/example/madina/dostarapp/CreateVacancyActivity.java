package com.example.madina.dostarapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madina.dostarapp.Items.Vacancy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CreateVacancyActivity extends SampleActivity {
    private static final String TAG = "CreateCourseActivity";
    private FirebaseFirestore db;
    Button create;
    EditText titleEditText, descEditText, contactsEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vacancy);

        db = FirebaseFirestore.getInstance();
        create = findViewById(R.id.create_button);
        titleEditText = findViewById(R.id.vacancy_title);
        descEditText = findViewById(R.id.vacancy_description);
        contactsEditText = findViewById(R.id.vacancy_contacts);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String desc = descEditText.getText().toString();
                String ownerEmail = contactsEditText.getText().toString();
                Vacancy vacancy = new Vacancy(ownerEmail, title, desc, new ArrayList<String>());
                //has to be uploaded to DB
                addVacancy(vacancy);
            }
        });
    }

    private void addVacancy(Vacancy vacancy){
        showProgressDialog();//cycle of loading
        // Add a new document with a generated ID
        db.collection("vacancies")
                .add(vacancy)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        hideProgressDialog();
                        Toast.makeText(CreateVacancyActivity.this, "course created!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        hideProgressDialog();
                        Toast.makeText(CreateVacancyActivity.this, "course creation failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
