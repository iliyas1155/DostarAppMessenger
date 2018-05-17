package com.example.madina.dostarapp;

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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.madina.dostarapp.Adapters.CousesAdapter;
import com.example.madina.dostarapp.Items.Course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CoursesActivity extends SampleActivity {
    private static final String TAG = "CoursesActivity";
    private FirebaseFirestore db;
    Button create;
    private static List<Course> courses;
    private List<String> categories;
    private static int selectedCourse;
    RecyclerView rv;
    CousesAdapter adapter;

    ViewGroup filterContainer;
    Spinner categoriesSpinner;
    Button  clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        db = FirebaseFirestore.getInstance();
        create = findViewById(R.id.create_button);

        rv = findViewById(R.id.rv_courses);
        rv.setHasFixedSize(true);
        selectedCourse = -1;

        LinearLayoutManager llm = new LinearLayoutManager(CoursesActivity.this);
        rv.setLayoutManager(llm);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(CoursesActivity.this, CreateCourseActivity.class);
                CoursesActivity.this.startActivity(myIntent);
            }
        });
        initializeData();

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(CoursesActivity.this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        selectedCourse = position;
                        Log.d(TAG, "\titem clicked:\tposition = " + position);
                        Intent myIntent = new Intent(CoursesActivity.this, CourseActivity.class);
                        CoursesActivity.this.startActivity(myIntent);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

        filterContainer = findViewById(R.id.courses_filter_container);
        categoriesSpinner = findViewById(R.id.courses_filter_spinner);
        clearButton = findViewById(R.id.clear_filter_button);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MainActivity.isAdmin == false){
            create.setVisibility(GONE);
        }
        getMessages();
    }

    public static Course getSelectedCourse(){
        return courses.get(selectedCourse);
    }

    private void initializeData(){
        courses = new ArrayList<>();
        categories = new ArrayList<>();
    }

    private void getMessages(){
        showProgressDialog();
        db.collection("courses").orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            courses.clear();
                            categories.clear();
                            HashSet<String> categoriesUnique = new HashSet();

                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String name = (String) document.getData().get("name");
                                String category = (String) document.getData().get("category");
                                String desc = (String) document.getData().get("desc");
                                String url = (String) document.getData().get("url");
                                long createdAt = (Long) document.getData().get("createdAt");
                                Course course = new Course(name, category, desc, url);
                                course.createdAt = createdAt;
                                courses.add(course);
                                if(category != null){
                                    categoriesUnique.add(category);
                                }
                            }
                            categories = new ArrayList(categoriesUnique);
                            adapter = new CousesAdapter(courses);
                            rv.setAdapter(adapter);
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
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setQueryHint(getString(R.string.courses_search));
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
                toggleFilter();
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

    private void toggleFilter() {
        if (filterContainer.getVisibility() == VISIBLE) {
            filterContainer.setVisibility(GONE);
            adapter.setFilter("");
            return;
        }

        filterContainer.setVisibility(VISIBLE);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(categoriesAdapter);
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                adapter.setFilter(categories.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setFilter("");
                filterContainer.setVisibility(GONE);
            }
        });
    }
}

