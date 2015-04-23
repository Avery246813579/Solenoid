package com.frostbyte.ludumdare.main;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.frostbyte.ludumdare.inputs.InputHandler;
import com.frostbyte.ludumdare.menus.MainMenu;
import com.frostbyte.ludumdare.menus.Menu;
import com.frostbyte.ludumdare.menus.TutorialMenu;
import com.frostbyte.ludumdare.objects.MusicPlayer;

public class GameManager {
	private List<Menu> menus = new ArrayList<Menu>(Arrays.asList(new MainMenu(this), new TutorialMenu(this)));
	public InputHandler inputHandler = new InputHandler(this);
	public MusicPlayer musicPlayer = new MusicPlayer();
	public GameLoop gameLoop = new GameLoop(this);
	public GameFrame gameFrame = new GameFrame();
	public boolean running = false;
	private int currentMenu;
	
	public GameManager(){
		gameFrame.addMouseListener(inputHandler);
		gameFrame.addMouseMotionListener(inputHandler);
		gameFrame.addMouseWheelListener(inputHandler);
		gameFrame.addKeyListener(inputHandler);
		
		loadGame();
	}
	
	public void loadGame(){
		running = true;
		gameLoop.start();
	}
	
	public void draw(Graphics graphics){
		menus.get(currentMenu).draw(graphics);
	}
	
	public void update(){
		menus.get(currentMenu).update();
		musicPlayer.update();
	}
	
	public void doubleBuffer(){
		Image image = null;
		Graphics graphics = null;
		
		image = gameFrame.createImage(GameFrame.WIDTH, GameFrame.HEIGHT);
		graphics = image.getGraphics();
		
		graphics.setColor(gameFrame.getBackground());
		graphics.fillRect(0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);
		graphics.setColor(gameFrame.getForeground());
		
		draw(graphics);
		gameFrame.getGraphics().drawImage(image, 0, 0, null);
		graphics.dispose();
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public int getCurrentMenu() {
		return currentMenu;
	}

	public void setCurrentMenu(int currentMenu) {
		this.currentMenu = currentMenu;
	}
}
