

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.image.*; 
import java.util.*;
public class Projectile {
	
	private final int PROJECTILE_SPEED = -100; //speed of projectile that will be fired from user
	private final int ENEMY_PROJECTILE_SPEED = 100;
	private ImageView myEnemyProjectile;
	private Text myProjectile;
	private boolean hasCollided = false;
	private Random myRandom;
	private String[] clippyTips = {"Hi"};
	/*
	 * Default constructor to create new projectile. 
	 */
	public Projectile(Image click) {
		myEnemyProjectile = new ImageView(click);
		
	}
	
	public Projectile() {
		//int index = myRandom.nextInt(clippyTips.length);
		myProjectile = new Text(clippyTips[0]);
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
		double xPosition = clippy.getX() + 25;
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
