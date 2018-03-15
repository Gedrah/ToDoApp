package eu.epitech.todolist.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import eu.epitech.todolist.Activities.TaskManagerActivity;
import eu.epitech.todolist.POJO.Task;
import eu.epitech.todolist.R;

import java.util.Objects;


public class ViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        TaskManagerActivity activity = (TaskManagerActivity) getActivity();
        Task currentTask = activity.getCurrentTask();

        setTaskOnView(R.id.title, view, currentTask.getTitle());
        setTaskOnView(R.id.description, view, currentTask.getDescription());
        setTaskOnView(R.id.day, view, currentTask.getDate());
        setTaskOnView(R.id.time, view, currentTask.getTime());

        Log.e("Data", currentTask.getCategory());

        String[] array = getResources().getStringArray(R.array.Category);
        setTaskOnView(R.id.spinner_category, view, array[Integer.parseInt(currentTask.getCategory())]);

        array = getResources().getStringArray(R.array.State);
        setTaskOnView(R.id.spinner_status, view, array[Integer.parseInt(currentTask.getState())]);

        setTaskImage(R.id.image, view, currentTask.getCategory());
        return view;
    }

    private void setTaskOnView(int resId, View view, String data)
    {
        TextView textView = (TextView) view.findViewById(resId);
        textView.setText(data);
    }

    private void setTaskImage(int resId, View view, String data)
    {
        ImageView category = (ImageView) view.findViewById(resId);
        if (Objects.equals("1", data)) {
            category.setImageResource(R.drawable.work);
        } else if (Objects.equals("2", data)) {
            category.setImageResource(R.drawable.home);
        } else {
            category.setImageResource(R.drawable.alarm);
        }
    }


}
