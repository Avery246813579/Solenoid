package com.frostbyte.ludumdare.menus;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.frostbyte.ludumdare.main.GameManager;
import com.frostbyte.ludumdare.map.Map;
import com.frostbyte.ludumdare.objects.MasterMind;
import com.frostbyte.ludumdare.objects.SlidingDoor;

public abstract class Menu{
	private List<SlidingDoor> doors = new ArrayList<SlidingDoor>();
	public MasterMind masterMind;
	Map map;
	public GameManager gameManager;
	
	public Menu(GameManager gameManager){
		this.gameManager = gameManager;
	}

	
	public abstract void draw(Graphics graphics);
	public abstract void update();
	
	public abstract void keyPressed(int keyCode);
	public abstract void keyReleased(int keyCode);
	public abstract void mouseClick(int button, int x, int y);
	public abstract void mouseMove(int x, int y);
	public abstract void wheelMove(int rotation);
	
	public List<SlidingDoor> getDoors() {
		return doors;
	}
	
	public void setDoors(List<SlidingDoor> doors) {
		this.doors = doors;
	}


	public void start() {
		
	}
}
