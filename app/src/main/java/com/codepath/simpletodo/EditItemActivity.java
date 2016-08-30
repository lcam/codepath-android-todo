package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText etEditItem;
    String item;
    String itemPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etEditItem = (EditText)findViewById(R.id.etEditItem);
        item = getIntent().getStringExtra("item"); //get old task
        etEditItem.setText(item); //set old task to text field
        etEditItem.setSelection(etEditItem.length()); //set cursor to end of task
        itemPos = getIntent().getStringExtra("itemPos"); //get pos of old task
        //Make keyboard show up when screen is brought up
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    //launched for a result
    public void onSubmit(View v) {
        //Prepare intent
        Intent intent = new Intent();
        //Pass relevant data back as a result
        intent.putExtra("item", etEditItem.getText().toString());
        intent.putExtra("itemPos", itemPos); //can be an int
        //Activity finished ok, return the data
        setResult(RESULT_OK, intent);
        finish(); //closes the activity, pass data to parent
    }
}
