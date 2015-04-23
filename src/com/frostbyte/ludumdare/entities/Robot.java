package com.frostbyte.ludumdare.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

import com.frostbyte.ludumdare.blocks.Material;
import com.frostbyte.ludumdare.display.Animation;
import com.frostbyte.ludumdare.map.Map;
import com.frostbyte.ludumdare.objects.MasterComputer;
import com.frostbyte.ludumdare.objects.SlidingDoor;
import com.frostbyte.ludumdare.objects.Sound;
import com.frostbyte.ludumdare.objects.SoundUtil;

public class Robot extends Entity {
	Weapon weapon;
	int delay, maxDelay = 60;

	public Robot(Map map, int x, int y) {
		super(map, x, y);

		weapon = new Weapon(map, x, y + 30);

		animations = new ArrayList<Animation>(Arrays.asList(new Animation(new String[] { "/SPRITES/ROBOT/IDLE_1.png", "/SPRITES/ROBOT/IDLE_2.png", "/SPRITES/ROBOT/IDLE_3.png",
				"/SPRITES/ROBOT/IDLE_4.png" }, 10), new Animation(new String[] { "/SPRITES/ROBOT/WALK_1.png", "/SPRITES/ROBOT/WALK_2.png", "/SPRITES/ROBOT/WALK_3.png", "/SPRITES/ROBOT/WALK_4.png" },
				10), new Animation(new String[] { "/SPRITES/ROBOT/FALLING_1.png" }, 10), new Animation(new String[] { "/SPRITES/ROBOT/JUMPING_3.png" }, 10)));

		this.width = 38;
		this.height = 56;

		this.MOVE_SPEED = 1;
		this.FALL_SPEED = 1;
		this.JUMP_SPEED = 1;

		this.MAX_FALL_SPEED = 10;
		this.MAX_MOVE_SPEED = 1;
		this.MAX_JUMP_SPEED = 1;

		this.MAX_JUMP_DISTANCE = 20;
	}

	public void update() {
		weapon.setX(x);
		weapon.setY(y + 30);
		weapon.update();

		if (delay > maxDelay) {
			// shootProjectile(true);
			delay = 0;
		} else {
			delay++;
		}

		super.update();
	}

	@Override
	public void draw(Graphics graphics) {
		weapon.draw(graphics);

		super.draw(graphics);
	}

	public void shootProjectile(boolean side) {
		weapon.shootProjectile(side);
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

	public void onMachine() {
		for (MasterComputer computer : map.getMenu().masterMind.getComputers()) {
			if (computer.intercepts(this)) {
				if (computer.isActive()) {
					computer.setActive(false);
					map.getOverlay()[computer.getLocation().getX()][computer.getLocation().getY()].setMaterial(Material.MACHINE_2);
					SoundUtil.playSound(Sound.TOGGLE);
				} else {
					computer.setActive(true);
					map.getOverlay()[computer.getLocation().getX()][computer.getLocation().getY()].setMaterial(Material.MACHINE_1);
					SoundUtil.playSound(Sound.TOGGLE);
				}

				computer.getMenu().masterMind.checkMaster();
			}
		}
	}
}
