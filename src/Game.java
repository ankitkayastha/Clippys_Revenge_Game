
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

import java.util.*;
public class Game {
	private final String TITLE = "Mordred's Revenge";
	private final int SIZE = 700;
	private final int KEY_INPUT_SPEED = 5;
	private Group root; //scene graph to organize scene
	private Scene myScene; 
	private Circle ship;
	private List<Enemy> myEnemyList; //list of enemies
	private int counter = 0;
	private List<Projectile> myList; //list will contain projectiles to be updated (movement)
	private Random myRandom; //random object for xPosition of ship
	private Text myText; //textfield to hold player's score
	private String myScore;
	private ImageView background;
	
	
	public String getTitle() {
		return TITLE;
	}
	/*
	 * Initialize and create the game's scene
	 */
	public Scene init(int width, int height) {
		myRandom = new Random();
		//create the scene graph for organization
		root = new Group();
		//create the scene to see the shapes
		myScene = new Scene(root, width, height);
		ship = new Circle(10, Color.RED);
		root.getChildren().add(ship);
		ship.setCenterX(SIZE/2);
		ship.setCenterY(SIZE - 100);
		myText = new Text(0, SIZE - 50, Integer.toString(100));
		root.getChildren().add(myText);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("images.jpe"));
		background = new ImageView(image);
		background.setFitHeight(SIZE);
		background.setFitWidth(SIZE);
		background.toBack();
		//root.getChildren().add(background);
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myList = new ArrayList<Projectile>();
		myEnemyList = new ArrayList<Enemy>();
		return myScene;
	} 
	
	/*
	 * This method will run during the game loop to update the shapes/game
	 */
	
	public void step(double elapsedTime) {
		
		counter++;
		//call update method for each projectile in myLIst
		for (Projectile x: myList) {
				x.updatePosition(elapsedTime);
		}
		if (Math.floorMod(counter, 200) == 0) {
			Enemy enemy = new Enemy();
			myEnemyList.add(enemy);
			root.getChildren().add(enemy.getEnemy());
			enemy.initializePositionEnemy();
		}
		
		for (int i = 0; i < myEnemyList.size(); i++) {
			myEnemyList.get(i).updatePositionEnemy(elapsedTime);
			for (int j = 0; j < myList.size(); j++) {
				if (myEnemyList.get(i).getEnemy().getBoundsInParent().intersects(myList.get(j).getProjectile().getBoundsInParent()) && myList.get(j).getProjectile().isVisible() && myEnemyList.get(i).getEnemy().isVisible()) {
					myEnemyList.get(i).getEnemy().setVisible(false);
					myList.get(j).getProjectile().setVisible(false);
					System.out.println("Hello!"); } } }  
		
		for (int i = 0; i < myEnemyList.size(); i++) {
			if (myEnemyList.get(i).getEnemy().getBoundsInParent().intersects(ship.getBoundsInParent()) && (myEnemyList.get(i).getEnemy().isVisible()))
					myText.setText(Integer.toString(Integer.parseInt(myText.getText()) - 5)); 
			}
		
	}
	
	/*
	 * Handles key input
	 */
	private void handleKeyInput(KeyCode code) {
		 switch(code) {
		 	case SPACE: //fire projectile
		 		Projectile projectile = new Projectile();
		 		myList.add(projectile);
		 		root.getChildren().add(projectile.getProjectile()); //show projectile in window
		 		projectile.initializeMotionProjectile(ship);
		 		break;
		 	case RIGHT:
		 		ship.setCenterX(ship.getCenterX() + KEY_INPUT_SPEED);
		 		break;
		 	case LEFT:
		 		ship.setCenterX(ship.getCenterX() - KEY_INPUT_SPEED);
		 		break;
		 	case DOWN:
		 		ship.setCenterY(ship.getCenterY() + KEY_INPUT_SPEED);
		 		break;
		 	case UP:
		 		ship.setCenterY(ship.getCenterY() - KEY_INPUT_SPEED);
		 		break;
		 	default:
		 }
	 }
	
	
}
