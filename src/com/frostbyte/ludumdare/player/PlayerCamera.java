package com.frostbyte.ludumdare.player;

import com.frostbyte.ludumdare.entities.Entity;
import com.frostbyte.ludumdare.main.GameFrame;
import com.frostbyte.ludumdare.map.Map;

public class PlayerCamera {
	private Map map;
	private Entity entity;
	private int x, y;
	private boolean yStop;

	public PlayerCamera(Map map, Entity entity) {
		this.map = map;
		this.entity = entity;

		if (map.getMenu().gameManager.getCurrentMenu() == 2) {
			y = 895;
		}else{
			y = 150;
		}
	}

	public void update() {
		if(y < 200 && map.getMenu().gameManager.getCurrentMenu() == 2){
			y = 895;
		}
		
		if (GameFrame.WIDTH / 2 <= entity.x && ((map.getBlocks().length - 1) * 15) > entity.x + (GameFrame.WIDTH / 2)) {
			x = entity.x - GameFrame.WIDTH / 2;
		}

		if ((GameFrame.HEIGHT / 2 < entity.y && (map.getBlocks()[0].length * 15) >= entity.y + (GameFrame.HEIGHT / 2)) && !(((map.getBlocks().length - 1) * 15) - (GameFrame.HEIGHT / 2) < entity.y)) {
			y = entity.y - (GameFrame.HEIGHT / 2);
		}
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
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

	public boolean isyStop() {
		return yStop;
	}

	public void setyStop(boolean yStop) {
		this.yStop = yStop;
	}
}
