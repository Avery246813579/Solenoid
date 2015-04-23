package com.frostbyte.ludumdare.menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.frostbyte.ludumdare.entities.Robot;
import com.frostbyte.ludumdare.main.GameManager;
import com.frostbyte.ludumdare.map.MapLoader;
import com.frostbyte.ludumdare.objects.Location;
import com.frostbyte.ludumdare.objects.MasterComputer;
import com.frostbyte.ludumdare.objects.MasterMind;
import com.frostbyte.ludumdare.objects.SlidingDoor;
import com.frostbyte.ludumdare.objects.Song;
import com.frostbyte.ludumdare.objects.SoundUtil;
import com.frostbyte.ludumdare.player.Player;
import com.frostbyte.ludumdare.player.PlayerCamera;

public class TutorialMenu extends Menu {
	SlidingDoor door_1;
	SlidingDoor door_2;
	int time = -1, buffer;

	public TutorialMenu(GameManager gameManager) {
		super(gameManager);

		List<MasterComputer> computers = new ArrayList<MasterComputer>();
		MapLoader loader = new MapLoader(this, "MAP_2");
		map = loader.getMap();

		computers.add(new MasterComputer(this, new Location(map, 67, 40), 1));
		computers.add(new MasterComputer(this, new Location(map, 72, 40), 1));
		computers.add(new MasterComputer(this, new Location(map, 77, 40), 1));
		masterMind = new MasterMind(map, new Location(map, 85, 41), computers, 3, new SlidingDoor(map, new ArrayList<Location>(Arrays.asList(new Location(map, 88, 48), new Location(map, 88, 47),
				new Location(map, 88, 46), new Location(map, 88, 45), new Location(map, 88, 44))), new Location(map, 666, 666)));

		door_1 = new SlidingDoor(map, new ArrayList<Location>(Arrays.asList(new Location(map, 7, 48), new Location(map, 7, 47), new Location(map, 7, 46), new Location(map, 7, 45), new Location(map,
				7, 44))), new Location(map, 666, 666));

		door_2 = new SlidingDoor(map, new ArrayList<Location>(Arrays.asList(new Location(map, 17, 48), new Location(map, 17, 47), new Location(map, 17, 46), new Location(map, 17, 45), new Location(
				map, 17, 44))), new Location(map, 666, 66));

		getDoors().add(
				new SlidingDoor(map, new ArrayList<Location>(Arrays.asList(new Location(map, 26, 48), new Location(map, 26, 47), new Location(map, 26, 46), new Location(map, 26, 45), new Location(
						map, 26, 44))), new Location(map, 24, 40), Song.STEP_4));
		
		getDoors().add(
				new SlidingDoor(map, new ArrayList<Location>(Arrays.asList(new Location(map, 59, 42), new Location(map, 59, 41), new Location(map, 59, 40), new Location(map, 59, 39), new Location(
						map, 59, 38))), new Location(map, 57, 40), Song.STEP_5));

		map.player = new Player(map, 2 * 15, 43 * 15);
		map.entities.add(map.player);
		map.entities.add(new Robot(map, 21 * 15, 38 * 15));
		map.entities.add(new Robot(map, 68 * 15, 38 * 15));
		map.camera = new PlayerCamera(map, map.player);
		// 69, 61 | control panel
	}

	@Override
	public void draw(Graphics graphics) {
		map.draw(graphics);
		graphics.setColor(Color.BLACK);
		graphics.drawString("Turn on sound for audio tutorial!", 600, 50);
	}

	@Override
	public void update() {
		if (true) {
			if(time == -1){
				time = (int) (System.nanoTime() / 1000000000);
			}
			
			int currentNanoTime = (int) (System.nanoTime() / 1000000000);
			if (currentNanoTime - time == 20) {
				door_1.open();
				SoundUtil.playSong(Song.STEP_2);
			}

			if (currentNanoTime - time == 54) {
				door_2.open();
				SoundUtil.playSong(Song.STEP_3);
			}
		}

		map.update();
		door_1.update();
		door_2.update();

		for (SlidingDoor door : getDoors()) {
			door.update();
		}

		masterMind.update();
	}

