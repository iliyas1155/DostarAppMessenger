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
import android.widget.Button;

import com.example.madina.dostarapp.Adapters.ForumAdapter;
import com.example.madina.dostarapp.Items.ForumTopic;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends SampleActivity {
    private static final String TAG = "CoursesActivity";
    private FirebaseFirestore db;
    Button create;
    private static List<ForumTopic> topics;
    private static int selectedTopic;
    ForumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        db = FirebaseFirestore.getInstance();
        create = findViewById(R.id.create_button);

        RecyclerView rv = findViewById(R.id.rv_topics);
        rv.setHasFixedSize(true);
        selectedTopic = -1;

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ForumActivity.this, CreateForumTopicActivity.class);
                ForumActivity.this.startActivity(myIntent);
            }
        });
        initializeData();
        adapter = new ForumAdapter(topics);
        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        selectedTopic = position;
                        setChosenTopic();
                        Log.d(TAG, "\titem clicked:\tposition = " + position);
                        Intent myIntent = new Intent(ForumActivity.this, ChatActivity.class);
                        myIntent.putExtra("title", topics.get(selectedTopic).name);
                        ForumActivity.this.startActivity(myIntent);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.setDefaultTopic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MainActivity.isAdmin == false){
            create.setVisibility(View.GONE);
        }
        getMessages();
    }

    private void setChosenTopic(){
        MainActivity.chosenTopic = topics.get(selectedTopic);
    }

    public static ForumTopic getSelectedTopic(){
        return topics.get(selectedTopic);
    }

    private void initializeData(){
        topics = new ArrayList<>();
    }

    private void getMessages(){
        showProgressDialog();
        db.collection("topics").orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            topics.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String name = (String) document.getData().get("name");
                                String desc = (String) document.getData().get("desc");
                                long createdAt = (Long) document.getData().get("createdAt");
                                ForumTopic topic = new ForumTopic(name, desc);
                                topic.createdAt = createdAt;
                                topics.add(topic);
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
        mSearchView.setQueryHint(getString(R.string.forum_search));
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
