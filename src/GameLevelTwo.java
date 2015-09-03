import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class GameLevelTwo extends Game {
	private Runnable runnableLevelTwo;
	private Scene myScene;
	private ImageView enemyBoss;
	private int bossLives = 3;
	private int bossHealth = 100;
	private boolean bossHasBeenAdded = false;
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



	//myList = new List<Projectile>();
	//private Runnable runnableLevelTwo;
	//private Scene myScene;
	
	/*public GameLevelOne(Runnable x) {
		runnableLevelTwo = x;
	} */


	public Scene initialize(int width, int height) {
		myScene = init(SIZE, SIZE);
		Image boss = new Image(getClass().getClassLoader().getResourceAsStream("index.jpe"));
		enemyBoss = new ImageView(boss);
		enemyBoss.setX(SIZE/2);
		enemyBoss.setY(100);
		return myScene;
	}  
	
	public void stepLevelTwo(double elapsedTime) {
		step(elapsedTime);
		if (getMyScore() >= 3000) {
			getGroup().getChildren().add(enemyBoss);
			bossHasBeenAdded = true;
		}
		if (bossHealth <= 0)
			bossLives = bossLives - 1;
		if (bossLives <= 0)
			getGroup().getChildren().remove(enemyBoss);
	}
	
	public void handleKeyInputExtra(KeyCode code) {
		handleKeyInput(code);
		switch(code) {
		}
		
	}
	
}
