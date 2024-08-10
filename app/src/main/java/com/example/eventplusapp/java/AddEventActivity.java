// File: app/src/main/java/com/example/eventplusapp/java/AddEventActivity.java
package com.example.eventplusapp.java;

import static com.example.eventplusapp.java.EventManagementActivity.eventAdapter;
import static com.example.eventplusapp.java.EventManagementActivity.eventList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eventplusapp.R;

import java.util.ArrayList;

public class AddEventActivity extends AppCompatActivity {

    private EditText editTextName, editTextDescription, editTextDate, editTextLocation;
    private Button buttonCreate;
    private EventDatabaseOperations eventDatabaseOperations;
    private int eventId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        editTextName = findViewById(R.id.edit_text_name);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextDate = findViewById(R.id.edit_text_date);
        editTextLocation = findViewById(R.id.edit_text_location);
        buttonCreate = findViewById(R.id.button_create);
        eventDatabaseOperations = new EventDatabaseOperations(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("eventId")) {
            eventId = intent.getIntExtra("eventId", -1);
            editTextName.setText(intent.getStringExtra("eventName"));
            editTextDescription.setText(intent.getStringExtra("eventDescription"));
            editTextDate.setText(intent.getStringExtra("eventDate"));
            editTextLocation.setText(intent.getStringExtra("eventLocation"));
            buttonCreate.setText("Speichern");
        }

        buttonCreate.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String description = editTextDescription.getText().toString();
            String date = editTextDate.getText().toString();
            String location = editTextLocation.getText().toString();

            Event event = new Event(eventId, name, description, date, location, new ArrayList<>());
            if (eventId == -1) {
                eventDatabaseOperations.insertEvent(event);
            } else {
                eventDatabaseOperations.updateEvent(event);
            }
            updateEventList();
            finish();
        });
    }
    private void updateEventList() {
        eventList.clear();
        eventList.addAll(eventDatabaseOperations.getAllEvents());
        eventAdapter.notifyDataSetChanged();
    }
}