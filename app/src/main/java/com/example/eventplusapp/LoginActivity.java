// File: app/src/main/java/com/example/eventplusapp/LoginActivity.java
package com.example.eventplusapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eventplusapp.user.User;
import com.example.eventplusapp.user.UserDatabaseOperations;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private UserDatabaseOperations userDatabaseOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userDatabaseOperations = new UserDatabaseOperations(this);

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEditText = findViewById(R.id.email);
                EditText passwordEditText = findViewById(R.id.password);

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                MessageDigest digest = null;
                try {
                    digest = MessageDigest.getInstance("SHA-256");
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

                User currUser = userDatabaseOperations.checkUser(email, new String(hash, StandardCharsets.UTF_8));
                if (currUser != null) {
                    MainActivity.loggedUser = currUser;
                    String firstName = currUser.getVorname();
                    String lastName = currUser.getNachname();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("firstName", firstName);
                    intent.putExtra("lastName", lastName);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Fehler: Ungültige Anmeldedaten", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}