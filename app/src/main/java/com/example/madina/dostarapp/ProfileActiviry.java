package com.example.madina.dostarapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import static com.example.madina.dostarapp.MainActivity.userProfile;

public class ProfileActiviry extends SampleActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "ProfileActiviry";
    private Button updateButton;
    private EditText nameEditText;
    private EditText aboutEditText;
    private EditText addressEditText;
    private Spinner genderSpinner;
    private EditText educationEditText;
    private EditText skillsEditText;
    private EditText workExperienceEditText;
    private EditText phoneNumberEditText;
    private FirebaseFirestore db;
    private String []gender;
    private int selectedGender = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = FirebaseFirestore.getInstance();

        updateButton = findViewById(R.id.edit_profile_button);

        nameEditText = findViewById(R.id.name_field);
        aboutEditText = findViewById(R.id.about_field);
        addressEditText = findViewById(R.id.address_field);
        phoneNumberEditText = findViewById(R.id.phone_field);
        workExperienceEditText = findViewById(R.id.work_experience_field);
        skillsEditText = findViewById(R.id.skills_field);
        educationEditText = findViewById(R.id.education_field);

        genderSpinner = findViewById(R.id.gender_spinner);
        gender = new String[2];
        gender[0] = getString(R.string.female);
        gender[1] = getString(R.string.male);

        genderSpinner.setOnItemSelectedListener(this);

        setData();
        setListeners();
    }
    private void setData(){
        nameEditText.setText(userProfile.name);
        aboutEditText.setText(userProfile.aboutYourself);
        addressEditText.setText(userProfile.address);
        phoneNumberEditText.setText(userProfile.phoneNumber);
        workExperienceEditText.setText(userProfile.workExperience);
        skillsEditText.setText(userProfile.skills);
        educationEditText.setText(userProfile.education);
    }
    private void setListeners(){
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
    }

    private void editProfile(){
        showProgressDialog();
        updateUserProfile();

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

//        db.collection(MainActivity.COLLECTION_USERS).add(userProfile);
        db.collection(MainActivity.COLLECTION_USERS).document(userProfile.id)
                .set(userProfile)
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
        userProfile.gender = gender[selectedGender];
        userProfile.education = educationEditText.getText().toString();
        userProfile.skills = skillsEditText.getText().toString();
        userProfile.aboutYourself = aboutEditText.getText().toString();
        userProfile.workExperience = workExperienceEditText.getText().toString();
        userProfile.address = addressEditText.getText().toString();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedGender = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d("Printing","onNothingSelected()");
    }
}
