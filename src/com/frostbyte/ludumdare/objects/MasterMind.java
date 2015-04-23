package com.frostbyte.ludumdare.objects;

import java.util.ArrayList;
import java.util.List;

import com.frostbyte.ludumdare.blocks.Material;
import com.frostbyte.ludumdare.map.Map;

public class MasterMind {
	private Map map;
	private List<MasterComputer> computers = new ArrayList<MasterComputer>();
	private Location location;
	private int needed;
	private boolean enabled = true;
	private SlidingDoor door;

	public MasterMind(Map map, Location location, List<MasterComputer> computers, int needed, SlidingDoor door) {
		this.map = map;
		this.location = location;
		this.computers = computers;
		this.needed = needed;
		this.door = door;
		checkMaster();
	}

	public void checkMaster() {
		int added = 0;
		for (MasterComputer computer : computers) {
			if (computer.isActive()) {
				added += computer.getVoltage();
			}
		}

		if (added > needed) {
			map.getOverlay()[location.getX()][location.getY()].setMaterial(Material.POWERBOX_3);
		} else if (added < needed) {
			map.getOverlay()[location.getX()][location.getY()].setMaterial(Material.POWERBOX_1);
		} else {
			map.getOverlay()[location.getX()][location.getY()].setMaterial(Material.POWERBOX_2);
			if (door != null) {
				door.open();
			} else {
				map.getMenu().gameManager.setCurrentMenu(1);
				map.getMenu().gameManager.musicPlayer.currentList = MusicPlayer.gameSongs;
				map.getMenu().gameManager.musicPlayer.currentLength = MusicPlayer.gameSongs.get(0).getLength();
				SoundUtil.playSong(Song.TRACK_4);
			}
			enabled = false;
		}
	}

	public void update() {
		if (door != null) {
			door.update();
		}
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public List<MasterComputer> getComputers() {
		return computers;
	}

	public void setComputers(List<MasterComputer> computers) {
		this.computers = computers;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public SlidingDoor getDoor() {
		return door;
	}

	public void setDoor(SlidingDoor door) {
		this.door = door;
	}
}
