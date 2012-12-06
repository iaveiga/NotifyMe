package com.example.notifyme;

//import com.example.notifyme.CreateNote.MyLocationListener;
import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MapChooser extends MapActivity {

	
	private MapController mapController;
	private MyLocationOverlay myLocationOverlay;
	private PointSelect itemizedoverlay;
	private MapView mapView;
	private Button TypeMapButton;
	private static String _latitud;
	private static String _longitud;
	protected List<Overlay> mapOverlays;


    
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_chooser);
        TypeMapButton= (Button) findViewById(R.id.Satellite);	     
        mapView = (MapView) findViewById(R.id.map);
        inicializeMap();
        itemizedoverlay = new PointSelect(); // Creo el Point Select para guardar la capa con las coordenadas
        mapView.getOverlays().add(itemizedoverlay); // Añado la capa con el punto
        mapOverlays = mapView.getOverlays();
        
    	itemizedoverlay.setOnSelectPOIListener(new OnSelectPOIListener() {   //POIListener
    	 public void onSelectPOI(int latitud, int longitud) {
    		 Drawable drawable = getResources().getDrawable(R.drawable.gpsmarker);
    		 OverlayItem overlayItem = new OverlayItem(new GeoPoint(latitud,longitud),"OK","");     
    		 MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable, mapView.getContext(), mapView);
    		 itemizedoverlay.addOverlay(overlayItem);
    		 mapOverlays.add(itemizedoverlay);
    		 _latitud = Integer.toString(latitud);
    		 _longitud = Integer.toString(longitud);
    	 }
    	});


 	

    }

	@Override
	public void finish() {
		Intent data = new Intent();
		data.putExtra("returnKey1", _latitud);
		data.putExtra("returnKey2", _longitud);
		setResult(RESULT_OK, data);
		super.finish();
	}
	
	
	
    //Renderiza el Mapa en la Activity en la ubicacion actual
	private void inicializeMap(){

        mapView.setBuiltInZoomControls(true);        
        mapController = mapView.getController();
        mapController.setZoom(15);  //the new zoom level, between 1 and 21 inclusive.
        mapView.setSatellite(true);
        mapController.animateTo(new GeoPoint(-2160624,-79921627));
        centerMyPosition();     

}
	
	//Implementacion de la funcion para centrar el mapa al punto de ubicacion	
	private void centerMyPosition(){
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        mapView.getOverlays().add(myLocationOverlay);
        myLocationOverlay.enableCompass();
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                mapController.animateTo(myLocationOverlay.getMyLocation()); 
                mapController.setZoom(17);        
            }
        });
       
	}

   public void selectOverlay(View view){

	   if(TypeMapButton.getText().equals("Satelite")){
			   mapView.setSatellite(true);
			   TypeMapButton.setText("Mapa");
	   }
	   else if(TypeMapButton.getText().equals("Mapa"))
	   {
		   mapView.setSatellite(false);
//		   mapView.setStreetView(true);
		   TypeMapButton.setText("Satelite");
	   }
	   
	   
	   
   }
    
  protected boolean isRouteDisplayed() {
      return false;
  }

}
