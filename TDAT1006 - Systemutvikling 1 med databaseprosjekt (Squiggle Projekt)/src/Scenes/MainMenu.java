package Scenes;

import Components.GameLobbyComponents.GameLogicComponents;
import Components.GameLobbyComponents.LiveChatComponents;
import Components.Threads.Music;
import Components.UserInfo;
import Database.DBConnection;
import CSS.Css;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Date;

import java.io.File;

/**
 * Class thats crates the main menu which contains several buttons for further choices
 */
public class MainMenu extends Scenes{

    private Label gameStartedLabel;

    /**
     * Constructor of the MainMenu scene
     * @param width width of the scene
     * @param height height of the scene
     */
    public MainMenu(double width, double height) {
        super(width, height);
        addUIControls(getGp());
    }

    /**
     * Method that creates the main menu scene
     * @param gridPane Gridpane which extends from the main scene
     */
    private void addUIControls(GridPane gridPane) {
        double prefHeight = 40;
        double prefWidth = 200;

        // Add Header
        File file = new File("resources/logos/Logo_MainMenu.png");
        Image image = new Image(file.toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setFitHeight(180);
        iv.setPreserveRatio(true);

        gridPane.add(iv, 0,0,2,1);
        GridPane.setHalignment(iv, HPos.CENTER);

        // Add error Label
        gameStartedLabel = new Label();
        gameStartedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gameStartedLabel.setStyle("-fx-text-fill: #ffffff");
        gridPane.add(gameStartedLabel, 0,1,2,1);
        GridPane.setHalignment(gameStartedLabel, HPos.CENTER);

        // Join game button
        Button joinGameButton = new Button("Join Game");
        Css.setStyle(joinGameButton);
        joinGameButton.setPrefHeight(prefHeight);
        joinGameButton.setDefaultButton(true);
        joinGameButton.setPrefWidth(prefWidth);
        gridPane.add(joinGameButton, 0, 2, 2, 1);
        GridPane.setHalignment(joinGameButton, HPos.CENTER);
        GridPane.setValignment(joinGameButton, VPos.CENTER);

        // My Page button
        Button myPageButton = new Button("My page");
        Css.setStyle(myPageButton);
        myPageButton.setPrefHeight(prefHeight);
        myPageButton.setPrefWidth(prefWidth);
        gridPane.add(myPageButton, 0, 3, 2, 1);
        GridPane.setHalignment(myPageButton, HPos.CENTER);
        GridPane.setValignment(myPageButton, VPos.CENTER);

        // Options button
        Button optionButton = new Button("Options");
        Css.setStyle(optionButton);
        optionButton.setPrefHeight(prefHeight);
        optionButton.setPrefWidth(prefWidth);
        gridPane.add(optionButton, 0,4, 2, 1);
        GridPane.setHalignment(optionButton, HPos.CENTER);
        GridPane.setValignment(optionButton, VPos.CENTER);

        // Log Out button
        Button logOutButton = new Button("Log Out");
        Css.setStyle(logOutButton);
        logOutButton.setPrefHeight(prefHeight);
        logOutButton.setPrefWidth(prefWidth);
        gridPane.add(logOutButton, 0,5, 2, 1);
        GridPane.setHalignment(logOutButton, HPos.CENTER);
        GridPane.setValignment(logOutButton, VPos.CENTER);

        // Quit button
        Button quitButton = new Button("Quit");
        Css.setStyle(quitButton);
        quitButton.setPrefHeight(prefHeight);
        quitButton.setPrefWidth(prefWidth);
        gridPane.add(quitButton, 0, 6, 2, 1);
        GridPane.setHalignment(quitButton, HPos.CENTER);
        GridPane.setValignment(quitButton, VPos.CENTER);

        Css.setBackground(gridPane);

        // BUTTON ACTION
        optionButton.setOnAction(e -> new Options(super.getWIDTH(), super.getHEIGHT()));
        joinGameButton.setOnAction(e -> joinGameSystem());
        logOutButton.setOnAction(e -> {
            if(ConfirmBox.display("Warning!", "Sure you want to \n log out?")){
                logOutSystem();
            }
        });
        quitButton.setOnAction(e -> {
            MainScene.closeProgram();
        });
        myPageButton.setOnAction(e -> {
            MainScene.mp = new MyPage(super.getWIDTH(), super.getHEIGHT());
            MainScene.setScene(MainScene.mp);
            MainScene.mm = null;
        });
    }

    /**
     * Method that update the database when the player wants to join the game.
     * When the first player joins a game the timer starts
     */
    private void joinGameSystem() {
        int time = (int)((DBConnection.getDrawTimer().getTime() - (new Date().getTime())) / 1000);
        if (time < GameLogicComponents.gameTime * 0.84 && time > 0 && DBConnection.getAmtPlayer() > 0) {
            gameStartedLabel.setText("Game already in progress, ends in: " + time + " seconds.");
        } else {
            DBConnection.joinGame();
            MainScene.gl = new GameLobby(MainScene.getWIDTH(), MainScene.getHEIGHT(), UserInfo.getDrawRound() == GameLogicComponents.getCurrentRound());
            LiveChatComponents.cleanChat();
            GameLogicComponents.setPrivileges();
            MainScene.setScene(MainScene.gl);
            MainScene.mm = null;
        }
    }

    /**
     * Methods that logs you out of the database and returns you to the log in scene
     */
    private void logOutSystem(){
        MainScene.li = new LogIn(super.getWIDTH(), super.getHEIGHT());
        MainScene.setScene(MainScene.li);
        MainScene.mm = null;
        DBConnection.setLoggedIn(UserInfo.getUserName(), 0);
        Music.stopMusic();
    }
}