	@Override
	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_A:
			map.getPlayer().LEFT = true;
			return;
		case KeyEvent.VK_D:
			map.getPlayer().RIGHT = true;
			return;
		case KeyEvent.VK_SPACE:
			map.getPlayer().JUMPING = true;
			return;
		case KeyEvent.VK_T:
			map.getPlayer().useMagnent();
			return;
		case KeyEvent.VK_E:
			map.getPlayer().onInteract();
			return;
			/**
			 * case KeyEvent.VK_CONTROL: if (!map.getPlayer().DEAD &&
			 * !map.getPlayer().PRONE && !map.getPlayer().JUMPING &&
			 * !map.getPlayer().FALLING) { map.getPlayer().currentAnimation =
			 * Entity.PRONE_ANIMATION; map.getPlayer().width = 58;
			 * map.getPlayer().height = 23; map.getPlayer().y =
			 * map.getPlayer().y + (map.getPlayer().defaultHeight - 23);
			 * map.getPlayer().PRONE = true; return; }
			 **/
		}
	}

	@Override
	public void keyReleased(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_A:
			map.getPlayer().LEFT = false;
			return;
		case KeyEvent.VK_D:
			map.getPlayer().RIGHT = false;
			return;
		case KeyEvent.VK_SPACE:
			map.getPlayer().JUMPING = false;
			map.getPlayer().CLIMBING = false;
			return;
		case KeyEvent.VK_T:
			map.getPlayer().FORCE = false;
			return;
			/**
			 * case KeyEvent.VK_CONTROL: if (map.getPlayer().PRONE) { int
			 * renderDistance = 1;
			 * 
			 * int startX = (int) (map.getPlayer().x / 15) - renderDistance; int
			 * finishX = (int) ((map.getPlayer().x + map.getPlayer().width) /
			 * 15) + renderDistance; int startY = (int) (map.getPlayer().y / 15)
			 * - renderDistance; int finishY = (int) ((map.getPlayer().y +
			 * map.getPlayer().height) / 15) + renderDistance;
			 * 
			 * if (startX < 0) { startX = 0; }
			 * 
			 * if (startY < 0) { startY = 0; }
			 * 
			 * if (finishX > (map.getBlocks().length - 1)) { finishX =
			 * (map.getBlocks().length - 1); }
			 * 
			 * if (finishY >= (map.getBlocks()[0].length - 1)) { finishY =
			 * (map.getBlocks()[0].length - 1); }
			 * 
			 * for (int x = (startX + 1); x <= (finishX - 1); x++) { Block block
			 * = map.getBlocks()[x][(map.getPlayer().y - 5) / 15];
			 * 
			 * if (!block.getMaterial().isSolid()) { map.getPlayer().y =
			 * map.getPlayer().y - (map.getPlayer().defaultHeight - 23);
			 * map.getPlayer().width = map.getPlayer().defaultWidth;
			 * map.getPlayer().height = map.getPlayer().defaultHeight;
			 * map.getPlayer().PRONE = false; return; } } }
			 **/
		}
		return;
	}

	@Override
	public void wheelMove(int rotation) {
		int tempLocation = map.getPlayer().getInventory().currentItem + rotation;

		if (tempLocation >= map.getPlayer().getInventory().content.length) {
			tempLocation = 0;
		}

		if (tempLocation <= -1) {
			tempLocation = map.getPlayer().getInventory().content.length - 1;
		}

		map.getPlayer().getInventory().currentItem = tempLocation;
		map.getPlayer().resetMagnent();
	}

	@Override
	public void mouseClick(int button, int x, int y) {

	}

	@Override
	public void mouseMove(int x, int y) {

	}
}
