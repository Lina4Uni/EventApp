// File: app/src/main/java/com/example/eventplusapp/java/AppointmentActivity.java
package com.example.eventplusapp.java;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventplusapp.BaseActivity;
import com.example.eventplusapp.MainActivity;
import com.example.eventplusapp.R;
import java.util.List;

public class AppointmentActivity extends BaseActivity {

    private Spinner eventSpinner;
    private List<String> eventList;
    private RecyclerView recyclerView;
    private AppointmentAdapter appointmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_appointment_creation);
        super.onCreate(savedInstanceState);

        eventSpinner = findViewById(R.id.spinner_events);
        recyclerView = findViewById(R.id.recycler_view_appointments);

        if (MainActivity.loggedUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            // Redirect to login screen or handle the error
            finish();
            return;
        }

        // Initialize eventList here
        eventList = getEventList(); // Ensure this method returns a non-null list

        if (eventList != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            eventSpinner.setAdapter(adapter);
        } else {
            // Handle the case where eventList is null
            // For example, show a Toast or log an error
        }

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String selectedEventId = eventList.get(position);
                //loadAppointments(selectedEventId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


        // Initialize the adapter with eventList and eventSpinner
        appointmentAdapter = new AppointmentAdapter(eventList, eventSpinner);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(appointmentAdapter);
    }

    private List<String> getEventList() {
        // Fetch events where user_id matches the loggedUser
        EventDatabaseOperations dbOperations = new EventDatabaseOperations(this);
        return dbOperations.getEventsByUserId(MainActivity.loggedUser.getUserId());
    }
}