package com.frostbyte.ludumdare.objects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.frostbyte.ludumdare.main.Main;

public class SoundUtil {
	static Clip currentSong;
	
	public static void playSong(Song song){
		stopMusic();
		
		try{
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(Main.class.getResource(song.getLocation()));
			clip.open(inputStream);
			clip.start();
			
			SoundUtil.currentSong = clip;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void playSound(Sound sound){
		try{
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(Main.class.getResource(sound.getLocation()));
			clip.open(inputStream);
			clip.start();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void stopMusic(){
		if(currentSong != null){
			currentSong.stop();
		}
	}
}
