

/**
 * This class provides sound effects for the Tetris gmae. The sound effects should
 * be found in the resources folder.
 * 
 * @author Baldwin Browne
 * @version November 16, 2014
 */

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class TetrisSFX {
	
	private TetrisController controller;
	private AudioClip clip1;
	private String clSFX;
	private AudioClip clip2;
	private String tetrisSFX;
	private AudioClip clip3;
	private String gameOver;
	
	/**
	 * Constructor for TetrisSFX class. Takes the file path of the of the sound
	 * effects and loads them into AudioClip Objects.
	 */
	public TetrisSFX(TetrisController controller) {
		this.controller = controller;
		
		clSFX = "file:res/Clear_Line.wav";
		tetrisSFX = "file:res/TetrisSFX.wav";
		gameOver = "file:res/Game_Over.wav";
		try 
		{
                    URL url = new URL(clSFX);
			clip1 = Applet.newAudioClip(url);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		try 
		{
                    URL url = new URL(tetrisSFX);
			clip2 = Applet.newAudioClip(url);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		try 
		{
                    URL url = new URL(gameOver);
			clip3 = Applet.newAudioClip(url);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Plays the clear line sound effect.
	 */
	public void playCL() {
            if(clip1 == null) return;
		try
		{ new Thread(){
			public void run(){
				clip1.play();
			}
		}.start();
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * Plays the Tetris sound effect for when four lines are cleared at a time.
	 */
	public void playTetris() {
            if(clip2 == null) return;
		try
		{ new Thread(){
			public void run(){
				clip2.play();
			}
		}.start();
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/**
	 * Plays the game over sound effect.
	 */
	public void playGameOver() {
            if(clip3 == null) return;
		try
		{ new Thread(){
			public void run(){
				clip3.play();
			}
		}.start();
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}

}
