package com.itk.gasnami.catan;

import android.util.Pair;

public class SizeHandler {
	
//	private
	static int screenFactor = 4; //TODO: should depend on the screensize!
	int landingWidth;
	int landingHeight;
	int landingVPadding;
	int chipsSize;
	
	int displayWidth ;
	int displayHeight;
	int positionPaddingHorizontal;
	int posiotionPaddingVertical;
	
	public SizeHandler(int displayWidth, int displayHeight) {
		this(displayWidth, displayHeight, 4);
	}
	
	public SizeHandler(int displayWidth, int displayHeight, int screenFactor) {
		this.displayWidth  = displayWidth;
		this.displayHeight = displayHeight;
		this.screenFactor  = screenFactor;
		
		calculateInitials();
	}
	
	private void calculateInitials() {
		landingWidth = (int)338/screenFactor;
		landingHeight = (int)390/screenFactor;
		landingVPadding = (int)292/screenFactor;
		chipsSize = (int)124/screenFactor;
		
		positionPaddingHorizontal = (displayWidth - 5 * landingWidth) / 2;
		posiotionPaddingVertical  = (displayHeight - 4 * landingVPadding - landingHeight - landingHeight / 2) / 2;
	}

	//TODO: implement for landingTiles and chipses
	public Pair<Integer, Integer> getLandingCoordinates(Pair<Integer, Integer> positions) {
		
		int i = positions.first;
		int j = positions.second;
		
		int temp = (j < 3) ? (3 - j - 1) : (j + 1 - 3);
		double hPadding = temp / 2.0;
		return new Pair<Integer, Integer>((int)((i + hPadding) * landingWidth) + positionPaddingHorizontal,
				j * landingVPadding + posiotionPaddingVertical);
	}
	
	//TODO: implement rescaling functionality
	public void reScale(float scaleFactor) {
		calculateInitials();
		
		landingWidth *= scaleFactor;
		landingHeight *= scaleFactor;
		landingVPadding *= scaleFactor;
		chipsSize *= scaleFactor;
		
		positionPaddingHorizontal = (displayWidth - 5 * landingWidth) / 2;
		posiotionPaddingVertical  = (displayHeight - 4 * landingVPadding - landingHeight - landingHeight / 2) / 2;
		
	}
}