package com.frostbyte.ludumdare.entities;

import java.awt.Graphics;

import com.frostbyte.ludumdare.blocks.Block;
import com.frostbyte.ludumdare.blocks.Material;
import com.frostbyte.ludumdare.map.Map;
import com.frostbyte.ludumdare.objects.Vector;

public class Projectile {
	public Vector velocity = new Vector(0, 0);
	public int x, y, moveX = 10, moveY = 2;
	public boolean isDead = false;
	public Weapon weapon;
	public Map map;

	public Projectile(Weapon weapon, Map map, int x, int y) {
		this.weapon = weapon;
		this.map = map;
		this.x = x;
		this.y = y;
	}

	public void shoot(boolean right) {
		int movingX = moveX;

		if (!right) {
			this.moveX = -moveX;
		}
		
		velocity.setX(movingX);
	}

	public void draw(Graphics g) {
		if (velocity.getX() > 0) {
			g.drawImage(Material.CEILING_VENT.getBufferedImage(), x - map.getCamera().getX(), y - map.getCamera().getY(), null);
		} else {
			g.drawImage(Material.CEILING_VENT.getBufferedImage(), x - map.getCamera().getX(), y - map.getCamera().getY(), null);
		}
	}

	public void update() {
		int xLocation = 0;
		
		if(velocity.getX() > 0){
			xLocation = (x + 15 + velocity.getX()) / 15;
		}else{
			xLocation =  (x - velocity.getX()) / 15;
		}
		
		if(xLocation >= (map.getBlocks().length - 1)){
			kill();
		}
		
		if(xLocation <= -1){
			kill();
		}
		
		Block block = map.getBlocks()[xLocation][(y / 15)];
		if(block.getMaterial().isSolid()){
			kill();
		}
		
		for(Entity entity : map.getEntities()){
			if(entity.x > x && entity.x < x + 10){
				if(entity.y < y + velocity.getY() &&  entity.y + entity.height > y + velocity.getY()){
					entity.DEAD = true;
					kill();
				}
			}
		}
		
		x += velocity.getX();
		y += velocity.getY();
	}
	
	public void kill(){
		isDead = true;
		weapon.getProjectiles().remove(this);
	}
}
