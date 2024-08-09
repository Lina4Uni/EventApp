// File: app/src/main/java/com/example/eventplusapp/LoginActivity.java
package com.example.eventplusapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private com.example.eventplusapp.java.DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new com.example.eventplusapp.java.DatabaseHelper(this);

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEditText = findViewById(R.id.email);
                EditText passwordEditText = findViewById(R.id.password);

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (databaseHelper.checkUser(email, password)) {
                    Toast.makeText(LoginActivity.this, "Du bist angemeldet", Toast.LENGTH_SHORT).show();
                    // Proceed to the next activity or main screen
                } else {
                    Toast.makeText(LoginActivity.this, "Fehler: Ungültige Anmeldedaten", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}