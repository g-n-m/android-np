package com.itk.gasnami.catan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.itk.gasnami.catan.Landing.Player;
import com.itk.gasnami.catan.Landing.Resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class Panel extends SurfaceView implements SurfaceHolder.Callback{
	//Drawing parameters
	private static int scaleFactor = 4; //TODO: should depend on the screensize!
	int newWidth = (int)338/scaleFactor;
	int newHeight = (int)390/scaleFactor;
	int vPadding = (int)292/scaleFactor;
	
	Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	int displayWidth = display.getWidth();
	int displayHeight = display.getHeight();
	int horizontalPadding = (displayWidth - 5 * newWidth) / 2;
	int verticalPadding = (displayHeight - 4 * vPadding - newHeight - newHeight / 2) / 2;
	
	private HashMap<Integer, Bitmap> bitmapCache = new HashMap<Integer, Bitmap>();
	
	//Game parameters
	private CatanThread thread;
	
	private HashMap<Integer, Pair<Landing, Landing> > landingTiles = 
			new HashMap<Integer, Pair<Landing,Landing>>();
	
	private int nOfPlayers = 2; //bővítés esetén erre kell alternatíva
	
	//private Graphic _currentGraphic = null;

	public Panel(Context context) {
		super(context);
		fillBitmapCache();
        InitializeTiles();
		getHolder().addCallback(this);
		thread = new CatanThread(this);
		setFocusable(true);
	}
	
	//TODO: a landingTiles-t érdemes ide tenni?
	private void fillBitmapCache() {
        bitmapCache.put(7,  BitmapFactory.decodeResource(getResources(), R.drawable.bandit3));
        bitmapCache.put(2,  BitmapFactory.decodeResource(getResources(), R.drawable.c02));
        bitmapCache.put(3,  BitmapFactory.decodeResource(getResources(), R.drawable.c03));
        bitmapCache.put(4,  BitmapFactory.decodeResource(getResources(), R.drawable.c05));
        bitmapCache.put(5,  BitmapFactory.decodeResource(getResources(), R.drawable.c05));
        bitmapCache.put(6,  BitmapFactory.decodeResource(getResources(), R.drawable.c06_r));
        bitmapCache.put(8,  BitmapFactory.decodeResource(getResources(), R.drawable.c08_r));
        bitmapCache.put(9,  BitmapFactory.decodeResource(getResources(), R.drawable.c09));
        bitmapCache.put(10, BitmapFactory.decodeResource(getResources(), R.drawable.c10));
        bitmapCache.put(11, BitmapFactory.decodeResource(getResources(), R.drawable.c11));
        bitmapCache.put(12, BitmapFactory.decodeResource(getResources(), R.drawable.c12));
        bitmapCache.put(R.drawable.water,  BitmapFactory.decodeResource(getResources(), R.drawable.water));
    }
	
    //TODO: change the size of the bitmaps and the coordinates!
    public void InitializeTiles() {
    	//private void fillBitmapCache() ??
    	ArrayList<Integer> resourceNumbers = new ArrayList<Integer>();
		resourceNumbers.addAll(Arrays.asList( 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12 ));
		
		ArrayList<Pair<Integer, Integer>> coordinates = new ArrayList<Pair<Integer,Integer>>();
		for(int j = 0; j < 5; j++) {
			int iMax = 5;
			int temp = (j < 3) ? (j + 1 - 3) : ( 3 - j - 1);
			iMax += temp;
			double hPadding = (-1.0) * temp / 2.0;
			  
			for(int i = 0; i < iMax; i++) {
				coordinates.add(new Pair<Integer, Integer>((int)((i + hPadding) * newWidth) + horizontalPadding, j * vPadding + verticalPadding));  
			}
		}
    	    	
    	GenerateTilesByType(4, resourceNumbers, R.drawable.forest, Resource.lumber, coordinates);
    	GenerateTilesByType(4, resourceNumbers, R.drawable.pasture, Resource.wool, coordinates);
    	GenerateTilesByType(4, resourceNumbers, R.drawable.field, Resource.grain, coordinates);
    	GenerateTilesByType(3, resourceNumbers, R.drawable.hill, Resource.brick, coordinates);
    	GenerateTilesByType(3, resourceNumbers, R.drawable.mountain, Resource.ore, coordinates);
    	
    	//Adding the desert tile
    	HashMap<Player, Integer> productionTest = CreateProductionHash();
    	
    	landingTiles.put(7, new Pair<Landing, Landing>(
    			new Landing(7, BitmapFactory.decodeResource(getResources(), 
    					R.drawable.desert), productionTest, Resource.none, 
    					coordinates.get(0), false), 
    			null));
    }
    
    //Generating tiles of a given type
    public void GenerateTilesByType(int nOfTiles, ArrayList<Integer> resourceNumbers, 
    		int drawable, Resource resource, ArrayList<Pair<Integer, Integer>> coordinates ) {
    	Random mRnd = new Random();
    	
    	for (int i = 0; i < nOfTiles; i++) {
    		HashMap<Player, Integer> productionTemp = CreateProductionHash();
    		
    		int randomed = mRnd.nextInt(resourceNumbers.size());
    		int resourceNumber = resourceNumbers.remove(randomed);
    		
    		randomed = mRnd.nextInt(coordinates.size());
    		Pair<Integer, Integer> coordinate = coordinates.remove(randomed);
    		
    		Landing tempLandingTile = 
    				new Landing(resourceNumber, BitmapFactory.decodeResource(getResources(), drawable), 
    						productionTemp, resource, coordinate, true);

    		if(!landingTiles.containsKey(resourceNumber)) {
    			landingTiles.put(resourceNumber, new Pair<Landing, Landing>(tempLandingTile, null));
    		} else {
    			landingTiles.put(resourceNumber, new Pair<Landing, Landing>(landingTiles.get(resourceNumber).first, tempLandingTile));
    		}
    	}
    }
    
    //Initialize a production hash with zero elements
    public HashMap<Player, Integer> CreateProductionHash(){
    	HashMap<Player, Integer> productionTemp = new HashMap<Player, Integer>(); 
    	for(int j = 0; j < nOfPlayers; j++){
    		productionTemp.put(Player.values()[j], 0);
    	}
    	
    	return productionTemp;
    }

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
        if (!thread.isAlive()) {                                                                                                                                                                 
            thread = new CatanThread(this);                                                                                                                                          
        }                                                                                                                                                                                         
        thread.setRunning(true);                                                                                                                                                                 
        thread.start();		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;                                                                                                                                                                     
        thread.setRunning(false);                                                                                                                                                                
        while (retry) {                                                                                                                                                                           
            try {                                                                                                                                                                                 
                thread.join();                                                                                                                                                                   
                retry = false;                                                                                                                                                                    
            } catch (InterruptedException e) {                                                                                                                                                    
                // we will try it again and again...                                                                                                                                              
            }                                                                                                                                                                                     
        }                                                                                                                                                                                         
        Log.i("thread", "Thread terminated...");
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// draw background
		canvas.drawColor(Color.DKGRAY);
//		for(int j = 0; j < displayHeight / vPadding; j++) {
//			for(int i = 0; i < displayWidth / newWidth; i++)
//			canvas.drawBitmap(Bitmap.createScaledBitmap( 
//					bitmapCache.get(R.drawable.water),
//					newWidth, newHeight, true), 0 + i * newWidth + (int)((j % 2) * 0.5 * newWidth) , 0 + j * vPadding, null);
//		}
		
		// draw tiles
		Bitmap bitmap;
		Pair<Integer, Integer> coordinates;
		
		Collection<Pair<Landing, Landing>> c = landingTiles.values();
		Iterator<Pair<Landing, Landing>> itr = c.iterator();
		
		while(itr.hasNext()) {
			Pair<Landing, Landing> actualLanding = itr.next();
			bitmap = Bitmap.createScaledBitmap(actualLanding.first.getBitmap4Land(),newWidth,newHeight,true);
			coordinates = actualLanding.first.getCoordinates();
			canvas.drawBitmap(bitmap, coordinates.first, coordinates.second, null);
			
			//Resource or Bandit chips
			int xCoord = (int) (coordinates.first + newWidth / 2 - 124 / scaleFactor / 2);
			int yCoord = (int) (coordinates.second + newHeight / 2 - 124 / scaleFactor / 2);
			bitmap = Bitmap.createScaledBitmap(
					bitmapCache.get(actualLanding.first.getResourceNumber()),
					(int)(124 / scaleFactor),(int)(124 / scaleFactor),true);
			coordinates = actualLanding.first.getCoordinates();
			canvas.drawBitmap(bitmap, xCoord, yCoord, null);
			
			if(actualLanding.second != null){
				bitmap = Bitmap.createScaledBitmap(actualLanding.second.getBitmap4Land(),newWidth,newHeight,true);
				coordinates = actualLanding.second.getCoordinates();
				canvas.drawBitmap(bitmap, coordinates.first, coordinates.second, null);
				
//				Resource or Bandit chips
				xCoord = (int) (coordinates.first + newWidth / 2 - 124 / scaleFactor / 2);
				yCoord = (int) (coordinates.second + newHeight / 2 - 124 / scaleFactor / 2);
				bitmap = Bitmap.createScaledBitmap(
						bitmapCache.get(actualLanding.first.getResourceNumber()),
						(int)(124 / scaleFactor),(int)(124 / scaleFactor),true);
				coordinates = actualLanding.first.getCoordinates();
				canvas.drawBitmap(bitmap, xCoord, yCoord, null);
			}
		}
		
	}

}
