package com.frostbyte.ludumdare.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.frostbyte.ludumdare.blocks.Block;
import com.frostbyte.ludumdare.blocks.Material;
import com.frostbyte.ludumdare.display.Animation;
import com.frostbyte.ludumdare.map.Map;
import com.frostbyte.ludumdare.menus.TestMenu;
import com.frostbyte.ludumdare.objects.Location;
import com.frostbyte.ludumdare.objects.MusicPlayer;
import com.frostbyte.ludumdare.objects.Song;
import com.frostbyte.ludumdare.objects.SoundUtil;
import com.frostbyte.ludumdare.objects.Vector;
import com.frostbyte.ludumdare.player.Player;

public abstract class Entity {
	public int x, y;
	public Map map;
	public List<Animation> animations = new ArrayList<Animation>();
	public int MOVE_SPEED, JUMP_SPEED, FALL_SPEED, MAX_MOVE_SPEED, MAX_JUMP_SPEED, MAX_FALL_SPEED, JUMP_DISTANCE, MAX_JUMP_DISTANCE, defaultWidth, defaultHeight, width, height, currentAnimation,
			deathTime, maxDeathTime = 50;
	public boolean JUMPING = false, FALLING = false, RIGHT = false, LEFT = false, RUNNING = false, PRONE = false, IN_JUMP = false, TIRED = false, DEAD = false, FORCE = false, CLIMBING = false;
	public boolean facingRight = true;
	public Vector velocity = new Vector(0, 0);

	public static final int IDLE_ANIMATION = 0, MOVE_ANIMATION = 1, FALLING_ANIMATION = 2, JUMPING_ANIMATION = 3, DEATH_ANIMATION = 4, CLIMBING_ANIMATION = 5, PRONE_ANIMATION = 6,
			FORCE_ANIMATION = 7;

	public Entity(Map map, int x, int y) {
		this.map = map;
		this.x = x;
		this.y = y;
	}

	public void update() {
		animations.get(currentAnimation).update();

		checkDeath();
		checkMovement();
		checkBlockCollision();
		checkLocation();
	}

	public void checkDeath() {
		if (DEAD) {
			if (deathTime > maxDeathTime) {
				map.getEntities().remove(this);
			} else {
				deathTime++;
			}
		}
	}

	public void checkLocation() {
		int targetX = x + velocity.getX();
		int targetY = y + velocity.getY();

		if (targetY < ((map.getBlocks()[0].length * 15) - height)) {
			y = targetY;
		}

		if (targetX < ((map.getBlocks().length * 15) - width)) {
			x = targetX;
		}
	}

