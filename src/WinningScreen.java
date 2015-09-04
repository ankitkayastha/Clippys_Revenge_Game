import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WinningScreen {
	private int SIZE = 700;
	private Scene myWinningScene;
	private Group myWinningRoot;
	
	public Scene init(int width, int height) {
		myWinningRoot = new Group();
		myWinningScene = new Scene(myWinningRoot, width, height);
		Image background = new Image(getClass().getClassLoader().getResourceAsStream("WindowsBackground.jpg"));
		ImageView backgroundImage = new ImageView(background);
		backgroundImage.setFitWidth(SIZE);
		backgroundImage.setFitHeight(SIZE);
		myWinningRoot.getChildren().add(backgroundImage);
		backgroundImage.toBack();
		Image clippy = new Image(getClass().getClassLoader().getResourceAsStream("clippy.jpg"));
		ImageView clippyPic = new ImageView(clippy);
		myWinningRoot.getChildren().add(clippyPic);
		clippyPic.setX(SIZE / 2);
		clippyPic.setY(200);
//		Button button = new Button("CLOSE THE PROGRAM");
//		myWinningRoot.getChildren().add(button);
//		button.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
//		button.setTranslateX(300);
//		button.setTranslateY(350);
//		button.setOnAction(e -> handleMouseClicked());
		
		return myWinningScene;
	}
}
