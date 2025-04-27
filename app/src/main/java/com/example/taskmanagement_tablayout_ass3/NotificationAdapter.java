package com.example.taskmanagement_tablayout_ass3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<NotificationModel> notificationList;

    public NotificationAdapter(List<NotificationModel> notificationList) {
        this.notificationList = notificationList;
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, datetimeText;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.notification_message);
            datetimeText = itemView.findViewById(R.id.notification_datetime);
        }
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationModel notification = notificationList.get(position);
        holder.messageText.setText(notification.getMessage());
        holder.datetimeText.setText(notification.getDatetime());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
