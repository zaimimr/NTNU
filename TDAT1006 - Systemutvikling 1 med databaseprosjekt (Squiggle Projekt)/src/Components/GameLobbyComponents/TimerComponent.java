package Components.GameLobbyComponents;

import Database.DBConnection;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Date;
import java.util.concurrent.CountDownLatch;



/**
 * Class that holds the timer UI
 * Where time remaining is synched across computers and displayed
 */
public class TimerComponent {
    private static Label countDown;
    public static int timeRemaining;

    /**
     * Adds the UI for timer, labels and countdown
     * @return VBox VerticalBox with the needed
     */
    public static VBox addTimerUI() {
        Date time = DBConnection.getDrawTimer();
        Date currentTime = new Date();
        long diff = time.getTime() - currentTime.getTime();
        timeRemaining = (int) diff / 1000;
        /*
        if (UserInfo.getDrawRound() == GameLogicComponents.getCurrentRound() + 1) {
            timeRemaining -= 3;
        }
        */

        VBox vb = new VBox();
        countDown = new Label("Remaining time: " + timeRemaining);
        countDown.setFont(new Font(20));
        countDown.setStyle("-fx-text-fill: white");
        vb.getChildren().add(countDown);
        return vb;
    }

    /**
     * Sets the text for countdown label
     * @see Label
     * @param gameStarted boolean game started or not
     */
    public static void setTimerText(boolean gameStarted) {
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
                                    timeRemaining--;
                                    if (gameStarted) {
                                        countDown.setText("Remaining time: " + timeRemaining);
                                    } else {
                                        countDown.setText("Game starts in: " + (timeRemaining - Math.round(GameLogicComponents.gameTime * 0.84)));
                                    }
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
}
