package com.example.mynotes.Adapter;

import android.animation.LayoutTransition;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotes.Model.Notes;
import com.example.mynotes.NotesClickListener;
import com.example.mynotes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    Context context;
    List<Notes> list;
    NotesClickListener listener;

    public NotesListAdapter(Context context, List<Notes> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
//      for setting title
        holder.textView_title.setText(list.get(position).getTitle());
//        to give floating effect marquee effect use this
//        holder.textView_title.setSelected(true);
        holder.textView_title.setSelected(true);

//for setting notes and date
        holder.textView_notes.setText(list.get(position).getNotes());
        holder.textView_date.setText(list.get(position).getDate());
        holder.textView_date.setSelected(true);

//        for pinned item
        if (list.get(position).isPinned()) {
            holder.imageVIew_pin.setImageResource(R.drawable.pin);
        } else {
            holder.imageVIew_pin.setImageResource(0);

        }

//        to get the random color everytime in notes we need to use the userDefined method
        int color_code = getRandomColor();
        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));


//  to set onCLick listener on the notes
        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                now implements the interface onClick method that we have created here using the object
//                of the NotesClickListener interface
                listener.onCLick(list.get(holder.getAdapterPosition()));

            }
        });

//for onLongClick Listener
        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_container);
                return true;
            }
        });


    }

    //   to get Random color in the tabs of the notes
    private int getRandomColor() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);

//to pick the random color eerytime we need to use this
        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //to update the recyclerviewAdapter with the filtered list
//    we are creating this method to replace the globally declared list with the passed local list
    public void filterList(List<Notes> filteredList) {

//        just change the list that we have globally created with the passed list
        list = filteredList;
        notifyDataSetChanged();
    }


}

class NotesViewHolder extends RecyclerView.ViewHolder {

    CardView notes_container;
    TextView textView_title, textView_date, textView_notes;
    ImageView imageVIew_pin;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);

        notes_container = itemView.findViewById(R.id.notes_container);
        textView_notes = itemView.findViewById(R.id.textView_Notes);
        textView_title = itemView.findViewById(R.id.textView_title);
        textView_date = itemView.findViewById(R.id.textView_Dates);
        imageVIew_pin = itemView.findViewById(R.id.imageViewPin);

    }
}