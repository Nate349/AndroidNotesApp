package com.example.androidnotes;

import android.util.JsonWriter;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Note {
    private final String title;
    private final String text;
    private DateFormat time = new SimpleDateFormat("EE MM dd hh:mm a");

    public Note(String title, String text, DateFormat time) {
        this.title = title;
        this.text = text;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public DateFormat getTime() {
        return time;
    }

    @NonNull
    public String toString(){
        try{
            StringWriter sw = new StringWriter();
            JsonWriter jw = new JsonWriter(sw);
            jw.setIndent("  ");
            jw.beginObject();
            jw.name("title").value(getTitle());
            jw.name("text").value(getText());
            jw.endObject();
            jw.close();
            return sw.toString();

        }catch(IOException e){
            e.printStackTrace();
        }
        return "";
    }
}
