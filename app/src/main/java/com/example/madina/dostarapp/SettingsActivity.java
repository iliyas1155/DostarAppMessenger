package com.example.madina.dostarapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.madina.dostarapp.Utils.SharedPreferencesUtil;

public class SettingsActivity extends SampleActivity implements AdapterView.OnItemSelectedListener  {
    Spinner languagesSpinner;
    Button toProfile, signOut, changeLanguage;
    String[] languages = {"ru","en"};
    static int chosenLanguage = 0;
    static boolean isLanguageChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        languagesSpinner = findViewById(R.id.languages_spinner);
        changeLanguage = findViewById(R.id.activate_changes_button);
        toProfile = findViewById(R.id.to_profile_button);
        signOut = findViewById(R.id.sign_out_button);

        languagesSpinner.setOnItemSelectedListener(this);
        setOnClickListeners();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setTitle(getString(R.string.settings)); // To refresh toolbar language after language change.
    }

    private void setOnClickListeners() {
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SettingsActivity.this, ProfileActiviry.class);
                SettingsActivity.this.startActivity(myIntent);
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage(languages[chosenLanguage]);
                isLanguageChanged = true;
                SharedPreferencesUtil.setLanguage(SettingsActivity.this, languages[chosenLanguage]);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        chosenLanguage = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Log.d("Printing","onNothingSelected()");
    }

    @Override
    public void onBackPressed() {
        if (isLanguageChanged) {
            Intent refreshMenu = new Intent(this, MenuActivity.class);
            refreshMenu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(refreshMenu);
            finish();
        }
        super.onBackPressed();
    }
}
