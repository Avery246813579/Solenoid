package com.frostbyte.ludumdare.objects;

public class Vector {
	private int x, y;
	
	public Vector(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public boolean isGoingRight(){
		return x > 0;
	}
	
	public boolean isGoingLeft(){
		return x < 0;
	}
	
	public boolean isGoingUp(){
		return y > 0;
	}
	
	public boolean isGoingDown(){
		return y < 0;
	}
	
	public boolean isMovingVertically(){
		return isGoingUp() || isGoingDown();
	}
	
	public boolean isMovingHorizontal(){
		return isGoingLeft() || isGoingRight();
	}
	
	public boolean isMoving(){
		return isMovingHorizontal() || isMovingVertically();
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
