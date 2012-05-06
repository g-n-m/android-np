package com.itk.gasnami.catan;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.View.MeasureSpec;

import com.itk.gasnami.catan.Landing.Player;

public class Corner extends Border {
	
	//for vertices
	public enum Fundament {
		enabled, settlement, city, disabled
	}
	
	//TODO: Ports?
	//TODO: Coordinates?
	//TODO: Clickable?
	//TODO: Bitmap?
	Fundament fundament;
	
	public Corner() {
		this.setClickable(false);
		this.owner = Player.none;
		this.fundament = Fundament.enabled;
	}
	
	public Corner(Context context) {
		super(context);
		this.setClickable(false);
		this.owner = Player.none;
		this.fundament = Fundament.enabled;
	}
	
	public void build(Player player, Fundament edifice) {
		this.owner = player;
		this.fundament = edifice;
//		TODO: - set the neighbors to disabled state
//		TODO: - updateProduction
	}

	@Override
	public String toString() {
		return this.owner.name() + "'s " + this.fundament.name();
	}
	
	//FIXME: what about overriding onMeasure?
	//TODO: should this placed into Border? In that case would Corner extend it?
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {		
//		DisplayMetrics displayMetrics = new DisplayMetrics();
//		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE); 
//		// the results will be higher than using the activity context object or the getWindowManager() shortcut
//		wm.getDefaultDisplay().getMetrics(displayMetrics);
//		int screenWidth = displayMetrics.widthPixels;
//		
////	    int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
////	    int myWidth = (screenWidth > mData.size()) ? screenWidth : mData.size();
//	    int myWidth = (int) (screenWidth);
//	    super.onMeasure(MeasureSpec.makeMeasureSpec(myWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
//	}

}
