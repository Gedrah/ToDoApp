package eu.epitech.todolist.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import eu.epitech.todolist.Activities.TaskManagerActivity;
import eu.epitech.todolist.R;

import java.util.*;

import static eu.epitech.todolist.Utils.Utils.fixedHoursTime;

public class CreateTaskFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_create_task, container, false);

        final EditText editDate = (EditText) view.findViewById(R.id.day);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        int date = monthOfYear + 1;
                        String dateString = dayOfMonth + "/" + date + "/" + year;
                        editDate.setText(dateString);
                    }
                };

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog d = new DatePickerDialog(getContext(),
                        dpd, mYear ,mMonth, mDay);
                d.show();

            }
        });

        final EditText editTime = (EditText) view.findViewById(R.id.time);
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ":" + minute;
                        editTime.setText(fixedHoursTime(time));
                    }
                };

                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog t = new TimePickerDialog(getContext(),
                        timeSetListener, hour, minute, true);
                t.show();

            }
        });

        Button valid = (Button) view.findViewById(R.id.valider);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskManagerActivity activity = (TaskManagerActivity) getActivity();
                activity.addTaskManagement(activity.getTaskFromView(view, true));
            }
        });

        final ImageView category = (ImageView)view.findViewById(R.id.image);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_category);
        String[] array = getResources().getStringArray(R.array.Category);
        List<String> list = new ArrayList<String>(Arrays.asList(array));
        list.remove(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    category.setImageResource(R.drawable.work);
                } else if (position == 1) {
                    category.setImageResource(R.drawable.home);
                } else {
                    category.setImageResource(R.drawable.alarm);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
}
