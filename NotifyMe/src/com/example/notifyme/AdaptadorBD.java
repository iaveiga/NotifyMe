package com.example.notifyme;

import java.sql.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdaptadorBD{

	public static final String KEY_IDNOTA = "_idnote";
	public static final String KEY_TITULONOTA = "_titulo";
	public static final String KEY_DETALLE = "_detalle";
	public static final String KEY_IDCATEGORIA = "_categoria";
	public static final String KEY_IDLONG= "_coordenada";
	public static final String KEY_REALIZADO = "_realizado";
	public static final String KEY_DATEA = "_anio";
	public static final String KEY_DATEM = "_mes";
	public static final String KEY_DATED = "_dia";
	public static final String KEY_LATITUD= "_latitud";
	public static final String KEY_LONGITUD = "_longitud";
	public static final String KEY_RANGO = "_rango";
	public static final String KEY_EXPIRACION = "_expiracion";

	private static final String NOMBRE_BASEDATOS = "notifyDB";
	private static final String TABLA_NOTAS = "notas";
	private static int VERSION_BASE_DATOS = 1;
	
	private static final String CREAR_TABLA_NOTA = "CREATE TABLE notas (_idnote integer primary key autoincrement not null, _titulo mediumtext, _detalle longtext, _categoria text,_latitud text, _longitud text, _rango text, _expiracion text, _realizado text, _anio text, _mes text, _dia text);";

	private final Context context;
	private SQLiteDatabase db;
	private DatabaseHelper DBHelper;
	
	
	 
	public AdaptadorBD(Context context){
		this.context=context;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper{
		DatabaseHelper(Context context) {
			super(context, NOMBRE_BASEDATOS, null, VERSION_BASE_DATOS);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try{
				db.execSQL(CREAR_TABLA_NOTA);
			}catch (SQLException e){
				e.printStackTrace();
			}
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
		
	}
	
	public void cerrar(){
		DBHelper.close();
	}
	
	public AdaptadorBD abrir() throws SQLException{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	public long insertarNota (String _titulo, String _detalle, String _categoria, String _realizado, String _latitud, String _longitud, String _rango, String _expiracion, String _anio, String _mes , String _dia ){
	ContentValues valoresIniciales = new ContentValues();
	valoresIniciales.put(KEY_TITULONOTA, _titulo);
	valoresIniciales.put(KEY_DETALLE, _detalle);
	valoresIniciales.put(KEY_IDCATEGORIA, _categoria);
	valoresIniciales.put(KEY_REALIZADO, _realizado);
	valoresIniciales.put(KEY_LATITUD, _latitud);
	valoresIniciales.put(KEY_LONGITUD, _longitud);
	valoresIniciales.put(KEY_RANGO, _rango);
	valoresIniciales.put(KEY_EXPIRACION, _expiracion);
	valoresIniciales.put(KEY_DATEA, _anio);
	valoresIniciales.put(KEY_DATED, _dia);
	valoresIniciales.put(KEY_DATEM, _mes);
	return db.insert(TABLA_NOTAS, null, valoresIniciales);
	}

	public boolean borrarNota(String Titulo){
		return db.delete(TABLA_NOTAS,KEY_TITULONOTA + "=" + Titulo,null)>0;
	}
	
	public Cursor obtenerTodaslasNotas(){
		return db.query(TABLA_NOTAS,new String [] {KEY_IDNOTA,KEY_TITULONOTA,KEY_DETALLE,KEY_IDCATEGORIA,KEY_REALIZADO,KEY_LATITUD,KEY_LONGITUD,KEY_RANGO,KEY_EXPIRACION,KEY_DATEA,KEY_DATED,KEY_DATEM},null,null,null,null,null);
	}
	
	
	
	
}
	
	