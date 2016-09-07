package com.codepath.simpletodo;

import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import java.util.Calendar;  // do not import java.icu.utils.Calendar
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddItemDialogFragment.AddItemDialogListener,
        EditItemDialogFragment.EditItemDialogListener, DatePickerDialog.OnDateSetListener{

    ArrayList<Tasks> items;
    ItemsAdapter adapter;

    RecyclerView rvItems;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    Tasks taskSelect;
    int taskSelectPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SimpleToDo");

        rvItems = (RecyclerView)findViewById(R.id.rvTasks);
        //readItems();

        // Query ActiveAndroid for list of data
        List<Tasks> queryResults = Tasks.findWithQuery(Tasks.class, "Select * from Task");
        //List<Tasks> queryResults = new Select().from(Tasks.class).execute();

        // Construct ArrayList for model type
        items = new ArrayList<Tasks>(queryResults);

        // Construct adapter plugging in the array source
        adapter = new ItemsAdapter(this, items);

        // Display dividers for task list
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvItems.addItemDecoration(itemDecoration);

        // Attach the adapter to the recyclerview to populate items
        rvItems.setAdapter(adapter);

        // Optionally customize the position you want to default scroll to
        layoutManager.scrollToPosition(0);
        // Attach layout manager to the RecyclerView
        rvItems.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miAdd:
                showAddDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void setupEditListener(int position) {
        taskSelect = items.get(position);
        String taskSelectName = taskSelect.name;
        taskSelectPos = position;
        showEditDialog(taskSelectName);
    }

    public void setupDeleteListener(int position) {
        // Deleting items
        //This removes the Author with ID = 23 from the database.
        //A common gotcha here is when there is no such Author with this ID,
        //so a null pointer check here is often advisable.
        //Tasks task = Tasks.findById(Tasks.class, (long)position); //NOT VALID BECAUSE task doesn't have ID field!!!!!!
        Tasks task = items.get(position);
        task.delete();

        items.remove(position);

        // Notify the adapter that an item was removed at position
        adapter.notifyItemRemoved(position);
    }



    private void showAddDialog() {
        FragmentManager fm = getSupportFragmentManager();
        // Set dialog title
        AddItemDialogFragment addItemDiaFrag = AddItemDialogFragment.newInstance("New Task");
        // Set dialog style
        addItemDiaFrag.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppDialogTheme);
        addItemDiaFrag.show(fm, "fragment_add_item");
    }

    private void showEditDialog(String oldTaskName) {
        FragmentManager fm = getSupportFragmentManager();
        // Set dialog title and pass old Task Name along
        EditItemDialogFragment editItemDialogFragment = EditItemDialogFragment.newInstance("Edit Item Below", oldTaskName);
        // Set dialog style
        editItemDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppDialogTheme);
        editItemDialogFragment.show(fm, "fragment_edit_item");
    }

    public void showDatePickerDialog(int position) {
        taskSelect = items.get(position);
        taskSelectPos = position;

        Bundle args = new Bundle();
        args.putLong("due", taskSelect.due);

        DatePickerFragment calendarFragment = new DatePickerFragment();
        calendarFragment.setArguments(args);
        calendarFragment.show(getSupportFragmentManager(), "datePicker");
    }



    // 3. This method is invoked in the activity when the listener is triggered
    // Access the data result passed to the activity here
    @Override
    public void onFinishAddDialog(String inputText) {
        // Create a new task
        Tasks task = new Tasks(inputText);
        task.save();

        //items.add(itemText);
        items.add(task);

        // Notify the adapter that an item was inserted at end of list
        adapter.notifyItemInserted(items.size()); //QUESTION: Why not items.size()-1

        // scroll to the bottom as items are added
        rvItems.scrollToPosition(adapter.getItemCount()-1);

        Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        //update selected task
        taskSelect.name = inputText;
        taskSelect.save();

        //inserting the updated task at the correct position in the array
        items.set(taskSelectPos, taskSelect);

        //notify the adapter such that the to-do list properly reflects the change
        adapter.notifyItemChanged(taskSelectPos);

        Toast.makeText(this, "Task edited", Toast.LENGTH_SHORT).show();
    }

    @Override // handle the date selected
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();

//        c.set(Calendar.YEAR, year);
//        c.set(Calendar.MONTH, monthOfYear);
//        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.set(year, monthOfYear, dayOfMonth, 0, 0);

        //update due date for selected task
        taskSelect.due = c.getTimeInMillis();
        taskSelect.save();

        //notify the adapter such that the to-do list properly reflects the change
        adapter.notifyItemChanged(taskSelectPos);

        Toast.makeText(this, "Due date saved", Toast.LENGTH_SHORT).show();
    }

}
