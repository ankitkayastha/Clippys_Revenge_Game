

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.*; 
import java.util.*;
public class Projectile {
	
	private final int PROJECTILE_SPEED = -100; //speed of projectile that will be fired from user
	private final int ENEMY_PROJECTILE_SPEED = 100;
	private ImageView myEnemyProjectile;
	private Text myProjectile;
	private boolean hasCollided = false;
	private Random myClippyRandom = new Random();
	private String[] clippyTips = {"Hi! I am Clippy, your office assistant.",  "Would you like some assistance today?", "It looks like you're writing a letter.", "Would you like help?", "Are you sure?", "This feature can help you.", "Are you really sure?", "I think it might be the best thing for you."};
	/*
	 * Default constructor to create new projectile. 
	 */
	public Projectile(Image click) {
		myEnemyProjectile = new ImageView(click);
		
	}
	public String[] getClippyTips() {
		return clippyTips;
	}
	public Projectile() {
		//clippyTips[0] = "Hi! I am Clippy";
		//clippyTips[1] = "Hi!";
		//System.out.println(clippyTips.length);
		//System.out.println(myRandom.nextInt(clippyTips.length));
		//int index = myRandom.nextInt(clippyTips.length);
		myProjectile = new Text(clippyTips[myClippyRandom.nextInt(clippyTips.length)]);
		myProjectile.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
		//myProjectile.setWrappingWidth(5);
		//myProjectile.setWrappingLength()
	}
	public boolean getHasCollided() {
		return hasCollided;
	}
	
	public void setHasCollided(boolean y) {
		hasCollided = y;
	}
	public ImageView getEnemyProjectile() {
		return myEnemyProjectile;
	}
	
	public Text getProjectile() {
		return myProjectile;
	}
	/*
	 * This method will initialize the motion of the projectile that will come from the clippy
	 */
	public void initializeMotionProjectile(ImageView clippy) {
		double xPosition = clippy.getX() - 5;
		double yPosition = clippy.getY();
		myProjectile.setX(xPosition);
		myProjectile.setY(yPosition);
	}
	/*
	 * Initialize projectiles coming from enemy
	 */
	public void initializeMotionEnemyProjectile(ImageView enemy) {
		myEnemyProjectile.setX(enemy.getX() + 15);
		myEnemyProjectile.setY(enemy.getY() - 10);
	}
	/*
	 * This method will update the position of the enemy projectiles
	 */
	public void updatePositionEnemyProjectile(double elapsedTime) {
		myEnemyProjectile.setY(myEnemyProjectile.getY() + ENEMY_PROJECTILE_SPEED * elapsedTime);
	}
	/*
	 * This method updates the position of the projectile in the y direction since there is no change
	 * in the x position of the projectile. PRojectiles are being shot vertcially upwards
	 */
	public void updatePosition(double elapsedTime) { 
		myProjectile.setY(myProjectile.getY() + PROJECTILE_SPEED * elapsedTime);
	}
}
