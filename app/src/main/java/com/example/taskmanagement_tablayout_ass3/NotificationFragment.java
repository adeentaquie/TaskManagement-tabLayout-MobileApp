package com.example.taskmanagement_tablayout_ass3;

import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerNotifications;
    private NotificationAdapter adapter;
    private List<NotificationModel> notificationList = new ArrayList<>();
    private DatabaseHelper dbHelper;

    public NotificationFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerNotifications = view.findViewById(R.id.recycler_notifications);
        dbHelper = new DatabaseHelper(getContext());

        recyclerNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NotificationAdapter(notificationList);
        recyclerNotifications.setAdapter(adapter);

        // Insert dummy notifications if none exist
        insertDummyNotificationsIfNeeded();
        loadNotifications();

        return view;
    }

    private void insertDummyNotificationsIfNeeded() {
        Cursor cursor = dbHelper.getAllNotifications();
        if (cursor.getCount() == 0) {
            dbHelper.addNotification("Meeting at 5 PM", "2025-04-30 17:00");
            dbHelper.addNotification("Assignment Deadline Tomorrow", "2025-05-01 23:59");
            dbHelper.addNotification("Team Lunch Scheduled", "2025-05-02 13:00");
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    private void loadNotifications() {
        notificationList.clear();
        Cursor cursor = dbHelper.getAllNotifications();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String message = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOTIFICATION_MESSAGE));
                String datetime = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOTIFICATION_DATETIME));

                notificationList.add(new NotificationModel(message, datetime));

            } while (cursor.moveToNext());

            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }
}
