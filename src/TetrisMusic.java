

/**
 * This class handles the background music for the Tetris game. You can call on this
 * class to play and stop the game's background music.
 * 
 * @author Baldwin Browne
 * @version November 16, 2014
 */

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class TetrisMusic {
	
	/**
	 * Constructs a TetrisMusic object using Tetris_Music.wav as the argument.
	 */
	
	private TetrisController controller;
	private String  music = "file:res/Tetris_Music.wav";
	private AudioClip clip;
	
	
	/**
	 * Constructor - creates an AudioClip loaded with fileName parameter.
	 * @param fileName
	 */
	public TetrisMusic(TetrisController controller){
		
		this.controller = controller;
		
		try {
                    URL url = new URL(music);
			clip = Applet.newAudioClip(url);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Method for playing the AudioClip in a loop.
	 */
	public void play() {
            if(clip == null) return;
		try {
			new Thread(){ 
				public void run(){
					clip.loop();
				}
			}.start();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * Method for stopping the AudioClip
	 */
	public void stop() {
            if(clip == null) return;
		clip.stop();

	}

}