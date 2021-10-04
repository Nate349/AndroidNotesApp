package com.example.androidnotes;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<MyViewHolder> {


    private final List<Note> noteList;
    private final MainActivity mainAct;

    NoteAdapter(List<Note> noteList, MainActivity ma) {
        this.noteList = noteList;
        mainAct = ma;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notelistview, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.title.setText(cutter(note.getTitle()));
        holder.text.setText(cutter(note.getText()));
        holder.datetime.setText(cutter(note.getTime()));
    }

    private String cutter(String cut) {
        if(cut.length() - 80 > 0){
            cut = cut.substring(0, 80) + "...";
            return cut;
        }
        else {
        return cut;
        }
    }
    @Override
    public int getItemCount() {
        return noteList.size();
    }
}