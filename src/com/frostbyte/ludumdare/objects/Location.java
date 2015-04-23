package com.frostbyte.ludumdare.objects;

import com.frostbyte.ludumdare.map.Map;

public class Location {
	private Map map;
	private int x, y;
	
	public Location(Map map, int x, int y){
		this.map = map;
		this.x = x;
		this.y = y;
	}
	
	public Location add(int x, int y){
		return new Location(getMap(), this.x + x, this.y + y);
	}
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
