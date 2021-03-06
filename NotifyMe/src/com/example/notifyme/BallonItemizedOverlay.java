package com.example.notifyme;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MapView.LayoutParams;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public abstract class BallonItemizedOverlay<Item extends OverlayItem> extends ItemizedOverlay<Item> {

	 private MapView mapView;
	 private BallonOverlayView<Item> balloonView;
	 private View clickRegion;
	 private int viewOffset;
	 final MapController mc;
	 private Item currentFocussedItem;
	 private int currentFocussedIndex;

	 private Context mContext;
	 
	 public BallonItemizedOverlay(Drawable defaultMarker, MapView mapView) {
	  super(defaultMarker);
	  mContext = mapView.getContext(); //context del map view
	  this.mapView = mapView; //map View
	  viewOffset = 0; 
	  mc = mapView.getController();
	 }
	 
	 public void setBalloonBottomOffset(int pixels) {
	  viewOffset = pixels;
	 }
	 public int getBalloonBottomOffset() {
	  return viewOffset;
	 }
	 protected MapView getMapView() {
	  return mapView;
	 }
	 
	 protected boolean onBalloonTap(int index, Item item) {
	  return false;
	 }
	 
	 @Override
	 protected final boolean onTap(int index) {
	  
	  currentFocussedIndex = index;
	  currentFocussedItem = createItem(index);
	  
	  boolean isRecycled;
	  if (balloonView == null) {
	   balloonView = createBalloonOverlayView();
	   clickRegion = (View) balloonView.findViewById(R.id.balloon_main_layout);
	   clickRegion.setOnTouchListener(createBalloonTouchListener());
	   isRecycled = false;
	  } else {
	   isRecycled = true;
	  }
	 
	  balloonView.setVisibility(View.GONE);
	  
	  List<Overlay> mapOverlays = mapView.getOverlays();
	  if (mapOverlays.size() > 1) {
	   hideOtherBalloons(mapOverlays);
	  }
	  
	  balloonView.setData(currentFocussedItem, mContext);
	  
	  GeoPoint point = currentFocussedItem.getPoint();
	  MapView.LayoutParams params = new MapView.LayoutParams(
	    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, point,
	    MapView.LayoutParams.BOTTOM_CENTER);
	  params.mode = MapView.LayoutParams.MODE_MAP;
	  
	  balloonView.setVisibility(View.VISIBLE);
	    
	  if (isRecycled) {
	   balloonView.setLayoutParams(params);
	  } else {
	   mapView.addView(balloonView, params);
	  }
	  
	  mc.animateTo(point);
	  
	  return true;
	 }
	 
	 protected BallonOverlayView<Item> createBalloonOverlayView() {
	  return new BallonOverlayView<Item>(getMapView().getContext(), getBalloonBottomOffset());
	 }

	 protected void hideBalloon() {
	  if (balloonView != null) {
	   balloonView.setVisibility(View.GONE);
	  }
	 }
	 
	 private void hideOtherBalloons(List<Overlay> overlays) {  
	  for (Overlay overlay : overlays) {
	   if (overlay instanceof BallonItemizedOverlay<?> && overlay != this) {
	    ((BallonItemizedOverlay<?>) overlay).hideBalloon();
	   }
	  }  
	 }
	 
	 private OnTouchListener createBalloonTouchListener() {
	 
	 return new OnTouchListener() {
	   public boolean onTouch(View v, MotionEvent event) {
	    
	    View l =  ((View) v.getParent()).findViewById(R.id.balloon_main_layout);
	    Drawable d = l.getBackground();
	    
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	     int[] states = {android.R.attr.state_pressed};
	     if (d.setState(states)) {
	      d.invalidateSelf();
	     }
	     return true;
	    } else if (event.getAction() == MotionEvent.ACTION_UP) {
	     int newStates[] = {};
	     if (d.setState(newStates)) {
	      d.invalidateSelf();
	     }
	     onBalloonTap(currentFocussedIndex, currentFocussedItem);
	     return true;
	    } else {
	     return false;
	    }
	    
	   }
	  };
	 }
	 
	}