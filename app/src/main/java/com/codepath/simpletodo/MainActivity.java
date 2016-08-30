package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddItemDialogFragment.AddItemDialogListener{
    ArrayList<String> items;
    ItemsAdapter adapter;
    RecyclerView rvItems;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    //REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 0;

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
        readItems();

        // Create adapter passing in the sample user data
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

    // 3. This method is invoked in the activity when the listener is triggered
    // Access the data result passed to the activity here
    @Override
    public void onFinishAddDialog(String inputText) {
        //Toast.makeText(this, inputText, Toast.LENGTH_SHORT).show();
        onAddItem(inputText);
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
        //EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        //String itemText = etNewItem.getText().toString();

        items.add(itemText);

        // Notify the adapter that an item was inserted at end of list
        adapter.notifyItemInserted(items.size()-1);

        // scroll to the bottom as items are added
        rvItems.scrollToPosition(adapter.getItemCount()-1);

        //etNewItem.setText(""); //reset text field
        writeItems();
    }

    private void setupListViewListener() {
        ItemClickSupport.addTo(rvItems).setOnItemLongClickListener(
                new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                        items.remove(position);

                        // Notify the adapter that an item was removed at position 0
                        adapter.notifyItemRemoved(position);

                        writeItems();
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
                        //prepare intent: MainActivity -> EditItemActivity
                        Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                        //pass data to launched activity
                        intent.putExtra("item", items.get(position));
                        intent.putExtra("itemPos", String.valueOf(position));
                        startActivityForResult(intent, REQUEST_CODE); //launch activity
                    }
                }
        );
    }

    //MainActivity, time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String name = intent.getExtras().getString("item");
            int namePos = Integer.parseInt(intent.getExtras().getString("itemPos"));

            Toast.makeText(this, "task edited", Toast.LENGTH_SHORT).show();

            //inserting the updated task at the correct position in the array
            //notify the adapter such that the to-do list properly reflects the change
            //persist the updated text back to the file
            items.set(namePos, name);
            adapter.notifyItemChanged(namePos);
            writeItems();
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
