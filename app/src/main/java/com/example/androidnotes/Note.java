package com.example.androidnotes;

import android.util.JsonWriter;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;


public class Note implements Serializable {
    private String title;
    private String text;
    private String time;

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
    public String getTime() {
        return time;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setText(String text){
        this.title = text;
    }
    public void setTime(String time){
        this.time = time;
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
