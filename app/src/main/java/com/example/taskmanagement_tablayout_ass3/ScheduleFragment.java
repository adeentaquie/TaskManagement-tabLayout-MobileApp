package com.example.taskmanagement_tablayout_ass3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleFragment extends Fragment {

    private EditText editTitle, editDescription;
    private Button btnPickDateTime, btnAddTask;
    private RecyclerView recyclerTasks;
    private ScheduleTaskAdapter adapter;
    private List<Task> taskList = new ArrayList<>();

    private String selectedDateTime = "";
    private DatabaseHelper dbHelper;

    public ScheduleFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        // Initialize views
        editTitle = view.findViewById(R.id.edit_title);
        editDescription = view.findViewById(R.id.edit_description);
        btnPickDateTime = view.findViewById(R.id.btn_pick_datetime);
        btnAddTask = view.findViewById(R.id.btn_add_task);
        recyclerTasks = view.findViewById(R.id.recycler_tasks);

        dbHelper = new DatabaseHelper(getContext());

        recyclerTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ScheduleTaskAdapter(taskList);
        recyclerTasks.setAdapter(adapter);

        loadUpcomingTasks(); // load existing tasks

        btnPickDateTime.setOnClickListener(v -> openDateTimePicker());
        btnAddTask.setOnClickListener(v -> addTask());

        return view;
    }

    private void openDateTimePicker() {
        Calendar calendar = Calendar.getInstance();

        // First open Date Picker
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (DatePicker view, int year, int month, int dayOfMonth) -> {

                    // After selecting date, open Time Picker
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                            (TimePicker view1, int hourOfDay, int minute) -> {

                                // Format selected date and time
                                selectedDateTime = String.format("%04d-%02d-%02d %02d:%02d",
                                        year, month + 1, dayOfMonth, hourOfDay, minute);

                                // Also set the text on TextView
                                TextView textSelectedDateTime = getView().findViewById(R.id.text_selected_datetime);
                                textSelectedDateTime.setText("Selected: " + selectedDateTime);

                            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

                    timePickerDialog.show();

                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }


    private void addTask() {
        String title = editTitle.getText().toString().trim();
        String description = editDescription.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description) || TextUtils.isEmpty(selectedDateTime)) {
            Toast.makeText(getContext(), "Please fill all fields and pick Date & Time", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.addTask(title, description, selectedDateTime, "upcoming");

        if (result != -1) {
            Toast.makeText(getContext(), "Task Added Successfully", Toast.LENGTH_SHORT).show();
            editTitle.setText("");
            editDescription.setText("");
            selectedDateTime = "";
            loadUpcomingTasks(); // Refresh list
        } else {
            Toast.makeText(getContext(), "Failed to Add Task", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadUpcomingTasks() {
        taskList.clear();
        Cursor cursor = dbHelper.getUpcomingTasks();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_DESC));
                String datetime = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TASK_DATETIME));

                taskList.add(new Task(title, description, datetime));

            } while (cursor.moveToNext());

            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }
}
