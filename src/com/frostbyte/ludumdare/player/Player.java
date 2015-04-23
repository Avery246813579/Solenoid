package com.frostbyte.ludumdare.player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

import com.frostbyte.ludumdare.blocks.Block;
import com.frostbyte.ludumdare.blocks.Material;
import com.frostbyte.ludumdare.display.Animation;
import com.frostbyte.ludumdare.entities.Entity;
import com.frostbyte.ludumdare.entities.Robot;
import com.frostbyte.ludumdare.map.Map;
import com.frostbyte.ludumdare.objects.MasterComputer;
import com.frostbyte.ludumdare.objects.SlidingDoor;
import com.frostbyte.ludumdare.objects.Sound;
import com.frostbyte.ludumdare.objects.SoundUtil;

public class Player extends Entity {
	private PlayerInventory inventory = new PlayerInventory();
	public boolean on;
	public boolean connected;
	public boolean updown;
	public int xoffset, yoffset;
	public Robot controlling;

	public Player(Map map, int x, int y) {
		super(map, x, y);

		animations = new ArrayList<Animation>(
				Arrays.asList(new Animation(new String[] { "/SPRITES/PLAYER/IDLE_1.png", "/SPRITES/PLAYER/IDLE_2.png", "/SPRITES/PLAYER/IDLE_3.png", "/SPRITES/PLAYER/IDLE_4.png" }, 15),
						new Animation(new String[] { "/SPRITES/PLAYER/WALKING_2.png", "/SPRITES/PLAYER/WALKING_3.png", "/SPRITES/PLAYER/WALKING_4.png", "/SPRITES/PLAYER/WALKING_5.png" }, 5),
						new Animation(new String[] { "/SPRITES/PLAYER/FALLING_1.png" }, 0), new Animation(new String[] { "/SPRITES/PLAYER/JUMPING_3.png" }, 0), new Animation(new String[] {
								"/SPRITES/PLAYER/DYING_1.png", "/SPRITES/PLAYER/DYING_2.png", "/SPRITES/PLAYER/DYING_3.png", "/SPRITES/PLAYER/DYING_4.png", "/SPRITES/PLAYER/DYING_5.png" }, 10),
						new Animation(new String[] { "/SPRITES/PLAYER/CLIMBING_1.png", "/SPRITES/PLAYER/CLIMBING_2.png", "/SPRITES/PLAYER/CLIMBING_3.png", "/SPRITES/PLAYER/CLIMBING_4.png" }, 10),
						new Animation(new String[] { "/SPRITES/PLAYER/PRONE_1.png", "/SPRITES/PLAYER/PRONE_2.png" }, 15), new Animation(new String[] { "/SPRITES/PLAYER/JUMPING_3.png" }, 0)));

		FALL_SPEED = 1;
		MOVE_SPEED = 1;
		JUMP_SPEED = 2;

		MAX_FALL_SPEED = 5;
		MAX_JUMP_SPEED = 3;
		MAX_MOVE_SPEED = 2;
		MAX_JUMP_DISTANCE = 35;

		height = defaultHeight = 56;
		width = defaultWidth = 28;
	}

