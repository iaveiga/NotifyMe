package com.example.notifyme;

import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BallonOverlayView <Item extends OverlayItem> extends FrameLayout {

	 protected LinearLayout layout;
	 protected TextView title;
	 protected TextView snippet;
	 protected LayoutInflater layoutinflater;
	 protected View balloonview;
	 protected ImageView imgclose;

	 protected int getIdView(){return R.layout.window_balloon_overlay;}
	 
	 public BallonOverlayView(final Context context, int balloonBottomOffset) {
	  super(context);
	  
	  setPadding(10, 0, 10, balloonBottomOffset);
	  layout = new LinearLayout(context);
	  layout.setVisibility(VISIBLE);
	  layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  balloonview = layoutinflater.inflate(getIdView(), layout);
	  title = (TextView) balloonview.findViewById(R.id.balloon_item_title);
	  snippet = (TextView) balloonview.findViewById(R.id.balloon_item_snippet);
	  
	  title.setVisibility(GONE);
	  snippet.setVisibility(GONE);

	  imgclose = (ImageView) balloonview.findViewById(R.id.close_img_button);
	  imgclose.setOnClickListener(new OnClickListener() {
	   public void onClick(View v) {layout.setVisibility(GONE);}
	  });
	  
	  FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	  params.gravity = Gravity.NO_GRAVITY;
	  addView(layout, params);   
	 }
	 
	 public void setData(Item item, Context mContext) {
	  
	  layout.setVisibility(VISIBLE);
	  if (item.getTitle() != null && item.getTitle().length() > 0) {
	   title.setVisibility(VISIBLE);
	   title.setText(item.getTitle());
	   title.setTextSize(9);
	  } else {
		   title.setVisibility(GONE);
	  }
	  if (item.getSnippet() != null && item.getSnippet().length() > 0) {
	   snippet.setVisibility(VISIBLE);
	   snippet.setText(item.getSnippet());
	  } else {
		   title.setVisibility(GONE);
	  }  
	 }

	}