	public void checkMovement() {
		if (DEAD) {
			JUMPING = false;
			LEFT = false;
			RIGHT = false;
			FALLING = false;

			currentAnimation = DEATH_ANIMATION;
			velocity = new Vector(0, 0);
			return;
		}

		if (FORCE) {
			currentAnimation = FORCE_ANIMATION;

			int renderDistance = 1;

			int startX = (int) (this.x / 15) - renderDistance;
			int finishX = (int) ((this.x + width) / 15) + renderDistance;
			int startY = (int) (this.y / 15) - renderDistance;
			int finishY = (int) ((this.y + height) / 15) + renderDistance;

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

			for (int x = (this.x / 15); x <= (this.x / 15 + 1); x++) {
				Block block = map.getBlocks()[x][(this.y) / 15];

				if (block.getMaterial().isSolid()) {
					FORCE = false;
					velocity.setY(0);
					FALLING = true;
				}
			}

			return;
		}

		if (PRONE) {
			if (JUMPING) {
				JUMPING = false;
			}
		}

		if (RIGHT) {
			LEFT = false;

			if (velocity.getX() + MOVE_SPEED >= MAX_MOVE_SPEED) {
				if (!RUNNING) {
					velocity.setX(MAX_MOVE_SPEED);
				} else {
					if (velocity.getX() + MOVE_SPEED >= MAX_MOVE_SPEED) {
						velocity.setX(MAX_MOVE_SPEED);
					} else {
						velocity.setX(velocity.getX() + MOVE_SPEED);
					}
				}
			} else {
				velocity.setX(velocity.getX() + MOVE_SPEED);
			}

			facingRight = true;
		}

		if (LEFT) {
			RIGHT = false;

			if (velocity.getX() - MOVE_SPEED <= -MAX_MOVE_SPEED) {
				if (!RUNNING) {
					velocity.setX(-MAX_MOVE_SPEED);
				} else {
					if (velocity.getX() - MOVE_SPEED <= -MAX_MOVE_SPEED) {
						velocity.setX(-MAX_MOVE_SPEED);
					} else {
						velocity.setX(velocity.getX() - MOVE_SPEED);
					}
				}
			} else {
				velocity.setX(velocity.getX() - MOVE_SPEED);
			}

			facingRight = false;
		}

		/**
		 * if (JUMPING || CLIMBING) { if (map.getBlocksAroundLocation(new
		 * Location(map, x, y), 1, 4).size() > 0) { for (Block block :
		 * map.getBlocksAroundLocation(new Location(map, x, y), 1, 4)) { if
		 * (block.getMaterial() == Material.LADDER || block.getMaterial() ==
		 * Material.LADDER_BOTTOM || block.getMaterial() == Material.LADDER_TOP)
		 * { climb = true; break; } } } }
		 **/
		if (JUMPING || CLIMBING) {
			Block block = map.getBlockAtBackground(new Location(map, x + 5, y + (height - 2)));
			Block block2 = map.getBlockAtBackground(new Location(map, x - 5, y + (height - 2)));

			if (block.getMaterial() == Material.LADDER || block.getMaterial() == Material.LADDER_BOTTOM || block.getMaterial() == Material.LADDER_TOP || block2.getMaterial() == Material.LADDER
					|| block2.getMaterial() == Material.LADDER_TOP || block2.getMaterial() == Material.LADDER_BOTTOM) {
				CLIMBING = true;
				FALLING = false;
			} else {
				if (CLIMBING) {
					velocity.setY(0);
					CLIMBING = false;
				}
			}
		}

		if (CLIMBING) {
			if (velocity.getY() - JUMP_SPEED <= MAX_JUMP_SPEED) {
				velocity.setY(-MAX_JUMP_SPEED);
			} else {
				velocity.setY(velocity.getY() - JUMP_SPEED);
			}
		} else if (JUMPING && velocity.getY() <= 0 && !IN_JUMP) {
			FALLING = false;

			if (velocity.getY() - JUMP_SPEED <= MAX_JUMP_SPEED) {
				velocity.setY(-MAX_JUMP_SPEED);
			} else {
				velocity.setY(velocity.getY() - JUMP_SPEED);
			}

			if (JUMP_DISTANCE > MAX_JUMP_DISTANCE) {
				JUMPING = false;
				JUMP_DISTANCE = 0;
				IN_JUMP = true;
				FALLING = true;
			} else {
				JUMP_DISTANCE += -velocity.getY();
			}
		}

		if (FALLING) {
			if (!IN_JUMP) {
				IN_JUMP = true;
			}

			if (CLIMBING) {
				CLIMBING = false;
			}

			JUMPING = false;
			if (JUMP_DISTANCE != 0) {
				JUMP_DISTANCE = 0;
			}

			if (velocity.getY() + FALL_SPEED >= MAX_FALL_SPEED) {
				velocity.setY(MAX_FALL_SPEED);
			} else {
				velocity.setY(velocity.getY() + FALL_SPEED);
			}
		}

		if (CLIMBING) {
			currentAnimation = CLIMBING_ANIMATION;
		} else if (FALLING) {
			currentAnimation = FALLING_ANIMATION;
		} else if (PRONE) {
			currentAnimation = PRONE_ANIMATION;
		} else if (JUMPING) {
			currentAnimation = JUMPING_ANIMATION;
		} else if (RIGHT || LEFT) {
			currentAnimation = MOVE_ANIMATION;
		} else {
			currentAnimation = IDLE_ANIMATION;
		}

		if (x - (width / 2) <= 0) {
			LEFT = false;
		}

		if (!RIGHT && !LEFT && !DEAD) {
			velocity.setX(0);
		}

		if (!FALLING && !JUMPING) {
			velocity.setY(0);
			IN_JUMP = false;
		}
	}

