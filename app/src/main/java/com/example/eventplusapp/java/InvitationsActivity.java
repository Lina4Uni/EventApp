// File: app/src/main/java/com/example/eventplusapp/java/InvitationsActivity.java
package com.example.eventplusapp.java;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplusapp.BaseActivity;
import com.example.eventplusapp.MainActivity;
import com.example.eventplusapp.R;

import java.util.List;

public class InvitationsActivity extends BaseActivity {
    private Spinner eventSpinner;
    private RecyclerView userRecyclerView;
    private UserStatusAdapter adapter;
    private List<UserStatus> userStatusList;
    private List<String> userEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_invitations);
        super.onCreate(savedInstanceState);

        eventSpinner = findViewById(R.id.spinner_events);
        userRecyclerView = findViewById(R.id.recycler_view_users);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadUserEvents();

        // Attach ItemTouchHelper to RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                UserStatus userStatus = userStatusList.get(position);

                if (userStatus.getUserId() == MainActivity.loggedUser.getUserId()) {
                    Toast.makeText(InvitationsActivity.this, "You cannot change your own status", Toast.LENGTH_SHORT).show();
                    adapter.notifyItemChanged(position);
                } else {
                    EventDatabaseOperations dbOperations = new EventDatabaseOperations(InvitationsActivity.this);
                    int selectedEventId = dbOperations.getEventIdByName((String) eventSpinner.getSelectedItem());

                    if (direction == ItemTouchHelper.RIGHT) {
                        userStatus.setStatus("ADD");
                        updateStatusInDatabase(userStatus.getUserId(), selectedEventId, Status.ADD.toString());
                    } else if (direction == ItemTouchHelper.LEFT) {
                        switch (userStatus.getStatus()) {
                            case "ADD":
                                userStatus.setStatus("PENDING");
                                updateStatusInDatabase(userStatus.getUserId(), selectedEventId, Status.PENDING.toString());
                                break;
                            case "PENDING":
                                Toast.makeText(InvitationsActivity.this, "bereits angefragt", Toast.LENGTH_SHORT).show();
                                break;
                            case "DECLINE":
                                Toast.makeText(InvitationsActivity.this, "bereits abgelehnt", Toast.LENGTH_SHORT).show();
                                break;
                            case "ACCEPTED":
                                Toast.makeText(InvitationsActivity.this, "nimmt bereits teil", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                    adapter.notifyItemChanged(position);
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(userRecyclerView);

        ItemTouchHelper itemTouchHelperRight = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                UserStatus userStatus = userStatusList.get(position);

                if (userStatus.getUserId() == MainActivity.loggedUser.getUserId()) {
                    Toast.makeText(InvitationsActivity.this, "You cannot change your own status", Toast.LENGTH_SHORT).show();
                    adapter.notifyItemChanged(position);
                } else {
                    userStatus.setStatus("ADD");
                    EventDatabaseOperations dbOperations = new EventDatabaseOperations(InvitationsActivity.this);
                    int selectedEventId = dbOperations.getEventIdByName((String) eventSpinner.getSelectedItem());
                    updateStatusInDatabase(userStatus.getUserId(), selectedEventId, "ADD");
                    adapter.notifyItemChanged(position);
                }
            }
        });
        itemTouchHelperRight.attachToRecyclerView(userRecyclerView);
    }

    private void loadUserEvents() {
        if (MainActivity.loggedUser == null) {
            Toast.makeText(this, "Bitte melden Sie sich an, um diese Funktion nutzen zu können.", Toast.LENGTH_SHORT).show();
            return;
        }

        userEvents = getUserEvents(MainActivity.loggedUser);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userEvents);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        eventSpinner.setAdapter(adapter);
        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedEvent = (String) parent.getItemAtPosition(position);
                loadUsersForEvent(selectedEvent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void loadUsersForEvent(String eventName) {
        EventDatabaseOperations dbOperations = new EventDatabaseOperations(this);
        int eventId = dbOperations.getEventIdByName(eventName);
        userStatusList = dbOperations.getUsersAndStatusesForEvent(eventId);

        adapter = new UserStatusAdapter(userStatusList);
        userRecyclerView.setAdapter(adapter);
    }

    private List<String> getUserEvents(User loggedUser) {
        return new EventDatabaseOperations(this).getEventsForUser(loggedUser);
    }

    private void updateStatusInDatabase(int userId, int eventId, String status) {
        EventDatabaseOperations dbOperations = new EventDatabaseOperations(this);
        dbOperations.updateUserStatus(userId, eventId, Status.ADD);
    }
}