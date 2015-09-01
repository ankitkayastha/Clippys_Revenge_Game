import javafx.scene.paint.Color;
import javafx.scene.shape.Circle; 
import java.util.*;
public class Enemy {
	private int SIZE = 700;
	private int ENEMY_SPEED = 50;
	private Circle myEnemy;
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
		myEnemy.setTranslateY(myEnemy.getTranslateY() + ENEMY_SPEED * elapsedTime);
	}
	
	
}
