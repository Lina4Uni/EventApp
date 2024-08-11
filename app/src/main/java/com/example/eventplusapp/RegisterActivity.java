// File: app/src/main/java/com/example/eventplusapp/RegisterActivity.java
package com.example.eventplusapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventplusapp.java.User;
import com.example.eventplusapp.java.UserDatabaseOperations;

public class RegisterActivity extends AppCompatActivity {

    private UserDatabaseOperations userDatabaseOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userDatabaseOperations = new UserDatabaseOperations(this);

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

                User currUser = userDatabaseOperations.insertUser(firstName, lastName, email, password);

                MainActivity.loggedUser = currUser;
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);
                startActivity(intent);
                finish();
            }
        });
    }
}