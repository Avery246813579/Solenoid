package com.frostbyte.ludumdare.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.frostbyte.ludumdare.main.GameManager;

public class InputHandler implements MouseListener, KeyListener, MouseMotionListener, MouseWheelListener{
	GameManager gameManager;
	
	public InputHandler(GameManager gameManager){
		this.gameManager = gameManager;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		gameManager.getMenus().get(gameManager.getCurrentMenu()).keyPressed(event.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent event) {
		gameManager.getMenus().get(gameManager.getCurrentMenu()).keyReleased(event.getKeyCode());
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		gameManager.getMenus().get(gameManager.getCurrentMenu()).mouseClick(event.getButton(), event.getX(), event.getY());
	}
	
	@Override
	public void mouseMoved(MouseEvent event) {
		gameManager.getMenus().get(gameManager.getCurrentMenu()).mouseMove(event.getX(), event.getY());
	}
	

	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		gameManager.getMenus().get(gameManager.getCurrentMenu()).wheelMove(event.getWheelRotation());
	}
	
	@Override
	public void mouseDragged(MouseEvent event) {
		
	}


	@Override
	public void keyTyped(KeyEvent event) {
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		
	}
}
