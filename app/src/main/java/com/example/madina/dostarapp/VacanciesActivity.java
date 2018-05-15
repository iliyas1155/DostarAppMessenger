package com.example.madina.dostarapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
import java.util.List;

public class VacanciesActivity extends SampleActivity {

    public ProgressDialog mProgressDialog;
    private static final String TAG = "VacanciesActivity";
    private FirebaseFirestore db;
    private Button create;
    private static List<Vacancy> vacancies;
    private static int selectedVacancy;
    private static HashMap<Vacancy, String> vacanciesIds;
    public static final String COLLECTION_NAME = "vacancies";
    VacanciesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacancies);

        db = FirebaseFirestore.getInstance();
        create = findViewById(R.id.create_button);
        RecyclerView rv = findViewById(R.id.rv_vacancies);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(VacanciesActivity.this);
        rv.setLayoutManager(llm);

        initializeData();
        adapter = new VacanciesAdapter(vacancies);
        rv.setAdapter(adapter);

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MainActivity.isAdmin == false){
            create.setVisibility(View.GONE);
        }
        getMessages();
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
                                String desc = (String) document.getData().get("desc");
                                String ownerEmail = (String) document.getData().get("ownerEmail");
                                List<String> responding = (List<String>) document.getData().get("responding");
                                if(responding == null){
                                    responding = new ArrayList();
                                }
                                long createdAt = (Long) document.getData().get("createdAt");
                                Vacancy vacancy = new Vacancy(ownerEmail, name, category, desc, responding);
                                vacancy.createdAt = createdAt;
                                vacancies.add(vacancy);
                                vacanciesIds.put(vacancy, document.getId());
                            }
                            adapter.notifyDataSetChanged();
                            hideProgressDialog();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setQueryHint(getString(R.string.vacancies_search));
        mSearchView.setOnQueryTextListener(new QueryListener());

        return super.onCreateOptionsMenu(menu);
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
}
