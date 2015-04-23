package com.frostbyte.ludumdare.objects;

public enum Sound {
	LEVER("/SOUND/LEVER.wav"),
	TOGGLE("/SOUND/TOGGLE.wav");
	
	private String location;
	Sound(String location){
		this.location = location;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
}
