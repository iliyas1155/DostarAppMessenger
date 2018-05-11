package com.example.madina.dostarapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.madina.dostarapp.Items.ChatMessage;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.TreeMap;


public class CalendarActivity extends SampleActivity {
    private String TAG = "Calendar";
    private CompactCalendarView calendar;
    private FirebaseFirestore db;
    private List<Event> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        db = FirebaseFirestore.getInstance();
        events = new ArrayList();
    }

    @Override
    protected void onStart() {
        super.onStart();

        calendar = findViewById(R.id.calendar);
        calendar.setLocale(TimeZone.getDefault(), new Locale(getLanguage()));
        calendar.setUseThreeLetterAbbreviation(true);

        Date currentFirstDate = calendar.getFirstDayOfCurrentMonth();
        String month = (currentFirstDate.toString()).substring(4,7);
        String year = (currentFirstDate.toString()).substring(30);
        getSupportActionBar().setTitle(month + " " + year);


        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = calendar.getEvents(dateClicked);
                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                String month = (firstDayOfNewMonth.toString()).substring(4,7);
                String year = (firstDayOfNewMonth.toString()).substring(30);

                getSupportActionBar().setTitle(month + " " + year);
            }
        });
        getEvents();
    }


    private void setEvents(){
        calendar.addEvents(events);
    }

    private void addEvent(Event event){
        // Add a new document with a generated ID
        db.collection("events")
                .add(event)
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

    private void getEvents(){
        showProgressDialog();
        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            events.clear();
                            for (DocumentSnapshot document : task.getResult()) {
                                int color = Integer.parseInt(document.getData().get("color").toString());
                                Object data = document.getData().get("data");
                                Long timeInMillis = (Long) document.getData().get("timeInMillis");
                                events.add(new Event(color, timeInMillis, data));
                            }
                            setEvents();
                            hideProgressDialog();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}
