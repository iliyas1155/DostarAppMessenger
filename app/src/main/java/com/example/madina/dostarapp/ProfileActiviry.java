package com.example.madina.dostarapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileActiviry extends SampleActivity {
    private static final String TAG = "ProfileActiviry";
    private Button signInButton;
    private EditText nameEditText;
    private EditText aboutEditText;
    private EditText addressEditText;
    private EditText phoneEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        signInButton = findViewById(R.id.edit_profile_button);
        nameEditText = findViewById(R.id.name_field);
        aboutEditText = findViewById(R.id.about_field);
        addressEditText = findViewById(R.id.address_field);
        phoneEditText = findViewById(R.id.phone_field);

        setData();
        setListeners();
    }
    private void setData(){
        signInButton.setVisibility(View.INVISIBLE);
        nameEditText.setText(MainActivity.currentUser.getDisplayName());
    }
    private void setListeners(){
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });

        nameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInButton.setVisibility(View.VISIBLE);
            }
        });

        aboutEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInButton.setVisibility(View.VISIBLE);
            }
        });

        addressEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInButton.setVisibility(View.VISIBLE);
            }
        });

        phoneEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void editProfile(){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameEditText.getText().toString())
                .build();
        MainActivity.currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }else{
                            Log.d(TAG, "Error in updating profile.");
                        }
                    }
                });


    }
}
