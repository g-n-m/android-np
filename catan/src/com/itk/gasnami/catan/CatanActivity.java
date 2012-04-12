package com.itk.gasnami.catan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import com.itk.gasnami.catan.Landing.Player;
import com.itk.gasnami.catan.Landing.Resource;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CatanActivity extends Activity {
	
	HashMap<Integer, Pair<Landing, Landing> > landingTiles = 
			new HashMap<Integer, Pair<Landing,Landing>>();
	int nOfPlayers = 2; //bővítés esetén erre kell alternatíva
	Button debugBtn;
	
	class myClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			
//			ArrayList<Integer> resourceNumbers = new ArrayList<Integer>();
//			resourceNumbers.addAll(Arrays.asList( 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12 ));
			String debugText = new String();
			debugText +="|rNr: " + landingTiles.get(7).first.getResourceNumber();
			debugText +="|bMp: " + landingTiles.get(7).first.getBitmap4Land();
			debugText +="|iAc: " + landingTiles.get(7).first.getIsActive();
			debugText +="|pRs: " + landingTiles.get(7).first.getProvidedResource();
			debugText +="|coo: " + landingTiles.get(7).first.getCoordinates().first 
					+ " " + landingTiles.get(7).first.getCoordinates().second;
			debugText +="|pRp: " + landingTiles.get(7).first.getProduction().size();
			for(int i = 0; i < landingTiles.get(7).first.getProduction().size(); i++) {
				debugText +=" " + 
				landingTiles.get(7).first.getProduction().get(Player.values()[i]);
			}
//			Toast.makeText(CatanActivity.this, debugText, Toast.LENGTH_LONG).show();
			Toast.makeText(CatanActivity.this, landingTiles.keySet().toString(), Toast.LENGTH_SHORT).show();
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
//        setContentView(R.layout.main);
        setContentView(new Panel(this));
//        InitializeTiles();
//        debugBtn = (Button) findViewById(R.id.debugBtn);
//        debugBtn.setOnClickListener(new myClickListener());
//        
    }
    
//    //TODO: change the size of the bitmaps and the coordinates!
//    public void InitializeTiles() {
//    	//private void fillBitmapCache() ??
//    	ArrayList<Integer> resourceNumbers = new ArrayList<Integer>();
//		resourceNumbers.addAll(Arrays.asList( 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12 ));
//    	
//    	HashMap<Player, Integer> productionTest = CreateProductionHash();
//    	
//    	landingTiles.put(7, new Pair<Landing, Landing>(
//    			new Landing(7, BitmapFactory.decodeResource(getResources(), 
//    					R.drawable.desert), productionTest, Resource.none, 
//    					new Pair<Integer, Integer>(0, 0), false), 
//    			null));
//    	
//    	GenerateTilesByType(4, resourceNumbers, R.drawable.forest, Resource.lumber, new Pair<Integer, Integer>(0, 0));
//    	GenerateTilesByType(4, resourceNumbers, R.drawable.pasture, Resource.wool, new Pair<Integer, Integer>(0, 0));
//    	GenerateTilesByType(4, resourceNumbers, R.drawable.field, Resource.grain, new Pair<Integer, Integer>(0, 0));
//    	GenerateTilesByType(3, resourceNumbers, R.drawable.hill, Resource.brick, new Pair<Integer, Integer>(0, 0));
//    	GenerateTilesByType(3, resourceNumbers, R.drawable.mountain, Resource.ore, new Pair<Integer, Integer>(0, 0));
//    }
//    
//    //Generating tiles of a given type
//    public void GenerateTilesByType(int nOfTiles, ArrayList<Integer> resourceNumbers, 
//    		int drawable, Resource resource, Pair<Integer, Integer> coordinates ) {
//    	Random mRnd = new Random();
//    	
//    	for (int i = 0; i < nOfTiles; i++) {
//    		HashMap<Player, Integer> productionTemp = CreateProductionHash();
//    		
//    		int randomed = mRnd.nextInt(resourceNumbers.size());
//    		
//    		int resourceNumber = resourceNumbers.remove(randomed);
//    		
//    		Landing tempLandingTile = 
//    				new Landing(resourceNumber, BitmapFactory.decodeResource(getResources(), drawable), 
//    						productionTemp, resource, coordinates, true);
//
//    		//TODO: külön fv-be?
//    		if(!landingTiles.containsKey(resourceNumber)) {
//    			landingTiles.put(resourceNumber, new Pair<Landing, Landing>(tempLandingTile, null));
//    		} else {
//    			landingTiles.put(resourceNumber, new Pair<Landing, Landing>(landingTiles.get(resourceNumber).first, tempLandingTile));
//    		}
//    	}
//    }
//    
//    //Initialize a production hash with zero elements
//    public HashMap<Player, Integer> CreateProductionHash(){
//    	HashMap<Player, Integer> productionTemp = new HashMap<Player, Integer>(); 
//    	for(int j = 0; j < nOfPlayers; j++){
//    		productionTemp.put(Player.values()[j], 0);
//    	}
//    	
//    	return productionTemp;
//    }
}