package com.example.madina.dostarapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.madina.dostarapp.Items.Vacancy;

import java.util.ArrayList;
import java.util.List;

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
            categories.add(vacancy.category);
            cities.add(vacancy.city);
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

        categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        regionsAdapter    = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, regions);
        citiesAdapter     = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);

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
            setScreenerColor(adapterView);
            chosenCategory = categories.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    }

    private class RegionSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            setScreenerColor(adapterView);
            // todo: Set cities here.
//            cities = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.cities)));
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    }

    private class CitySelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            setScreenerColor(adapterView);
            chosenCity = cities.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}
    }

    private class FilterListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent data = new Intent();
            data.putExtra("chosenCategory", chosenCategory);
            data.putExtra("chosenCity",     chosenCity);
            setResult(RESULT_OK, data);
        }
    }

    private void setScreenerColor(AdapterView<?> adapterView) {
        TextView selectedGenderTv = ((TextView) adapterView.getChildAt(0));
        selectedGenderTv.setTextColor(Color.WHITE);
        selectedGenderTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
    }
}