	public void useMagnent() {
		boolean canUse = false;

		if (inventory.content[inventory.currentItem].equalsIgnoreCase("FORCE")) {
			int xOffset = 2;
			int yOffset = 10;

			boolean hasMag = false;
			for (int x = (this.x / 15) - xOffset; x < (this.x + width) / 15 + 1; x++) {
				for (int y = ((this.y + height) / 15); y > (this.y / 15) - yOffset; y--) {
					if (y < 0) {
						y = 0;
						break;
					}

					if (x < 0) {
						x = 0;
					}

					if (x >= (map.getBlocks().length)) {
						x = map.getBlocks().length - 1;
					}

					if (y >= map.getBlocks()[0].length) {
						y = map.getBlocks()[0].length - 1;
						break;
					}

					Block block = map.getBlocks()[x][y];
					if (block.getMaterial() == Material.VENT_1 || block.getMaterial() == Material.PIPE_2 || block.getMaterial() == Material.PIPE_6) {
						hasMag = true;
					}
				}
			}

			if (!hasMag) {
				return;
			}

			FORCE = true;
			velocity.setY(-3);
			velocity.setX(0);
		} else {
			for (Entity entity : map.getEntities()) {
				if (entity instanceof Robot) {
					if (map.getCamera().getY() < entity.y && entity.y > map.getCamera().getY()) {
						if (entity.x > (x - (entity.width / 2)) && entity.x < (x + width + (entity.width / 2))) {
							canUse = true;
							updown = true;
							controlling = (Robot) entity;
						}
					}

					if (entity.y > (y - (entity.height / 2)) && entity.y < (y + height + (entity.height / 2))) {
						if (map.getCamera().getX() < entity.x && entity.x > map.getCamera().getX()) {
							canUse = true;
							updown = false;
							controlling = (Robot) entity;
						}
					}
				}
			}
		}

		if (!canUse) {
			return;
		}

		if (inventory.content[inventory.currentItem].equalsIgnoreCase("MOVE")) {
			if (on) {
				on = false;
			} else {
				on = true;
			}
		} else if (inventory.content[inventory.currentItem].equalsIgnoreCase("BINARY")) {
			controlling.onTurn();
			controlling.onMachine();
		}
	}

	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
	}

	public void resetMagnent() {
		on = false;
		FORCE = false;
	}

	public void update() {
		if (on && !connected) {
			xoffset = controlling.x - x;
			yoffset = controlling.y - y;
			connected = true;
		}

		if (!on) {
			connected = false;
		}

		int renderDistance = 1;
		if (controlling != null && connected) {
			int startX = (int) (controlling.x / 15) - renderDistance;
			int finishX = (int) ((controlling.x + width) / 15) + renderDistance;
			int startY = (int) (controlling.y / 15) - renderDistance;
			int finishY = (int) ((controlling.y + height) / 15) + renderDistance;

			if (startX < 0) {
				startX = 0;
			}

			if (startY < 0) {
				startY = 0;
			}

			if (finishX > (map.getBlocks().length - 1)) {
				finishX = (map.getBlocks().length - 1);
			}

			if (finishY >= (map.getBlocks()[0].length - 1)) {
				finishY = (map.getBlocks()[0].length - 1);
			}

			if (JUMPING) {
				for (int x = (startX + 1); x <= (finishX - 1); x++) {
					Block block = map.getBlocks()[x][(controlling.y - 5) / 15];
					Block overlay = map.getOverlay()[x][(controlling.y - 5) / 15];

					if (block.getMaterial().isSolid() || overlay.getMaterial().isSolid()) {
						on = false;
						controlling.JUMPING = false;
					}
				}
			}

			if (RIGHT) {
				for (int y = (startY + 2); y <= (finishY - 1); y++) {
					if (y > 0) {
						Block block = map.getBlocks()[(controlling.x + controlling.width + velocity.getX()) / 15][y];
						Block overlay = map.getOverlay()[(controlling.x + controlling.width + velocity.getX()) / 15][y];

						if (block.getMaterial().isSolid() || overlay.getMaterial().isSolid()) {
							on = false;
							controlling.RIGHT = false;
							controlling.velocity.setX(0);
						}
					}
				}
			}

			if (LEFT) {
				for (int y = (startY + 2); y <= (finishY - 1); y++) {
					Block block = map.getBlocks()[(controlling.x - 5) / 15][y];
					Block overlay = map.getOverlay()[(controlling.x - 5) / 15][y];

					if (block.getMaterial().isSolid() || overlay.getMaterial().isSolid()) {
						on = false;
						controlling.LEFT = false;
						controlling.velocity.setX(0);
					}
				}
			}

			Block block = map.getBlocks()[(x / 15)][((this.y + height) / 15 + 1)];
			Block block2 = map.getBlocks()[((x + width) / 15)][((this.y + height) / 15 + 1)];
			if (!JUMPING && !CLIMBING && !FORCE) {
				if (block == null || block2 == null) {
					on = false;
					controlling.y = y + yoffset;
				} else {
					if (!block.getMaterial().isSolid() && !block2.getMaterial().isSolid()) {
						FALLING = true;
						IN_JUMP = true;
						JUMPING = false;
					} else {
						if (this instanceof Player) {
							this.y = ((this.y + height) / 15 + 1) * 15 - height - 5;
						} else {
							this.y = ((this.y + height) / 15 + 1) * 15 - height - 5;
						}
						FALLING = false;
						JUMPING = false;
						IN_JUMP = false;
						velocity.setY(0);
					}
				}
			}
		}
		
		if (connected && on) {
			controlling.x = x + xoffset;
			controlling.y = y + yoffset;
		}

		super.update();
	}

	public void onInteract() {
		onTurn();

		for (MasterComputer computer : map.getMenu().masterMind.getComputers()) {
			if (computer.intercepts(this)) {
				if (computer.isActive()) {
					computer.setActive(false);
					map.getOverlay()[computer.getLocation().getX()][computer.getLocation().getY()].setMaterial(Material.MACHINE_2);
				} else {
					computer.setActive(true);
					map.getOverlay()[computer.getLocation().getX()][computer.getLocation().getY()].setMaterial(Material.MACHINE_1);
				}

				computer.getMenu().masterMind.checkMaster();
			}
		}
	}

	public void onTurn() {
		Rectangle2D robotRect = new Rectangle(x, y, width, height);

		for (SlidingDoor door : map.getMenu().getDoors()) {
			if (!door.isActive()) {
				Rectangle2D leverRect = new Rectangle(door.getLever().getX() * 15, door.getLever().getY() * 15, 15, 30);

				if (leverRect.intersects(robotRect)) {
					map.getOverlay()[door.getLever().getX()][door.getLever().getY()].setMaterial(Material.LEVER_ON);
					SoundUtil.playSound(Sound.LEVER);
					door.open();
				}
			}
		}
	}

	public PlayerInventory getInventory() {
		return inventory;
	}

	public void setInventory(PlayerInventory inventory) {
		this.inventory = inventory;
	}

}
