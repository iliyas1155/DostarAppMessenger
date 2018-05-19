package com.example.madina.dostarapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.madina.dostarapp.Adapters.AdminSupportAdapter;
import com.example.madina.dostarapp.Adapters.ForumAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.madina.dostarapp.MainActivity.COLLECTION_USERS;

public class AdminSupportActivity extends SampleActivity {
    private static final String TAG = "AdminSupportActivity";
    private List<String> usersEmailsList;
    private FirebaseFirestore db;
    private AdminSupportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_support);
        db = FirebaseFirestore.getInstance();

        usersEmailsList = new ArrayList();

        RecyclerView rv = findViewById(R.id.rv_emails);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        adapter = new AdminSupportAdapter(usersEmailsList);
        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Log.d(TAG, "\titem clicked:\tposition = " + position);
                        Intent myIntent = new Intent(AdminSupportActivity.this, ChatActivity.class);
                        myIntent.putExtra("collection", "support");
                        myIntent.putExtra("title", getString(R.string.support));
                        myIntent.putExtra("document", usersEmailsList.get(position));
                        AdminSupportActivity.this.startActivity(myIntent);
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );

    }

    @Override
    protected void onStart() {
        super.onStart();
        getUsersEmails();
    }

    public void getUsersEmails(){
        showProgressDialog();
        db.collection(COLLECTION_USERS).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            usersEmailsList.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String userEmail = (String) document.getData().get("email");
                                usersEmailsList.add(userEmail);
                            }
                            adapter.notifyDataSetChanged();
                            hideProgressDialog();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}
