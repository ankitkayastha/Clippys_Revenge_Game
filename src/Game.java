
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

import java.util.*;
public class Game {
	private static final String TITLE = "Clippy's Revenge";
	private static final int SIZE = 700;
	private static final int KEY_INPUT_SPEED = 5;
	private Group root; //scene graph to organize scene
	private Scene myScene; 
	private ImageView clippy;
	private List<Enemy> myEnemyList; //list of enemies
	private int counter = 0;
	private List<Projectile> myList; //list will contain projectiles to be updated (movement)
	private Random myRandom; //random object for xPosition of ship
	private Text myHealth; //text object to hold player's health
	private Text myScore; //text object to hold player's score
	private Image clicker;
	private Image click; 
	private ImageView background; 
	String[] clippyArray = new String[10];
	private List<Projectile> myEnemyProjectile;//list of enemy projectiles
	
	public String getTitle() {
		return TITLE;
	}
	/*
	 * Initialize and create the game's scene
	 */
	public Scene init(int width, int height) {
		//clippyArray = {};
		myRandom = new Random();
		//create the scene graph for organization
		root = new Group();
		//create the scene to see the shapes
		myScene = new Scene(root, width, height);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("Clippy.jpg"));
		clippy = new ImageView(image);
		//ship = new Circle(10, Color.RED);
		root.getChildren().add(clippy);
		clippy.setX(SIZE/2);
		clippy.setY(SIZE/2);
		myHealth = new Text(0, SIZE - 50, Integer.toString(100));
		Text scoreLabel = new Text(SIZE - 50, SIZE - 660, "SCORE");
		scoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
		myScore = new Text(SIZE - 50, SIZE - 650, Integer.toString(0));
		root.getChildren().add(myHealth);
		root.getChildren().add(myScore);
		root.getChildren().add(scoreLabel);
		Image imageTwo = new Image(getClass().getClassLoader().getResourceAsStream("WindowsBackground.jpg"));
		background = new ImageView(imageTwo);
		background.setFitHeight(SIZE);
		background.setFitWidth(SIZE);
		root.getChildren().add(background);
		background.toBack(); 
		click = new Image(getClass().getClassLoader().getResourceAsStream("click.png"));
		//clicker = new ImageView(click); 
		//root.getChildren().add(clicker);
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myList = new ArrayList<Projectile>();
		myEnemyList = new ArrayList<Enemy>();
		myEnemyProjectile = new ArrayList<Projectile>();
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
		
		//initialize motion of enemies
		if (Math.floorMod(counter, 200) == 0) {
			Enemy enemy = new Enemy();
			myEnemyList.add(enemy);
			root.getChildren().add(enemy.getEnemy());
			enemy.initializePositionEnemy();
		}
		
		//enemies firing projectiles
		for (Enemy a: myEnemyList) {
			if (a.getEnemy().isVisible() && Math.floorMod(counter,  300) == 0) {
				Projectile projectileOne = new Projectile(click);
				myEnemyProjectile.add(projectileOne);
				root.getChildren().add(projectileOne.getEnemyProjectile());
				projectileOne.initializeMotionEnemyProjectile(a.getEnemy());
			}
		} 
		
		/*for (Projectile x: myEnemyProjectile)
					//myEnemyProjectile.add(projectileOne);
				x.updatePositionEnemy(elapsedTime);  */
		//detection between enemy and projectile fired from user
		for (int i = 0; i < myEnemyList.size(); i++) {
			myEnemyList.get(i).updatePositionEnemy(elapsedTime);
			for (int j = 0; j < myList.size(); j++) {
				if (myEnemyList.get(i).getEnemy().getBoundsInParent().intersects(myList.get(j).getProjectile().getBoundsInParent()) && myList.get(j).getProjectile().isVisible() && myEnemyList.get(i).getEnemy().isVisible()) {
					myEnemyList.get(i).getEnemy().setVisible(false);
					myList.get(j).getProjectile().setVisible(false);
					myScore.setText(Integer.toString(Integer.parseInt(myScore.getText()) + 100));
				} 
			} 
		}  
		
		

		//detection between projectile fired from enemy and projectile fired from user
		for (Projectile x: myEnemyProjectile) { 
			x.updatePositionEnemyProjectile(elapsedTime);
			for (Projectile y: myList) {
				if (x.getEnemyProjectile().getBoundsInParent().intersects(y.getProjectile().getBoundsInParent()) && x.getEnemyProjectile().isVisible() && y.getProjectile().isVisible()) {
					x.getEnemyProjectile().setVisible(false);
					y.getProjectile().setVisible(false);
				}
			}
		}
		
		//detection between enemy and user
		for (int i = 0; i < myEnemyList.size(); i++) {
			if (myEnemyList.get(i).getEnemy().getBoundsInParent().intersects(clippy.getBoundsInParent()) && (myEnemyList.get(i).getEnemy().isVisible()) && !myEnemyList.get(i).getHasCollided()) {
					myHealth.setText(Integer.toString(Integer.parseInt(myHealth.getText()) - 5));
					myEnemyList.get(i).setHasCollided(true);
				}
			}
		
		
		//detection between projectiles from enemy and user
		for (Projectile x: myEnemyProjectile) { 
			if (x.getEnemyProjectile().getBoundsInParent().intersects(clippy.getBoundsInParent()) && x.getEnemyProjectile().isVisible() && !x.getHasCollided()) {
				myHealth.setText(Integer.toString(Integer.parseInt(myHealth.getText()) - 1)); 
				x.setHasCollided(true);
			}
		}
	}
	/*
	 * Handles key input
	 */
	private void handleKeyInput(KeyCode code) {
		 switch(code) {
		 	case SPACE: //fire projectile
		 		int index = myRandom.nextInt(10);
		 		Projectile projectileTip = new Projectile();
		 		myList.add(projectileTip);
		 		root.getChildren().add(projectileTip.getProjectile()); //show projectile in window
		 		projectileTip.initializeMotionProjectile(clippy);
		 		break;
		 	case RIGHT:
		 		clippy.setX(clippy.getX() + KEY_INPUT_SPEED);
		 		break;
		 	case LEFT:
		 		clippy.setX(clippy.getX() - KEY_INPUT_SPEED);
		 		break;
		 	case DOWN:
		 		clippy.setY(clippy.getY() + KEY_INPUT_SPEED);
		 		break;
		 	case UP:
		 		clippy.setY(clippy.getY() - KEY_INPUT_SPEED);
		 		break;
		 	default:
		 }
	 }
	
}
