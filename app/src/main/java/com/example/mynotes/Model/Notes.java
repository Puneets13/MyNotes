package com.example.mynotes.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//step 1 ENTITY MAKING
//to use notes as a table we will add an entity as
@Entity(tableName = "notes")

//WE USE SERIALISABLE SO THAT WECAN PASS THE INTENT AS START_ACTIVITY_FOR_RESULT
public class Notes implements Serializable {
    //to make a Primary key in table we will use annotation primary key and will
//set it to autoGenerate to generate the id when new notes are created
    @PrimaryKey(autoGenerate = true)
    int ID = 0;

    //to create the columns we will use @columnInfo and will give its name there
//    this is column annotation
    @ColumnInfo(name = "title")
    String title = "";

    @ColumnInfo(name = "notes")
    String notes = "";

    @ColumnInfo(name = "date")
    String date = "";

    @ColumnInfo(name = "pinned")
    Boolean pinned = false;

//    now generate the getter and setter for it


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean isPinned() {
        return pinned;
    }

    public void setPinned(Boolean pinned) {
        this.pinned = pinned;
    }
}
