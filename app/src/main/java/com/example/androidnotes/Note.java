package com.example.androidnotes;

import android.util.JsonWriter;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Note implements Serializable {
    private final String title;
    private final String text;
    private final String time;

    public Note(String title, String text, String updateTime) {
        this.title = title;
        this.text = text;
        this.time = updateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
    public String getTime() { return time;
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
            jw.name("time").value(getTime());
            jw.endObject();
            jw.close();
            return sw.toString();

        }catch(IOException e){
            e.printStackTrace();
        }
        return "";
    }
}
