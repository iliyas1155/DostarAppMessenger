package com.example.madina.dostarapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.madina.dostarapp.Items.Vacancy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CreateVacancyActivity extends SampleActivity {
    private static final String TAG = "CreateCourseActivity";
    private FirebaseFirestore db;
    private ArrayList<String> regions;
    private ArrayList<String> cities;
    private HashMap<String, Integer> regionsPaths;
    Button create;
    EditText titleEditText, categoryEditText, descEditText, contactsEditText;
    Spinner regionSpinner, citySpinner;
    private String chosenRegion, chosenCity;
    private ArrayAdapter<String> regionsAdapter;
    private ArrayAdapter<String> citiesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vacancy);

        db = FirebaseFirestore.getInstance();

        setInitData();
        init();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String category = categoryEditText.getText().toString();
                String region = chosenRegion;
                String city = chosenCity;
                String desc = descEditText.getText().toString();
                String ownerEmail = contactsEditText.getText().toString();
                Vacancy vacancy = new Vacancy(ownerEmail, title, category, region, city, desc, new ArrayList<String>());
                //has to be uploaded to DB
                addVacancy(vacancy);
            }
        });

    }
    private void setInitData(){
        regions    = new ArrayList<>();
        cities     = new ArrayList<>();
        regionsPaths = new HashMap();

        regionsPaths.put("Атырауская область", R.array.region_atyrau);
        regionsPaths.put("Алматинская область", R.array.region_almaty);
        regionsPaths.put("Акмолинская область", R.array.region_akmolinsk);
        regionsPaths.put("Павлодарская область", R.array.region_pavlodar);
        regionsPaths.put("ЮКО", R.array.region_sowth);

        regions.addAll(regionsPaths.keySet());

    }
    private void init(){
        create = findViewById(R.id.create_button);
        titleEditText = findViewById(R.id.vacancy_title);
        categoryEditText = findViewById(R.id.vacancy_category);
        descEditText = findViewById(R.id.vacancy_description);
        contactsEditText = findViewById(R.id.vacancy_contacts);

        regionSpinner   = findViewById(R.id.vacancy_region);
        citySpinner     = findViewById(R.id.vacancy_city);

        regionSpinner.setOnItemSelectedListener(new CreateVacancyActivity.RegionSelectedListener());
        citySpinner.setOnItemSelectedListener(new CreateVacancyActivity.CitySelectedListener());

        regionsAdapter    = new ArrayAdapter<>(this, R.layout.spinner_item, regions);
        citiesAdapter     = new ArrayAdapter<>(this, R.layout.spinner_item, cities);

        regionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        regionSpinner.setAdapter(regionsAdapter);
        citySpinner.setAdapter(citiesAdapter);
        regionsAdapter.notifyDataSetChanged();
    }
    private void addVacancy(Vacancy vacancy){
        showProgressDialog();//cycle of loading
        // Add a new document with a generated ID
        db.collection("vacancies")
                .add(vacancy)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        hideProgressDialog();
                        Toast.makeText(CreateVacancyActivity.this, "course created!",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        hideProgressDialog();
                        Toast.makeText(CreateVacancyActivity.this, "course creation failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private class RegionSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            chosenRegion = regions.get(position);
            Integer citiesArrayId = regionsPaths.get(chosenRegion);
            if (citiesArrayId != null) {
                cities.clear();
                cities.addAll(Arrays.asList(getResources().getStringArray(citiesArrayId)));
                citiesAdapter.notifyDataSetChanged();
                chosenCity = cities.get(0);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    }

    private class CitySelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            chosenCity = cities.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    }
}
