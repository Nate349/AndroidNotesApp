package com.example.androidnotes;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView text;
    TextView datetime;


    public MyViewHolder(@NonNull View view) {
        super(view);
        title = view.findViewById(R.id.titleList);
        text = view.findViewById(R.id.textList);
        datetime = view.findViewById(R.id.Datelist);
    }
}