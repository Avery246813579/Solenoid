package com.frostbyte.ludumdare.objects;

import java.util.ArrayList;
import java.util.List;

import com.frostbyte.ludumdare.blocks.Material;
import com.frostbyte.ludumdare.map.Map;

public class SlidingDoor {
	private List<Location> door = new ArrayList<Location>();
	private boolean active = false, open = false;
	private Location lever;
	private Map map;
	Song song;
	int buffer;

	public SlidingDoor(Map map, List<Location> door, Location lever) {
		this.door = door;
		this.lever = lever;
		this.map = map;
	}

	public SlidingDoor(Map map, List<Location> door, Location lever, Song song) {
		this.door = door;
		this.lever = lever;
		this.map = map;
		this.song = song;
	}

	public void update() {
		if (open) {
			if (buffer > 20) {
				if (door.size() > 0) {
					if (map.getMenu().gameManager.getCurrentMenu() == 2) {
						map.getOverlay()[door.get(0).getX()][door.get(0).getY()].setMaterial(Material.IRON_WALL_2);
					} else {
						map.getOverlay()[door.get(0).getX()][door.get(0).getY()].setMaterial(Material.BRICK_WALL);
					}
					door.remove(0);
					buffer = 0;
				} else {
					open = false;
				}
			} else {
				buffer++;
			}
		}
	}

	public void open() {
		open = true;
		active = true;
		
		if (song != null) {
			SoundUtil.playSong(song);
		}
	}

	public List<Location> getDoor() {
		return door;
	}

	public void setDoor(List<Location> door) {
		this.door = door;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Location getLever() {
		return lever;
	}

	public void setLever(Location lever) {
		this.lever = lever;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}