	public void checkBlockCollision() {
		int renderDistance = 1;

		int startX = (int) (this.x / 15) - renderDistance;
		int finishX = (int) ((this.x + width) / 15) + renderDistance;
		int startY = (int) (this.y / 15) - renderDistance;
		int finishY = (int) ((this.y + height) / 15) + renderDistance;

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
				Block block = map.getBlocks()[x][(this.y - 5) / 15];
				Block overlay = map.getOverlay()[x][(this.y - 5) / 15];

				if (block.getMaterial().isSolid() || overlay.getMaterial().isSolid()) {
					JUMPING = false;
				}
			}
		}

		if (RIGHT) {
			for (int y = (startY + 2); y <= (finishY - 1); y++) {
				if (y > 0) {
					Block block = map.getBlocks()[(this.x + width + velocity.getX()) / 15][y];
					Block overlay = map.getOverlay()[(this.x + width + velocity.getX()) / 15][y];

					if (block.getMaterial().isSolid() || overlay.getMaterial().isSolid()) {
						RIGHT = false;
						velocity.setX(0);
					}
				}
			}
		}

		if (LEFT) {
			for (int y = (startY + 2); y <= (finishY - 1); y++) {
				Block block = map.getBlocks()[(this.x - 5) / 15][y];
				Block overlay = map.getOverlay()[(this.x - 5) / 15][y];

				if (block.getMaterial().isSolid() || overlay.getMaterial().isSolid()) {
					LEFT = false;
					velocity.setX(0);
				}
			}
		}

		Block block = map.getBlocks()[(x / 15)][((this.y + height) / 15 + 1)];
		Block block2 = map.getBlocks()[((x + width) / 15)][((this.y + height) / 15 + 1)];

		if (map.getMenu().gameManager.getCurrentMenu() == 2) {
			Rectangle2D over = new Rectangle(x, y, 1, 1);
			Rectangle2D under = new Rectangle(79 * 15 - 2, 56 * 15 - 4, 30, 90);

			if (over.intersects(under)) {
				if (map.getMenu().gameManager.getCurrentMenu() == 2) {
					map.getMenu().gameManager.setCurrentMenu(0);
				}
			}
		}

		if (this instanceof Player) {
			if (x > 15 * 93) {
				map.getMenu().gameManager.getMenus().add(new TestMenu(map.getMenu().gameManager));
				map.getMenu().gameManager.setCurrentMenu(2);
				map.getMenu().gameManager.musicPlayer.currentList = MusicPlayer.gameSongs;
				map.getMenu().gameManager.musicPlayer.currentLength = MusicPlayer.gameSongs.get(0).getLength();
				SoundUtil.playSong(Song.TRACK_4);
			}
		}

		if (!JUMPING && !CLIMBING && !FORCE) {
			if (block == null || block2 == null) {
				y = y - velocity.getY();
				velocity.setY(0);
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

	public void checkEntityCollision() {
		// Rectangle2D rectangle2d = new Rectangle(x, y, width, height);
	}

	public void draw(Graphics graphics) {
		if (!animations.isEmpty()) {
			BufferedImage image = animations.get(currentAnimation).getCurrentBufferedImage();

			if (facingRight) {
				graphics.drawImage(image, x - map.getCamera().getX(), y - map.getCamera().getY(), null);
			} else {
				graphics.drawImage(image, x + width - map.getCamera().getX(), y - map.getCamera().getY(), -image.getWidth(), image.getHeight(), null);
			}
		}
	}
}
