package com.codepath.simpletodo;


import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import static android.R.attr.order;

@Table(name = "Task")
public class Tasks extends SugarRecord {

    @Unique
    String name;
    long due;
    int priority;
    long listOrder;

    // Default constructor is necessary for SugarRecord
    public Tasks(){

    }

    public Tasks(String name){
        //super();
        //this.remoteId = remoteId;
        this.name = name;
        this.due = 0;
        this.priority = 0;
        this.listOrder = 0;
    }
}
