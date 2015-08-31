
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import java.util.*;
public class Game {
	private static String TITLE = "Mordred's Revenge";
	private static int SIZE = 700;
	private static int KEY_INPUT_SPEED = 5;
	private Group root; //scene graph to organize scene
	private Scene myScene; 
	private Circle ship;
	private List<Enemy> myEnemyList; //list of enemies
	private static int counter = 0;
	private List<Projectile> myList; //list will contain projectiles to be updated (movement)
	
	private Random myRandom; //random object for xPosition of ship
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
		ship.setCenterX(350);
		ship.setCenterY(600);
		
		
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
		//System.out.println("This method is being called");
		//call update method for each projectile in myLIst
		//Enemy enemy = null;
		for (Projectile x: myList) {
			for (Enemy y: myEnemyList) {
				x.updatePosition(elapsedTime);
				if (x.getProjectile().getBoundsInParent().intersects(y.getEnemy().getBoundsInParent())) 
					System.out.println("Hello"); }
		}
		//for (Enemy y: myEnemyList)
			//y.updatePositionEnemy(elapsedTime);
		if (Math.floorMod(counter, 100) == 0) {
			Enemy enemy = new Enemy();
			myEnemyList.add(enemy);
			//System.out.println(myEnemyList.size());
			root.getChildren().add(enemy.getEnemy());
			enemy.initializePositionEnemy();}
			//for (Enemy y: myEnemyList)
				//y.updatePositionEnemy(elapsedTime);}
		//System.out.println(myEnemyList.size());
		for (int i = 0; i < myEnemyList.size(); i++) {
			myEnemyList.get(i).updatePositionEnemy(elapsedTime); 
		}
			//System.out.println(i); }
		//System.out.println(myEnemyList.size());
			//System.out.println("New position for " + i + " is " + myEnemyList.get(i).getEnemy().getCenterY()); }
		//System.out.println(myEnemyList.size());
		//enemy.s(myRandom.nextInt(SIZE));
		
		//if (myEnemyList.size() <= 5)
		//enemy.initializePositionEnemy();
		//for (Enemy y: myEnemyList)
			//enemy.updatePositionEnemy(elapsedTime);
		//circle.setCenterX(myRandom.nextInt(SIZE));
		//for (Circle mycircle: myEnemyList)
			//circle.updatePosition(elapsedTime);
		
	}
	/*public void stepTwo (double elapsedTime) {
		counter++;
		if (Math.floorMod(counter, 100) == 0) {
			Enemy enemy = new Enemy();
			myEnemyList.add(enemy);
			root.getChildren().add(enemy.getEnemy());
			enemy.initializePositionEnemy(); }
		for (Enemy y: myEnemyList)
			y.updatePositionEnemy(elapsedTime);
	} */
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
