package com.example.madina.dostarapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.madina.dostarapp.Items.Course;
import com.example.madina.dostarapp.Items.ForumTopic;
import com.example.madina.dostarapp.Utils.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String COLLECTION_USERS = "users";
    private static final String TAG = "MainActivity";
    public static FirebaseAuth mAuth;
    public static UserProfile userProfile;
    public static FirebaseUser currentUser;
    public static List<String> ADMIN_EMAILS_LIST;
    public static boolean isAdmin;
    public static ForumTopic chosenTopic;
    public ProgressDialog mProgressDialog;
    private static FirebaseFirestore db;
    Button signIn, signUp;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signIn = findViewById(R.id.sign_in_button);
        signUp = findViewById(R.id.sign_up_button);

        FirebaseApp.initializeApp(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        setOnClick();
        ADMIN_EMAILS_LIST = new ArrayList();
        ADMIN_EMAILS_LIST.add("iliyas1155@gmail.com");
        userProfile = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        isAdmin = false;
        setDefaultTopic();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public static void setDefaultTopic(){
        chosenTopic = new ForumTopic("main", "default topic");
    }

    private void setOnClick(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, SignInActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, SignUpActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    public FirebaseUser getUser(){
        return currentUser;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            Intent myIntent = new Intent(MainActivity.this, MenuActivity.class);
            MainActivity.this.startActivity(myIntent);
            getUserProfile(user.getUid());
        } else {
        }
    }

    public static void getUserProfile(final String userId){
        userProfile = new UserProfile(currentUser.getUid(), currentUser.getEmail());
        userProfile.name = currentUser.getDisplayName();
        DocumentReference docRef = db.collection(COLLECTION_USERS).document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData().get("id"));
                        userProfile.phoneNumber = (String) document.getData().get("phoneNumber");
                        userProfile.gender = (String) document.getData().get("gender");
                        userProfile.education = (String) document.getData().get("education");
                        userProfile.skills = (String) document.getData().get("skills");
                        userProfile.aboutYourself = (String) document.getData().get("aboutYourself");
                        userProfile.workExperience = (String) document.getData().get("workExperience");
                        userProfile.address = (String) document.getData().get("address");

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("@string/loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public static void signOut() {
        mAuth.signOut();
    }
}
