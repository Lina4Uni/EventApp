// File: app/src/main/java/com/example/eventplusapp/java/AppointmentActivity.java
package com.example.eventplusapp.appointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventplusapp.BaseActivity;
import com.example.eventplusapp.MainActivity;
import com.example.eventplusapp.R;
import com.example.eventplusapp.db.EventDatabaseOperations;
import com.example.eventplusapp.eventmanagement.Event;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AppointmentActivity extends BaseActivity {

    private static final int REQUEST_CODE_CREATE_APPOINTMENT = 1;

    private Spinner eventSpinner;
    private List<Event> eventList;
    private RecyclerView recyclerView;
    private AppointmentAdapter appointmentAdapter;
    private FloatingActionButton fabAddAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_appointment_creation);
        super.onCreate(savedInstanceState);

        eventSpinner = findViewById(R.id.spinner_events);
        recyclerView = findViewById(R.id.recycler_view_appointments);
        fabAddAppointment = findViewById(R.id.fab_add_appointment);

        if (MainActivity.loggedUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            // Redirect to login screen or handle the error
            finish();
            return;
        }

        // Initialize eventList here
        eventList = getEventList(); // Ensure this method returns a non-null list

        if (eventList != null) {
            List<String> eventNames = new ArrayList<>();
            for (Event event : eventList) {
                eventNames.add(event.getEventName());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            eventSpinner.setAdapter(adapter);
        } else {
            // Handle the case where eventList is null
            // For example, show a Toast or log an error
        }

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedEventId = eventList.get(position).getEventId();
                loadAppointments(selectedEventId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Initialize the adapter with an empty list
        appointmentAdapter = new AppointmentAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(appointmentAdapter);

        // Set OnClickListener for FloatingActionButton
        fabAddAppointment.setOnClickListener(v -> {
            int selectedEventId = eventSpinner.getSelectedItemPosition();
            Intent intent = new Intent(AppointmentActivity.this, CreateAppointmentActivity.class);
            intent.putExtra("selectedEventId", selectedEventId);
            startActivityForResult(intent, REQUEST_CODE_CREATE_APPOINTMENT);
        });
    }

    private List<Event> getEventList() {
        // Fetch events where user_id matches the loggedUser
        EventDatabaseOperations dbOperations = new EventDatabaseOperations(this);
        return dbOperations.getEventsByUserId(MainActivity.loggedUser.getUserId());
    }

    private void loadAppointments(int eventId) {
        EventDatabaseOperations dbOperations = new EventDatabaseOperations(this);
        List<Appointment> appointments = dbOperations.getAppointmentsByEventId(eventId);
        appointmentAdapter.setAppointments(appointments);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CREATE_APPOINTMENT && resultCode == RESULT_OK) {
            int selectedEventId = eventSpinner.getSelectedItemPosition();
            loadAppointments(selectedEventId);
        }
    }
}