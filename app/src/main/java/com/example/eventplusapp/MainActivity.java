// File: app/src/main/java/com/example/eventplusapp/MainActivity.java
package com.example.eventplusapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private boolean isLoggedIn = false; // This should be replaced with actual login state
    private String firstName = "John"; // Replace with actual user data
    private String lastName = "Doe"; // Replace with actual user data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> onOptionsItemSelected(item));

        // Ensure the header view is properly inflated
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView headerText = headerView.findViewById(R.id.header_text);
        TextView loginText = headerView.findViewById(R.id.login_text);
        TextView registerText = headerView.findViewById(R.id.register_text);

        // Underline the text programmatically
        underlineTextView(loginText);
        underlineTextView(registerText);

        loginText.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });
        registerText.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        });

        if (isLoggedIn) {
            headerText.setText(firstName + " " + lastName);
        } else {
            headerText.setText("Kein Profil");
        }
    }

    private void underlineTextView(TextView textView) {
        SpannableString content = new SpannableString(textView.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(content);
    }

    public void onTileClick(View view) {
        String tag = (String) view.getTag();
        int layoutId = R.layout.main_activity; // Default layout

        switch (tag) {
            case "menu_event_management":
                layoutId = R.layout.activity_event_management;
                break;
            case "menu_participant_management":
                layoutId = R.layout.activity_participant_management;
                break;
            case "menu_schedule_creation":
                layoutId = R.layout.activity_schedule_creation;
                break;
            case "menu_invitations":
                layoutId = R.layout.activity_invitations;
                break;
            case "menu_reminders":
                layoutId = R.layout.activity_reminders;
                break;
        }

        changeView(layoutId);
    }

    public boolean changeView(int view) {
        setContentView(view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> onOptionsItemSelected(item));

        // Inflate the header view
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView headerText = headerView.findViewById(R.id.header_text);
        TextView loginText = headerView.findViewById(R.id.login_text);
        TextView registerText = headerView.findViewById(R.id.register_text);
        // Underline the text programmatically
        underlineTextView(loginText);
        underlineTextView(registerText);

        loginText.setOnClickListener(v -> {
            setContentView(R.layout.activity_login);
        });
        registerText.setOnClickListener(v -> setContentView(R.layout.activity_register));

        if (isLoggedIn) {
            headerText.setText(firstName + " " + lastName);
        } else {
            headerText.setText("Kein Profil");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean changeView = false;
        if (id == R.id.menu_event_home) {
            int view = R.layout.main_activity;
            changeView(view);
            changeView = true;
        } else if (id == R.id.menu_event_management) {
            int view = R.layout.activity_event_management;
            changeView(view);
            changeView = true;
        } else if (id == R.id.menu_participant_management) {
            changeView(R.layout.activity_participant_management);
            changeView = true;
        } else if (id == R.id.menu_schedule_creation) {
            changeView(R.layout.activity_schedule_creation);
            changeView = true;
        } else if (id == R.id.menu_invitations) {
            changeView(R.layout.activity_invitations);
            changeView = true;
        } else if (id == R.id.menu_reminders) {
            changeView(R.layout.activity_reminders);
            changeView = true;
        }
        if (changeView) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}