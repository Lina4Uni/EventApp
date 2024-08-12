// File: app/src/main/java/com/example/eventplusapp/java/AppointmentAdapter.java
package com.example.eventplusapp.java;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventplusapp.R;
import java.util.ArrayList;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private List<Appointment> appointments = new ArrayList<>();
    private List<String> eventList;
    private Spinner eventSpinner;

    public AppointmentAdapter(List<String> eventList, Spinner eventSpinner) {
        this.eventList = eventList;
        this.eventSpinner = eventSpinner;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.descriptionTextView.setText(appointment.getDescription());
        holder.dateTextView.setText(appointment.getDate());
        holder.timeTextView.setText(appointment.getTime());
        holder.editButton.setOnClickListener(v -> {
            // Retrieve the description, date, and time from the respective fields
            String description = holder.descriptionTextView.getText().toString();
            String date = holder.dateTextView.getText().toString();
            String time = holder.timeTextView.getText().toString();

            // Get the selected event ID from the spinner
            String selectedEventId = eventList.get(eventSpinner.getSelectedItemPosition());

            // Insert a new appointment into the database
            EventDatabaseOperations dbOperations = new EventDatabaseOperations(v.getContext());
            Appointment newAppointment = new Appointment(0, date, time, description, Integer.parseInt(selectedEventId));
            dbOperations.insertAppointment(newAppointment);

            // Navigate back to the AppointmentActivity
            Intent intent = new Intent(v.getContext(), AppointmentActivity.class);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        notifyDataSetChanged();
    }

    static class AppointmentViewHolder extends RecyclerView.ViewHolder {

        TextView descriptionTextView;
        TextView dateTextView;
        TextView timeTextView;
        Button editButton;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.text_description);
            dateTextView = itemView.findViewById(R.id.text_date);
            timeTextView = itemView.findViewById(R.id.text_time);
            editButton = itemView.findViewById(R.id.button_edit);
        }
    }
}