package com.example.taskmanagement_tablayout_ass3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ScheduleTaskAdapter extends RecyclerView.Adapter<ScheduleTaskAdapter.TaskViewHolder> {

    private List<Task> taskList;

    public ScheduleTaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, descText, datetimeText;

        public TaskViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.task_title);
            descText = itemView.findViewById(R.id.task_desc);
            datetimeText = itemView.findViewById(R.id.task_datetime);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.titleText.setText(task.getTitle());
        holder.descText.setText(task.getDescription());
        holder.datetimeText.setText(task.getDatetime());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
