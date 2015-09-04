import java.util.*; 

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class GameLevelTwo extends Game {
	private Runnable runnableLevelTwo;
	//private Scene myScene;
	private Boss enemyBoss;
	private Text bossLives;
	private Text bossHealth;
	private Group levelTwoRoot;
	private Text bossHealthLabel;
	private Text bossLivesLabel;
	//private ImageView bossProjectile;
	private Image bossProjectile;
	private List<Projectile> bossProjectileList;
	private boolean bossHasBeenAdded = false;
	private int counter;
	private boolean labelsAdded = false;
	public GameLevelTwo(Runnable y) {
		super(y);
		// TODO Auto-generated constructor stub
	}


	/*public int getScore() {
		return Integer.parseInt(getMyScore());
	} */
	public GameLevelTwo() {
		// TODO Auto-generated constructor stub
	}
	
	public Text getBossHealth() {
		return bossHealth;
	}
	public void updateBossHealth(int n) {
		bossHealth.setText(Integer.toString(Integer.parseInt(bossHealth.getText()) - n));
	}
	
	
	//myList = new List<Projectile>();
	public void updateBossLives() {
		bossLives.setText(Integer.toString(Integer.parseInt(bossLives.getText()) - 1));
		bossHealth.setText(Integer.toString(100));
	}
	
	/*public GameLevelOne(Runnable x) {
		runnableLevelTwo = x;
	} */

	@Override
	public Scene init(int width, int height) {
		//init(width, height);
		//super.init(width, height);
		Scene scene = getScene();
		scene = super.init(SIZE, SIZE);
		//myScene = init(SIZE, SIZE);
		enemyBoss = new Boss();
		bossProjectile = new Image(getClass().getClassLoader().getResourceAsStream("Fixafrozenmac.jpg"));
		//bossProjectile = new ImageView(image);
		//System.out.println("testing scene");
		bossProjectileList = new ArrayList<Projectile>();
		return scene;
	}  
	
	@Override
	public void step(double elapsedTime) {
		counter++;
		if (!(enemyBoss.getBoss().getBoundsInParent().intersects(getClippy().getBoundsInParent())))
			enemyBoss.setBossHasCollided(false);
		if (!bossHasBeenAdded)
			super.step(elapsedTime);
		for (Projectile x: getMyList())
			x.updatePosition(elapsedTime);
		//System.out.println("No variables creaed");
		//System.out.println("Level 2");
		Projectile projectileBoss = null;
		if (getMyScore() >= 3000 && !bossHasBeenAdded && !labelsAdded) {
			bossHealthLabel = new Text(40, 40, "BOSS HEALTH");
			getGroup().getChildren().add(enemyBoss.getBoss());
			getGroup().getChildren().add(bossHealthLabel);
			bossHealth = new Text(40, 60, Integer.toString(100));
			getGroup().getChildren().add(bossHealth);
			bossHasBeenAdded = true;
			bossLivesLabel = new Text(40, 80, "# BOSS LIVES");
			getGroup().getChildren().add(bossLivesLabel);
			bossLives = new Text(40, 100, Integer.toString(3));
			getGroup().getChildren().add(bossLives);
			labelsAdded = true;
			//System.out.println(bossHasBeenAdded);
		}
		if (bossHasBeenAdded) {
			for (Enemy y: getEnemyList()) {
				getGroup().getChildren().remove(y.getEnemy());
			}
			for (Projectile x: getEnemyProjectileList()) {
					getGroup().getChildren().remove(x.getEnemyProjectile());
				}
				//getGroup().getChildren().remove(y.getEnemy());
			if (Integer.parseInt(bossHealth.getText()) <= 0)
				updateBossLives();
			//enemyBoss.updateLives(bossHealth, bossLives);
			//enemyBoss.checkBoss(bossLives);
			enemyBoss.updateMotion(elapsedTime); 
			if (Math.floorMod(counter, 100) == 0) {
				//System.out.println("This is being called again");
				projectileBoss = new Projectile(bossProjectile);
				bossProjectileList.add(projectileBoss);
				getGroup().getChildren().add(projectileBoss.getEnemyProjectile());
				projectileBoss.initializeMotionEnemyProjectile(enemyBoss.getBoss());
			}
			detectionBossUserProjectile();
			detectionBossProjectileUser(elapsedTime);
			//for (Projectile x: bossProjectileList)
			//	x.updatePositionEnemyProjectile(elapsedTime);
		}
	}
	
	
	
	//detection between boss projectile and user/boss projectile and user projectile
	public void detectionBossProjectileUser(double elapsedTime) {
		for (Projectile x: bossProjectileList) {
			if (x.getEnemyProjectile().getBoundsInParent().intersects(getClippy().getBoundsInParent()) && !x.getHasCollided()) {
				getHealth().setText(Integer.toString(Integer.parseInt(getHealth().getText()) - 5 ));
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
			getHealth().setText(Integer.toString(Integer.parseInt(getHealth().getText()) - 10));
			enemyBoss.setBossHasCollided(true);
		}
		for (Projectile x: getMyList()) {
			if (x.getProjectile().getBoundsInParent().intersects(enemyBoss.getBoss().getBoundsInParent()) && !x.getHasCollided() && x.getProjectile().isVisible()) {
				//System.out.println("Health being updated");
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
