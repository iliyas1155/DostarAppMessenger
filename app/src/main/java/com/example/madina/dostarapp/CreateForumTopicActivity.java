package com.example.madina.dostarapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.madina.dostarapp.Items.ForumTopic;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateForumTopicActivity extends SampleActivity {
    private static final String TAG = "CreateForumTopic";
    private FirebaseFirestore db;
    private String collection;
    Button create;
    EditText titleEditText, descEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_forum_topic);

        db = FirebaseFirestore.getInstance();
        create = findViewById(R.id.create_button);
        titleEditText = findViewById(R.id.course_title);
        descEditText = findViewById(R.id.course_description);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String desc = descEditText.getText().toString();
                ForumTopic topic = new ForumTopic(title, desc);

                addCourse(topic);//uploading to DB
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.collection = getIntent().getStringExtra("collection");
    }

    private void addCourse(ForumTopic topic){
        showProgressDialog();//cycle of loading
        // Add a new document with a generated ID
        db.collection(collection)
                .add(topic)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        hideProgressDialog();
                        Toast.makeText(CreateForumTopicActivity.this, "topic created!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        hideProgressDialog();
                        Toast.makeText(CreateForumTopicActivity.this, "topic creation failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
