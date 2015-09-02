import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle; 
import java.util.*;
public class Enemy {
	private static final int SIZE = 700;
	private static final int ENEMY_SPEED = 50;
	private ImageView myEnemy; 
	private Random myRandom = new Random();
	private boolean hasCollided = false;
	/*
	 * Default constructor to create new enemy
	 */
	
	public Enemy() {
		Image enemy = new Image(getClass().getClassLoader().getResourceAsStream("images.jpe"));
		myEnemy = new ImageView(enemy);
	}
	
	/*
	 * Returns the enemy as a circle
	 */
	public ImageView getEnemy() {
		
		return myEnemy;
	}
	
	public boolean getHasCollided() {
		return hasCollided;
	}
	public void setHasCollided(boolean x) {
		hasCollided = x;
	}
	public void initializePositionEnemy() {
		myEnemy.setX(myRandom.nextInt(SIZE));
		myEnemy.setY(0);
	}
	public void updatePositionEnemy(double elapsedTime) {
		myEnemy.setY(myEnemy.getY() + ENEMY_SPEED * elapsedTime);
	}
	
	
}
