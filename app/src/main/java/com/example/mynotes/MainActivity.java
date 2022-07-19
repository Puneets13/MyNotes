package com.example.mynotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.mynotes.Adapter.NotesListAdapter;
import com.example.mynotes.Database.RoomDB;
import com.example.mynotes.Model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;
    List<Notes> notes = new ArrayList<>();
    RoomDB database;
    FloatingActionButton fab_add;
    SearchView searchView_home;
    //    for deleting the note
    Notes selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_id);
        searchView_home = findViewById(R.id.searchView_home);

//intialiseing the databse
        database = RoomDB.getInstance(this);
//        now we try to get all the nodes of the database and store that in the notes list created above
//  RoomDB is a databse and we can access that databse using DAO
//  so we need to get all the note so we connect the database with mainDAO
        notes = database.mainDAO().getAll();

//        to update the recycler view when the new node is added
//        create the function updateRecyclerView to update the list of recyclerView in MainActivity

        updateRecyclerView(notes);

//        to add the notes into the database and setting onClick Listener
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            since we need to get the data from the NoteMaker Activity for the intent we have made
//                so we will startActivityForResult and override the method As onActivityResult where we get the info from the intent
                Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
                startActivityForResult(intent, 101);

            }
        });

//to add searchView to the MainActivity
        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//          create the method own your own to implement searcgh
                filter(newText);
                return true;
            }
        });


    }

    //to implement the search view filter
    private void filter(String newText) {
        List<Notes> filteredList = new ArrayList<>();
        for (Notes singleNote : notes) {
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
                    || singleNote.getNotes().toLowerCase().contains(newText.toLowerCase())) {
//            if the text is contained in the node then add it to the filteredList
                filteredList.add(singleNote);
//now pass this filtered list to the adapter to display that
            }
        }

        notesListAdapter.filterList(filteredList);

    }

    //if the intent activity  result request code is 101 then only this method will work
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
//       if the intent from where it starts(noteMakerActivity) matches then the note get saved
            if (resultCode == Activity.RESULT_OK) {
                Notes new_notes = (Notes) data.getSerializableExtra("note");
//now insert the newNote into the database
                database.mainDAO().insert(new_notes);
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Added to Notes", Toast.LENGTH_SHORT).show();
            }

        }
//        the code 102 is for editing the existing note
        else if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.mainDAO().update(new_notes.getID(), new_notes.getNotes(), new_notes.getNotes());
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }

    }

    private void updateRecyclerView(List<Notes> notes) {
//here we will populate our recycler view
        recyclerView.setHasFixedSize(true);
//        since the layout of the notes is like a grid with containers so we will use
//        staggeredGridlayout manager instead of linearLayout Manager

//        pass the first parameter as spanCount = 2 means there will be 2 columns in the layout ..you can have anynumber of columns
//        pass the second paramter as the orientation of the columns and it should be VERTICAL
//        so use LinearLayoutManager.Vertical ;
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
//        update the adapter that we have created as NotesListAdapter
//        we need to pass the context , list , noteClickListener to the adapter
        notesListAdapter = new NotesListAdapter(MainActivity.this, notes, notesClickListener);
        recyclerView.setAdapter(notesListAdapter);


    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onCLick(Notes notes) {
//for making updation option
//            to edit the already existing note we will create an intent to NotesTakerActivity and
//            with the RequestCode of 102 the  activty for this would be handled
            Intent intent = new Intent(MainActivity.this, NotesTakerActivity.class);
            intent.putExtra("old_note", notes);
            startActivityForResult(intent, 102);

        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
//for deleting the notes in longPressed
            selectedNote = new Notes();
            selectedNote = notes;
            showPopup(cardView);
        }
    };

    //    method for deleting the notes
    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popupmenu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pin:
                if (selectedNote.isPinned()) {
                    database.mainDAO().pin(selectedNote.getID(), false);
                    Toast.makeText(this, "Unpinned", Toast.LENGTH_SHORT).show();
                } else {
                    database.mainDAO().pin(selectedNote.getID(), true);
                    Toast.makeText(this, "Pinned", Toast.LENGTH_SHORT).show();
                }
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
                return true;


            case R.id.delete:
                database.mainDAO().delete(selectedNote);
                notes.remove(selectedNote);
                notesListAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:return false;
        }

    }
}