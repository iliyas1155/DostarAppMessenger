package com.example.madina.dostarapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madina.dostarapp.Adapters.VacanciesAdapter;
import com.example.madina.dostarapp.Items.Vacancy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class VacanciesActivity extends SampleActivity {

    public ProgressDialog mProgressDialog;
    private static final String TAG = "VacanciesActivity";
    private FirebaseFirestore db;
    private Button create;
    private static List<Vacancy> vacancies;
    private static int selectedVacancy;
    private static HashMap<Vacancy, String> vacanciesIds;
    public static final String COLLECTION_NAME = "vacancies";
    private static final int VACANCIES_FILTER_REQUEST = 1000;
    private boolean isVacanciesLoaded = false;

    RecyclerView rv;
    VacanciesAdapter adapter;

    ViewGroup filterContainer;
    ViewGroup categoryFilterContainer;
    ViewGroup regionFilterContainer;
    ViewGroup cityFilterContainer;
    TextView  categoryTv;
    TextView  regionTv;
    TextView  cityTv;
    ImageView categoryDeleteIv;
    ImageView regionDeleteIv;
    ImageView cityDeleteIv;
    Button    clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancies);

        db = FirebaseFirestore.getInstance();
        create = findViewById(R.id.create_button);
        rv = findViewById(R.id.rv_vacancies);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(VacanciesActivity.this);
        rv.setLayoutManager(llm);

        initializeData();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(VacanciesActivity.this, CreateVacancyActivity.class);
                VacanciesActivity.this.startActivity(myIntent);
            }
        });

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(VacanciesActivity.this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        selectedVacancy = position;
                        Log.d(TAG, "\titem clicked:\tposition = " + position);
                        Intent myIntent = new Intent(VacanciesActivity.this, VacancyActivity.class);
                        VacanciesActivity.this.startActivity(myIntent);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

        // The following views are needed for filter.
        filterContainer         = findViewById(R.id.filter_tags_container);
        categoryFilterContainer = findViewById(R.id.filter_tag_category);
        regionFilterContainer     = findViewById(R.id.filter_tag_region);
        cityFilterContainer     = findViewById(R.id.filter_tag_city);
        categoryTv              = findViewById(R.id.filter_category_name);
        regionTv                  = findViewById(R.id.filter_region_name);
        cityTv                  = findViewById(R.id.filter_city_name);
        categoryDeleteIv        = findViewById(R.id.filter_tag_category_delete);
        regionDeleteIv            = findViewById(R.id.filter_tag_region_delete);
        cityDeleteIv            = findViewById(R.id.filter_tag_city_delete);
        clearButton             = findViewById(R.id.clear_filter_button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MainActivity.isAdmin == false){
            create.setVisibility(GONE);
        }
        if (!isVacanciesLoaded) {
            getMessages();
        }
    }

    public static Vacancy getSelectedVacancy(){
        return vacancies.get(selectedVacancy);
    }

    public static String getSelectedVacancyId(){
        return vacanciesIds.get(vacancies.get(selectedVacancy));
    }

    private void initializeData(){
        vacancies = new ArrayList<>();
        vacanciesIds = new HashMap();
    }

    private void getMessages(){
        showProgressDialog();
        db.collection(COLLECTION_NAME).orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            vacancies.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String name = (String) document.getData().get("name");
                                String category = (String) document.getData().get("category");
                                String region = (String) document.getData().get("region");
                                String city = (String) document.getData().get("city");
                                String desc = (String) document.getData().get("desc");
                                String ownerEmail = (String) document.getData().get("ownerEmail");
                                List<String> responding = (List<String>) document.getData().get("responding");
                                if(responding == null){
                                    responding = new ArrayList();
                                }
                                long createdAt = (Long) document.getData().get("createdAt");
                                Vacancy vacancy = new Vacancy(ownerEmail, name, category, region, city, desc, responding);
                                vacancy.createdAt = createdAt;
                                vacancies.add(vacancy);
                                vacanciesIds.put(vacancy, document.getId());
                            }
                            adapter = new VacanciesAdapter(vacancies);
                            rv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            hideProgressDialog();
                            isVacanciesLoaded = true;
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setQueryHint(getString(R.string.vacancies_search));
        mSearchView.setOnQueryTextListener(new QueryListener());

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_filter:
                startFilter();
                break;
        }
        return true;
    }

    private class QueryListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            adapter.getFilter().filter(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            adapter.getFilter().filter(query);
            return false;
        }
    }

    private void startFilter() {
        Intent startFilter = new Intent(this, VacanciesFilterActivity.class);
        startFilter.putParcelableArrayListExtra("vacancies", (ArrayList<? extends Parcelable>) vacancies);
        startActivityForResult(startFilter, VACANCIES_FILTER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String chosenCategory = data.getStringExtra("chosenCategory");
        final String chosenRegion   = data.getStringExtra("chosenRegion");
        final String chosenCity     = data.getStringExtra("chosenCity");

        categoryTv.setText(chosenCategory);
        regionTv.setText(chosenRegion);
        cityTv.setText(chosenCity);

        updateFilters();

        filterContainer.setVisibility(VISIBLE);
        categoryFilterContainer.setVisibility(VISIBLE);
        regionFilterContainer.setVisibility(VISIBLE);
        cityFilterContainer.setVisibility(VISIBLE);

        categoryDeleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryFilterContainer.setVisibility(GONE);
                categoryTv.setText("");
                if (areFiltersEmpty()) {
                    filterContainer.setVisibility(GONE);
                }
                updateFilters();
            }
        });

        regionDeleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regionFilterContainer.setVisibility(GONE);
                regionTv.setText("");
                if (areFiltersEmpty()) {
                    filterContainer.setVisibility(GONE);
                }
                updateFilters();
            }
        });

        cityDeleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cityFilterContainer.setVisibility(GONE);
                cityTv.setText("");
                if (areFiltersEmpty()) {
                    filterContainer.setVisibility(GONE);
                }
                updateFilters();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setFilter(null, null, null);
                filterContainer.setVisibility(GONE);
            }
        });
    }

    private boolean areFiltersEmpty(){
        if (categoryFilterContainer.getVisibility() == GONE && regionFilterContainer.getVisibility() == GONE && cityFilterContainer.getVisibility() == GONE) {
            return true;
        }else{
            return false;
        }
    }

    private void updateFilters(){
        String category = categoryTv.getText().toString();
        String region = regionTv.getText().toString();
        String city = cityTv.getText().toString();
        category = category.isEmpty() ? null : category;
        region = region.isEmpty() ? null : region;
        city = city.isEmpty() ? null : city;
        adapter.setFilter(category, region, city);
    }
}
