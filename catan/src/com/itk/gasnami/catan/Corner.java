package com.itk.gasnami.catan;

import com.itk.gasnami.catan.Landing.Player;

public class Corner extends Border {
	
	//for vertices
	public enum Fundament {
		enabled, settlement, city, disabled
	}
	
	//TODO: Ports?
	Fundament fundament;
	
	public Corner() {
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
