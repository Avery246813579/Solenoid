package com.frostbyte.ludumdare.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.frostbyte.ludumdare.map.Map;

public class Weapon {
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private int x, y;
	private Map map;
	
	public Weapon(Map map, int x, int y){
		this.map = map;
		this.x = x;
		this.y = y;
	}
	
	public void shootProjectile(boolean right){
		Projectile projectile = new Projectile(this, map, x, y);
		
		if(right){
			projectile.shoot(true);
		}else{
			projectile.shoot(false);
		}
		
		projectiles.add(projectile);
	}
	
	public void update(){
		if(!projectiles.isEmpty()){
			for(Projectile projectile : projectiles){
				projectile.update();
				
				if(projectile.isDead){
					break;
				}
			}
		}
	}
	
	public void draw(Graphics graphics){
		if(!projectiles.isEmpty()){
			for(Projectile projectile : projectiles){
				projectile.draw(graphics);
				
				if(projectile.isDead){
					break;
				}
			}
		}
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

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(List<Projectile> projectiles) {
		this.projectiles = projectiles;
	}
}
