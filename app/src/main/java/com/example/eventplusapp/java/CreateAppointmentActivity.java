// File: app/src/main/java/com/example/eventplusapp/java/CreateAppointmentActivity.java
package com.example.eventplusapp.java;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eventplusapp.R;

public class CreateAppointmentActivity extends AppCompatActivity {

    private EditText descriptionEditText;
    private EditText dateEditText;
    private EditText timeEditText;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        descriptionEditText = findViewById(R.id.editText_description);
        dateEditText = findViewById(R.id.editText_date);
        timeEditText = findViewById(R.id.editText_time);
        createButton = findViewById(R.id.button_create);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = descriptionEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String time = timeEditText.getText().toString();

                if (description.isEmpty() || date.isEmpty() || time.isEmpty()) {
                    Toast.makeText(CreateAppointmentActivity.this, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT).show();
                } else {
                    // Save the appointment details to the database
                    // For now, just show a toast
                    Toast.makeText(CreateAppointmentActivity.this, "Termin erstellt: " + description, Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                }
            }
        });
    }
}