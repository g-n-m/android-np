package com.itk.gasnami.catan;

import android.content.Context;

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

}
