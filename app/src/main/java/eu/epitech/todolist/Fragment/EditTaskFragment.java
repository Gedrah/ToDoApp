package eu.epitech.todolist.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.widget.*;
import eu.epitech.todolist.Activities.TaskManagerActivity;
import eu.epitech.todolist.POJO.Task;
import eu.epitech.todolist.R;

import java.util.*;

import static eu.epitech.todolist.Utils.Utils.fixedHoursTime;


public class EditTaskFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_edit_task, container, false);
        TaskManagerActivity activity = (TaskManagerActivity) getActivity();
        Task currentTask = activity.getCurrentTask();

        setTaskOnView(R.id.title, view, currentTask.getTitle());
        setTaskOnView(R.id.description, view, currentTask.getDescription());
        setTaskOnView(R.id.day, view, currentTask.getDate());
        setTaskOnView(R.id.time, view, currentTask.getTime());

        String[] array = getResources().getStringArray(R.array.Category);
        List<String> list = new ArrayList<String>(Arrays.asList(array));
        list.remove(0);

        setTaskSpinner(R.id.spinner_category, view, Integer.parseInt(currentTask.getCategory()) - 1
                , list);

        array = getResources().getStringArray(R.array.State);
        List<String> listState = new ArrayList<String>(Arrays.asList(array));

        setTaskSpinner(R.id.spinner_status, view, Integer.parseInt(currentTask.getState()), listState);

        setTaskImage(R.id.image, view, currentTask.getCategory());

        Button valid = (Button) view.findViewById(R.id.valider);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskManagerActivity activity = (TaskManagerActivity) getActivity();
                activity.editTaskManagement(activity.getTaskFromView(view, false));
            }
        });

        Button delete = (Button) view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle(getResources().getString(R.string.delete));
                alertDialog.setMessage(getResources().getString(R.string.delete_message));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.accept_message),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                TaskManagerActivity activity = (TaskManagerActivity) getActivity();
                                activity.deleteTaskManagement();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.cancel_message),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_category);
        final ImageView category = (ImageView) view.findViewById(R.id.image);

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

    private void setTaskSpinner(int resId, View view, int position, List<String> list)
    {
        Spinner spinner = (Spinner) view.findViewById(resId);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(position);
    }

}
