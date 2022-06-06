package Scenes;

import Components.UserInfo;
import Database.DBConnection;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

/**
 * Class which sets scenes
 */
public class MainScene {

    private static final double HEIGHT = 600;
    private static final double WIDTH = 1000;

    public static Stage stage;

    // Scenes
    public static Scenes li = new LogIn(WIDTH, HEIGHT);
    public static Scenes mm = null;
    public static Scenes su = null;
    public static Scenes mp = null;
    public static Scenes gl = null;
    public static Scenes rs = null;

    // User
    public static UserInfo user = new UserInfo();

    /**
     * Method thats gets the height of the stage
     * @return returns the height
     */
    public static double getHEIGHT() {
        return HEIGHT;
    }
    /**
     * Method thats gets the width of the stage
     * @return returns the width
     */
    public static double getWIDTH() {
        return WIDTH;
    }

    /**
     * Method that set the given scene
     * @param sc Is the scene the method creates and displays
     */
    public static void setScene(Scenes sc) {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    stage.setScene(sc.getSc());
                                }finally{
                                    latch.countDown();
                                }
                            }
                        });
                        latch.await();
                        //Keep with the background work
                        return null;
                    }
                };
            }
        };
        service.start();

    }

    /**
     * Method that initialize the stage and sets the titlte
     * @param stage Is the stages that gets initialized
     */
    public void initialize(Stage stage) {
        MainScene.stage = stage;
        MainScene.stage.centerOnScreen();
        MainScene.stage.setTitle("Squiggle");
        MainScene.stage.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        MainScene.stage.setResizable(false);
        setScene(li);
        MainScene.stage.show();
        MainScene.stage.centerOnScreen();
    }

    /**
     * Method that close the program correctly. Updates the database, logs out the player and close the current stage
     */
    public static void closeProgram(){
        if(ConfirmBox.display("Warning!", "Sure you want to \n exit?")){
            Components.GameLobbyComponents.LiveChatComponents.turnOffLiveChatTimer();
            DBConnection.exitGame();
            DBConnection.setLoggedIn(LogIn.getUserName(), 0);
            Components.Threads.Timers.stopHeartBeat();
            stage.close();
        }
    }
}
