package com.frostbyte.ludumdare.menus;

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
import com.frostbyte.ludumdare.player.Player;
import com.frostbyte.ludumdare.player.PlayerCamera;

public class TestMenu extends Menu {
	public TestMenu(GameManager gameManager) {
		super(gameManager);

		MapLoader loader = new MapLoader(this, "MAP_1");
		map = loader.getMap();

		List<MasterComputer> computers = new ArrayList<MasterComputer>();
		computers.add(new MasterComputer(this, new Location(map, 52, 72), 5));
		computers.add(new MasterComputer(this, new Location(map, 61, 72), 5));
		computers.add(new MasterComputer(this, new Location(map, 69, 72), 5));
		masterMind = new MasterMind(map, new Location(map, 66, 59), computers, 10, new SlidingDoor(map, new ArrayList<Location>(Arrays.asList(new Location(map, 69, 61), new Location(map, 69, 60),
				new Location(map, 69, 59), new Location(map, 69, 58))), new Location(map, 4000, 4000)));

		getDoors().add(
				new SlidingDoor(map, new ArrayList<Location>(Arrays.asList(new Location(map, 57, 87), new Location(map, 57, 86), new Location(map, 57, 85), new Location(map, 57, 84))), new Location(
						map, 63, 96)));

		getDoors().add(
				new SlidingDoor(map, new ArrayList<Location>(Arrays.asList(new Location(map, 76, 87), new Location(map, 76, 86), new Location(map, 76, 85), new Location(map, 76, 84))), new Location(
						map, 73, 96)));

		map.player = new Player(map, 30, 1300);
		map.entities.add(map.player);
		map.entities.add(new Robot(map, 885, 880));
		map.entities.add(new Robot(map, 1005, 1340));
		map.camera = new PlayerCamera(map, map.player);

		// 69, 61 | control panel
	}
	
	@Override
	public void start(){
		MapLoader loader = new MapLoader(this, "MAP_1");
		map = loader.getMap();

		List<MasterComputer> computers = new ArrayList<MasterComputer>();
		computers.add(new MasterComputer(this, new Location(map, 52, 72), 5));
		computers.add(new MasterComputer(this, new Location(map, 61, 72), 5));
		computers.add(new MasterComputer(this, new Location(map, 69, 72), 5));
		masterMind = new MasterMind(map, new Location(map, 66, 59), computers, 10, new SlidingDoor(map, new ArrayList<Location>(Arrays.asList(new Location(map, 69, 61), new Location(map, 69, 60),
				new Location(map, 69, 59), new Location(map, 69, 58))), new Location(map, 4000, 4000)));

		getDoors().add(
				new SlidingDoor(map, new ArrayList<Location>(Arrays.asList(new Location(map, 57, 87), new Location(map, 57, 86), new Location(map, 57, 85), new Location(map, 57, 84))), new Location(
						map, 63, 96)));

		getDoors().add(
				new SlidingDoor(map, new ArrayList<Location>(Arrays.asList(new Location(map, 76, 87), new Location(map, 76, 86), new Location(map, 76, 85), new Location(map, 76, 84))), new Location(
						map, 73, 96)));

		map.player = new Player(map, 30, 1300);
		map.entities.add(map.player);
		map.entities.add(new Robot(map, 885, 880));
		map.entities.add(new Robot(map, 1005, 1340));
		map.camera = new PlayerCamera(map, map.player);
	}

	@Override
	public void draw(Graphics graphics) {
		map.draw(graphics);
	}

	@Override
	public void update() {
		map.update();

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
