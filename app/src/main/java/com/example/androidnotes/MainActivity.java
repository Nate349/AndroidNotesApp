package com.example.androidnotes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{
    final ArrayList<Note> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        noteList.clear();
        loadFile();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        adapter = new NoteAdapter(noteList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::handleResult);
        this.setTitle(String.format(Locale.US, "Android Notes (%d)", adapter.getItemCount()));
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.aboutbutton){
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        if(item.getItemId() == R.id.addNote){
            Intent intent = new Intent(this, EditNote.class);
            activityResultLauncher.launch(intent);
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.menu,menu);
        return true;
    }


    @Override
    public void onClick(View view) {
        int pos = recyclerView.getChildLayoutPosition(view);

        Note n = noteList.get(pos);

        Intent intent = new Intent(this, EditNote.class);
        intent.putExtra("NOTE", n);
        intent.putExtra("position", pos);
        activityResultLauncher.launch(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        Note note = noteList.get(pos);
        b.setPositiveButton("YES", (dialogInterface, i) -> {
            noteList.remove(pos);
            adapter.notifyItemRemoved(pos);
            saveNote();
            this.setTitle(String.format(Locale.US, "Android Notes (%d)", adapter.getItemCount()));
        });
        b.setNegativeButton("NO", null);
        b.setTitle("Delete Note"+ " '" + note.getTitle()+ "'?");

        b.create().show();
        return true;
    }
    private ArrayList<Note> loadFile() {
        try {
            InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String text = jsonObject.getString("text");
                String time = jsonObject.getString("time");
                Note note = new Note(title,text,time);
                noteList.add(note);
            }

        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return noteList;
    }
    public void handleResult(ActivityResult result) {
        Intent data = result.getData();
        if (data == null) {
            return;
        }
        if (result.getResultCode() == RESULT_OK) {
            Note note = (Note) data.getSerializableExtra("new note");
            if (data.getBooleanExtra("editedNote", false)){
                Note note1 =note;
                noteList.remove(data.getIntExtra("position",-1));
                adapter.notifyDataSetChanged();

            }
            if (note != null) {
                noteList.add(0, note);
                adapter.notifyItemInserted(0);
            }

        }
        saveNote();
        this.setTitle(String.format(Locale.US, "Android Notes (%d)", adapter.getItemCount()));
    }
    private void saveNote() {

        try {
            FileOutputStream fos = getApplicationContext().
                    openFileOutput("Note.json", Context.MODE_PRIVATE);

            PrintWriter printWriter = new PrintWriter(fos);
            printWriter.print(noteList);
            printWriter.close();
            fos.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}