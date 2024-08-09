// File: app/src/main/java/com/example/eventplusapp/RegisterActivity.java
package com.example.eventplusapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private com.example.eventplusapp.java.DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new com.example.eventplusapp.java.DatabaseHelper(this);

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText firstNameEditText = findViewById(R.id.first_name);
                EditText lastNameEditText = findViewById(R.id.last_name);
                EditText emailEditText = findViewById(R.id.email);
                EditText passwordEditText = findViewById(R.id.password);

                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                databaseHelper.insertUser(firstName, lastName, email, password);
                Toast.makeText(RegisterActivity.this, "Du bist registriert", Toast.LENGTH_SHORT).show();
            }
        });
    }
}