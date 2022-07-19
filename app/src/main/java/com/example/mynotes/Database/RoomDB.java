package com.example.mynotes.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mynotes.Model.Notes;




//the DATABSE FOR NOTES APP
//STEP 3 DATABASE

//to initialise it with DataBase we will use DataBase Annotation
//we will use the ENTITY(TABLE NAME THAT WE HAVE CREATED) AS notes.class
@Database(entities = Notes.class, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    //    now create the instance of the ROOM databse
    private static RoomDB database;
    private static String DATABASE_NAME = "NoteAPP";

//    (Singleton pattern) of creating databse to avoid rebuild if database already exsits
    //to create the instance of the databse we will create the method
    public synchronized static RoomDB getInstance(Context context) {
//    if the databse instance is null ..then we will create new instance
//    else pass the initial instance only
        if (database == null) {
//    create new instance database
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

//the databse should always contain the method with 0 argument to connect the DAO with the database
    public abstract MainDAO mainDAO();


}


