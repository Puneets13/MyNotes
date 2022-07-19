package com.example.mynotes.Database;


//this interface is named as MainDAO = Main Data Access Object
//STEP 2 DAO
import static androidx.room.OnConflictStrategy.REPLACE;

import android.provider.ContactsContract;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mynotes.Model.Notes;

import java.util.List;

//add the annotation as Dao
@Dao
public interface MainDAO {

    @Insert(onConflict = REPLACE)
    void insert(Notes notes);

    //    to retreieve all the notes ordered by id in INCREASING order
    @Query("SELECT * FROM notes ORDER BY id ASC")
    List<Notes> getAll();

    @Query("UPDATE notes SET title = :title , notes = :notes WHERE ID = :id")
    void update(int id, String title, String notes);

    @Delete
    void delete(Notes notes);

    @Query("UPDATE notes SET pinned= :pin WHERE ID = :id")
    void pin(int id , boolean pin);
}
