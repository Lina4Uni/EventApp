// File: app/src/main/java/com/example/eventplusapp/java/ReminderAdapter.java
package com.example.eventplusapp.java;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplusapp.R;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private final List<Reminder> reminderList;
    private final Context context;

    public ReminderAdapter(Context context, List<Reminder> reminderList) {
        this.context = context;
        this.reminderList = reminderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
        EventDatabaseOperations eventDatabaseOperations = new EventDatabaseOperations(context);
        String eventName = eventDatabaseOperations.getEventNameById(reminder.getEventId());

        if ("EventRequest".equals(reminder.getType())) {
            holder.reminderMessageTextView.setText("Einladung zum " + eventName + " Event");
        } else {
            holder.reminderMessageTextView.setText(reminder.getMessage());
        }

        holder.acceptButton.setOnClickListener(v -> acceptReminder(reminder, position));
        holder.declineButton.setOnClickListener(v -> declineReminder(reminder, position));
    }

    private void acceptReminder(Reminder reminder, int position) {
        EventDatabaseOperations dbOperations = new EventDatabaseOperations(context);
        dbOperations.deleteReminder(reminder.getRequestedUserId(), reminder.getEventId());
        dbOperations.updateUserStatus(reminder.getRequestedUserId(), reminder.getEventId(), Status.ACCEPTED);

        reminderList.remove(position);
        notifyItemRemoved(position);
    }

    private void declineReminder(Reminder reminder, int position) {
        EventDatabaseOperations dbOperations = new EventDatabaseOperations(context);
        dbOperations.deleteReminder(reminder.getRequestedUserId(), reminder.getEventId());
        dbOperations.updateUserStatus(reminder.getRequestedUserId(), reminder.getEventId(), Status.DECLINE);

        reminderList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView reminderMessageTextView;
        public Button acceptButton;
        public Button declineButton;

        public ViewHolder(View itemView) {
            super(itemView);
            reminderMessageTextView = itemView.findViewById(R.id.reminder_message);
            acceptButton = itemView.findViewById(R.id.accept_button);
            declineButton = itemView.findViewById(R.id.decline_button);
        }
    }
}