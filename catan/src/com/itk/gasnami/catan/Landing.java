package com.itk.gasnami.catan;

import java.util.HashMap;

import android.graphics.Bitmap;
import android.util.Pair;

public class Landing {

	public enum Player {
		Player1, Player2, Player3, Player4
	}

	public enum Resource {
		brick, lumber, wool, grain, ore, none
	}

	private int resourceNumber = 0; // bitmap ehhez is?
	private Bitmap bitmap4Land;
	private HashMap<Player, Integer> production;
	private Resource providedResource; // összefügg a typeOfLand - el!
	private Pair<Integer, Integer> coordinates; // tesztMember a rajzoláshoz
	private boolean isActive = true;

	public Landing(int resourceNumber, Bitmap bitmap4Land,
			Resource providedResource, Pair<Integer, Integer> coordinates) {
		super();
		this.resourceNumber = resourceNumber;
		this.bitmap4Land = bitmap4Land;
		this.production = null;
		this.providedResource = providedResource;
		this.coordinates = coordinates;
	}

	public Landing(int resourceNumber, Bitmap bitmap4Land,
			HashMap<Player, Integer> production, Resource providedResource,
			Pair<Integer, Integer> coordinates) {
		super();
		this.resourceNumber = resourceNumber;
		this.bitmap4Land = bitmap4Land;
		this.production = production;
		this.providedResource = providedResource;
		this.coordinates = coordinates;
	}
	
	public Landing(int resourceNumber, Bitmap bitmap4Land,
			HashMap<Player, Integer> production, Resource providedResource,
			Pair<Integer, Integer> coordinates, boolean isActive) {
		super();
		this.resourceNumber = resourceNumber;
		this.bitmap4Land = bitmap4Land;
		this.production = production;
		this.providedResource = providedResource;
		this.coordinates = coordinates;
		this.isActive = isActive;
	}

	public HashMap<Player, Integer> getProduction() {
		return production;
	}

	public void setProduction(HashMap<Player, Integer> production) {
		this.production = production;
	}

	public int getResourceNumber() {
		return resourceNumber;
	}

	public Bitmap getBitmap4Land() {
		return bitmap4Land;
	}

	public Resource getProvidedResource() {
		return providedResource;
	}

	public Pair<Integer, Integer> getCoordinates() {
		return coordinates;
	}
	
	public boolean getIsActive() {
		return isActive;
	}
	
	public void setIsActive(boolean isActive){
		this.isActive = isActive;
	}
}
