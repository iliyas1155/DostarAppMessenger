package com.example.madina.dostarapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import static com.example.madina.dostarapp.MainActivity.userProfile;

public class ProfileActiviry extends SampleActivity {
    private static final String TAG = "ProfileActiviry";
    private Button signInButton;
    private EditText nameEditText;
    private EditText aboutEditText;
    private EditText addressEditText;
    private EditText genderEditText;
    private EditText educationEditText;
    private EditText skillsEditText;
    private EditText workExperienceEditText;
    private EditText phoneNumberEditText;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = FirebaseFirestore.getInstance();

        signInButton = findViewById(R.id.edit_profile_button);
        nameEditText = findViewById(R.id.name_field);
        aboutEditText = findViewById(R.id.about_field);
        addressEditText = findViewById(R.id.address_field);
        phoneNumberEditText = findViewById(R.id.phone_field);

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
    }

    private void editProfile(){
        showProgressDialog();
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

        db.collection(MainActivity.COLLECTION_USERS).document(userProfile.id)
                .set(userProfile, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        hideProgressDialog();
                        showToastMessage(getString(R.string.profile_update_success));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        showToastMessage(getString(R.string.profile_update_failure));
                    }
                });
    }
    
    private void updateUserProfile(){
        userProfile.name = nameEditText.getText().toString();
        userProfile.phoneNumber = phoneNumberEditText.getText().toString();
        userProfile.gender = genderEditText.getText().toString();
        userProfile.education = educationEditText.getText().toString();
        userProfile.skills = skillsEditText.getText().toString();
        userProfile.aboutYourself = aboutEditText.getText().toString();
        userProfile.workExperience = workExperienceEditText.getText().toString();
        userProfile.address = addressEditText.getText().toString();
    }
}
