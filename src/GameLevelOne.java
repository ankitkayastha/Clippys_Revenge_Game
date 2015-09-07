// This entire file is part of my masterpiece.
// Ankit Kayastha

import javafx.application.Platform;
import javafx.scene.*; 
import javafx.scene.image.* ;
import javafx.scene.input.KeyCode;
import javafx.scene.text.*; 

import java.util.*;

public class GameLevelOne extends Game {
	private Runnable continuationScreenRunnable;
	
	public GameLevelOne (Runnable x, Runnable y) {
		super(y);
		continuationScreenRunnable = x;
	}
	/*
	 * Initialize and create the game's scene
	 */
	public Scene init(int width, int height) {
		
		Scene scene = super.init(width, height);
		return scene;
	} 
	/*
	 * This method will run during the game loop to update the shapes/game
	 */
	
	public void step(double elapsedTime) {
		super.step(elapsedTime);
		executeRunnable();
	}
	
	@Override
	public void executeRunnable() {
		if (Integer.parseInt(getMyScore().getText()) >= 2000)
			continuationScreenRunnable.run();
	}
}