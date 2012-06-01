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

	@Override
	protected Boolean doInBackground(
			Pair<Float, Resources>... params) {
		
		Set<Integer> keys = Panel.bitmapCache.keySet();
		Iterator<Integer> itr = keys.iterator();
		Resources res = params[0].second;
		float scaleFactor = params[0].first;
	
		while(itr.hasNext()) {
			int index = itr.next();
			try {
				BitmapFactory.Options opts = new BitmapFactory.Options();
				opts.inSampleSize = (int)(1.0f * SizeHandler.getScreenFactor()/scaleFactor);
		    
				if(index<13){
					Panel.bitmapCache.put(index, new Pair<Bitmap, Integer>(
		    			Bitmap.createScaledBitmap(
		   					BitmapFactory.decodeResource(
	  							res, 
	    						Panel.bitmapCache.get(index).second),
	    						SizeHandler.getChipsSize(), SizeHandler.getChipsSize(), true),
		    					Panel.bitmapCache.get(index).second) );
				}
				else if(index<34){
					Panel.bitmapCache.put(index, new Pair<Bitmap, Integer>(
		    			Bitmap.createScaledBitmap(
		   					BitmapFactory.decodeResource(
								res, 
	    						Panel.bitmapCache.get(index).second),
		    					SizeHandler.getLandingWidth(), SizeHandler.getLandingHeight(), true),
		    					Panel.bitmapCache.get(index).second) );
				}
			}
			catch (Exception e) {
				Log.e("MyTag", "Failure to get drawable id.", e);
				return false;
			}
		}
			
		return true;
	}

//	@Override
//	protected void onPostExecute(Boolean result) {
//		if(result) {
//			
//		}
//	}
}
