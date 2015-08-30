

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {
	private static final int SIZE = 700; //size of game window
	private static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	
	
	private Game myGame;
	
	@Override
	public void start(Stage s) {
		
		myGame = new Game(); //create new game
		s.setTitle(myGame.getTitle()); //set title of game
		
		//attach game to stage and display it
		Scene scene = myGame.init(SIZE, SIZE);
		s.setScene(scene);
		s.show();
	}
	
	/*
	 * Start the program
	 */
	
	public static void main(String[] args) {
		launch(args);
	}
}
