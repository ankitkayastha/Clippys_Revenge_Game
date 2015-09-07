

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
public class Main extends Application {
	private static final int SIZE = 700; //size of game window
	private static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private ImageView losingScreen;
	private Timeline animation;
	private Timeline animationTwo;
	private WinningScreen winning;
	private ContinuationScreen continuation;
	private SplashScreen splash;
	private Game levelOne;
	private Game levelTwo;
	public Timeline getAnimation() {
		return animation;
	}
	@Override
	public void start(Stage s) {
		
		levelOne = new GameLevelOne(() -> setContinuationScreen(s), () -> setLosingScreen(levelOne.getRoot())); //create new game
		s.setTitle(levelOne.getTitle()); //set title of game
		splash = new SplashScreen(() -> setSceneLevelOne(s));
		Scene splashScene = splash.init(SIZE, SIZE);
		s.setScene(splashScene); 
		s.show();
		continuation = new ContinuationScreen(() -> setSceneLevelTwo(s));
		levelTwo = new GameLevelTwo(() -> setLosingScreen(levelTwo.getRoot()), () -> setWinningScreen(s));
		winning = new WinningScreen();
	}
	
	private void setWinningScreen(Stage s) {
		
		animation.stop();
		Scene winningScene = winning.init(SIZE, SIZE);
		s.setScene(winningScene);
		s.show();
	}
	
	private void setContinuationScreen(Stage s) {
		
		
		//stop animation to stop crashing
		animation.stop();
		Scene continuationScene = continuation.init(SIZE, SIZE);
		s.setScene(continuationScene);
		s.show();
	}
	
	private void setSceneLevelTwo(Stage s) {
		Scene scene = levelTwo.init(SIZE, SIZE);
		s.setScene(scene);
		s.show();
		
		KeyFrame frameTwo = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e-> levelTwo.step(SECOND_DELAY));
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frameTwo);
		animation.play();
		
	}
	
	private void setSceneLevelOne(Stage s) {
		Scene scene = levelOne.init(SIZE, SIZE);
		s.setScene(scene);
		s.show();
		
		//game loop and timeline, add key frame to timeline
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),e -> levelOne.step(SECOND_DELAY));
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE); //will go on for indefinite time
		animation.getKeyFrames().add(frame);
		animation.play();
		
	}
	public void setLosingScreen(Group root) {
		Image screen = new Image(getClass().getClassLoader().getResourceAsStream("windows8.png"));
		losingScreen = new ImageView(screen);
		losingScreen.setFitWidth(SIZE);
		losingScreen.setFitHeight(SIZE);
		root.getChildren().add(losingScreen);
		animation.stop();
	} 
	/*
	 * Start the program
	 */
	
	public static void main(String[] args) {
		launch(args);
	}
}
