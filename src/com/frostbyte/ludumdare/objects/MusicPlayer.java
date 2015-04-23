package com.frostbyte.ludumdare.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MusicPlayer {
	public static List<Song> gameSongs = new ArrayList<Song>(Arrays.asList(Song.TRACK_4, Song.TRACK_3));
	public static List<Song> menuSongs = new ArrayList<Song>(Arrays.asList(Song.TRACK_2, Song.TRACK_1));
	public List<Song> currentList = menuSongs;
	public int currentSong;
	public int currentLength = currentList.get(0).getLength(), time;
	public int mili = 0;
	
	public MusicPlayer(){
		SoundUtil.playSong(currentList.get(currentSong));
	}
	
	public void update(){
		if(mili <= 25){
			mili++;
			return;
		}
		
		if(time > currentLength){
			if(currentList.size() - 1 <= currentSong){
				currentSong = 0;
			}else{
				currentSong++;
			}
			
			time = 0;
			currentLength = currentList.get(currentSong).getLength();
			SoundUtil.playSong(currentList.get(currentSong));
		}else{
			time++;
		}
		
		mili = 0;
	}
}
