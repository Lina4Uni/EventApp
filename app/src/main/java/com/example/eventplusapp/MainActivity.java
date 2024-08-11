// File: app/src/main/java/com/example/eventplusapp/MainActivity.java
package com.example.eventplusapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.eventplusapp.java.DatabaseHelper;
import com.example.eventplusapp.java.EventManagementActivity;
import com.example.eventplusapp.java.InvitationsActivity;
import com.example.eventplusapp.java.ParticipantManagementActivity;
import com.example.eventplusapp.java.RemindersActivity;
import com.example.eventplusapp.java.ScheduleCreationActivity;
import com.example.eventplusapp.java.User;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity {

    public static User loggedUser;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.main_activity);
        super.onCreate(savedInstanceState);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.d("MainActivity", "Database created/opened");
    }

    public void onTileClick(View view) {
        String tag = (String) view.getTag();
        int layoutId = R.layout.main_activity; // Default layout
        Intent intent;
        switch (tag) {
            case "menu_event_management":
                intent = new Intent(MainActivity.this, EventManagementActivity.class);
                startActivity(intent);
                break;
            case "menu_participant_management":
                intent = new Intent(MainActivity.this, ParticipantManagementActivity.class);
                startActivity(intent);
                break;
            case "menu_schedule_creation":
                intent = new Intent(MainActivity.this, ScheduleCreationActivity.class);
                startActivity(intent);
                break;
            case "menu_invitations":
                intent = new Intent(MainActivity.this, InvitationsActivity.class);
                startActivity(intent);
                break;
            case "menu_reminders":
                intent = new Intent(MainActivity.this, RemindersActivity.class);
                startActivity(intent);
                break;
        }
    }
}