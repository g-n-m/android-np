package com.itk.gasnami.catan;

import java.util.HashMap;

//import android.graphics.Bitmap;
import android.util.Pair;

public class Landing {

	//TODO: ennek máshol kéne legyen a helye
	//NOTE: none stands for free particles
	public enum Player {
		Player1, Player2, Player3, Player4, none
	}

	//NOTE: any stands for Ports
	public enum Resource {
		brick, lumber, wool, grain, ore, none, any
	}

	private int resourceNumber = 0; 
	private HashMap<Player, Integer> production;
	private Resource providedResource;
	private Pair<Integer, Integer> coordinates;
	private boolean isActive = true;

	public Landing(int resourceNumber, 
			Resource providedResource, Pair<Integer, Integer> coordinates) {
		super();
		this.resourceNumber = resourceNumber;
		this.production = null;
		this.providedResource = providedResource;
		this.coordinates = coordinates;
	}

	public Landing(int resourceNumber, 
			HashMap<Player, Integer> production, Resource providedResource,
			Pair<Integer, Integer> coordinates) {
		super();
		this.resourceNumber = resourceNumber;
		this.production = production;
		this.providedResource = providedResource;
		this.coordinates = coordinates;
	}
	
	public Landing(int resourceNumber, 
			HashMap<Player, Integer> production, Resource providedResource,
			Pair<Integer, Integer> coordinates, boolean isActive) {
		super();
		this.resourceNumber = resourceNumber;
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
	
	public void updateProduction(Player player, int quantity) {
		if(this.production.containsKey(player)){
//			this.production.remove(player); // kell ez?
			this.production.put(player, quantity);
		} else {
			// le lehetne kezelni (log?), de exception-el nem biztos, hogy stabil lenne
		}
	}

	public int getResourceNumber() {
		return resourceNumber;
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
