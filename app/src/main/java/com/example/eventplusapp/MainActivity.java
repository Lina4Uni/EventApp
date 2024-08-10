// File: app/src/main/java/com/example/eventplusapp/MainActivity.java
package com.example.eventplusapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.eventplusapp.java.EventManagementActivity;
import com.example.eventplusapp.java.InvitationsActivity;
import com.example.eventplusapp.java.ParticipantManagementActivity;
import com.example.eventplusapp.java.RemindersActivity;
import com.example.eventplusapp.java.ScheduleCreationActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity {

    public static boolean isLoggedIn = false;
    public static String firstName = "John";
    public static String lastName = "Doe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        loginText.setOnClickListener(v -> setContentView(R.layout.activity_login));
        registerText.setOnClickListener(v -> setContentView(R.layout.activity_register));

        if (isLoggedIn) {
            headerText.setText("Hallo " + firstName + " " + lastName);
            loginText.setVisibility(View.GONE);
            registerText.setVisibility(View.GONE);
            logoutText.setVisibility(View.VISIBLE);
        } else {
            headerText.setText("Kein Profil");
            logoutText.setVisibility(View.GONE);
        }

        logoutText.setOnClickListener(v -> {
            // Handle logout action
            isLoggedIn = false;
            headerText.setText("Kein Profil");
            loginText.setVisibility(View.VISIBLE);
            registerText.setVisibility(View.VISIBLE);
            logoutText.setVisibility(View.GONE);
        });
    }

    private void underlineTextView(TextView textView) {
        SpannableString content = new SpannableString(textView.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(content);
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