package com.example.madina.dostarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.madina.dostarapp.Items.Vacancy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class VacanciesFilterActivity extends SampleActivity {
    private static final String TAG = "VacanciesFilterActivity";

    private List<Vacancy> vacancies;
    private List<String>  categories;
    private List<String>  regions;
    private List<String>  cities;

    private String chosenCategory;
    private String chosenCity;

    private Spinner categorySpinner;
    private Spinner regionSpinner;
    private Spinner citySpinner;
    private Button  filterButton;

    private ArrayAdapter<String> categoriesAdapter;
    private ArrayAdapter<String> regionsAdapter;
    private ArrayAdapter<String> citiesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancies_filter);
        loadVacancies();
        init();
    }

    private void loadVacancies() {
        vacancies  = getIntent().getParcelableArrayListExtra("vacancies");
        categories = new ArrayList<>();
        regions    = new ArrayList<>();
        cities     = new ArrayList<>();

        for (Vacancy vacancy : vacancies) {
            categories.add(vacancy.category != null ? vacancy.category : "");
            regions.add(vacancy.region != null ? vacancy.region : "");
        }
    }

    private void init() {
        categorySpinner = findViewById(R.id.category_filter);
        regionSpinner   = findViewById(R.id.region_filter);
        citySpinner     = findViewById(R.id.city_filter);
        filterButton    = findViewById(R.id.filter_button);

        categorySpinner.setOnItemSelectedListener(new CategorySelectedListener());
        regionSpinner.setOnItemSelectedListener(new RegionSelectedListener());
        citySpinner.setOnItemSelectedListener(new CitySelectedListener());
        filterButton.setOnClickListener(new FilterListener());

        categoriesAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, categories);
        regionsAdapter    = new ArrayAdapter<>(this, R.layout.spinner_item, regions);
        citiesAdapter     = new ArrayAdapter<>(this, R.layout.spinner_item, cities);

        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(categoriesAdapter);
        regionSpinner.setAdapter(regionsAdapter);
        citySpinner.setAdapter(citiesAdapter);
    }


    private class CategorySelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            chosenCategory = categories.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    }

    private class RegionSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            String region = regions.get(position);
            StringTokenizer tokenizer = new StringTokenizer(region);
            String resourseName = "region_" + tokenizer.nextToken().toLowerCase();
            int citiesArrayId = getResources().getIdentifier(resourseName, "array", getPackageName());
            if (citiesArrayId != 0) {
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

    private class FilterListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            passData();
        }
    }

    @Override
    public void onBackPressed() {
        passData();
        super.onBackPressed();
    }

    private void passData() {
        Intent data = new Intent();
        data.putExtra("chosenCategory", chosenCategory);
        data.putExtra("chosenCity",     chosenCity);
        setResult(RESULT_OK, data);
        finish();
    }
}
