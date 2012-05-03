package com.itk.gasnami.catan;

import com.itk.gasnami.catan.Landing.Player;

public class Border {
	
//	//for edges
//	public enum PathElement {
//		enabled, road //Later ship could be added for example...
//	}
	
	//TODO: implement clickable stuff
	
	protected Player owner;
	
	public Border() {
		this.owner = Player.none;
	}
	
	public void build(Player player) {
		this.owner = player;
	}

	@Override
	public  String toString() {
		return this.owner.name();
	}
}
