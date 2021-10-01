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

public class EditNote extends AppCompatActivity {
    EditText title;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        title = findViewById(R.id.titleText);
        text = findViewById(R.id.noteText);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.saveedit,menu);
        return true;}


    @Override
    public void onBackPressed() {
        // Simple Ok & Cancel dialog - no view used.
        if(title.getText().toString().isEmpty()){
            finish();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setPositiveButton("YES", null);
            builder.setNegativeButton("NO", null);

            builder.setMessage("Do you want to save " + title.getText());
            builder.setTitle("Your note was not saved!");

            AlertDialog dialog = builder.create();
            dialog.show();
            super.onPause();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.saveButton){

            return true;
        }

        else{
            return super.onOptionsItemSelected(item);
        }
    }
}