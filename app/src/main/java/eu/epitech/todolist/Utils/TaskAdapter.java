package eu.epitech.todolist.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import eu.epitech.todolist.Activities.TaskManagerActivity;
import eu.epitech.todolist.POJO.Task;
import eu.epitech.todolist.R;

import java.util.*;

import static eu.epitech.todolist.Utils.Utils.compareDate;
import static eu.epitech.todolist.Utils.Utils.compareTime;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private Context context;

    public TaskAdapter(List<Task> tasks, Context ctx) {
        this.taskList = tasks;
        context = ctx;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, final int position) {
        holder.bind(taskList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskManagerActivity.class);
                intent.putExtra("fragment", "Edit");
                intent.putExtra("id", taskList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void updateList(List<Task> newList)
    {
        taskList = newList;
        notifyDataSetChanged();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView type;
        private TextView date;
        private TextView time;
        private ImageView category;
        private Spinner status;

        public TaskViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.Name);
            type = (TextView) view.findViewById(R.id.Description);
            date = (TextView) view.findViewById(R.id.Date);
            time = (TextView) view.findViewById(R.id.Time);
            category = (ImageView) view.findViewById(R.id.category);
            status = (Spinner) view.findViewById(R.id.doneButton);
        }

        public void bind(Task task)
        {
            name.setText(task.getTitle());
            type.setText(task.getDescription());
            handleDateTime(task);
            if (Objects.equals("1", task.getCategory())) {
                category.setImageResource(R.drawable.work);
            } else if (Objects.equals("2", task.getCategory())) {
                category.setImageResource(R.drawable.home);
            } else {
                category.setImageResource(R.drawable.alarm);
            }
            handleSpinner(task);

        }

        private void handleDateTime(Task task) {
            if (compareDate(task.getDate()))
            {
                if (compareTime(task.getTime())) {
                    time.setTextColor(Color.RED);
                } else {
                    time.setTextColor(Color.BLACK);
                }
                date.setTextColor(Color.RED);
            } else {
                date.setTextColor(Color.BLACK);
                time.setTextColor(Color.BLACK);
            }
            date.setText(task.getDate());
            time.setText(task.getTime());
        }





        private void handleSpinner(Task task) {
            String[] array = context.getResources().getStringArray(R.array.State);
            List<String> listState = new ArrayList<String>(Arrays.asList(array));
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                    android.R.layout.simple_spinner_item, listState);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            status.setAdapter(adapter);
            status.setSelection(Integer.parseInt(task.getState()));
            status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    setDesignForSpinner(((TextView) parent.getChildAt(0)), position, status);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        private void setDesignForSpinner(TextView text, int position, Spinner parent)
        {
            text.setTypeface(Typeface.DEFAULT_BOLD);
            if (position == 0) {
                text.setText(context.getResources().getString(R.string.state_todo));
                text.setTextColor(Color.WHITE);
                parent.setBackgroundResource(R.drawable.round_button);

            } else if (position == 1) {
                text.setText(context.getResources().getString(R.string.state_inprogress));
                text.setTextColor(Color.BLACK);
                parent.setBackgroundResource(R.drawable.round_button_orange);

            } else {
                text.setText(context.getResources().getString(R.string.state_done));
                parent.setBackgroundResource(R.drawable.round_button_green);
                text.setTextColor(Color.WHITE);
            }
        }
    }

}
