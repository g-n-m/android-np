package com.itk.gasnami.catan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import com.itk.gasnami.catan.Corner.Fundament;
import com.itk.gasnami.catan.Landing.Player;
import com.itk.gasnami.catan.Landing.Resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class Panel extends SurfaceView implements SurfaceHolder.Callback{
	//Drawing parameters
	int stepper = 0;
	
	Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	int displayWidth = display.getWidth();
	int displayHeight = display.getHeight();	
    SizeHandler sizeHandler = new SizeHandler(displayWidth, displayHeight);
	
    //Drag & pinch zoom parameters
	private static final int INVALID_POINTER_ID = -1;
    private float mPosX;
    private float mPosY;

    private float mLastTouchX;
    private float mLastTouchY;
    private int mActivePointerId = INVALID_POINTER_ID;

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
	
    //Handle bitmaps
    //TODO: ezt is át kéne helyezni? Lekérdezések?
	public HashMap<Integer, Bitmap> bitmapCache = new HashMap<Integer, Bitmap>();
	
	//Game parameters
	private CatanThread thread;
	
	private HashMap<Integer, Pair<Landing, Landing> > landingTiles = 
			new HashMap<Integer, Pair<Landing,Landing>>();
	
	//TODO: visibility should be changed!
	//TODO: ennek saját hely kéne, a Panel fölé kell egy manager osztály!
	public Corner[][] vertices = new Corner[11][6];	
	public Border[][] edges = new Border[6][11];
		
	
	//NOTE: bővítés esetén erre kell alternatíva
	//TODO: Valószínűleg paraméterben kéne érkezzen
	private int nOfPlayers = 2; 

	public Panel(Context context) {
		super(context);
		fillBitmapCache();
        InitializeTiles();
        InitializeBorders();
		getHolder().addCallback(this);
		thread = new CatanThread(this);
		setFocusable(true);
		
		// Create our ScaleGestureDetector
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
	}
	
	private void fillBitmapCache() {
		//Chipses
		bitmapCache.put(0,  BitmapFactory.decodeResource(getResources(), R.drawable.none));
        bitmapCache.put(7,  BitmapFactory.decodeResource(getResources(), R.drawable.bandit3));
        bitmapCache.put(2,  BitmapFactory.decodeResource(getResources(), R.drawable.c02));
        bitmapCache.put(3,  BitmapFactory.decodeResource(getResources(), R.drawable.c03));
        bitmapCache.put(4,  BitmapFactory.decodeResource(getResources(), R.drawable.c04));
        bitmapCache.put(5,  BitmapFactory.decodeResource(getResources(), R.drawable.c05));
        bitmapCache.put(6,  BitmapFactory.decodeResource(getResources(), R.drawable.c06_r));
        bitmapCache.put(8,  BitmapFactory.decodeResource(getResources(), R.drawable.c08_r));
        bitmapCache.put(9,  BitmapFactory.decodeResource(getResources(), R.drawable.c09));
        bitmapCache.put(10, BitmapFactory.decodeResource(getResources(), R.drawable.c10));
        bitmapCache.put(11, BitmapFactory.decodeResource(getResources(), R.drawable.c11));
        bitmapCache.put(12, BitmapFactory.decodeResource(getResources(), R.drawable.c12));
        //LandingTiles
        bitmapCache.put(20, BitmapFactory.decodeResource(getResources(), R.drawable.hill));
        bitmapCache.put(21, BitmapFactory.decodeResource(getResources(), R.drawable.forest));
        bitmapCache.put(22, BitmapFactory.decodeResource(getResources(), R.drawable.pasture));
        bitmapCache.put(23, BitmapFactory.decodeResource(getResources(), R.drawable.field));
        bitmapCache.put(24, BitmapFactory.decodeResource(getResources(), R.drawable.mountain));
        bitmapCache.put(25, BitmapFactory.decodeResource(getResources(), R.drawable.desert));
        //BuildingParts
        bitmapCache.put(30,  BitmapFactory.decodeResource(getResources(), R.drawable.none));
        bitmapCache.put(31, BitmapFactory.decodeResource(getResources(), R.drawable.settlement_gray));
        bitmapCache.put(32, BitmapFactory.decodeResource(getResources(), R.drawable.city_gray));
        //Background
        bitmapCache.put(101,  BitmapFactory.decodeResource(getResources(), R.drawable.water1));
        bitmapCache.put(102,  BitmapFactory.decodeResource(getResources(), R.drawable.water2));
        bitmapCache.put(103,  BitmapFactory.decodeResource(getResources(), R.drawable.water3));
        bitmapCache.put(104,  BitmapFactory.decodeResource(getResources(), R.drawable.water4));
    }
	
	public void InitializeBorders() {
    	for(int j = 0; j < 6; j++) {
    		for(int i = 0; i < 11; i++) {
    			
    			if(i + j > 1 || i + j < 14 || (j == 0 && (i != 9 || i!= 10)) || (j==1 && i!=10)) {
    				vertices[i][j] = new Corner(getContext());
        			edges[j][i] = new Border(getContext());
        			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        			vertices[i][j].setImageBitmap(bitmapCache.get(31));
//        			vertices[i][j].setVisibility(INVISIBLE);
        			vertices[i][j].setLayoutParams( params );
        			//NOTE: stands for debug:
        			vertices[i][j].fundament = Fundament.settlement;
    			}    			
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
	
    public void InitializeTiles() {
    	ArrayList<Integer> resourceNumbers = new ArrayList<Integer>();
		resourceNumbers.addAll(Arrays.asList( 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12 ));
		
		ArrayList<Pair<Integer, Integer>> coordinates = new ArrayList<Pair<Integer,Integer>>();
		
		for(int j = 0; j < 5; j++) {
			int iMax = j % 4 == 2 ? 5 : 4;
			  
			for(int i = j % 4 == 0 ? 1 : 0 ; i < iMax; i++) {
				coordinates.add(new Pair<Integer, Integer>(i, j));
			}
		}
    	    	
    	GenerateTilesByType(4, resourceNumbers, Resource.lumber, coordinates);
    	GenerateTilesByType(4, resourceNumbers, Resource.wool, coordinates);
    	GenerateTilesByType(4, resourceNumbers, Resource.grain, coordinates);
    	GenerateTilesByType(3, resourceNumbers, Resource.brick, coordinates);
    	GenerateTilesByType(3, resourceNumbers, Resource.ore, coordinates);
    	
    	//Adding the desert tile
    	HashMap<Player, Integer> productionTest = CreateProductionHash();
    	
    	landingTiles.put(7, new Pair<Landing, Landing>(
    			new Landing(0, productionTest, Resource.none, coordinates.get(0), false), null));
    }
        
    //Generating tiles of a given type
    public void GenerateTilesByType(int nOfTiles, ArrayList<Integer> resourceNumbers, 
    		Resource resource, ArrayList<Pair<Integer, Integer>> coordinates ) {
    	Random mRnd = new Random();
    	
    	for (int i = 0; i < nOfTiles; i++) {
    		HashMap<Player, Integer> productionTemp = CreateProductionHash();
    		
    		int randomed = mRnd.nextInt(resourceNumbers.size());
    		int resourceNumber = resourceNumbers.remove(randomed);
    		
    		randomed = mRnd.nextInt(coordinates.size());
    		Pair<Integer, Integer> coordinate = coordinates.remove(randomed);
    		
    		Landing tempLandingTile = new Landing(resourceNumber, productionTemp, resource, coordinate, true);

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
    public boolean onTouchEvent(MotionEvent ev) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);
        
        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN: {
            final float x = ev.getX();
            final float y = ev.getY();
            
            mLastTouchX = x;
            mLastTouchY = y;
            mActivePointerId = ev.getPointerId(0);
            break;
        }
            
        case MotionEvent.ACTION_MOVE: {
            final int pointerIndex = ev.findPointerIndex(mActivePointerId);
            final float x = ev.getX(pointerIndex);
            final float y = ev.getY(pointerIndex);

            // Only move if the ScaleGestureDetector isn't processing a gesture.
            if (!mScaleDetector.isInProgress()) {
                final float dx = x - mLastTouchX;
                final float dy = y - mLastTouchY;

                mPosX += dx;
                mPosY += dy;

                invalidate();
            }

            mLastTouchX = x;
            mLastTouchY = y;

            break;
        }
            
        case MotionEvent.ACTION_UP: {
            mActivePointerId = INVALID_POINTER_ID;
            break;
        }
            
        case MotionEvent.ACTION_CANCEL: {
            mActivePointerId = INVALID_POINTER_ID;
            break;
        }
        
        case MotionEvent.ACTION_POINTER_UP: {
            final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) 
                    >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            final int pointerId = ev.getPointerId(pointerIndex);
            if (pointerId == mActivePointerId) {
                // This was our active pointer going up. Choose a new
                // active pointer and adjust accordingly.
                final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                mLastTouchX = ev.getX(newPointerIndex);
                mLastTouchY = ev.getY(newPointerIndex);
                mActivePointerId = ev.getPointerId(newPointerIndex);
            }
            break;
        }
        }
        
        return true;
    }

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// Auto-generated method stub
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
		
//        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mPosX, mPosY);
        sizeHandler.reScale(mScaleFactor);
		
//        XXX: water beckground + animation test...
////		Random mRnd = new Random();
//		for(int j = 0; j < displayHeight / vPadding + 1; j++) {
//			for(int i = 0; i < displayWidth / newWidth + 2; i++)
//				canvas.drawBitmap(Bitmap.createScaledBitmap( 
////					bitmapCache.get(101 + mRnd.nextInt(4)),
////					bitmapCache.get(101 + (stepper + i) % 4),
//					bitmapCache.get(101),
//					newWidth, newHeight, true), 
//					0 + i * newWidth + (int)((j % 2) * 0.5 * newWidth) - (2 * newWidth - horizontalPadding % newWidth), 
//					0 + j * vPadding - (newHeight - verticalPadding % newHeight), null);
//		}
//		stepper = (stepper + 1) % 4;
		
		// draw tiles
		Collection<Pair<Landing, Landing>> c = landingTiles.values();
		Iterator<Pair<Landing, Landing>> itr = c.iterator();
		
		while(itr.hasNext()) {
			Pair<Landing, Landing> actualLanding = itr.next();
			drawLanding(actualLanding.first, canvas);
			
			if(actualLanding.second != null){
				drawLanding(actualLanding.second, canvas);
			}
		}
		
		for(int j = 0; j < 6; j++) {
    		for(int i = 0; i < 11; i++) {
    			
    			if(vertices[i][j] != null) {
    				Bitmap bitmap = Bitmap.createScaledBitmap(bitmapCache.get(30 + vertices[i][j].fundament.ordinal()), 
    						sizeHandler.getBuildingWidth(),sizeHandler.getBuildingHeight(),true);
    				Pair<Integer, Integer> coordinates = sizeHandler.getCornerCoordinates(i, j);
    				canvas.drawBitmap(bitmap, coordinates.first, coordinates.second, null);
    			}    			
    		}
    	}
		
        canvas.restore();
	}
	
	//Gives the bitmap of the landing
	private Bitmap getLandingsBitmap(Resource resource) {
//		if(resource.equals(Resource.lumber)) return bitmapCache.get(31);
		return bitmapCache.get(20 + resource.ordinal());
	}
	
	private void drawLanding(Landing actualLanding, Canvas canvas) {
		//FIXME: itt sokszorozódnak meg a képek! Ezt a bitmapCache kéne tudja?
		Bitmap bitmap = Bitmap.createScaledBitmap(getLandingsBitmap(actualLanding.getProvidedResource()), 
				sizeHandler.getLandingWidth(),sizeHandler.getLandingHeight(),true);
		Pair<Integer, Integer> coordinates = sizeHandler.getLandingCoordinates(actualLanding.getCoordinates()); 
		canvas.drawBitmap(bitmap, coordinates.first, coordinates.second, null);
		
		//Resource or Bandit chips
		int xCoord = (int) (coordinates.first + sizeHandler.getLandingWidth() / 2 - sizeHandler.getChipsSize() / 2);
		int yCoord = (int) (coordinates.second + sizeHandler.getLandingHeight() / 2 - sizeHandler.getChipsSize() / 2);
		bitmap = Bitmap.createScaledBitmap(
			    bitmapCache.get(actualLanding.getIsActive() ? actualLanding.getResourceNumber() : 7),
				sizeHandler.getChipsSize(),sizeHandler.getChipsSize(),true);
		coordinates = actualLanding.getCoordinates();
		canvas.drawBitmap(bitmap, xCoord, yCoord, null);
	}
	
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
	    @Override
	    public boolean onScale(ScaleGestureDetector detector) {
	        mScaleFactor *= detector.getScaleFactor();
	        
	        // Don't let the object get too small or too large.
	        mScaleFactor = Math.max(1.f, Math.min(mScaleFactor, 1.f * sizeHandler.getScreenFactor()));

	        invalidate();
	        return true;
	    }
	}

}
