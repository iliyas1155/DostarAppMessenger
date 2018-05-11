package com.example.madina.dostarapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.madina.dostarapp.Items.ForumTopic;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static FirebaseAuth mAuth;
    public static FirebaseUser currentUser;
    public static List<String> ADMIN_EMAILS_LIST;
    public static boolean isAdmin;
    public static ForumTopic chosenTopic;
    public ProgressDialog mProgressDialog;
    Button signIn, signUp;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signIn = findViewById(R.id.sign_in_button);
        signUp = findViewById(R.id.sign_up_button);

        FirebaseApp.initializeApp(MainActivity.this);

        mAuth = FirebaseAuth.getInstance();

        setOnClick();
        ADMIN_EMAILS_LIST = new ArrayList();
        ADMIN_EMAILS_LIST.add("iliyas1155@gmail.com");
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
        } else {

        }
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
