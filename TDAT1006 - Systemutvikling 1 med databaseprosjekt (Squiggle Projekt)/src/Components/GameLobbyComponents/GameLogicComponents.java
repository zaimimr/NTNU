package Components.GameLobbyComponents;

import Components.Threads.Timers;
import Components.UserInfo;
import Database.DBConnection;
import Scenes.GameLobby;
import Scenes.MainScene;
import Scenes.Results;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

import static Components.Threads.Timers.*;

/**
 * Class that deals with actual game rules and mechanics
 */
public class GameLogicComponents {

    // Usually 95
    public static int gameTime = 95;
    private static int currentRound = 1;

    /**
     * Gets the current round number
     * @return current round
     */
    public static int getCurrentRound() {
        return currentRound;
    }

    /**
     * Sets canvas according to who is looking at it
     */
    public static void setPrivileges() {
        stopHeartBeat();
        Timers.startHeartBeat();
    }

    /**
     * Increments round counter
     */
    public static void incrementRoundCounter() {
        currentRound++;
    }

    /**
     * Sets the curent round of the game
     * @param currentRound
     */
    public static void setCurrentRound(int currentRound) {
        GameLogicComponents.currentRound = currentRound;
    }

    /**
     * Sets new drawer, or quits game if everyone has drawn.
     */
    public static void reset() {
        if (currentRound <= DBConnection.getMaxRound()) {
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
                                        if (UserInfo.getDrawRound() != GameLogicComponents.getCurrentRound()) {
                                            while (DBConnection.getDrawTimer().getTime() - new Date().getTime() <= 10000) {
                                            // while ((int)(DBConnection.getDrawTimer().getTime()) < (int)(new Date().getTime()) - 2000) {
                                            }
                                        }
                                        MainScene.gl = new GameLobby(MainScene.getWIDTH(), MainScene.getHEIGHT(), UserInfo.getDrawRound() == GameLogicComponents.getCurrentRound());
                                        LiveChatComponents.cleanChat();
                                        GameLogicComponents.setPrivileges();
                                        MainScene.setScene(MainScene.gl);
                                        UserInfo.setGuessedCorrectly(false);
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
        } else {
            try {
            Thread.sleep(2000);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }

            stopHeartBeat();
            MainScene.rs = new Results(MainScene.getWIDTH(), MainScene.getHEIGHT());
            MainScene.setScene(MainScene.rs);
            MainScene.gl = null;
            setCurrentRound(1);
        }
    }
}