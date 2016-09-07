package com.codepath.simpletodo;


import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

@Table(name = "Task")
public class Tasks extends SugarRecord {

    @Unique
    //long remoteId;
    String name;
    long due;


    // Default constructor is necessary for SugarRecord
    public Tasks(){

    }

//    // This is the unique id given by the server
//    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
//    private long remoteId;
//
//    // This is a regular field
//    @Column(name = "Name")
//    private String name;


    public Tasks(String name){
        //super();
        //this.remoteId = remoteId;
        this.name = name;
        this.due = 0;
    }

//    public String getName() {
//        return name;
//    }
//
//    public long getRemoteId() {
//        return remoteId;
//    }
//
//    public void setName(String n) {
//        name = n;
//    }
}
