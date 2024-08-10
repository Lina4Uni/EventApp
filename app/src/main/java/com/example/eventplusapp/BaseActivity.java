// File: app/src/main/java/com/example/eventplusapp/BaseActivity.java
package com.example.eventplusapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
    }

    protected void setupNavigation(Toolbar toolbar) {
        drawer = findViewById(R.id.drawer_layout);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return handleNavigationItemSelected(item) || super.onOptionsItemSelected(item);
    }
}