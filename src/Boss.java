import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.util.*;
public class Boss {
	private ImageView myBoss;
	private int SIZE = 700;
	private int bossLives = 3;
	private int bossHealth = 100;
	private int BOSS_X_SPEED = 80;
	private int BOSS_Y_SPEED = 80;
	private boolean bossHasCollided = false;
	private Image click = new Image(getClass().getClassLoader().getResourceAsStream("click.png"));
	private List<Projectile> bossProjectileList;
	
	
	public Boss() {
		Image boss = new Image(getClass().getClassLoader().getResourceAsStream("index.jpe"));
		myBoss = new ImageView(boss);
		myBoss.setX(SIZE / 2);
		myBoss.setY(100);
		bossProjectileList = new ArrayList<Projectile>();
	}
	public boolean getBossHasCollided() {
		return bossHasCollided;
	}
	public void setBossHasCollided(boolean x) {
		bossHasCollided = x;
	}
	public ImageView getBoss() {
		return myBoss;
	}
	public void updateLives(Text health, Text lives) {
		if (Integer.parseInt(health.getText()) <= 0) {
			lives.setText(Integer.toString(Integer.parseInt(lives.getText()) - 1));
		}
		health.setText(Integer.toString(100));
	}
	public void updateMotion(double elapsedTime) {
		myBoss.setX(myBoss.getX() + BOSS_X_SPEED * elapsedTime);
		myBoss.setY(myBoss.getY() + BOSS_Y_SPEED * elapsedTime);
		if (myBoss.getX() + 80 >= SIZE || myBoss.getX() <= 0)
			BOSS_X_SPEED = BOSS_X_SPEED * -1;
		if (myBoss.getY() <= 0 || myBoss.getY() + 70 >= SIZE)
			BOSS_Y_SPEED = BOSS_Y_SPEED * -1;
			
	}
	
	public void fireProjectile(Image click, Group root, ImageView enemyProjectile) {
		Projectile projectile = new Projectile(click);
		bossProjectileList.add(projectile);
		enemyProjectile = new ImageView(click);
		root.getChildren().add(enemyProjectile);
		
	}
	public void checkBoss(Text lives) {
		if (Integer.parseInt(lives.getText()) <= 0)
			System.out.println("You win!");
	}
}
