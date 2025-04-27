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

public class PastFragment extends Fragment {

    private RecyclerView recyclerPastTasks;
    private ScheduleTaskAdapter adapter;
    private List<Task> pastTaskList = new ArrayList<>();
    private DatabaseHelper dbHelper;

    public PastFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past, container, false);

        recyclerPastTasks = view.findViewById(R.id.recycler_past_tasks);
        dbHelper = new DatabaseHelper(getContext());

        recyclerPastTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ScheduleTaskAdapter(pastTaskList);
        recyclerPastTasks.setAdapter(adapter);

        loadPastTasks();

        return view;
    }

    private void loadPastTasks() {
        pastTaskList.clear();
        Cursor cursor = dbHelper.getPastTasks();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_DESC));
                String datetime = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_DATETIME));

                pastTaskList.add(new Task(title, description, datetime));

            } while (cursor.moveToNext());

            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }
}
