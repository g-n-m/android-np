package com.itk.gasnami.catan;

import android.util.Pair;

public class SizeHandler {
	
	private static int screenFactor = 4; //TODO: should depend on the screensize!
	private int landingWidth;
	private int landingHeight;
	private int buildingWidth;
	private int buildingHeight;
	private int landingVPadding;
	private int chipsSize;
	
	private int displayWidth ;
	private int displayHeight;
	private int positionPaddingHorizontal;
	private int posiotionPaddingVertical;

	public SizeHandler(int displayWidth, int displayHeight) {
		this(displayWidth, displayHeight, 4);
	}
	
	public SizeHandler(int displayWidth, int displayHeight, int screenFactor) {
		this.displayWidth  = displayWidth;
		this.displayHeight = displayHeight;
		SizeHandler.screenFactor = screenFactor;
		
		calculateInitials();
	}
	
	private void calculateInitials() {
		landingWidth = (int)338/screenFactor;
		landingHeight = (int)390/screenFactor;
		buildingWidth = (int)(27/screenFactor*2.5);
		buildingHeight = (int)(41/screenFactor*2.5);
		landingVPadding = (int)291/screenFactor;
		chipsSize = (int)124/screenFactor;
		
		positionPaddingHorizontal = (displayWidth - 5 * landingWidth) / 2;
		posiotionPaddingVertical  = (displayHeight - 4 * landingVPadding - landingHeight - landingHeight / 2) / 2;
	}

	//TODO: what about chipses?
	public Pair<Integer, Integer> getLandingCoordinates(Pair<Integer, Integer> positions) {
		
		int i = positions.first;
		int j = positions.second;
		
		double hPadding =  (j % 2) / 2.0;
		return new Pair<Integer, Integer>((int)((i + hPadding) * landingWidth) + positionPaddingHorizontal,
				j * landingVPadding + posiotionPaddingVertical);
	}
	
	//TODO: what about chipses?
	public Pair<Integer, Integer> getCornerCoordinates(int x, int y) {
		
		int vPadding = (x + 1) % 2;
		int hPadding =  y % 2;
		return new Pair<Integer, Integer>((int)(x * 0.5 * landingWidth) + positionPaddingHorizontal  - buildingWidth / 2,
				y * landingVPadding + ((vPadding + hPadding) % 2) * (landingVPadding / 3) + posiotionPaddingVertical  - (int)(buildingHeight * 0.85));
	}
	
	// implements rescaling functionality
	public void reScale(float scaleFactor) {
		calculateInitials();
		
		landingWidth *= scaleFactor;
		landingHeight *= scaleFactor;
		buildingWidth *= scaleFactor;
		buildingHeight *= scaleFactor;
		landingVPadding *= scaleFactor;
		chipsSize *= scaleFactor;
		
		positionPaddingHorizontal = (displayWidth - 5 * landingWidth) / 2;
		posiotionPaddingVertical  = (displayHeight - 4 * landingVPadding - landingHeight - landingHeight / 2) / 2;
		
	}
	
	//Getters
	public int getScreenFactor() {
		return screenFactor;
	}
	
	public int getLandingWidth() {
		return landingWidth;
	}

	public int getBuildingHeight() {
		return buildingHeight;
	}
	
	public int getBuildingWidth() {
		return buildingWidth;
	}

	public int getLandingHeight() {
		return landingHeight;
	}

	public int getChipsSize() {
		return chipsSize;
	}
}
