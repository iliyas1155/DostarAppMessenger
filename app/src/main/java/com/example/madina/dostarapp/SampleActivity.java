package com.example.madina.dostarapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.madina.dostarapp.Utils.SharedPreferencesUtil;

import java.util.Locale;
import static com.example.madina.dostarapp.MainActivity.userProfile;

public class SampleActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    private static String language = "en";
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage(SharedPreferencesUtil.getLanguage(this));
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
    }

    @Override
    protected void onStart() {
        Log.d("MenuActivity", "start super super onStart()");
        super.onStart();
        Log.d("MenuActivity", "end super super onStart()");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navView = findViewById(R.id.nav_view);
        NavViewListener navViewListener = new NavViewListener(this, drawer);
        navView.setNavigationItemSelectedListener(navViewListener);
    }

    public String getResume(){
        String resume = "";
        if(userProfile.name != null) resume += getString(R.string.full_name).toUpperCase() + ":\n" + userProfile.name + "\n\n";
        if(userProfile.gender != null) resume += getString(R.string.gender).toUpperCase() + ":\n" + userProfile.gender+ "\n\n";
        if(userProfile.email != null) resume += getString(R.string.email).toUpperCase() + ":\n" + userProfile.email+ "\n\n";
        if(userProfile.phoneNumber != null) resume += getString(R.string.phone_number).toUpperCase() + ":\n" + userProfile.phoneNumber+ "\n\n";
        if(userProfile.address != null) resume += getString(R.string.address).toUpperCase() + ":\n" + userProfile.address+ "\n\n";
        if(userProfile.workExperience != null) resume += getString(R.string.work_experience).toUpperCase() + ":\n" + userProfile.workExperience+ "\n\n";
        if(userProfile.aboutYourself != null) resume += getString(R.string.about).toUpperCase() + ":\n" + userProfile.aboutYourself+ "\n\n";
        if(userProfile.skills != null) resume += getString(R.string.skills).toUpperCase() + ":\n" + userProfile.skills+ "\n\n";
        if(userProfile.education != null) resume += getString(R.string.education).toUpperCase() + ":\n" + userProfile.education+ "\n\n";
        return resume;
    }

    public void setSupportActionBarIcon(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.activate_changes, R.string.forum);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
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

    protected void setLanguage(String language){
        this.language = language;
        updateLanguage();
    }

    protected String getLanguage(){
        return language;
    }

    private void updateLanguage(){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    protected void signOut() {
        MainActivity.signOut();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected void showToastMessage(String toastMessage){
        toast.setText(toastMessage);
        toast.show();
    }
}
