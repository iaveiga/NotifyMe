package com.example.notifyme;

import java.io.File;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class CreateNote extends Activity {
	
	private static Activity activity;
	private TextView TextTitle;
	private static String title;
	private TextView TextDescription;
	private static String Description;
	private static String categoria;
	private static String anio = "2000";
	private static String dia = "12";
	private static String mes = "12";
	private static String latitud = "";
	private static String longitud = "";
	private static String rango = "";
	private static String expiracion = "";
	private static int _indf = 0;
	private static int _indc = 1;
	private static int _indm = 0;
	private long id;
	private static final int REQUEST_CODE = 10;
    AdaptadorBD db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        db = new AdaptadorBD(this); 
        db.abrir();
        activity= this;
    	TextTitle = (TextView)findViewById(R.id._NameText);
    	TextDescription = (TextView)findViewById(R.id._NoteText);
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			if (data.hasExtra("returnKey1")) {
				latitud = data.getExtras().getString("returnKey1");
				longitud = data.getExtras().getString("returnKey2");
				}
		}
	}
        

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_note, menu);
        return true;
    }

    public void MapActivity(View view) {
        Intent i = new Intent(this, MapChooser.class );
        startActivityForResult(i, REQUEST_CODE);
        _indm = 1;
        
   }
    
    public void CategoriaActivity(View view) {
    	
    	
    	AlertDialog.Builder  d = new AlertDialog.Builder(activity);
    	
    	final String elements [] = {"Trabajo","Estudio","Bancos","Pagos","Basicos","Citas","Compras","Familia","Negocios","Varios"};
    	
    	d.setMultiChoiceItems(elements, null , new OnMultiChoiceClickListener() {
    		 @Override
    		 public void onClick(DialogInterface dialog, int which, boolean isChecked) {
    		       if(isChecked){
    		          String str = elements[which];
    		          categoria= str;
    		        }
    		   }
    		 });
    		d.setPositiveButton("Save", new OnClickListener() {
    		   @Override
    		   public void onClick(DialogInterface dialog, int which) {
    			   
    		   }				
    		});
    		d.show();
        
   }
    
    public void SaveNote(View view){
    	title = TextTitle.getText().toString();
    	Description = TextDescription.getText().toString();
    	if(!title.equals("")){
    		if(!Description.equals("")){
        		if(_indc!=0){  
            		if(_indm!=0){    
            			if(_indf!=0){  
            				id = db.insertarNota (title, Description, categoria, "false", latitud, longitud,  "50",  expiracion, anio,  mes ,dia);
            				Toast.makeText(this, "Ingreso Exitoso",
            						Toast.LENGTH_SHORT).show();
            				db.cerrar();
            		    	TextTitle.setText("");
            		    	TextDescription.setText("");
            		    	_indf=0;
            		    	_indc=0;
            		    	_indm=0;
            		    	latitud="";
            		    	longitud="";
                    	}
            			else{
            				Toast.makeText(this, "Por favor asegurate de completar toda la informacion requerida",
            						Toast.LENGTH_SHORT).show();
            				}
                	}
        			else{
        				Toast.makeText(this, "Por favor asegurate de completar toda la informacion requerida",
        						Toast.LENGTH_SHORT).show();
        				}
            	}
        		else{
    				Toast.makeText(this, "Por favor asegurate de completar toda la informacion requerida",
    						Toast.LENGTH_SHORT).show();
    				}
        	}
    		else{
				Toast.makeText(this, "Por favor asegurate de completar toda la informacion requerida",
						Toast.LENGTH_SHORT).show();
				}
    	}
    	else{
			Toast.makeText(this, "Por favor asegurate de completar toda la informacion requerida",
					Toast.LENGTH_SHORT).show();
			}
    	
    }
    
    public void limpiar(View v){
    	TextTitle.setText("");
    	TextDescription.setText("");
    	_indf=0;
    	_indc=0;
    	_indm=0;
    	latitud="";
    	longitud="";
    }
    
    public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");

	}
    
    public void Exit(View v) {
    	finish();
 	}
    
    
	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			anio=Integer.toString(year);
			mes=Integer.toString(month);
			dia=Integer.toString(day);
			_indf = 1;
			}
					
	}

	
}
