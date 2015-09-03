import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.*;
public class setSplashScreen {
	
	private Scene mySplashScene;
	private Group mySplashRoot;
	private final int SIZE = 700;
	private Text myTitle; 
	private Text myTitlePartTwo;
	private Runnable myRunnable;
	
	public setSplashScreen(Runnable x) {
		myRunnable = x;
		
	}
	public Scene init(int width, int height) {
		mySplashRoot = new Group();
		mySplashScene = new Scene(mySplashRoot, width, height);
		Image background = new Image(getClass().getClassLoader().getResourceAsStream("maxresdefault.jpg"));
		ImageView backgroundImage = new ImageView(background);
		backgroundImage.setFitWidth(SIZE);
		backgroundImage.setFitHeight(SIZE);
		mySplashRoot.getChildren().add(backgroundImage);
		backgroundImage.toBack();
		Image clippy = new Image(getClass().getClassLoader().getResourceAsStream("clippy.jpg"));
		ImageView clippyPic = new ImageView(clippy);
		mySplashRoot.getChildren().add(clippyPic);
		clippyPic.setX(100);
		clippyPic.setY(325);
		Text text = new Text(230, 600, "PRESS SPACEBAR TO BEGIN");
		text.setFill(Color.WHITE);
		text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		mySplashRoot.getChildren().add(text);
		mySplashScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		myTitle = new Text(130, 100, "CLIPPY'S");
		myTitlePartTwo = new Text(390, 105, "REVENGE");
		myTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
		myTitlePartTwo.setFont(Font.font("Chiller", FontWeight.BOLD, 60));
		myTitlePartTwo.setFill(Color.RED);
		myTitle.setFill(Color.BLUE);
		mySplashRoot.getChildren().add(myTitle);
		mySplashRoot.getChildren().add(myTitlePartTwo);
		return mySplashScene;
	}
	
	public void handleKeyInput(KeyCode code) {
		if (code.equals(KeyCode.SPACE)) {
			myRunnable.run();
			//s.setScene(scene);
			//s.show(); }
		}
	}
}
