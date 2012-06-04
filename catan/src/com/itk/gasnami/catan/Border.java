package com.itk.gasnami.catan;

import android.content.Context;
import android.widget.ImageView;

import com.itk.gasnami.catan.Landing.Player;

public class Border extends ImageView {
	
//	//for edges
//	public enum PathElement {
//		enabled, road //Later ship could be added for example...
//	}
	
	//TODO: Orientation needed / multiple drawings (3 cases)
	protected Player owner;
	
	//FIXME: is this needed?
	public Border() {
		super(null);
		this.setClickable(false);
		this.owner = Player.none;
	}
	
	public Border(Context context) {
		super(context);
		this.setClickable(false);
		this.owner = Player.none;
	}
	
	public void build(Player player) {
		this.owner = player;
	}

	@Override
	public  String toString() {
		return this.owner.name() + "'s";
	}
}
