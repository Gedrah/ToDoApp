package com.aziaka.donavan.todoapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import org.w3c.dom.ls.LSException;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    private List<Task> list;
    private TaskAdapter adapter;
    private Random random;
    private String[] category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        random = new Random();
        category = getResources().getStringArray(R.array.Category);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < 10; i++)
            createContent(i);
        setupsEvents();

    }

    private void createContent(int i) {
        int nbr = random.nextInt(category.length);
        String cat = category[nbr];
        if (cat.equals("All Tasks"))
            cat = "None";
        Task task = new Task();
        task.setCategory(cat);
        task.setName("Ok" + Integer.toString(i));
        task.setDate("Ok");
        list.add(task);
    }

    private void setupsEvents() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new TaskAdapter(list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        // category
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTextSize(17);

                List<Task> currentList = filterListByCategory(parent.getSelectedItem().toString());
                updateContent(currentList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // searchbar
        final MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView)MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Task> currentList = filterListByName(s);
                updateContent(currentList);
                return false;
            }
        });



        return true;
    }

    private void updateContent(List<Task> list1) {
        adapter.updateList(list1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            return true;
        } else if (id == R.id.spinner) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Task> filterListByCategory(String category)
    {
        List<Task> currentList = new ArrayList<>();
        Task task;
        if (category.equals("All Tasks"))
        {
            for (int i = 0; i < list.size(); i++) {
                if (!Objects.equals(list.get(i).getCategory(), "Finished"))
                {
                    task = list.get(i);
                    currentList.add(task);
                }
            }
        }
        else
        {
            for (int i = 0; i < list.size(); i++)
            {
                if (Objects.equals(list.get(i).getCategory(), category))
                {
                    task = list.get(i);
                    currentList.add(task);
                }
            }
        }
        return currentList;
    }

    private List<Task> filterListByName(String name)
    {
        List<Task> currentList = new ArrayList<>();
        Task task;
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i).getName().toLowerCase().contains(name))
            {
                task = list.get(i);
                currentList.add(task);
            }
        }
        return currentList;
    }

}