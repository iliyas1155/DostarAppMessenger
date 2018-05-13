package com.example.madina.dostarapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.madina.dostarapp.Adapters.ChatAdapter;
import com.example.madina.dostarapp.Items.ChatMessage;
import com.example.madina.dostarapp.Items.ForumTopic;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends SampleActivity {
    public static boolean showLoading;
    private static final String TAG = "ChatActivity";
    private FirebaseFirestore db;
    private Button sendMessage;
    private List<ChatMessage> messagesList;
    private EditText inputEditText;
    private ChatAdapter adapter;
    private ForumTopic topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        db = FirebaseFirestore.getInstance();
        topic = MainActivity.chosenTopic;

        inputEditText = findViewById(R.id.input_edit_text);
        sendMessage = findViewById(R.id.send_message_button);
        showLoading = true;

        RecyclerView rv = findViewById(R.id.list_of_messages);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(ChatActivity.this);
        rv.setLayoutManager(llm);

        messagesList = new ArrayList<>();
        adapter = new ChatAdapter(messagesList);
        rv.setAdapter(adapter);

        setMessagesListener();

        setOnClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String title = getIntent().getStringExtra("title");
        getSupportActionBar().setTitle(title != null ? title : getString(R.string.app_name));
    }

    //    private void initializeData(){
//        messagesList = new ArrayList<>();
//        messagesList.add(new ChatMessage("Hi", "Iliyas"));
//        messagesList.add(new ChatMessage("Love you", "Symbat"));
//        messagesList.add(new ChatMessage("Zhanym", "Symbat"));
//    }

    private void setOnClickListener(){
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String messageText = inputEditText.getText().toString();
            inputEditText.setText("");
            Log.d(TAG, "'MainActivity.currentUser' = " + MainActivity.currentUser);
            String sender = MainActivity.currentUser.getDisplayName();
            ChatMessage message = new ChatMessage(messageText, sender);
            addMessage(message);

            }
        });

    }

    private void addMessage(ChatMessage message){
        // Add a new document with a generated ID
        db.collection(topic.name)
                .add(message)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void getMessages(){
        if(showLoading){
            showProgressDialog();
        }
        db.collection(topic.name).orderBy("messageTime", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            messagesList.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String messageText = (String) document.getData().get("messageText");
                                String messageUser = (String) document.getData().get("messageUser");
                                Long messageTime = (Long) document.getData().get("messageTime");
                                messagesList.add(new ChatMessage(messageText, messageUser, messageTime));
                                adapter.notifyDataSetChanged();

                            }
                            if(showLoading){
                                hideProgressDialog();
                            }
                            showLoading = false;
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    private void setMessagesListener(){
        db.collection(topic.name)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        getMessages();
                    }
                });
    }
}
