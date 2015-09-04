
import javafx.application.Platform;
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
	public static final int SIZE = 700;
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
	private ImageView losingScreen;
	private Image click; 
	private ImageView background;
	private Runnable continuationScreenRunnable;
	private Runnable losingScreenRunnable;
	private List<Projectile> myEnemyProjectile;//list of enemy projectiles
	public int getCounter() {
		return counter;
	}
	//public Game() {
	//	
	//}
	public List<Projectile> getMyList() {
		return myList;
	}
	public List<Enemy> getEnemyList() {
		return myEnemyList;
	}
	
	public List<Projectile> getEnemyProjectileList() {
		return myEnemyProjectile;
	}
	public ImageView getClippy() {
		return clippy;
	}
	public Image getImageClick() {
		return click;
	}
	public String getTitle() {
		return TITLE;
	}
	public Scene getScene() {
		return myScene;
	}
	public Runnable getContinuationScreenRunnable() {
		return continuationScreenRunnable;
	}
	
	
	
	public Group getGroup() {
		return root;
	}
	public Game (Runnable x, Runnable y) {
		losingScreenRunnable = x;
		continuationScreenRunnable = y;
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
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("Clippy.jpg"));
		clippy = new ImageView(image);
		root.getChildren().add(clippy);
		clippy.setX(SIZE/2 - 50);
		clippy.setY(SIZE - 100);
		Text healthLabel = new Text(25, SIZE - 65, "HEALTH");
		healthLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
		myHealth = new Text(25, SIZE - 50, Integer.toString(100));
		Text scoreLabel = new Text(SIZE - 50, SIZE - 665, "SCORE");
		scoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
		setMyScore(new Text(SIZE - 50, SIZE - 650, Integer.toString(0)));
		root.getChildren().add(myHealth);
		root.getChildren().add(myScore);
		root.getChildren().add(scoreLabel);
		root.getChildren().add(healthLabel);
		Image imageTwo = new Image(getClass().getClassLoader().getResourceAsStream("WindowsBackground.jpg"));
		background = new ImageView(imageTwo);
		background.setFitHeight(SIZE);
		background.setFitWidth(SIZE);
		root.getChildren().add(background);
		background.toBack(); 
		click = new Image(getClass().getClassLoader().getResourceAsStream("click.png"));
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myList = new ArrayList<Projectile>();
		myEnemyList = new ArrayList<Enemy>();
		myEnemyProjectile = new ArrayList<Projectile>();
		return myScene;
	} 
	public Text getHealth() {
		return myHealth;
		//(Integer.parseInt(myHealth.getText()));
	}
	/*
	 * This method will run during the game loop to update the shapes/game
	 */
	
	public void step(double elapsedTime) {
		if (Integer.parseInt(getHealth().getText()) <= 0) {
			losingScreenRunnable.run(); 
		}
			executeRunnable();
		counter++;
		//call update method for each projectile in myLIst
		//System.out.println(myList.size());
		for (Projectile x: myList) {
			//System.out.println("This is being called");	
			x.updatePosition(elapsedTime);
		}
		//initialize motion of enemies
		createEnemy();
		enemyFireProjectile(); 
		//detection between enemy and projectile fired from user
		collisionDetectionEnemyUser(elapsedTime); 
		//detection between projectile fired from enemy and projectile fired from user
		collisionDetectionEnemyProjectileUser(elapsedTime);
	}
		
		
		
		
	public void executeRunnable() {
		if (Integer.parseInt(myScore.getText()) >= 2000)
			continuationScreenRunnable.run();
	}
	
	/**
	 * 
	 * @param elapsedTime time that has elapsed in game so far
	 */
	public void collisionDetectionEnemyProjectileUser(double elapsedTime) {
		for (Projectile x: myEnemyProjectile) { 
			if (x.getEnemyProjectile().getBoundsInParent().intersects(clippy.getBoundsInParent()) && x.getEnemyProjectile().isVisible() && !x.getHasCollided()) {
				myHealth.setText(Integer.toString(Integer.parseInt(myHealth.getText()) - 1)); 
				x.setHasCollided(true);
			}
			x.updatePositionEnemyProjectile(elapsedTime);
			for (Projectile y: myList) {
				if (x.getEnemyProjectile().getBoundsInParent().intersects(y.getProjectile().getBoundsInParent()) && x.getEnemyProjectile().isVisible() && y.getProjectile().isVisible()) {
					x.getEnemyProjectile().setVisible(false);
					y.getProjectile().setVisible(false);
				}
			}
		}
	}

	/**
	 * collision detection between enemy and user as well as user projectiles and enemy
	 * @param elapsedTime time elpased in game so far
	 */
	public void collisionDetectionEnemyUser(double elapsedTime) {
		for (int i = 0; i < myEnemyList.size(); i++) {
			if (myEnemyList.get(i).getEnemy().getBoundsInParent().intersects(clippy.getBoundsInParent()) && (myEnemyList.get(i).getEnemy().isVisible()) && !myEnemyList.get(i).getHasCollided()) {
				myHealth.setText(Integer.toString(Integer.parseInt(myHealth.getText()) - 5));
				myEnemyList.get(i).setHasCollided(true);
			}	
			myEnemyList.get(i).updatePositionEnemy(elapsedTime);
			for (int j = 0; j < myList.size(); j++) {
				if (myEnemyList.get(i).getEnemy().getBoundsInParent().intersects(myList.get(j).getProjectile().getBoundsInParent()) && myList.get(j).getProjectile().isVisible() && myEnemyList.get(i).getEnemy().isVisible()) {
					myEnemyList.get(i).getEnemy().setVisible(false);
					myList.get(j).getProjectile().setVisible(false);
					myScore.setText(Integer.toString(getMyScore() + 100));
				} 
			} 
		}
	}

	/**
	 * Fire projectile from enemy
	 */
	public void enemyFireProjectile() {
		//.out.println("This is being called");
		for (Enemy a: myEnemyList) {
			if (a.getEnemy().isVisible() && Math.floorMod(counter,  300) == 0) {
				Projectile projectileOne = new Projectile(click);
				myEnemyProjectile.add(projectileOne);
				//System.out.println("This is being called");
				root.getChildren().add(projectileOne.getEnemyProjectile());
				projectileOne.initializeMotionEnemyProjectile(a.getEnemy());
				//System.out.println(projectileOne.getProjectile().isVisible());
			}
		}
	}
	
	public void createEnemy() {
		if (Math.floorMod(counter, 100) == 0) {
			Enemy enemy = new Enemy();
			myEnemyList.add(enemy);
			root.getChildren().add(enemy.getEnemy());
			enemy.initializePositionEnemy();
		}
	}
	/*
	 * Handles key input
	 */
	public void handleKeyInput(KeyCode code) {
		 switch(code) {
		 	case SPACE: //fire projectile
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
		 	case H:
		 		myHealth.setText(Integer.toString(100));
		 		break;
		 	case D:
		 		myHealth.setText(Integer.toString(0));
		 		break;
		 	case S:
		 		myScore.setText(Integer.toString((getMyScore() + 500)));
		 		break;
		 	default:
		 }
	 }
	public int getMyScore() {
		return Integer.parseInt(myScore.getText());
	}
	public void setMyScore(Text myScore) {
		this.myScore = myScore;
	}
	
}
