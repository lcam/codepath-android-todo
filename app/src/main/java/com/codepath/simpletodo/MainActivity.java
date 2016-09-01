package com.codepath.simpletodo;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddItemDialogFragment.AddItemDialogListener,
        EditItemDialogFragment.EditItemDialogListener{

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
        List<Tasks> queryResults = new Select().from(Tasks.class).execute();

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

        setupListViewListener(); //for removing items
        setupEditListener(); //for editing items
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void showAddDialog() {
        FragmentManager fm = getSupportFragmentManager();
        // Set dialog title
        AddItemDialogFragment addItemDiaFrag = AddItemDialogFragment.newInstance("New Task");
        addItemDiaFrag.show(fm, "fragment_add_item");
    }

    private void showEditDialog(String oldTaskName) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialogFragment editItemDialogFragment = EditItemDialogFragment.newInstance("Edit Item Below", oldTaskName);
        editItemDialogFragment.show(fm, "fragment_edit_item");
    }


    // 3. This method is invoked in the activity when the listener is triggered
    // Access the data result passed to the activity here
    @Override
    public void onFinishAddDialog(String inputText) {
        onAddItem(inputText);
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        onEditItem(inputText);
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
        }    }

    public void onAddItem(String itemText) {
        // Create a new task
        Tasks task = new Tasks(items.size()-1, itemText);
        task.save();

        //items.add(itemText);
        //List<Tasks> queryResults = new Select().from(Tasks.class).where("Name = ?", itemText).execute();
        items.add(task);

        // Notify the adapter that an item was inserted at end of list
        adapter.notifyItemInserted(items.size()-1);

        // scroll to the bottom as items are added
        rvItems.scrollToPosition(adapter.getItemCount()-1);

        Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
    }

    public void onEditItem(String itemText) {
        //update selected task
        taskSelect.setName(itemText);
        taskSelect.save();

        //inserting the updated task at the correct position in the array
        items.set(taskSelectPos, taskSelect);

        //notify the adapter such that the to-do list properly reflects the change
        adapter.notifyItemChanged(taskSelectPos);

        Toast.makeText(this, "Task edited", Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener() {
        ItemClickSupport.addTo(rvItems).setOnItemLongClickListener(
                new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                        items.remove(position);

                        // Notify the adapter that an item was removed at position
                        adapter.notifyItemRemoved(position);

                        // Deleting items
                        //QUESTION: Why doesn't this work???
                        //Tasks task = Tasks.load(Tasks.class, position);
                        //task.delete();
                        new Delete().from(Tasks.class).where("remote_id = ?", position).execute();

                        return true;
                    }
                }
        );
    }

    private void setupEditListener() {
        ItemClickSupport.addTo(rvItems).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        taskSelect = items.get(position);
                        String taskSelectName = taskSelect.getName();
                        taskSelectPos = position;
                        showEditDialog(taskSelectName);
                    }
                }
        );
    }

}
