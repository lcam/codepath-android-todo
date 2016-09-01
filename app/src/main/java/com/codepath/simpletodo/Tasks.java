package com.codepath.simpletodo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

//The "name" part of the annotations refers to the name the Table or Columns will be given,
//so make sure to use the SQLite naming conventions for those.
//Also note that ActiveAndroid creates a local id (Id)
//in addition to our manually managed remoteId (unique) which is the id on the server (for networked applications).
//To access that primary key Id, you can call getId() on an instance of your model.

@Table(name = "Tasks")
public class Tasks extends Model {
    // Make sure to have a default constructor for every ActiveAndroid model
    public Tasks(){
        super();
    }

    // This is the unique id given by the server
    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long remoteId;

    // This is a regular field
    @Column(name = "Name")
    private String name;


    public Tasks(int remoteId, String name){
        super();
        this.remoteId = remoteId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getRemoteId() {
        return remoteId;
    }

    public void setName(String n) {
        name = n;
    }
}
