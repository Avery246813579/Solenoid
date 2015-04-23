package com.frostbyte.ludumdare.menus;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.frostbyte.ludumdare.main.GameManager;
import com.frostbyte.ludumdare.objects.Song;
import com.frostbyte.ludumdare.objects.SoundUtil;

public class MainMenu extends Menu{
	BufferedImage background, play, quit;
	
	public MainMenu(GameManager gameManager){
		super(gameManager);
		
		try{
			background = ImageIO.read(getClass().getResourceAsStream("/GUI/BACKGROUND.png"));
			play = ImageIO.read(getClass().getResourceAsStream("/GUI/PLAY.png"));
			quit = ImageIO.read(getClass().getResourceAsStream("/GUI/EXIT.png"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(background, 0, 0, null);
		graphics.drawImage(play, 300, 300, null);
		graphics.drawImage(quit, 325, 400, null);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void keyPressed(int keyCode) {
		
	}

	@Override
	public void keyReleased(int keyCode) {
		
	}

	@Override
	public void mouseClick(int button, int x, int y) {
		Rectangle2D playRect = new Rectangle(x, y, 1, 1);
		Rectangle2D pRect = new Rectangle(300, 300, play.getWidth(), play.getHeight());
		Rectangle2D qRect = new Rectangle(325, 400, quit.getWidth(), quit.getHeight());
		
		if(playRect.intersects(pRect)){
			gameManager.setCurrentMenu(1);
			SoundUtil.stopMusic();
			SoundUtil.playSong(Song.STEP_1);
			gameManager.musicPlayer.currentList = null;
			gameManager.musicPlayer.currentLength = 2000;
			//gameManager.musicPlayer.currentList = MusicPlayer.gameSongs;
			//gameManager.musicPlayer.currentLength = MusicPlayer.gameSongs.get(0).getLength();
			//SoundUtil.playSong(Song.TRACK_4);
		}
		
		if(qRect.intersects(pRect)){
			System.exit(0);
		}

	}

	@Override
	public void mouseMove(int x, int y) {
		
	}

	@Override
	public void wheelMove(int rotation) {
		
	}
}
