package Scenes;

import Components.GameLobbyComponents.GameLogicComponents;
import Database.DBConnection;
import CSS.Css;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.io.File;

import static Components.GameLobbyComponents.AvatarComponents.addAvatarUI;
import static Components.GameLobbyComponents.CanvasComponents.*;
import static Components.GameLobbyComponents.LiveChatComponents.liveChatUI;
import static Components.GameLobbyComponents.TimerComponent.addTimerUI;
import static Components.GameLobbyComponents.WordComponents.addWordUI;
import static Components.Threads.Timers.stopHeartBeat;

/**
 * Class which displays the game lobby where the game takes place
 */
public class GameLobby extends Scenes{

    public static BorderPane bp;

    /**
     * Constructor of the gameLobby class
     * @param WIDTH width of the Scene
     * @param HEIGHT height of the scene
     * @param drawing boolean saying if a user is drawing or not
     */
    public GameLobby(double WIDTH, double HEIGHT, boolean drawing) {
        super(WIDTH, HEIGHT);
        bp = new BorderPane();
        setSc(new Scene(bp, WIDTH, HEIGHT));
        addUIControls(bp, drawing);
    }

    /**
     * Method that creates the game lobby scene with the game-components: Canvas, avatars and livechat
     * @param borderPane creates a borderPane where we put the game-components.
     * @param drawing a boolean checking if the player is drawing. If the player is drawing the drawingcomponents will appear
     */
    private void addUIControls(BorderPane borderPane, boolean drawing){
        HBox canvas = addCanvasUI(drawing);
        VBox livechat = liveChatUI();
        VBox avatar = addAvatarUI();
        Button leaveButton = new Button("Leave game");
        Css.setStyle(leaveButton);
        Button optionsButton = new Button("Options");
        Css.setStyle(optionsButton);
        Region spacer = new Region();

        borderPane.setCenter(canvas);
        borderPane.setRight(livechat);
        borderPane.setLeft(avatar);

        BorderPane.setMargin(avatar,new Insets(36,2,30,2));
        BorderPane.setMargin(livechat,new Insets(6,2,30,2));
        BorderPane.setMargin(canvas,new Insets(0,2,0,2));
        String url = new File("resources/SquiggleTheme.png").toURI().toString();
        borderPane.setStyle("-fx-background-image: url(\"" + url + "\");");
        if (drawing) {
            HBox drawingUI = addDrawingUI();
            borderPane.setBottom(drawingUI);
            BorderPane.setMargin(drawingUI, new Insets(0,15,0,10));
        } else {
            HBox noDrawingUI = new HBox();
            noDrawingUI.setHgrow(spacer,Priority.ALWAYS);
            noDrawingUI.getChildren().addAll(leaveButton, spacer, optionsButton);
            BorderPane.setMargin(noDrawingUI,new Insets(0,10,0,10));
            borderPane.setBottom(noDrawingUI);
            BorderPane.setMargin(avatar,new Insets(40,2,30,2));
            BorderPane.setMargin(livechat,new Insets(12,2,30,2));
            BorderPane.setMargin(canvas,new Insets(0,2,0,2));
        }
        optionsButton.setOnAction(e -> new Options(MainScene.getWIDTH(), MainScene.getHEIGHT()));

        leaveButton.setOnAction(e -> {
            DBConnection.exitGame();
            stopHeartBeat();
            MainScene.mm = new MainMenu(MainScene.getWIDTH(), MainScene.getHEIGHT());
            MainScene.setScene(MainScene.mm);
            MainScene.gl = null;
            GameLogicComponents.setCurrentRound(1);
        });
        setTop();
    }

    /**
     * Sets a HBox with word and timer at the top of the gamelobby-window
     */
    public static void setTop(){
        HBox hb = new HBox();
        hb.setSpacing(60);
        hb.getChildren().addAll(addTimerUI(), addWordUI());
        bp.setTop(hb);
    }
}
