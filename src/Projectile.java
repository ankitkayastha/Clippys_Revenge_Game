

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.image.ImageView;
public class Projectile {
	
	private static int PROJECTILE_SPEED = -100; //speed of projectile that will be fired
	private Circle myProjectile;
	/*
	 * Default constructor to create new projectile. 
	 */
	public Projectile() {
		myProjectile = new Circle(5, Color.BLUE);
		
	}
	
	public Circle getProjectile() {
		return myProjectile;
	}
	/*
	 * This method will initialize the motion of the projectile that will come from the ship
	 */
	public void initializeMotionProjectile(Circle ship) {
		double xPosition = ship.getCenterX();
		double yPosition = ship.getCenterY();
		myProjectile.setCenterX(xPosition);
		myProjectile.setCenterY(yPosition);
	}
	
	/*
	 * This method updates the position of the projectile in the y direction since there is no change
	 * in the x position of the projectile. PRojectiles are being shot vertcially upwards
	 */
	public void updatePosition(double elapsedTime) { 
		myProjectile.setCenterY(myProjectile.getCenterY() + PROJECTILE_SPEED * elapsedTime);
	}
}
