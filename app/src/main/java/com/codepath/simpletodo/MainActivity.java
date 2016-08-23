package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    //ArrayAdapter<String> itemsAdapter;
    ItemsAdapter adapter;
    //ListView lvItems;
    RecyclerView rvItems;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    //REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lvItems = (ListView)findViewById(R.id.lvItems);
        rvItems = (RecyclerView)findViewById(R.id.rvTasks);

//        items = new ArrayList<>(); // now done in readItems()
        readItems();

        //itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        // Create adapter passing in the sample user data
        adapter = new ItemsAdapter(this, items);

        //lvItems.setAdapter(itemsAdapter);
        // Attach the adapter to the recyclerview to populate items
        rvItems.setAdapter(adapter);

        // Optionally customize the position you want to default scroll to
        layoutManager.scrollToPosition(0);
        // Attach layout manager to the RecyclerView
        rvItems.setLayoutManager(layoutManager);

        // items.add("First Item"); //no longer hard-coded
        // items.add("Second Item");

        setupListViewListener(); //for removing items
        setupEditListener(); //for editing items
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        //itemsAdapter.add(itemText);
        items.add(itemText);

        // Notify the adapter that an item was inserted at position 0
        adapter.notifyItemInserted(items.size()-1);

        etNewItem.setText(""); //reset text field
        writeItems();
    }

    private void setupListViewListener() {
//        lvItems.setOnItemLongClickListener(
//                new AdapterView.OnItemLongClickListener() {
//                    @Override
//                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
//                        items.remove(pos);
//                        itemsAdapter.notifyDataSetChanged();
//                        writeItems();
//                        return true;
//                    }
//                }
//        );
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
//        lvItems.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
//                        //prepare intent: MainActivity -> EditItemActivity
//                        Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
//                        //pass data to launched activity
//                        intent.putExtra("item", items.get(pos));
//                        intent.putExtra("itemPos", String.valueOf(pos));
//                        startActivityForResult(intent, REQUEST_CODE); //launch activity
//                    }
//                }
//        );
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
            //itemsAdapter.notifyDataSetChanged();
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
