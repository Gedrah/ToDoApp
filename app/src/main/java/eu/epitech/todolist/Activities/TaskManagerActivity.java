package eu.epitech.todolist.Activities;

import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import eu.epitech.todolist.Fragment.CreateTaskFragment;
import eu.epitech.todolist.Fragment.EditTaskFragment;
import eu.epitech.todolist.Fragment.ViewFragment;
import eu.epitech.todolist.POJO.Task;
import eu.epitech.todolist.R;
import eu.epitech.todolist.Utils.TaskContract;
import eu.epitech.todolist.Utils.TaskDbHelper;

import java.util.*;

public class TaskManagerActivity extends AppCompatActivity {

    private Task currentTask;
    private TaskDbHelper mHelper;
    private Map<Integer, String> errorMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        errorMessages = fillErrorMessageMap();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_manager);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mHelper = new TaskDbHelper(this);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        manageFragment(getIntent().getIntExtra("id", 0), getIntent().getStringExtra("fragment"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void manageFragment(int id, String fragment) {
        if (Objects.equals(fragment, "Create")) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new CreateTaskFragment()).commit();
        } else {
            currentTask = getTaskById(id);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ViewFragment()).commit();
        }
    }

    public void loadEditFrag(View view) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditTaskFragment()).commit();
    }

    public Task getCurrentTask()
    {
        return currentTask;
    }

    public Task getTaskById(int id)
    {
        Task task = new Task();

        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID,
                        TaskContract.TaskEntry.COL_TASK_TITLE,
                        TaskContract.TaskEntry.COL_TASK_DECS,
                        TaskContract.TaskEntry.COL_TASK_DATE,
                        TaskContract.TaskEntry.COL_TASK_TIME,
                        TaskContract.TaskEntry.COL_TASK_CATEGORY,
                        TaskContract.TaskEntry.COL_TASK_STATUS},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry._ID);
            Log.e("ID", Integer.toBinaryString(id) + " and " + cursor.getInt(idx));
            if (id == cursor.getInt(idx))
            {
                idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
                task.setTitle(cursor.getString(idx));
                TextView title = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.addTask_title);
                title.setText(task.getTitle());

                idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DECS);
                task.setDescription(cursor.getString(idx));

                idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_DATE);
                task.setDate(cursor.getString(idx));

                idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TIME);
                task.setTime(cursor.getString(idx));

                idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_CATEGORY);
                task.setCategory(cursor.getString(idx));

                idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_STATUS);
                task.setState(cursor.getString(idx));

                idx = cursor.getColumnIndex(TaskContract.TaskEntry._ID);
                task.setId(cursor.getInt(idx));
            }

        }
        cursor.close();
        db.close();
        return task;
    }

    public void deleteTaskManagement() {
        deleteTask(currentTask);
        onBackPressed();
    }

    private void deleteTask(Task task)
    {
        String Id = Integer.toString(task.getId());

        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry._ID + " = ?",
                new String[]{Id});
        db.close();
    }

    public void editTaskManagement(Task task) {
        mHelper = new TaskDbHelper(this);

        if (checkTaskValidity(task) == 0)
        {
            Log.e("task", "delete");
            deleteTask(currentTask);
            ContentValues values = new ContentValues();
            addValue(values, task);
            SQLiteDatabase db = mHelper.getWritableDatabase();
            db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);
            db.close();
            Log.e("task", "add");
            onBackPressed();
        } else {
            Snackbar.make(findViewById(android.R.id.content),
                    errorMessages.get(checkTaskValidity(task)), Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    public void addTaskManagement(Task task) {
        mHelper = new TaskDbHelper(this);

        if (checkTaskValidity(task) == 0)
        {
            ContentValues values = new ContentValues();
            addValue(values, task);
            SQLiteDatabase db = mHelper.getWritableDatabase();
            db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);
            db.close();
            onBackPressed();
        } else {
            Snackbar.make(findViewById(android.R.id.content),
                    errorMessages.get(checkTaskValidity(task)), Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    private void addValue(ContentValues values, Task data)
    {
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, data.getTitle());
        values.put(TaskContract.TaskEntry.COL_TASK_DECS, data.getDescription());
        values.put(TaskContract.TaskEntry.COL_TASK_DATE, data.getDate());
        values.put(TaskContract.TaskEntry.COL_TASK_TIME, data.getTime());
        values.put(TaskContract.TaskEntry.COL_TASK_CATEGORY, data.getCategory());
        values.put(TaskContract.TaskEntry.COL_TASK_STATUS, data.getState());
    }

    public Task getTaskFromView(View view, boolean create)
    {
        Task task = new Task();

        task.setTitle(getDataFromView(R.id.title, view));
        task.setDescription(getDataFromView(R.id.description, view));
        task.setDate(getDataFromView(R.id.day, view));
        task.setTime(getDataFromView(R.id.time, view));
        task.setCategory(getDataFromSpinner(R.id.spinner_category, view, true));
        if (create)
            task.setState("0");
        else
            task.setState(getDataFromSpinner(R.id.spinner_status, view, false));
        return task;
    }

    private String getDataFromView(int resId, View view)
    {
        EditText edit = (EditText)view.findViewById(resId);
        return edit.getText().toString();
    }

    private String getDataFromSpinner(int resId, View view, boolean category) {
        Spinner edit = (Spinner) view.findViewById(resId);
        if (category)
            return Integer.toString(edit.getSelectedItemPosition() + 1);
        return Integer.toString(edit.getSelectedItemPosition());
    }

    private int checkTaskValidity(Task task)
    {
        String[] datas = {
          task.getTitle(), task.getCategory(), task.getDescription(),
                task.getDate(), task.getTime(), task.getState()
        };
        List<String> list = Arrays.asList(datas);

        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).isEmpty() && currentTask == null)
                return 1;
            else if (list.get(i).isEmpty() && currentTask != null) {
                setTaskDataByNbr(task, i, currentTask);
            } else if (i == 5 && list.get(i).equals("3")) {
                return -1;
            }
        }

        return 0;
    }

    private void setTaskDataByNbr(Task task, int i, Task currentTask) {
        if (i == 1) {
            task.setTitle(currentTask.getTitle());
        } else if (i == 2) {
            task.setCategory(currentTask.getCategory());
        } else if (i == 3) {
            task.setDescription(currentTask.getDescription());
        } else if (i == 4) {
            task.setDate(currentTask.getDate());
        } else if (i == 5) {
            task.setTime(currentTask.getTime());
        } else if (i == 6) {
            task.setState(currentTask.getState());
        }
    }

    private Map<Integer, String> fillErrorMessageMap()
    {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "A field is still empty");
        return map;
    }


}
