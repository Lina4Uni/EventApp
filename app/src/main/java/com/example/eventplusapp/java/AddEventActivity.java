// File: app/src/main/java/com/example/eventplusapp/java/AddEventActivity.java
package com.example.eventplusapp.java;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.eventplusapp.R;

import java.util.ArrayList;

public class AddEventActivity extends AppCompatActivity {

    private EditText editTextName, editTextDescription, editTextDate, editTextLocation;
    private Button buttonCreate;
    private EventDatabaseOperations eventDatabaseOperations;

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

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String description = editTextDescription.getText().toString();
                String date = editTextDate.getText().toString();
                String location = editTextLocation.getText().toString();

                if (name.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
                    Toast.makeText(AddEventActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Event event = new Event(0, name, description, date, location, new ArrayList<>());
                    eventDatabaseOperations.insertEvent(event);
                    Toast.makeText(AddEventActivity.this, "Event created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddEventActivity.this, EventManagementActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}