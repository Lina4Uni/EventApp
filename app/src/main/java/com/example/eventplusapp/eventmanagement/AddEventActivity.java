// File: app/src/main/java/com/example/eventplusapp/java/AddEventActivity.java
package com.example.eventplusapp.eventmanagement;

import static com.example.eventplusapp.eventmanagement.EventManagementActivity.eventAdapter;
import static com.example.eventplusapp.eventmanagement.EventManagementActivity.eventList;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventplusapp.MainActivity;
import com.example.eventplusapp.R;
import com.example.eventplusapp.db.EventDatabaseOperations;
import com.example.eventplusapp.user.User;

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
            User currUser = MainActivity.loggedUser;
            if (currUser != null)
            {
                Event event = new Event(eventId, name, description, date, location, new ArrayList<>(), MainActivity.loggedUser.getUserId());
                if (eventId == -1) {
                    eventDatabaseOperations.insertEvent(currUser, event);
                } else {
                    eventDatabaseOperations.updateEvent(event);
                }
                updateEventList();
                finish();
            }
            else
                Toast.makeText(AddEventActivity.this, "Funktion nur bei Anmeldung verfügbar", Toast.LENGTH_SHORT).show();
        });
    }
    private void updateEventList() {
        eventList.clear();
        eventList.addAll(eventDatabaseOperations.getAllEvents());
        eventAdapter.notifyDataSetChanged();
    }
}