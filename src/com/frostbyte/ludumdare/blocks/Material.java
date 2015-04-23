package com.frostbyte.ludumdare.blocks;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public enum Material {
	AIR("AIR", false, 200),
	STAIR_BASE("STAIR_BASE", true, 203),
	AIRDUCT("AIRDUCT", true, 0),
	AIRDUCT_WALL("AIRDUCT_WALL", false, 1),
	ALARM_OFF("ALARM_OFF", false, 2),
	ALARM_ON("ALARM_ON", false, 3),
	ASPHALT_1("ASPHALT_1", true, 4),
	ASPHALT_2("ASPHALT_2",true, 5),
	BARREL_1("BARREL_1", false, 6),
	BARREL_2("BARREL_2", false, 7),
	BARREL_3("BARREL_3", false,8),
	BLUE_WIRE_1("BLUE_WIRE_1", false, 9),
	BLUE_WIRE_2("BLUE_WIRE_2", false, 10),
	BLUE_WIRE_3("BLUE_WIRE_3", false, 11),
	BLUE_WIRE_4("BLUE_WIRE_4", false, 12),
	BLUE_WIRE_5("BLUE_WIRE_5", false, 13),
	BLUE_WIRE_6("BLUE_WIRE_6", false, 14),
	BRICK("BRICK", false, 15),
	BRICK_WALL("BRICK_WALL", false, 16),
	CAVE_WALL("CAVE_WALL", false, 17),
	CEILING_VENT("CEILING_VENT", false, 200),
	CLOUD_1("CLOUD_1", false, 18),
	CLOUD_2("CLOUD_2", false, 19),
	CLOUD_3("CLOUD_3", false, 20),
	CLOUD_4("CLOUD_4", false, 21),
	CLOUD_5("CLOUD_5", false, 22),
	CONTROL_PANEL_1("CONTROL_PANEL_1",false, 23),
	CONTROL_PANEL_2("CONTROL_PANEL_2", false,24),
	CRATE_1("CRATE_1", false, 25),
	CRATE_2("CRATE_2", false, 26),
	DIRT("DIRT", true, 27),
	DIRTY_WATER_1("DIRTY_WATER_1", false, 28),
	DIRTY_WATER_2("DIRTY_WATER_2", true, 29),
	DIRTY_WATER_3("DIRTY_WATER_3", true, 30),
	DOOR_1("DOOR_1", true, 31),
	DOOR_2("DOOR_2", false, 32),
	FACTORY_FLOOR_1("FACTORY_FLOOR_1", true, 33),
	FACTORY_FLOOR_2("FACTORY_FLOOR_2", true, 34),
	//FACTORY_WALL
	FIRE_ALARM("FIRE_ALARM", false, 34),
	//FIRE_EXTINGUISHER
	//GLASS
	//GRASS
	GRASS_BLOCK("GRASS_BLOCK", true, 40),
	GREEN_WIRE_1("GREEN_WIRE_1", false, 41),
	GREEN_WIRE_2("GREEN_WIRE_2", false, 42),
	GREEN_WIRE_3("GREEN_WIRE_3", false, 43),
	GREEN_WIRE_4("GREEN_WIRE_4", false, 44),
	GREEN_WIRE_5("GREEN_WIRE_5", false, 45),
	GREEN_WIRE_6("GREEN_WIRE_6", false, 46),
	IRON_FLOOR("IRON_FLOOR", true, 47),
	IRON_WALL_1("IRON_WALL_1", true, 48),
	IRON_WALL_2("IRON_WALL_2", false, 49),
	JANITOR_CART("JANITOR CART", false, 50),
	LADDER("LADDER", false, 51),
	LADDER_BOTTOM("LADDER_BOTTOM", false, 52),
	LADDER_TOP("LADDER_TOP", false, 53),
	LEVER_OFF("LEVER_OFF", false, 54),
	LEVER_ON("LEVER_ON", false, 55),
	SLIDING_DOOR("SLIDING_DOOR", true, 101),
	//LIGHT_1
	//LIGHT_2
	MACHINE_1("MACHINE_1", false, 666),
	MACHINE_2("MACHINE_2", false, 58),
	MOTORCYCLE("MOTORCYCLE", true, 59),
	//PAINTED_ARROW_1
	//PAINTED_ARROW_2
	//PAINTED_ARROW_3
	//PAINTED_ARROW_4
	PIPE_1("PIPE_1", false, 64),
	PIPE_2("PIPE_2", false, 65),
	PIPE_3("PIPE_3", false, 66),
	PIPE_4("PIPE_4", false, 67),
	PIPE_5("PIPE_5", false, 68),
	PIPE_6("PIPE_6", false, 69),
	RED_WIRE_1("RED_WIRE_1", false, 71),
	RED_WIRE_2("RED_WIRE_2", false, 72),
	RED_WIRE_3("RED_WIRE_3", false, 73),
	RED_WIRE_4("RED_WIRE_4", false, 74),
	RED_WIRE_5("RED_WIRE_5", false, 75),
	RED_WIRE_6("RED_WIRE_6", false, 76),
	SKY("SKY", false, 77),
	//TOXIC_LIQUIC
	VENT_1("VENT_1", false, 79),
	VENT_2("VENT_2", false, 80),
	//WARNING_1
	//WARNING_2
	WINDOW("WINDOW", false, 83),
//	WOODEN_FLOOR
	YELLOW_WIRE_1("YELLOW_WIRE_1", false, 85),
	YELLOW_WIRE_2("YELLOW_WIRE_2", false, 86),
	YELLOW_WIRE_3("YELLOW_WIRE_3", false, 87),
	YELLOW_WIRE_4("YELLOW_WIRE_4", false, 88),
	YELLOW_WIRE_5("YELLOW_WIRE_5", false, 89),
	YELLOW_WIRE_6("YELLOW_WIRE_6", false, 90),
	POWERBOX_1("POWERBOX_1", false, 97),
	POWERBOX_2("POWERBOX_2", false, 98),
	POWERBOX_3("POWERBOX_3", false, 99);
	
	
	private BufferedImage bufferedImage;
	private boolean solid;
	private int blockCode;
	
	Material(String imageLocation, boolean solid, int blockCode){
		try{
			this.bufferedImage = ImageIO.read(getClass().getResourceAsStream("/MATERIALS/" + imageLocation + ".png"));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		this.solid = solid;
		this.blockCode = blockCode;
	}
	
	public static Material getMaterialFromCode(int code){
		for(Material material : values()){
			if(material.blockCode == code){
				return material;
			}
		}
		
		return Material.AIR;
	}

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public int getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(int blockCode) {
		this.blockCode = blockCode;
	}
}
