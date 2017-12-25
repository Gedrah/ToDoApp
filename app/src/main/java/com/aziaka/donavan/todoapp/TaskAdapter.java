package com.aziaka.donavan.todoapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;

    public TaskAdapter(List<Task> tasks) {
        this.taskList = tasks;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item, parent, false);
        Log.d("NBR", String.valueOf(taskList.size()));

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        holder.bind(taskList.get(position));
    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView date;
        private TextView type;
        private CheckBox done;

        public TaskViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.Name);
            date = (TextView) view.findViewById(R.id.Date);
            type = (TextView) view.findViewById(R.id.Type);
        }

        public void bind(Task task)
        {
            name.setText(task.getName());
            date.setText(task.getDate());
            type.setText(task.getCategory());
        }
    }

}
