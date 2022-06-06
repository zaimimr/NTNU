package Scenes;

import Components.Authentication;
import Components.UserInfo;
import Database.DBConnection;
import CSS.Css;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.io.File;


/**
 * Class which makes the log in scene. The scene contains of log in labels, register button, help-button and optiions-button
 */
public class LogIn extends Scenes {

    private static TextField nameField;
    private static PasswordField passwordField;
    private static Label loginError;


    /**
     * Constructor of the logIn scene
     * @param WIDTH width of the scene
     * @param HEIGHT height of the scene
     */
    LogIn(double WIDTH, double HEIGHT) {
        super(WIDTH, HEIGHT);
        addUIControls(getGp());
    }

    /**
     * Method which gets the username written
     * @return returns the usernanme written in the namefield
     */
    public static String getUserName(){
        if(nameField.getText() == null){
            return null;
        }
        return nameField.getText();
    }

    /**
     * Method which gets the password written
     * @return Returns the password written in the password field
     */
    public static String getPassword(){
        if(passwordField.getText() == null){
            return null;
        }
        return passwordField.getText();
    }

    /**
     * Method which creates the log in scene
     * @param gridPane Uses a Gridpane which extends from main scene
     */
    private void addUIControls(GridPane gridPane) {
        double prefHeight = 40;

        File file = new File("resources/logos/Logo_LogIn.png");
        Image image = new Image(file.toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setFitHeight(180);
        iv.setPreserveRatio(true);
        gridPane.add(iv,0,0,2,1);

        GridPane.setHalignment(iv, HPos.CENTER);

        //Add error Label
        loginError = new Label();
        Css.setErrorStyle(loginError);
        gridPane.add(loginError,1,0,2,2);
        loginError.setVisible(false);

        // Add Name Label
        Label nameLabel = new Label("Username :");
        Css.setStyle(nameLabel);
        gridPane.add(nameLabel, 0,1);

        // Add Name Text Field
        nameField = new TextField();
        Css.setStyle(nameField);
        nameField.setPrefHeight(prefHeight);
        nameField.setPrefWidth(200);
        nameField.setPromptText("johnAdams");
        gridPane.add(nameField, 1,1);

        // Add Password Label
        Label passwordLabel = new Label("Password : ");
        Css.setStyle(passwordLabel);
        gridPane.add(passwordLabel, 0, 3);

        // Add Password Field
        passwordField = new PasswordField();
        Css.setStyle(passwordField);
        passwordField.setPrefHeight(prefHeight);
        passwordField.setPrefWidth(100);
        passwordField.setPromptText("password");
        gridPane.add(passwordField, 1, 3);

        // Add Login Button
        Button logInButton = new Button("Login");
        Css.setStyle(logInButton);
        logInButton.setPrefHeight(prefHeight);
        logInButton.setDefaultButton(true);
        logInButton.setPrefWidth(200);
        gridPane.add(logInButton, 0, 4, 2, 1);
        GridPane.setHalignment(logInButton, HPos.CENTER);

        // Add Registration Button
        Button regButton = new Button("Register new user");
        Css.setStyle(regButton);
        regButton.setPrefHeight(prefHeight);
        regButton.setPrefWidth(200);
        gridPane.add(regButton, 0, 5, 2, 1);
        GridPane.setHalignment(regButton, HPos.CENTER);

        // Help button
        Button helpbutton = new Button("Help");
        Css.setStyle(helpbutton);
        helpbutton.setPrefWidth(80);
        gridPane.add(helpbutton, 0, 14);

        // Tooltips
        final Tooltip tooltipName = new Tooltip();
        tooltipName.setText("Write your username");
        nameField.setTooltip(tooltipName);
        Css.setStyle(tooltipName);

        final Tooltip tooltipPassword = new Tooltip();
        tooltipPassword.setText("Write your password");
        passwordField.setTooltip(tooltipPassword);
        Css.setStyle(tooltipPassword);

        //ButtonAction
        logInButton.setOnAction(e -> loginSystem());
        regButton.setOnAction(e -> {
            MainScene.su = new SignUp(super.getWIDTH(), super.getHEIGHT());
            MainScene.setScene(MainScene.su);
            MainScene.li = null;
        });
        helpbutton.setOnAction(e -> new Help(super.getWIDTH(), super.getHEIGHT()));

        Css.setBackground(gridPane);
    }

    /**
     * Method which logs in the user to the database
     */
    private void loginSystem(){
        Authentication.logIn(getWIDTH(), getHEIGHT());
        UserInfo.setUserName(getUserName());
        UserInfo.initializeUser(DBConnection.getUserID(getUserName()));
    }

    /**
     * Method which checks if the log in information is correct
     * @param b if the log in information is wrong, a errormessage will occour
     */
    public static void visibleLoginError(boolean b){
        loginError.setVisible(b);
    }

    /**
     * Method which set the error text
     * @param newText
     */
    public static void setTextLoginError(String newText) {
        loginError.setText(newText);
    }
}