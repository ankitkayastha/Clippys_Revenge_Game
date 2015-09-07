// This entire file is part of my masterpiece.
// Ankit Kayastha

import java.util.*; 

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class GameLevelTwo extends Game {
	private Runnable losingScreen;
	private Runnable winningScreen;
	private Boss enemyBoss;
	private Text bossLives;
	private Text bossHealth;
	private Group levelTwoRoot;
	private Text bossHealthLabel;
	private Text bossLivesLabel;
	private Image bossProjectile;
	private List<Projectile> bossProjectileList;
	private boolean bossHasBeenAdded = false;
	private int counter;
	private boolean labelsAdded = false;
	
	public GameLevelTwo(Runnable x, Runnable y) {
		super(x);
		winningScreen = y;
	}

	public Text getBossHealth() {
		return bossHealth;
	}
	public void updateBossHealth(int n) {
		bossHealth.setText(Integer.toString(Integer.parseInt(bossHealth.getText()) - n));
	}
	
	public void updateBossLives() {
		bossLives.setText(Integer.toString(Integer.parseInt(bossLives.getText()) - 1));
		bossHealth.setText(Integer.toString(100));
	}

	@Override
	public Scene init(int width, int height) {
		Scene scene = super.init(SIZE, SIZE);
		enemyBoss = new Boss();
		bossProjectile = new Image(getClass().getClassLoader().getResourceAsStream("Fixafrozenmac.jpg"));
		bossProjectileList = new ArrayList<Projectile>();
		return scene;
	}  
	
	
	
	@Override
	public void step(double elapsedTime) {
		if (bossHasBeenAdded) {
			for (Enemy y: getMyEnemyList()) {
				getRoot().getChildren().remove(y.getEnemy());
				y.getEnemy().setVisible(false);
			}
			for (Projectile x: getMyEnemyProjectile()) {
					getRoot().getChildren().remove(x.getEnemyProjectile());
					x.getEnemyProjectile().setVisible(false);
			}
		} 
		super.step(elapsedTime);
		counter++;
		if (!(enemyBoss.getBoss().getBoundsInParent().intersects(getClippy().getBoundsInParent())))
			enemyBoss.setBossHasCollided(false);
		if (!bossHasBeenAdded)
			super.step(elapsedTime);
		for (Projectile x: getMyList())
			x.updatePosition(elapsedTime);
		Projectile projectileBoss = null;
		if (Integer.parseInt(getMyScore().getText()) >= 3000 && !bossHasBeenAdded && !labelsAdded) {
			bossHealthLabel = new Text(40, 40, "BOSS HEALTH");
			getRoot().getChildren().add(enemyBoss.getBoss());
			getRoot().getChildren().add(bossHealthLabel);
			bossHealth = new Text(40, 60, Integer.toString(100));
			getRoot().getChildren().add(bossHealth);
			bossHasBeenAdded = true;
			bossLivesLabel = new Text(40, 80, "# BOSS LIVES");
			getRoot().getChildren().add(bossLivesLabel);
			bossLives = new Text(40, 100, Integer.toString(3));
			getRoot().getChildren().add(bossLives);
			labelsAdded = true;
		}
		if (bossHasBeenAdded) {
			executeRunnable();
			for (Enemy y: getMyEnemyList()) {
				getRoot().getChildren().remove(y.getEnemy());
				y.getEnemy().setVisible(false);
			}
			for (Projectile x: getMyEnemyProjectile()) {
					getRoot().getChildren().remove(x.getEnemyProjectile());
					x.getEnemyProjectile().setVisible(false);
			} 
			if (Integer.parseInt(bossHealth.getText()) <= 0)
				updateBossLives();
			enemyBoss.updateMotion(elapsedTime); 
			if (Math.floorMod(counter, 100) == 0) {
				projectileBoss = new Projectile(bossProjectile);
				bossProjectileList.add(projectileBoss);
				getRoot().getChildren().add(projectileBoss.getEnemyProjectile());
				projectileBoss.initializeMotionEnemyProjectile(enemyBoss.getBoss());
			}
			detectionBossUserProjectile();
			detectionBossProjectileUser(elapsedTime);
		}
	}
	
	@Override
	public void executeRunnable() {
		if (bossLives != null && Integer.parseInt(bossLives.getText()) <= 0)
			winningScreen.run();
	}
	//detection between boss projectile and user/boss projectile and user projectile
	public void detectionBossProjectileUser(double elapsedTime) {
		for (Projectile x: bossProjectileList) {
			if (x.getEnemyProjectile().getBoundsInParent().intersects(getClippy().getBoundsInParent()) && !x.getHasCollided() && x.getEnemyProjectile().isVisible()) {
				getMyHealth().setText(Integer.toString(Integer.parseInt(getMyHealth().getText()) - 5 ));
				x.setHasCollided(true);
			}
			x.updatePositionEnemyProjectile(elapsedTime);
			for (Projectile y: getMyList()) {
				if (x.getEnemyProjectile().getBoundsInParent().intersects(y.getProjectile().getBoundsInParent()) && x.getEnemyProjectile().isVisible() && y.getProjectile().isVisible()) {
					x.getEnemyProjectile().setVisible(false);
					y.getProjectile().setVisible(false);
				}
			}
		}
	}
	
	//detection between boss and clippy, user projectile and boss
	public void detectionBossUserProjectile() {
		if (enemyBoss.getBoss().getBoundsInParent().intersects(getClippy().getBoundsInParent()) && !enemyBoss.getBossHasCollided()) {
			getMyHealth().setText(Integer.toString(Integer.parseInt(getMyHealth().getText()) - 10));
			enemyBoss.setBossHasCollided(true);
		}
		for (Projectile x: getMyList()) {
			if (x.getProjectile().getBoundsInParent().intersects(enemyBoss.getBoss().getBoundsInParent()) && !x.getHasCollided() && x.getProjectile().isVisible()) {
				updateBossHealth(10); 
				x.setHasCollided(true);
				x.getProjectile().setVisible(false);
			}
		}
	}
	@Override
	public void handleKeyInput(KeyCode code) {
		super.handleKeyInput(code);
		switch(code) {
		case E:
			bossHealth.setText(Integer.toString(0));
			break;
		default:
			break;
		}
	}
}
