import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ContinuationScreen {
	private Group myContinuationRoot;
	private Scene myContinuationScene;
	private Runnable runnableLevelTwo;
	
	private int SIZE = 700;
	
	public ContinuationScreen(Runnable x) {
		runnableLevelTwo = x;
	}
	public Scene init(int width, int height) {
		myContinuationRoot = new Group();
		myContinuationScene = new Scene(myContinuationRoot, width, height);
		Image background = new Image(getClass().getClassLoader().getResourceAsStream("WindowsBackground.jpg"));
		ImageView backgroundImage = new ImageView(background);
		backgroundImage.setFitWidth(SIZE);
		backgroundImage.setFitHeight(SIZE);
		myContinuationRoot.getChildren().add(backgroundImage);
		backgroundImage.toBack();
		Image clippy = new Image(getClass().getClassLoader().getResourceAsStream("clippy.jpg"));
		ImageView clippyPic = new ImageView(clippy);
		myContinuationRoot.getChildren().add(clippyPic);
		clippyPic.setX(SIZE / 2);
		clippyPic.setY(200);
		Button button = new Button("CONTINUE TO LEVEL 2");
		myContinuationRoot.getChildren().add(button);
		button.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
		button.setTranslateX(300);
		button.setTranslateY(350);
		button.setOnAction(e -> runnableLevelTwo.run());
		
		return myContinuationScene;
	}
}
