package com.itk.gasnami.catan;

import java.util.HashMap;

import com.itk.gasnami.catan.Corner.Fundament;
import com.itk.gasnami.catan.Landing.Player;

import android.app.Activity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class CatanActivity extends Activity {
	
	//Variables for debuging
	HashMap<Integer, Pair<Landing, Landing> > landingTiles = 
			new HashMap<Integer, Pair<Landing,Landing>>();
	int nOfPlayers = 2; //bővítés esetén erre kell alternatíva
	
	//TODO: visibility should be changed!
	//TODO: ennek saját hely kéne, a Panel fölé kell egy manager osztály!
	public Corner[][] vertices = new Corner[11][6];	
	public Border[][] edges = new Border[6][11];
	
	Panel debugPanel;
	
	FrameLayout parent;
	RelativeLayout internalForBorders;
	Button debugBtn;
	
	//Show debug datas as Toast messages
	class myClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			
//			ArrayList<Integer> resourceNumbers = new ArrayList<Integer>();
//			resourceNumbers.addAll(Arrays.asList( 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12 ));
			String debugText = new String();
//			debugText +="|rNr: " + landingTiles.get(7).first.getResourceNumber();
//			debugText +="|bMp: " + landingTiles.get(7).first.getBitmap4Land();
//			debugText +="|iAc: " + landingTiles.get(7).first.getIsActive();
//			debugText +="|pRs: " + landingTiles.get(7).first.getProvidedResource();
//			debugText +="|coo: " + landingTiles.get(7).first.getCoordinates().first 
//					+ " " + landingTiles.get(7).first.getCoordinates().second;
//			debugText +="|pRp: " + landingTiles.get(7).first.getProduction().size();
//			for(int i = 0; i < landingTiles.get(7).first.getProduction().size(); i++) {
//				debugText +=" " + 
//				landingTiles.get(7).first.getProduction().get(Player.values()[i]);
//			}
			/*
			if(debugPanel.vertices[0][0] == null) {
				debugText = "nullValue!";
			} else {
				debugText = debugPanel.vertices[0][0].toString();
			}
			Toast.makeText(CatanActivity.this, debugText, Toast.LENGTH_SHORT).show();
			
			if(debugPanel.edges[0][0] == null) {
				debugText = "nullValue!";
			} else {
				debugText = debugPanel.edges[0][0].toString();
			}
			Toast.makeText(CatanActivity.this, debugText, Toast.LENGTH_SHORT).show();
			*/
			SizeHandler sizeHandler = new SizeHandler(1, 1);
			
			Toast.makeText(CatanActivity.this, vertices[4][4].toString(), Toast.LENGTH_LONG).show();
			vertices[4][4].build(Player.Player1, Fundament.settlement);
			Toast.makeText(CatanActivity.this, vertices[4][4].toString(), Toast.LENGTH_LONG).show();
			debugText = "h: " + vertices[4][4].getHeight() + " w: " + vertices[4][4].getWidth() +
//					" " + vertices[4][4].getDrawable();
					" " + (30 + vertices[4][4].fundament .ordinal()) +
					" " + sizeHandler.getCornerCoordinates(4, 4).first +
					" " + sizeHandler.getCornerCoordinates(4, 4).second;
			Toast.makeText(CatanActivity.this, debugText , Toast.LENGTH_LONG).show();
////			Toast.makeText(CatanActivity.this, debugText, Toast.LENGTH_LONG).show();
//			Toast.makeText(CatanActivity.this, landingTiles.keySet().toString(), Toast.LENGTH_SHORT).show();
//			resourceNumbers.remove(2);
//			Toast.makeText(CatanActivity.this, resourceNumbers.get(2)+"", Toast.LENGTH_LONG).show();
//			Toast.makeText(CatanActivity.this, resourceNumbers.remove(2)+"", Toast.LENGTH_LONG).show();
		}
	}
	
	//Vszeg kéne valami szép kezelőosztály...
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        //TODO: A custom panel View elé csapni az infoBar-okat FrameLayout-al
        debugPanel = new Panel(this);
        debugBtn = (Button) findViewById(R.id.debugBtn);
        parent = (FrameLayout) findViewById(R.id.parent);
        internalForBorders = new RelativeLayout(this);
        internalForBorders.addView(debugPanel);
        InitializeBorders(internalForBorders);
        parent.removeAllViews();
        parent.addView(internalForBorders);
        parent.addView(debugBtn);
        
        
        
//        setContentView(new Panel(this));

//        InitializeTiles();
//        debugBtn = (Button) findViewById(R.id.debugBtn);
        debugBtn.setOnClickListener(new myClickListener());
//        
    }
    
    //Initialize the vertices & edges
    public void InitializeBorders(RelativeLayout layout) {
    	for(int j = 0; j < 6; j++) {
    		for(int i = 0; i < 11; i++) {
    			
    			if(i + j > 1 || i + j < 14 || (j == 0 && (i != 9 || i!= 10)) || (j==1 && i!=10)) {
    				vertices[i][j] = new Corner(this);
        			edges[j][i] = new Border(this);
        			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        			vertices[i][j].setImageResource(R.drawable.settlement_gray);
        			vertices[i][j].setLayoutParams( params );
//        			vertices[i][j].setTr
//        			vertices[i][j].setVisibility(View.VISIBLE);
    				
        			//FIXME: absoluteLayout-ra, vagy tárolni hova mehet a click.
        			layout.addView(vertices[i][j]);
    			}
    			
    			vertices[i][j] = new Corner(this);
    			edges[j][i] = new Border(this);
    			vertices[i][j].setImageResource(R.drawable.settlement_gray);
//    			vertices[i][j].setMinimumHeight(minHeight);
//    			vertices[i][j].layout(20, 20, 20, 20);
//    			vertices[i][j].setLayoutParams(getLayoutParams());
//    			vertices[i][j].setBackgroundResource(R.drawable.bandit3_opp);
    			//vertices[i][j].setBackgroundDrawable(R.drawable.bandit3_opp);
    		}
    	}
    	
    	//Handle the top left part of the matrix:
    	vertices[ 0][ 0] = null;	edges[ 0][ 0] = null;
    	vertices[ 0][ 1] = null;	edges[ 1][ 0] = null;
    	vertices[ 1][ 0] = null;	edges[ 0][ 1] = null;
    	//Handle the top right part of the matrix:    	
    	vertices[ 9][ 0] = null;	edges[ 0][ 9] = null;
    	vertices[10][ 0] = null;	edges[ 0][10] = null;
    	vertices[10][ 1] = null;	edges[ 1][10] = null;
    	//Handle the bottom left part of the matrix:
    	vertices[ 0][ 4] = null;	edges[ 0][ 4] = null;
    	vertices[ 0][ 5] = null;	edges[ 0][ 5] = null;
    	vertices[ 1][ 5] = null;	edges[ 1][ 5] = null;
    	//Handle the bottom right part of the matrix:
    	vertices[10][ 4] = null;	edges[ 4][10] = null;
    	vertices[10][ 5] = null;	edges[ 5][10] = null;
    	vertices[ 9][ 5] = null;	edges[ 5][ 9] = null;
    }

}