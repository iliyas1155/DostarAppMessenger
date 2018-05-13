package com.example.madina.dostarapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    EditText eventText;
    TextView selectedDateEventsOutput;
    Button create;
    Date selectedDate = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        db = FirebaseFirestore.getInstance();
        events = new ArrayList();
        create = findViewById(R.id.add_event);
        eventText = findViewById(R.id.event_edit_text);
        selectedDateEventsOutput = findViewById(R.id.selected_date_events_output);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = eventText.getText().toString();
                if (selectedDate != null){
                    Event newEvent = new Event(Color.BLACK, selectedDate.getTime(), data);
                    addEvent(newEvent);
                }else{
                    Toast.makeText(CalendarActivity.this, "No date selected",
                            Toast.LENGTH_SHORT).show();
                }
                eventText.setText("");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        calendar = findViewById(R.id.calendar);
        calendar.setLocale(TimeZone.getDefault(), new Locale(getLanguage()));
        calendar.setUseThreeLetterAbbreviation(true);
        calendar.showCalendarWithAnimation();

        Date currentFirstDate = calendar.getFirstDayOfCurrentMonth();
        String month = (currentFirstDate.toString()).substring(4,7);
        String year = (currentFirstDate.toString()).substring(30);
        getSupportActionBar().setTitle(month + " " + year);


        calendar.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                selectedDate = dateClicked;
                List<Event> events = calendar.getEvents(dateClicked);
                String showText = "";
                for (int i = 0; i < events.size(); i++) {
                    showText += (i + 1) + ") " + events.get(i).getData() + "\n";
                }
                selectedDateEventsOutput.setText(showText);
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


    @Override
    protected void onResume() {
        super.onResume();
        if(MainActivity.isAdmin == false){
            create.setVisibility(View.GONE);
            eventText.setVisibility(View.GONE);
        }
    }

    private void setEvents(){
        calendar.removeAllEvents();
        calendar.addEvents(events);
    }

    private void addEvent(Event event){
        showProgressDialog();
        db.collection("events")
                .add(event)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        hideProgressDialog();
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(CalendarActivity.this, "Event added",
                                Toast.LENGTH_SHORT).show();
                        getEvents();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(CalendarActivity.this, "Failed to add event",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getEvents(){
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
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}
