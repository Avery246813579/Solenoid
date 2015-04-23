package com.frostbyte.ludumdare.main;

public class GameLoop extends Thread implements Runnable{
	GameManager gameManager;
	int FPS = 60;
	long targetTime = 1000 / FPS;
	
	public GameLoop(GameManager gameManager){
		this.gameManager = gameManager;
	}
	
	@Override
	public void run() {
		long start = 0;
		long elapsed;
		long wait;
		
		while(gameManager.running){
			synchronized (this) {
				gameManager.update();
				gameManager.doubleBuffer();

				elapsed = System.nanoTime() - start;
				wait = targetTime - elapsed / 1000000;
				
				if(wait < 0){
					wait = 5;
				}
				
				try{
					Thread.sleep(wait);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
}
