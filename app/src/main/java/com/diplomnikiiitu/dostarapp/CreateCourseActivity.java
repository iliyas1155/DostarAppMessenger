package com.diplomnikiiitu.dostarapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.diplomnikiiitu.dostarapp.Items.Course;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateCourseActivity extends SampleActivity {
    private static final String TAG = "CreateCourseActivity";
    private FirebaseFirestore db;
    Button create;
    EditText titleEditText, categoryEditText, descEditText, youTubeLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        db = FirebaseFirestore.getInstance();
        create = findViewById(R.id.create_button);
        titleEditText = findViewById(R.id.course_title);
        categoryEditText = findViewById(R.id.course_category);
        descEditText = findViewById(R.id.course_description);
        youTubeLink = findViewById(R.id.youtube_link);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String category = categoryEditText.getText().toString();
                String desc = descEditText.getText().toString();
                String url = youTubeLink.getText().toString();
                Course course = new Course(title, category, desc, url);

                addCourse(course);//uploading to DB
            }
        });
    }

    private void addCourse(Course course){
        showProgressDialog();//cycle of loading
        // Add a new document with a generated ID
        db.collection("courses")
                .add(course)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        hideProgressDialog();
                        Toast.makeText(CreateCourseActivity.this, "course created!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        hideProgressDialog();
                        Toast.makeText(CreateCourseActivity.this, "course creation failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
