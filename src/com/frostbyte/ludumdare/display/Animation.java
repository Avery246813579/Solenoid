package com.frostbyte.ludumdare.display;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Animation {
	private List<BufferedImage> images = new ArrayList<BufferedImage>();
	private int currentImage, delay, time;
	
	public Animation(String[] stringImages, int delay){
		try{
			for(String string : stringImages){
				images.add(ImageIO.read(getClass().getResourceAsStream(string)));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		this.delay = delay;
	}
	
	public Animation(List<BufferedImage> bufferedImages, int delay){
			images = bufferedImages;
			this.delay = delay;
	}
	
	public void update(){
		if(delay < time){
			time = 0;
			
			currentImage++;
			if((images.size() - 1) < currentImage){
				currentImage = 0;
			}
		}else{
			time++;
		}
	}
	
	public BufferedImage getCurrentBufferedImage(){
		return images.get(currentImage);
	}
	
	public List<BufferedImage> getImages() {
		return images;
	}
	
	public void setImages(List<BufferedImage> images) {
		this.images = images;
	}

	public int getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(int currentImage) {
		this.currentImage = currentImage;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
}
