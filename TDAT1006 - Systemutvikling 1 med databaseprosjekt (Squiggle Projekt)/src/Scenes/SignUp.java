package Scenes;

import Components.Authentication;
import Components.Toast;
import CSS.Css;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Tooltip;

import java.io.File;


/**
 * Signup Scene where user can register
 *
 */
public class SignUp extends Scenes {
    //UI initialize object variables
    private static TextField nameField;
    private static TextField emailField;
    private static PasswordField passwordField;
    private static PasswordField rePasswordField;

    private static Label errorUserAndMail;
    private static Label errorPassword;
    private static Label emptyUser;
    private static Label emptyMail;
    private static Label emptyPassword;

    private static int avatarID = 1;


    /**
     * Constructor for the signup scene
     * @param WIDTH     Width of the scene, double
     * @param HEIGHT    Height of the scene, double
     */
    SignUp(double WIDTH, double HEIGHT) {
        super(WIDTH, HEIGHT);
        //UI method adds nodes to the pane
        addUIControls(getGp());
    }

    /**
     * Adds all the UI to the scene, buttons, labels etc.
     * @param gridPane  Gridpane that the signup UI is to be added to
     */
    private void addUIControls(GridPane gridPane) {
        double prefHeight = 40;

        File file = new File("resources/logos/Logo_SignUp.png");
        Image image = new Image(file.toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setFitHeight(180);
        iv.setPreserveRatio(true);

        gridPane.add(iv, 0,0,4,1);
        GridPane.setHalignment(iv, HPos.CENTER);

        //Add error Label
        errorUserAndMail = new Label("Username or email already taken");
        gridPane.add(errorUserAndMail,1,0,2,2);
        errorUserAndMail.setVisible(false);
        Css.setErrorStyle(errorUserAndMail);
        // Add Name Label
        Label nameLabel = new Label("Username :");
        Css.setStyle(nameLabel);
        gridPane.add(nameLabel, 0,1);

        // Add Name Text Field
        nameField = new TextField();
        nameField.setPrefHeight(prefHeight);
        nameField.setPromptText("Ola Nordmann");
        Css.setStyle(nameField);
        gridPane.add(nameField, 1,1);

        //Add empty Label
        emptyUser = new Label("Fill in username");
        gridPane.add(emptyUser,2,1,2,1);
        emptyUser.setVisible(false);
        Css.setErrorStyle(emptyUser);

        // Add Email Label
        Label emailLabel = new Label("Email : ");
        Css.setStyle(emailLabel);
        gridPane.add(emailLabel, 0, 2);

        // Add Email Text Field
        emailField = new TextField();
        emailField.setPrefHeight(prefHeight);
        Css.setStyle(emailField);
        emailField.setPromptText("party@myhouse.tonight");
        gridPane.add(emailField, 1, 2);

        //Add empty Label
        emptyMail = new Label("Fill in mail");
        gridPane.add(emptyMail,2,2,2,1);
        emptyMail.setVisible(false);
        Css.setErrorStyle(emptyMail);

        // Add Name Label
        Label avatarLabel = new Label("Avatar : ");
        Css.setStyle(avatarLabel);
        gridPane.add(avatarLabel, 0,3);

        //Add ImageView to show avatar
        ImageView avatarView = new ImageView(getAvatar(avatarID));
        avatarView.setFitWidth(150);
        avatarView.setFitHeight(150);
        gridPane.add(avatarView, 1, 3, 1, 1);
        GridPane.setHalignment(avatarView, HPos.CENTER);

        //Add button to go left
        Button leftButton = new Button("<");
        gridPane.add(leftButton, 1,3);
        GridPane.setHalignment(leftButton, HPos.CENTER);
        GridPane.setMargin(leftButton, new Insets(0,120,0,0));
        Css.selectorButton(leftButton);

        //Add button to go right
        Button rightButton = new Button(">");
        gridPane.add(rightButton, 1,3);
        GridPane.setHalignment(rightButton, HPos.CENTER);
        GridPane.setMargin(rightButton, new Insets(0,0,0,120));
        Css.selectorButton(rightButton);

        //Add error Label
        errorPassword = new Label("Password don't match");
        gridPane.add(errorPassword,1,3,2,1);
        GridPane.setValignment(errorPassword, VPos.BOTTOM);
        errorPassword.setVisible(false);
        Css.setErrorStyle(errorPassword);

        // Add Password Label
        Label passwordLabel = new Label("Password : ");
        Css.setStyle(passwordLabel);
        gridPane.add(passwordLabel, 0, 4);

        // Add Password Field
        passwordField = new PasswordField();
        passwordField.setPrefHeight(prefHeight);
        Css.setStyle(passwordField);
        passwordField.setPromptText("password");
        gridPane.add(passwordField, 1, 4);

        //Add empty Label
        emptyPassword = new Label("Fill in password");
        gridPane.add(emptyPassword,2,4,2,1);
        emptyPassword.setVisible(false);
        Css.setErrorStyle(emptyPassword);

        // Add RePassword Label
        Label rePasswordLabel = new Label("Password : ");
        Css.setStyle(rePasswordLabel);
        gridPane.add(rePasswordLabel, 0, 5);

        // Add RePassword Field
        rePasswordField = new PasswordField();
        rePasswordField.setPrefHeight(prefHeight);
        Css.setStyle(rePasswordField);
        rePasswordField.setPromptText("re-enter password");
        gridPane.add(rePasswordField, 1, 5);

        // Add Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setPrefHeight(prefHeight);
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        Css.setStyle(submitButton);
        gridPane.add(submitButton, 0, 6, 4, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setValignment(submitButton, VPos.CENTER);

        // Add option button
        Button optionButton = new Button("Options");
        gridPane.add(optionButton, 3, 7);
        Css.setStyle(optionButton);
        GridPane.setHalignment(optionButton, HPos.LEFT);
        GridPane.setValignment(optionButton, VPos.BOTTOM);

        // Go back button
        Button backButton = new Button("Go Back");
        gridPane.add(backButton, 0, 7);
        Css.setStyle(backButton);
        GridPane.setHalignment(backButton, HPos.LEFT);
        GridPane.setValignment(backButton, VPos.BOTTOM);

        //////////////Tooltips//////////////////////////////////
        final Tooltip tooltipName = new Tooltip();
        tooltipName.setText("Write your username");
        nameField.setTooltip(tooltipName);
        Css.setStyle(tooltipName);

        final Tooltip tooltipEmail = new Tooltip();
        tooltipEmail.setText("Write your Email");
        emailField.setTooltip(tooltipEmail);
        Css.setStyle(tooltipEmail);

        final Tooltip tooltipPassword = new Tooltip();
        tooltipPassword.setText("Write your password");
        passwordField.setTooltip(tooltipPassword);
        Css.setStyle(tooltipPassword);

        final Tooltip tooltipRePassword = new Tooltip();
        tooltipRePassword.setText("Write your password one more time");
        rePasswordField.setTooltip(tooltipRePassword);
        Css.setStyle(tooltipRePassword);

        ///////Button action//////////////////////////////
        backButton.setOnAction(e -> {
            MainScene.li = new LogIn(super.getWIDTH(), super.getHEIGHT());
            MainScene.setScene(MainScene.li);
            MainScene.su = null;
        });

        submitButton.setOnAction(e -> {
            if(Authentication.submit()){
                MainScene.li = new LogIn(super.getWIDTH(), super.getHEIGHT());
                MainScene.setScene(MainScene.li);;
                MainScene.su = null;
                String toastMsg = "Registration successful";
                Toast.makeText(toastMsg,1000, 500, 500);
            }
        });

        optionButton.setOnAction(e -> new Options(super.getWIDTH(), super.getHEIGHT()));

        rightButton.setOnAction(e -> {
            avatarID = super.loopAvatar(avatarID,1, 1,getMax());
            avatarView.setImage(super.getAvatar(avatarID));
        });

        leftButton.setOnAction(e -> {
            avatarID = super.loopAvatar(avatarID, -1,1,getMax());
            avatarView.setImage(super.getAvatar(avatarID));
        });
    }
    /**
     * Sets email error message visible or invisible
     * @param b sets visible, true, or invisible false
     */
    public static void visibleUserMail(boolean b){
        errorUserAndMail.setVisible(b);
    }

    /**
     * Sets password error message visible or invisible
     * @param b sets visible, true, or invisible false
     */
    public static void visiblePassword(boolean b){
        errorPassword.setVisible(b);
    }

    /**
     * Sets empty username error message visible or invisible
     * @param b sets visible, true, or invisible false
     */
    public static void visibleEmptyUser(boolean b){
        emptyUser.setVisible(b);
    }

    /**
     * Sets empty email error message visible or invisible
     * @param b sets visible, true, or invisible false
     */
    public static void visibleEmptyMail(boolean b){
        emptyMail.setVisible(b);
    }

    /**
     * Sets empty password error message visible or invisible
     * @param b sets visible, true, or invisible false
     */
    public static void visibleEmptyPassword(boolean b){
        emptyPassword.setVisible(b);
    }

    /**
     * Gets username from userName TextField
     *
     * @see TextField
     * @return String value of the textfield's content
     */
    public static String getName(){
        if(nameField.getText().isEmpty()){
            return null;
        }
        return nameField.getText();
    }

    /**
     * Gets email from email TextField
     *
     * @see TextField
     * @return String value of the textfield's content
     */
    public static String getMail(){
        if(emailField.getText().isEmpty()){
            return null;
        }
        return emailField.getText();
    }

    /**
     * Gets password from password TextField
     *
     * @see TextField
     * @return String value of the textfield's content
     */
    public static String getPassword(){
        if(passwordField.getText().equals(rePasswordField.getText()) && !passwordField.getText().isEmpty()){
            return passwordField.getText();
        }else {
            return null;
        }
    }

    /**
     * Gets avatarID depending on what avatar user Selected
     *
     * @see ImageView
     * @return int value of the image ID
     */
    public static int getAvatarID() {
        return avatarID;
    }
}
