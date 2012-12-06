package com.example.notifyme;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Notes extends Activity {

	private Nota[] datos = new Nota[]{new Nota("",""),new Nota("",""),new Nota("",""),new Nota("",""),new Nota("",""),new Nota("",""),new Nota("",""),new Nota("",""),new Nota("",""),new Nota("",""),new Nota("",""),new Nota("",""),new Nota("",""),new Nota("",""),new Nota("",""),new Nota("",""),new Nota("","")};
    AdaptadorBD db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
    	db = new AdaptadorBD(this);  	
        db.abrir();
        Cursor c = db.obtenerTodaslasNotas();
        if(c.moveToFirst())
        {   int i=0;
        	do{
				datos[i]= new Nota(c.getString(1),c.getString(2));
				i++;
        	}while(c.moveToNext());
        }
        
        
        AdaptadorNota adaptador = 	new AdaptadorNota(this);
        ListView lstOpciones = (ListView)findViewById(R.id.LstOpciones);   
        lstOpciones.setAdapter(adaptador);
        }
        
        class AdaptadorNota extends ArrayAdapter{
        	
        	Activity context;
        	
        	AdaptadorNota(Activity context) {
        		super(context, R.layout.activity_notes, datos);
        		this.context = context;
        	}
        	
        	public View getView(int position, View convertView, ViewGroup parent) {
    			LayoutInflater inflater = context.getLayoutInflater();
    			View item = inflater.inflate(R.layout.activity_notes, null);
    			
    			TextView lblTitulo = (TextView)item.findViewById(R.id.LblTitulo);
    			lblTitulo.setText(datos[position].getTitulo());
    			
    			return(item);
    		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_notes, menu);
        return true;
    }

}
