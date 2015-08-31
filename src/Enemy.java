import javafx.scene.paint.Color;
import javafx.scene.shape.Circle; 
import java.util.*;
public class Enemy {
	private static int SIZE = 700;
	private static int ENEMY_SPEED = 50;
	private static Circle myEnemy;
	private Random myRandom = new Random();
	/*
	 * Default constructor to create new enemy
	 */
	
	public Enemy() {
		myEnemy = new Circle(20, Color.CYAN);
	}
	
	/*
	 * Returns the enemy as a circle
	 */
	public Circle getEnemy() {
		
		return myEnemy;
	}
	
	
	
	public void initializePositionEnemy() {
		myEnemy.setCenterX(myRandom.nextInt(SIZE));
		myEnemy.setCenterY(0);
	}
	public void updatePositionEnemy(double elapsedTime) {
		//System.out.println("This method is being called");
		//System.out.println(myEnemy.getCenterY());
		myEnemy.setCenterY(myEnemy.getCenterY() + ENEMY_SPEED * elapsedTime);
	}
	
	
}
