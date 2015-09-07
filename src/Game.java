// This entire file is part of my masterpiece.
// Ankit Kayastha

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public abstract class Game {
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
	private Text healthLabel;
	private Text scoreLabel;
	
	public Game(Runnable y) {
		losingScreenRunnable = y;
	}
	
	public Group getRoot() {
		return root;
	}
	public Scene getMyScene() {
		return myScene;
	}
	public ImageView getClippy() {
		return clippy;
	}
	public List<Enemy> getMyEnemyList() {
		return myEnemyList;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public List<Projectile> getMyList() {
		return myList;
	}
	public String getTitle() {
		return TITLE;
	}
	public Text getMyHealth() {
		return myHealth;
	}
	public void setMyHealth(Text myHealth) {
		this.myHealth = myHealth;
	}
	public Text getMyScore() {
		return myScore;
	}
	public void setMyScore(Text myScore) {
		this.myScore = myScore;
	}
	public ImageView getLosingScreen() {
		return losingScreen;
	}
	public void setLosingScreen(ImageView losingScreen) {
		this.losingScreen = losingScreen;
	}
	public Image getClick() {
		return click;
	}
	public Runnable getContinuationScreenRunnable() {
		return continuationScreenRunnable;
	}

	public Runnable getLosingScreenRunnable() {
		return losingScreenRunnable;
	}

	public List<Projectile> getMyEnemyProjectile() {
		return myEnemyProjectile;
	}

	public Text getHealthLabel() {
		return healthLabel;
	}
	
	public Scene init(int width, int height) {
		myRandom = new Random();
		root = new Group();
		myScene = new Scene(root, width, height);
		String[] fileNames = {"Clippy.jpg", "WindowsBackground.jpg"};
		ImageView[] myImageViews = createImageView(fileNames);
		clippy = myImageViews[0];
		background = myImageViews[1];
		healthLabel = new Text(25, SIZE - 65, "HEALTH");
		healthLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 10));

		myHealth = new Text(25, SIZE - 50, Integer.toString(100));
		scoreLabel = new Text(SIZE - 50, SIZE - 665, "SCORE");
		background.setFitHeight(SIZE);
		background.setFitWidth(SIZE);
		setMyScore(new Text(SIZE - 50, SIZE - 650, Integer.toString(0)));
		Node[] myArray = {clippy, getHealthLabel(), myHealth, scoreLabel, myScore, background};
		addToRoot(myArray);
		background.toBack();
		scoreLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
		clippy.setX(SIZE/2 - 50);
		clippy.setY(SIZE - 100);
		click = new Image(getClass().getClassLoader().getResourceAsStream("click.png"));
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myList = new ArrayList<Projectile>();
		myEnemyList = new ArrayList<Enemy>();
		myEnemyProjectile = new ArrayList<Projectile>();
		
		return myScene;
		
	}

	public void step(double elapsedTime) {
		if (Integer.parseInt(myHealth.getText()) <= 0) {
			losingScreenRunnable.run(); 
		}
	counter++;
		for (Projectile x: myList) {
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
	public abstract void executeRunnable();
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
					myScore.setText(Integer.toString(Integer.parseInt(myScore.getText()) + 100));
				} 
			} 
		}
	}

	public void enemyFireProjectile() {
		for (Enemy a: myEnemyList) {
			if (a.getEnemy().isVisible() && Math.floorMod(counter,  300) == 0) {
				Projectile projectileOne = new Projectile(click);
				myEnemyProjectile.add(projectileOne);
				root.getChildren().add(projectileOne.getEnemyProjectile());
				projectileOne.initializeMotionEnemyProjectile(a.getEnemy());
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
	public ImageView[] createImageView(String[] fileName) {
		Image[] myImageArray = new Image[fileName.length];
		ImageView[] myImages = new ImageView[fileName.length];
		for (int i = 0; i < fileName.length; i++) {
			myImageArray[i] = new Image(getClass().getClassLoader().getResourceAsStream(fileName[i]));
			myImages[i] = new ImageView(myImageArray[i]);
		}
		return myImages;
	}
	
	public void addToRoot(Node[] myObjectArray) {
		for (int i = 0; i < myObjectArray.length; i++) {
			root.getChildren().add(myObjectArray[i]);
		}
	}
	
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
		 		myScore.setText(Integer.toString((Integer.parseInt(myScore.getText()) + 500)));
		 		break;
		 	default:
		 }
	 }
}
