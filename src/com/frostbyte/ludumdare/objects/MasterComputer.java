package com.frostbyte.ludumdare.objects;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.frostbyte.ludumdare.entities.Entity;
import com.frostbyte.ludumdare.menus.Menu;
import com.frostbyte.ludumdare.menus.TestMenu;

public class MasterComputer {
	private Location location;
	private boolean active = false;
	private int voltage;
	private Menu menu;

	public MasterComputer(Menu menu, Location location, int voltage) {
		this.location = location;
		this.voltage = voltage;
		this.menu = menu;
	}
	
	public boolean intercepts(Entity entity) {
		if (menu.masterMind != null) {
			if(!menu.masterMind.isEnabled()){
				return false;
			}
			
			Rectangle2D playerRect = new Rectangle(entity.x, entity.y - (entity.width / 2), entity.width, entity.height);
			for (int x = location.getX(); x < location.getX() + 4; x++) {
				for (int y = location.getY(); y > location.getY() - 2; y--) {
					Rectangle2D blockRect = new Rectangle(x * 15, y * 15, 15, 15);

					if (playerRect.intersects(blockRect)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getVoltage() {
		return voltage;
	}

	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Menu getMenu(){
		return menu;
	}
	
	public void setMenu(TestMenu menu) {
		this.menu = menu;
	}
}
