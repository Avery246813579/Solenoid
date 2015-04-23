package com.frostbyte.ludumdare.blocks;

public class Block {
	private Material material;
	
	public Block(Material material){
		this.material = material;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
}
