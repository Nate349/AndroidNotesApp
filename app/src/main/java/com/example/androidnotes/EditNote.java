package com.example.androidnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;

public class EditNote extends AppCompatActivity {
    EditText title;
    EditText text;
    private String time;
    boolean editedNote = false;
    String startingTitle;
    String startingText;
    Note currentnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        title = findViewById(R.id.titleText);
        text = findViewById(R.id.noteText);
        time = Calendar.getInstance().getTime().toString();
        if (getIntent().hasExtra("existingNote")) {
            editedNote = true;
            Note n = (Note) getIntent().getSerializableExtra("existingNote");
            currentnote = n;
            title.setText(n.getTitle());
            text.setText(n.getText());
        }
        setStart(title.getText().toString(),text.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.saveedit,menu);
        return true;}


    @Override
    public void onBackPressed() {
        if(title.getText().toString().isEmpty() || startingText.equals(text.getText().toString()) && startingTitle.equals(title.getText().toString())){
            if(title.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "You can not save without a title!", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setPositiveButton("YES", (dialogInterface, i) -> {
                onSaveClick();
            });
            builder.setNegativeButton("NO", (dialogInterface, i) -> {
                finish();
            });
            builder.setMessage("Do you want to save " + title.getText());
            builder.setTitle("Your note was not saved!");
            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }
    public void setStart(String startTl, String startTx){
        startingTitle = startTl;
        startingText = startTx;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.saveButton){
                onSaveClick();
        }
        return false;
    }

    public void onSaveClick(){
        if (title.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "You can not save without a title!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(startingText.equals(text.getText().toString())){
            if(startingTitle.equals(title.getText().toString())){
                finish();
            }}

        Note n = new Note(title.getText().toString(),text.getText().toString(), Calendar.getInstance().getTime().toString());
        Intent inte = new Intent();
        inte.putExtra("new note", n);
        inte.putExtra("position",getIntent().getIntExtra("position", -1));
        inte.putExtra("editedNote", editedNote);
        setResult(RESULT_OK, inte);
        finish();

    }


}