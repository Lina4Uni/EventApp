// File: app/src/main/java/com/example/eventplusapp/java/EventManagementActivity.java
package com.example.eventplusapp.java;

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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplusapp.MainActivity;
import com.example.eventplusapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class EventManagementActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private EventDatabaseOperations eventDatabaseOperations;
    private FloatingActionButton fabAddEvent;
    private TextView textViewNoEvents;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_management);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> onOptionsItemSelected(item));

        // Inflate the header view
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

        loginText.setOnClickListener(v -> setContentView(R.layout.activity_login));
        registerText.setOnClickListener(v -> setContentView(R.layout.activity_register));

        if (MainActivity.isLoggedIn) {
            headerText.setText("Hallo " + MainActivity.firstName + " " + MainActivity.lastName);
            loginText.setVisibility(View.GONE);
            registerText.setVisibility(View.GONE);
            logoutText.setVisibility(View.VISIBLE);
        } else {
            headerText.setText("Kein Profil");
            logoutText.setVisibility(View.GONE);
        }

        logoutText.setOnClickListener(v -> {
            // Handle logout action
            MainActivity.isLoggedIn = false;
            headerText.setText("Kein Profil");
            loginText.setVisibility(View.VISIBLE);
            registerText.setVisibility(View.VISIBLE);
            logoutText.setVisibility(View.GONE);
        });

        recyclerView = findViewById(R.id.recycler_view_events);
        fabAddEvent = findViewById(R.id.fab_add_event);
        textViewNoEvents = findViewById(R.id.text_view_no_events);

        eventDatabaseOperations = new EventDatabaseOperations(this);
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(eventList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(eventAdapter);

        loadEvents();

        fabAddEvent.setOnClickListener(v -> {
            Intent intent = new Intent(EventManagementActivity.this, AddEventActivity.class);
            startActivity(intent);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Event event = eventList.get(position);
                eventDatabaseOperations.deleteEvent(event.getEventId());
                eventList.remove(position);
                eventAdapter.notifyItemRemoved(position);
                Toast.makeText(EventManagementActivity.this, "Event deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void loadEvents() {
        eventList.clear();
        eventList.addAll(eventDatabaseOperations.getAllEvents());
        eventAdapter.notifyDataSetChanged();

        if (eventList.isEmpty()) {
            textViewNoEvents.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            textViewNoEvents.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void underlineTextView(TextView textView) {
        SpannableString content = new SpannableString(textView.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_event_home) {
            Intent intent = new Intent(EventManagementActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}