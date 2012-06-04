package com.itk.gasnami.catan;

import java.util.Iterator;
import java.util.Set;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

public class ResizeTask extends AsyncTask<Pair<Float, Resources>, 
											Void, 
											Boolean > {
	// This is doing all the bitmap resize in a background thread 
	@Override
	protected Boolean doInBackground(
			Pair<Float, Resources>... params) {
		
		Set<Integer> keys = Panel.bitmapCache.keySet();
		Iterator<Integer> itr = keys.iterator();
		Resources res = params[0].second;
	
		while(itr.hasNext()) {
			int index = itr.next();
			try {
				int withSize = 0; int heightSize = 0;
				
				// Case of Chipses
				if(index < 13){
					withSize =SizeHandler.getChipsSize(); heightSize = SizeHandler.getChipsSize();
				}
				// Case of Landing tiles
				else if(index < 34){
					withSize =SizeHandler.getLandingWidth(); heightSize = SizeHandler.getLandingHeight();
				}
				// Case of buildings & roads
				else if(index < 60){
					withSize =SizeHandler.getBuildingWidth(); heightSize = SizeHandler.getBuildingHeight();
				}
				
				else if(index < 70){
					withSize =SizeHandler.getBuildingHeight(); heightSize = SizeHandler.getBuildingWidth();
				}
				
				Panel.bitmapCache.put(index, new Pair<Bitmap, Integer>(
		    			Bitmap.createScaledBitmap(
		   					BitmapFactory.decodeResource(
	  							res, 
	    						Panel.bitmapCache.get(index).second),
	    						withSize, heightSize, true),
		    					Panel.bitmapCache.get(index).second) );
			}
			catch (Exception e) {
				Log.e("MyTag", "Failure to get drawable id.", e);
				return false;
			}
		}
			
		return true;
	}
}
