package eu.epitech.todolist.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import eu.epitech.todolist.POJO.Task;
import eu.epitech.todolist.R;
import eu.epitech.todolist.Utils.TaskAdapter;
import eu.epitech.todolist.Utils.TaskContract;
import eu.epitech.todolist.Utils.TaskDbHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TaskAdapter adapter;
    private TaskDbHelper mHelper;
    private List<Task> list;
    private Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        list = new ArrayList<>();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mHelper = new TaskDbHelper(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setupEvents();
        updateUI();
    }



    private void setupEvents()
    {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new TaskAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.spinner_filter);
        // category
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.Category, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setPopupBackgroundResource(R.color.colorAccent);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTextSize(17);
                adapter.updateList(filterListByCategory(Integer.toString(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // searchbar
        final MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView)MenuItemCompat.getActionView(searchItem);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.updateList(filterListByName(s));
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    private void updateUI() {
        List<Task> list = new ArrayList<>();
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
            Task task = new Task();
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            task.setTitle(cursor.getString(idx));

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

            list.add(task);
        }

        cursor.close();
        db.close();
        adapter.updateList(list);
        this.list = list;
        TextView noTask = (TextView) findViewById(R.id.noTask);
        if (list.size() == 0) {
            noTask.setVisibility(View.VISIBLE);
        } else {
            noTask.setVisibility(View.GONE);
        }
    }


    public List<Task> filterListByCategory(String category)
    {
        List<Task> currentList = new ArrayList<>();
        Task task;

        Log.e("data", category);
        if (Objects.equals("0", category)) {
            return list;
        }
        for (int i = 0; i < list.size(); i++)
        {
            if (Objects.equals(list.get(i).getCategory(), category))
            {
                task = list.get(i);
                currentList.add(task);
            }
        }
        return currentList;
    }

    public List<Task> filterListByName(String name)
    {
        List<Task> currentList = new ArrayList<>();
        Task task;
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).getTitle().toLowerCase().contains(name))
            {
                task = list.get(i);
                currentList.add(task);
            }
        }
        return currentList;
    }


    public void moveToCreateTask(View view) {
        Intent intent = new Intent(MainActivity.this, TaskManagerActivity.class);
        intent.putExtra("fragment", "Create");
        startActivity(intent);
    }

}
