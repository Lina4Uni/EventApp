// File: app/src/main/java/com/example/eventplusapp/RegisterActivity.java
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
                EditText confirmPasswordEditText = findViewById(R.id.confirm_password);

                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordConfirm = confirmPasswordEditText.getText().toString();
                if (!password.equals(passwordConfirm))
                {
                    Toast.makeText(RegisterActivity.this, "Passwörter stimmen nicht überein", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
                    MessageDigest digest = null;
                    try {
                        digest = MessageDigest.getInstance("SHA-256");
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                    byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

                    User currUser = userDatabaseOperations.insertUser(firstName, lastName, email, new String(hash, StandardCharsets.UTF_8));

                    MainActivity.loggedUser = currUser;
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.putExtra("firstName", firstName);
                    intent.putExtra("lastName", lastName);
                    startActivity(intent);
                    finish();
                } else {
                    if (password.length() < 8) {
                        Toast.makeText(RegisterActivity.this, "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show();
                    } else if (!password.matches(".*\\d.*")) {
                        Toast.makeText(RegisterActivity.this, "Password must contain at least one digit", Toast.LENGTH_SHORT).show();
                    } else if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
                        Toast.makeText(RegisterActivity.this, "Password must contain at least one special character", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}