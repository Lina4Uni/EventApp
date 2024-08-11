// File: app/src/main/java/com/example/eventplusapp/BaseActivity.java
package com.example.eventplusapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.eventplusapp.java.EventManagementActivity;
import com.example.eventplusapp.java.InvitationsActivity;
import com.example.eventplusapp.java.ParticipantManagementActivity;
import com.example.eventplusapp.java.RemindersActivity;
import com.example.eventplusapp.java.ScheduleCreationActivity;
import com.google.android.material.navigation.NavigationView;

public abstract class BaseActivity extends AppCompatActivity {

    protected DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout); // Initialize the drawer here
        setupNavigation(toolbar);

        // Inflate the header view
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        if (headerView == null) {
            headerView = navigationView.inflateHeaderView(R.layout.nav_header);
        }
        TextView headerText = headerView.findViewById(R.id.header_text);
        TextView loginText = headerView.findViewById(R.id.login_text);
        TextView registerText = headerView.findViewById(R.id.register_text);
        TextView logoutText = headerView.findViewById(R.id.logout_text);
        // Underline the text programmatically
        underlineTextView(loginText);
        underlineTextView(registerText);
        underlineTextView(logoutText);
        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        registerText.setOnClickListener(v -> {
            Intent intent = new Intent(BaseActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        if (MainActivity.loggedUser != null) {
            headerText.setText("Hallo " + MainActivity.loggedUser.getVorname() + " " + MainActivity.loggedUser.getNachname());
            loginText.setVisibility(View.GONE);
            registerText.setVisibility(View.GONE);
            logoutText.setVisibility(View.VISIBLE);
        } else {
            headerText.setText("Kein Profil");
            logoutText.setVisibility(View.GONE);
        }
        logoutText.setOnClickListener(v -> {
            // Handle logout action
            MainActivity.loggedUser = null;
            headerText.setText("Kein Profil");
            loginText.setVisibility(View.VISIBLE);
            registerText.setVisibility(View.VISIBLE);
            logoutText.setVisibility(View.GONE);
        });
    }

    protected void setupNavigation(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::handleNavigationItemSelected);
    }

    private boolean handleNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_event_home) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        } else if (id == R.id.menu_event_management) {
            startActivity(new Intent(this, EventManagementActivity.class));
            finish();
            return true;
        } else if (id == R.id.menu_participant_management) {
            startActivity(new Intent(this, ParticipantManagementActivity.class));
            finish();
            return true;
        } else if (id == R.id.menu_schedule_creation) {
            startActivity(new Intent(this, ScheduleCreationActivity.class));
            finish();
            return true;
        } else if (id == R.id.menu_invitations) {
            startActivity(new Intent(this, InvitationsActivity.class));
            finish();
            return true;
        } else if (id == R.id.menu_reminders) {
            startActivity(new Intent(this, RemindersActivity.class));
            finish();
            return true;
        }
        return false;
    }

    public static void underlineTextView(TextView textView) {
        SpannableString content = new SpannableString(textView.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return handleNavigationItemSelected(item) || super.onOptionsItemSelected(item);
    }
}