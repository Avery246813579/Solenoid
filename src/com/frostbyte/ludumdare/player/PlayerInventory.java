package com.frostbyte.ludumdare.player;

public class PlayerInventory {
	public String[] content = new String[3];
	public int currentItem;
	
	public PlayerInventory(){
		content[0] = "MOVE";
		content[1] = "BINARY";
		content[2] = "FORCE";
	}
}
