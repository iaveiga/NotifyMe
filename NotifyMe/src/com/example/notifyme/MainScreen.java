package com.example.notifyme;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainScreen extends Activity {
	
	
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_screen, menu);
        return true;
    }
    
    public void NewNoteActivity(View view) {
        Intent i = new Intent(this, CreateNote.class );
        startActivity(i);        
   }
    
    public void NotesActivity(View view) {
        Intent i = new Intent(this, Notes.class );
        startActivity(i);        
   }
    
    public void SettingsActivity(View view) {
        Intent i = new Intent(this, SettingsActivity.class );
            startActivity(i);        
   }
    
    public void ExitActivity(View view) {
        finish();       
   }
    
}