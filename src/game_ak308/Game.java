package game_ak308;

import javafx.scene.Group;
import javafx.scene.Scene;

public class Game {
	private static String TITLE = "Mordred's Revenge";
	private Group root; //scene graph to organize scene
	private Scene myScene; //
	public String getTitle() {
		return TITLE;
		
	}
	
	public Scene init(int width, int height) {
		//create the scene graph for organization
		root = new Group();
		//create the scene to see the shapes
		myScene = new Scene(root, width, height);
		
		
		
		
		
		return myScene;
		
		
		
	}
}
