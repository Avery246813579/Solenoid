package com.frostbyte.ludumdare.map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.frostbyte.ludumdare.blocks.Block;
import com.frostbyte.ludumdare.blocks.Material;
import com.frostbyte.ludumdare.entities.Entity;
import com.frostbyte.ludumdare.menus.Menu;
import com.frostbyte.ludumdare.menus.TestMenu;
import com.frostbyte.ludumdare.objects.Location;
import com.frostbyte.ludumdare.player.Player;
import com.frostbyte.ludumdare.player.PlayerCamera;

public class Map {
	public Block[][] blocks;
	public Block[][] overlay;
	public Player player;
	public PlayerCamera camera;
	public List<Entity> entities = new ArrayList<Entity>();
	public Menu menu;

	public Map(Menu menu, int width, int height) {
		blocks = new Block[width][height];
		overlay = new Block[width][height];
		this.menu = menu;

		for (int x = 0; x < blocks.length; x++) {
			for (int y = 0; y < blocks.length; y++) {
				if (y < 0 && x < 0 && x >= (blocks.length) && y >= blocks[0].length) {
					blocks[x][y] = new Block(Material.AIR);
				}
			}
		}
	}

	public void update() {
		camera.update();

		for (Entity entity : entities) {
			entity.update();
		}
		
	}

	public void draw(Graphics graphics) {
		camera.setyStop(false);

		for (int x = 0; x < blocks.length; x++) {
			for (int y = 0; y < blocks[0].length; y++) {
				Block block = blocks[x][y];

				if (block != null) {
					graphics.drawImage(block.getMaterial().getBufferedImage(), (x * 15) - camera.getX(), (y * 15) - camera.getY(), null);
				}

				if (y < 0) {
					camera.setyStop(true);
				}

				if (x < 0) {
					x = 0;
				}

				if (x >= (blocks.length)) {
					x = blocks.length - 1;
				}

				if (y >= blocks[0].length) {
					camera.setyStop(true);
					y = blocks[0].length - 1;
				}
			}
		}

		for (int x = 0; x < overlay.length; x++) {
			for (int y = 0; y < overlay[0].length; y++) {
				Block block = overlay[x][y];

				if (block != null) {
					graphics.drawImage(block.getMaterial().getBufferedImage(), (x * 15) - camera.getX(), (y * 15) - camera.getY(), null);
				}
			}
		}

		for (Entity entity : entities) {
			entity.draw(graphics);
		}

		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, 55, 45);
		graphics.setColor(Color.WHITE);
		graphics.drawString(player.getInventory().content[player.getInventory().currentItem], 10, 40);
	}

	public List<Block> getBlocksAroundLocation(Location location, int xRadius, int yRadius) {
		int tx = (int) Math.ceil(location.getX() / 15);
		int ty = (int) Math.ceil(location.getY() / 15);
		tx++;

		if (ty < 0) {
			ty = 0;
		}

		if (tx < 0) {
			tx = 0;
		}

		if (tx >= (blocks.length)) {
			tx = blocks.length - 1;
		}

		if (ty >= blocks[0].length) {
			ty = blocks[0].length - 1;
		}

		List<Block> blockList = new ArrayList<Block>();
		for (int x = tx - xRadius; x < tx + xRadius; x++) {
			for (int y = ty; y < ty + yRadius; y++) {
				if (y < 0) {
					y = 0;
				}

				if (x < 0) {
					x = 0;
				}

				if (x >= (blocks.length)) {
					x = blocks.length - 1;
				}

				if (y >= blocks[0].length) {
					y = blocks[0].length - 1;
				}

				blockList.add(blocks[x][y]);
			}
		}

		return blockList;
	}

	public List<Block> getBlocksAroundBackground(Location location, int xRadius, int yRadius) {
		int tx = (int) Math.ceil(location.getX() / 15);
		int ty = (int) Math.ceil(location.getY() / 15);
		tx++;

		if (ty < 0) {
			ty = 0;
		}

		if (tx < 0) {
			tx = 0;
		}

		if (tx >= (overlay.length)) {
			tx = overlay.length - 1;
		}

		if (ty >= overlay[0].length) {
			ty = overlay[0].length - 1;
		}

		List<Block> blockList = new ArrayList<Block>();
		for (int x = tx - xRadius; x < tx + xRadius; x++) {
			for (int y = ty; y < ty + yRadius; y++) {
				if (y < 0) {
					y = 0;
				}

				if (x < 0) {
					x = 0;
				}

				if (x >= (overlay.length)) {
					x = overlay.length - 1;
				}

				if (y >= overlay[0].length) {
					y = overlay[0].length - 1;
				}

				blockList.add(overlay[x][y]);
			}
		}

		return blockList;
	}

	public Block getBlockAtLocation(Location location) {
		int x = location.getX() / 15;
		int y = location.getY() / 15;

		if (y < 0) {
			y = 0;
		}

		if (x < 0) {
			x = 0;
		}

		if (x >= (blocks.length)) {
			x = blocks.length - 1;
		}

		if (y >= blocks[0].length) {
			y = blocks[0].length - 1;
		}

		return blocks[x][y];
	}

	public Block getBlockAtBackground(Location location) {
		int x = location.getX() / 15;
		int y = location.getY() / 15;

		if (y < 0) {
			y = 0;
		}

		if (x < 0) {
			x = 0;
		}

		if (x >= (overlay.length)) {
			x = overlay.length - 1;
		}

		if (y >= overlay[0].length) {
			y = overlay[0].length - 1;
		}

		return overlay[x][y];
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public PlayerCamera getCamera() {
		return camera;
	}

	public void setCamera(PlayerCamera camera) {
		this.camera = camera;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public Block[][] getOverlay() {
		return overlay;
	}

	public void setOverlay(Block[][] overlay) {
		this.overlay = overlay;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(TestMenu menu) {
		this.menu = menu;
	}
}
