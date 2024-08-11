// File: app/src/main/java/com/example/eventplusapp/java/UserStatusAdapter.java
package com.example.eventplusapp.java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventplusapp.R;

import java.util.List;

public class UserStatusAdapter extends RecyclerView.Adapter<UserStatusAdapter.ViewHolder> {
    private final List<UserStatus> userStatusList;

    public UserStatusAdapter(List<UserStatus> userStatusList) {
        this.userStatusList = userStatusList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_status, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserStatus userStatus = userStatusList.get(position);
        holder.emailTextView.setText(userStatus.getEmail());

        switch (userStatus.getStatus()) {
            case "ADD":
                holder.statusImageView.setImageResource(R.drawable.ic_status_add);
                break;
            case "ACCEPTED":
                holder.statusImageView.setImageResource(R.drawable.ic_status_accepted);
                break;
            case "DECLINE":
                holder.statusImageView.setImageResource(R.drawable.ic_status_rejected);
                break;
            case "PENDING":
                holder.statusImageView.setImageResource(R.drawable.ic_status_pending);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return userStatusList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView emailTextView;
        public ImageView statusImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            emailTextView = itemView.findViewById(R.id.text_view_email);
            statusImageView = itemView.findViewById(R.id.image_view_status);
        }
    }
}