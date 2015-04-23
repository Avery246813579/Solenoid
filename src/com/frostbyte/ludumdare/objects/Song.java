package com.frostbyte.ludumdare.objects;

public enum Song {
	TRACK_1("/MUSIC/TRACK_1.wav", 138),
	TRACK_2("/MUSIC/TRACK_2.wav", 46),
	TRACK_3("/MUSIC/TRACK_3.wav", 132),
	TRACK_4("/MUSIC/TRACK_4.wav", 150),
	STEP_1("/SOUND/STEP_1.wav", 19),
	STEP_2("/SOUND/STEP_2.wav", 38),
	STEP_3("/SOUND/STEP_3.wav", 28),
	STEP_4("/SOUND/STEP_4.wav", 27),
	STEP_5("/SOUND/STEP_5.wav", 40);
	
	private String location;
	private int length;
	private Song(String location, int length) {
		this.location = location;
		this.length = length;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
