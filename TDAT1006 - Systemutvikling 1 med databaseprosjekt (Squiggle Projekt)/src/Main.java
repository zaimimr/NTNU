
import Scenes.MainScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    /**
     * Main
     * Starts the game
     */
    private MainScene mainScene = new MainScene();
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        mainScene.initialize(stage);
    }
}
