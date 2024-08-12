// File: app/src/main/java/com/example/eventplusapp/java/EventManagementActivity.java
package com.example.eventplusapp.eventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventplusapp.BaseActivity;
import com.example.eventplusapp.R;
import com.example.eventplusapp.db.EventDatabaseOperations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class EventManagementActivity extends BaseActivity {

    private RecyclerView recyclerView;
    public static EventAdapter eventAdapter;
    public static List<Event> eventList;
    private EventDatabaseOperations eventDatabaseOperations;
    private FloatingActionButton fabAddEvent;
    private TextView textViewNoEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_event_management);
        super.onCreate(savedInstanceState);


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

        eventAdapter.setOnItemClickListener(event -> {
            Intent intent = new Intent(EventManagementActivity.this, AddEventActivity.class);
            intent.putExtra("eventId", event.getEventId());
            intent.putExtra("eventName", event.getEventName());
            intent.putExtra("eventDescription", event.getDescription());
            intent.putExtra("eventDate", event.getDate());
            intent.putExtra("eventLocation", event.getLocation());
            startActivity(intent);
        });
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
}