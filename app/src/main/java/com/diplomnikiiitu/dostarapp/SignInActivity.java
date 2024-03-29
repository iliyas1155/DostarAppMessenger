package com.diplomnikiiitu.dostarapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import static com.diplomnikiiitu.dostarapp.MainActivity.currentUser;
import static com.diplomnikiiitu.dostarapp.MainActivity.mAuth;

public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";
    public ProgressDialog mProgressDialog;
    Button signIn;
    EditText emailEditText, passwordEditText;
    private Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);

        emailEditText = findViewById(R.id.email_field);
        passwordEditText = findViewById(R.id.password_field);
        signIn = findViewById(R.id.sign_in_button);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                signIn(email,password);
            }
        });

        if(currentUser != null){
            emailEditText.setText(currentUser.getEmail());
        }
    }

    public void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm(email, password, null)) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            currentUser = mAuth.getCurrentUser();
                            showToastMessage(getString(R.string.auth_success));
                            updateUI(currentUser);
                            MainActivity.getUserProfile(currentUser.getUid());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            showToastMessage(getString(R.string.auth_failure));
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private boolean validateForm(String email, String password, String name) {
        boolean valid = true;
        if (email.isEmpty()) {
            valid = false;
        }
        if (password.isEmpty()) {
            valid = false;
        }
        if(name != null) {
            if (name.isEmpty()) {
                valid = false;
            }
        }
        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {// && user.isEmailVerified()
            Intent myIntent = new Intent(SignInActivity.this, MenuActivity.class);
            SignInActivity.this.startActivity(myIntent);
            finish();
//        } else if(user != null && user.isEmailVerified() == false){// && user.isEmailVerified()
//                showToastMessage("Email is not verified");
        }else{
            showToastMessage(getString(R.string.auth_failure));
        }
    }

    private void showToastMessage(String toastMessage){
        toast.setText(toastMessage);
        toast.show();
    }
}
