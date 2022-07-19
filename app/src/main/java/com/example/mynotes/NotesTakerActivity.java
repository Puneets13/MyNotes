package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mynotes.Model.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;


//when the user Press on the + button the user is sent to the notesMakerActivity
//and the object for the Notes is being made and is passed back to the Main Activity
public class NotesTakerActivity extends AppCompatActivity {
    EditText editText_title, editText_notes;
    Button ImageView_save;
    Notes notes;

    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);
        editText_notes = findViewById(R.id.edittext_notes);
        editText_title = findViewById(R.id.edittext_title);
        ImageView_save = findViewById(R.id.imageView_save);


//        intent form MainActivty for updation of the Notes
//        we need to check if the notes requests for the the upgradation or for the new Making

        notes = new Notes();
        try {
            notes = (Notes) getIntent().getSerializableExtra("old_note");
            editText_title.setText(notes.getTitle());
            editText_notes.setText(notes.getNotes());
            isOldNote = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editText_title.getText().toString();
                String description = editText_notes.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(NotesTakerActivity.this, "Please add Your Note", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat formatter = new SimpleDateFormat("EEE,d MMM yyyy HH:mm a");
                Date date = new Date();

//             to check if the note isold one or we have to create it a new node
//                we will use the Boolean to check it
                if (!isOldNote) {
                    notes = new Notes();
                }
                notes.setTitle(title);
                notes.setNotes(description);
                notes.setDate(formatter.format(date));

//                pass this intent to the MainActivity to share the information
//            setResult method is used to pass the information to the MainActivty from the current Activity
//                here we havent mentioned the Activity to go from ...so the SetResult method is used to pass the data from current acivity to mainActivity
//        to MainActivity
                Intent intent = new Intent();
                intent.putExtra("note", notes);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });


    }
}