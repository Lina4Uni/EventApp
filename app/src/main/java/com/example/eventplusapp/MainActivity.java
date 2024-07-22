package com.example.eventplusapp;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuItemType type = MenuItemType.fromId(item.getItemId());
        switch (type) {
            case EVENT_MANAGEMENT:
                // Logik für Eventverwaltung
                return true;
            case PARTICIPANT_MANAGEMENT:
                // Logik für Teilnehmerverwaltung
                return true;
            case SCHEDULE_CREATION:
                // Logik für Zeitplanerstellung
                return true;
            case INVITATIONS:
                // Logik für Einladungen
                return true;
            case REMINDERS:
                // Logik für Erinnerungen
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

// Schritt 1: Definieren Sie ein enum für die Menü-IDs
enum MenuItemType {
    EVENT_MANAGEMENT(R.id.menu_event_management),
    PARTICIPANT_MANAGEMENT(R.id.menu_participant_management),
    SCHEDULE_CREATION(R.id.menu_schedule_creation),
    INVITATIONS(R.id.menu_invitations),
    REMINDERS(R.id.menu_reminders),
    UNKNOWN(0); // Fallback-Wert

    private final int id;

    MenuItemType(int id) {
        this.id = id;
    }

    static MenuItemType fromId(int id) {
        for (MenuItemType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
