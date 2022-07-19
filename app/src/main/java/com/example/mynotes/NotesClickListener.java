package com.example.mynotes;

import androidx.cardview.widget.CardView;

import com.example.mynotes.Model.Notes;

public interface NotesClickListener {
//    create the interface for implementing the methods on our own
void onCLick(Notes notes);
void onLongClick(Notes notes, CardView cardView);

}
