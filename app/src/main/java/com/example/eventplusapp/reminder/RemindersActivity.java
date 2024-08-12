// File: app/src/main/java/com/example/eventplusapp/java/RemindersActivity.java
package com.example.eventplusapp.reminder;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventplusapp.BaseActivity;
import com.example.eventplusapp.MainActivity;
import com.example.eventplusapp.R;
import com.example.eventplusapp.db.EventDatabaseOperations;

import java.util.List;

public class RemindersActivity extends BaseActivity {
    private RecyclerView reminderRecyclerView;
    private ReminderAdapter reminderAdapter;
    private List<Reminder> reminderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_reminders);
        super.onCreate(savedInstanceState);

        if (MainActivity.loggedUser == null) {
            Toast.makeText(this, "Bitte melden Sie sich an, um diese Funktion nutzen zu können.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        reminderRecyclerView = findViewById(R.id.recycler_view_reminders);
        reminderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadRemindersForUser(MainActivity.loggedUser.getUserId());
    }

    private void loadRemindersForUser(int userId) {
        EventDatabaseOperations dbOperations = new EventDatabaseOperations(this);
        reminderList = dbOperations.getRemindersForUser(userId);

        reminderAdapter = new ReminderAdapter(this, reminderList);
        reminderRecyclerView.setAdapter(reminderAdapter);
    }
}