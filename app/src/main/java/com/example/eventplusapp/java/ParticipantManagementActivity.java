// File: app/src/main/java/com/example/eventplusapp/java/ParticipantManagementActivity.java
package com.example.eventplusapp.java;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventplusapp.R;
import java.util.ArrayList;
import java.util.List;

public class ParticipantManagementActivity extends AppCompatActivity {

    private Spinner spinnerEvents;
    private RecyclerView recyclerViewParticipants;
    private ParticipantAdapter participantAdapter;
    private List<Event> eventList;
    private List<Participant> participantList;
    private EventDatabaseOperations eventDatabaseOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_management);

        spinnerEvents = findViewById(R.id.spinner_events);
        recyclerViewParticipants = findViewById(R.id.recycler_view_participants);

        eventDatabaseOperations = new EventDatabaseOperations(this);
        eventList = eventDatabaseOperations.getAllEvents();
        participantList = new ArrayList<>();
        participantAdapter = new ParticipantAdapter(participantList);

        recyclerViewParticipants.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewParticipants.setAdapter(participantAdapter);

        ArrayAdapter<Event> eventArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventList);
        eventArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEvents.setAdapter(eventArrayAdapter);

        spinnerEvents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Event selectedEvent = (Event) parent.getItemAtPosition(position);
                loadParticipants(selectedEvent.getEventId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                participantList.clear();
                participantAdapter.notifyDataSetChanged();
            }
        });
    }

    private void loadParticipants(int eventId) {
        participantList.clear();
        //participantList.addAll(eventDatabaseOperations.getParticipantsForEvent(eventId));
        //participantAdapter.notifyDataSetChanged();
    }
}