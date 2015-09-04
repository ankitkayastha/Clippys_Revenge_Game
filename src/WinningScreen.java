import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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
		Text myText = new Text(0, SIZE/2, "CONGRATULATIONS!! YOU HAVE WON!");
		myText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35));
		myWinningRoot.getChildren().add(myText);
		return myWinningScene;
	}
}
