package Scenes;

import Components.Authentication;
import Components.UserInfo;
import Database.DBConnection;
import CSS.Css;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;

/**
 * Class thats creates and displays the MyPage, where you can view your avatar, and change the avatar and password
 */
public class MyPage extends Scenes{
    private static Button backButton;
    private static Button buttonChoose;
    private static PasswordField newPassword;
    private static PasswordField repeatPassword;
    private static Button buttonChangePassword;
    private static String fileLocation = "resources/avatars/";

    private int avatarID = UserInfo.getAvatarID();

    /**
     * Constructor of myPage.
     * @param WIDTH width of the scene
     * @param HEIGHT height of the scene
     */
    public MyPage(double WIDTH, double HEIGHT){
        super(WIDTH, HEIGHT);
        addUIControls(getGp());
    }

    /**
     * Method which creates the MyPage scene
     * @param gridPane extendes a Gridpane from the MainScene
     */
    private void addUIControls(GridPane gridPane){
        // Header image
        File file = new File("resources/logos/Logo_MyPage.png");
        Image image = new Image(file.toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setFitHeight(180);
        iv.setPreserveRatio(true);

        gridPane.add(iv, 0, 0, 5, 1);
        gridPane.setHalignment(iv, HPos.CENTER);

        // Username label
        Label nameLabel = new Label("Username: " + UserInfo.getUserName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Css.setStyle(nameLabel);
        gridPane.add(nameLabel, 0, 1, 2, 1);
        gridPane.setHalignment(nameLabel, HPos.LEFT);
        gridPane.setValignment(nameLabel, VPos.TOP);

        // Email label
        Label emailLabel = new Label("Email: " + UserInfo.getUserEmail());
        emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Css.setStyle(emailLabel);
        gridPane.add(emailLabel, 0, 2, 2, 1);
        gridPane.setHalignment(emailLabel, HPos.LEFT);
        gridPane.setValignment(emailLabel, VPos.TOP);

        // Change password Button
        buttonChangePassword = new Button("Change password");
        Css.setStyle(buttonChangePassword);
        buttonChangePassword.setPrefHeight(40);
        buttonChangePassword.setPrefWidth(180);
        gridPane.add(buttonChangePassword, 0, 3, 2, 1);
        gridPane.setHalignment(buttonChangePassword, HPos.LEFT);

        // Current avatar
        Label currentAvatarLabel = new Label("Current avatar:");
        Css.setStyle(currentAvatarLabel);
        gridPane.add(currentAvatarLabel, 1, 6, 3, 1);
        gridPane.setMargin(currentAvatarLabel, new Insets(0, 0, 150, 300));
        gridPane.setHalignment(currentAvatarLabel, HPos.LEFT);
        gridPane.setValignment(currentAvatarLabel,VPos.BOTTOM);

        Image avatar = getAvatar(avatarID);
        ImageView avatarImage = new ImageView(avatar);
        gridPane.add(avatarImage, 1, 6, 2, 1);
        avatarImage.setFitHeight(150);
        avatarImage.setFitWidth(150);
        gridPane.setMargin(avatarImage,new Insets(0,0,0,300));
        gridPane.setHalignment(avatarImage, HPos.LEFT);
        gridPane.setValignment(avatarImage, VPos.BOTTOM);

        // Avatar selection
        // Select new avatar label
        Label newAvatar = new Label("Select new avatar:");
        Css.setHeaderStyle(newAvatar);
        gridPane.add(newAvatar, 1, 0, 3, 1);
        gridPane.setHalignment(newAvatar, HPos.LEFT);
        gridPane.setMargin(newAvatar, new Insets(90,0,0,490));

        //Add ImageView to show avatar
        ImageView avatarView = new ImageView(getAvatar(avatarID));
        avatarView.setFitWidth(340);
        avatarView.setFitHeight(340);
        gridPane.add(avatarView, 1, 0, 3, 5);
        gridPane.setHalignment(avatarView, HPos.LEFT);
        gridPane.setValignment(avatarView, VPos.TOP);
        gridPane.setMargin(avatarView, new Insets(148, 0, 0, 460));

        //Add button to go left
        Button leftButton = new Button("<");
        gridPane.add(leftButton, 1,2, 3, 5);
        GridPane.setHalignment(leftButton, HPos.LEFT);
        gridPane.setValignment(leftButton, VPos.CENTER);
        GridPane.setMargin(leftButton, new Insets(0,120,0,453));
        Css.selectorButton(leftButton);

        //Add button to go right
        Button rightButton = new Button(">");
        gridPane.add(rightButton, 1,2, 3, 5);
        GridPane.setHalignment(rightButton, HPos.LEFT);
        gridPane.setValignment(rightButton, VPos.CENTER);
        GridPane.setMargin(rightButton, new Insets(0,0,0,765));
        Css.selectorButton(rightButton);

        // Update current avatar button
        buttonChoose = new Button("Choose avatar");
        Css.setStyle(buttonChoose);
        buttonChoose.setPrefHeight(40);
        buttonChoose.setPrefWidth(150);
        gridPane.add(buttonChoose, 3, 7, 2, 1);
        gridPane.setHalignment(buttonChoose, HPos.LEFT);
        gridPane.setMargin(buttonChoose, new Insets(0, 0, 0, 345));

        // Back button
        backButton = new Button("Back");
        Css.setStyle(backButton);
        backButton.setPrefHeight(40);
        backButton.setPrefWidth(80);
        gridPane.add(backButton, 0, 7, 1, 1);
        gridPane.setHalignment(backButton, HPos.LEFT);

        VBox winsvbox = wins();
        gridPane.add(winsvbox, 0,2,3,3);
        gridPane.setMargin(winsvbox, new Insets(190,0,0,0));

        // Button action
        buttonChangePassword.setOnAction(e -> {
            displayNewPassword("Change password");
        });

        Css.setBackground(gridPane);

        buttonChoose.setOnAction(e -> {
            Image chosenAvatar = chosenAvatar(avatarID);
            avatarImage.setImage(chosenAvatar);
            UserInfo.setAvatarID(avatarID);
            DBConnection.setAvatarID(UserInfo.getUserID(), avatarID);
        });

        rightButton.setOnAction(e -> {
            avatarID = super.loopAvatar(avatarID,1, 1,getMax());
            avatarView.setImage(super.getAvatar(avatarID));
        });

        leftButton.setOnAction(e -> {
            avatarID = super.loopAvatar(avatarID, -1,1,getMax());
            avatarView.setImage(super.getAvatar(avatarID));
        });

        backButton.setOnAction(e -> {
            MainScene.mm = new MainMenu(MainScene.getWIDTH(), MainScene.getHEIGHT());
            MainScene.setScene(MainScene.mm);
            MainScene.mp = null;
        });
    }

    /**
     * Methods that interact with images in resources
     * @param avatarID uses the avatarID to get the chorence image
     * @return returns the avatar image
     */
    private Image chosenAvatar(int avatarID){
        File file = new File(fileLocation + (avatarID) + ".jpg");
        Image image = new Image(file.toURI().toString());
        return image;
    }

    /**
     * Method for creating new password - new popup window
     * @param title The title of the popup window
     */
    private void displayNewPassword(String title){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label header = new Label("Change password");
        Css.setHeaderStyle(header);
        newPassword = new PasswordField();
        newPassword.setPrefHeight(40);
        newPassword.setPrefWidth(200);
        newPassword.setPromptText("Enter new password");

        repeatPassword = new PasswordField();
        repeatPassword.setPrefHeight(40);
        repeatPassword.setPromptText("Repeat your new password");

        Button save = new Button("Save change");
        save.setPrefHeight(40);
        save.setPrefWidth(200);
        Css.setStyle(save);
        save.setOnAction(e -> {
            if(changePassword()){
                window.close();
            }
        });

        GridPane grid = new GridPane();
        grid.setVgap(30);
        grid.setAlignment(Pos.CENTER);
        grid.add(header, 0, 0);
        grid.add(newPassword, 0, 1);
        grid.add(repeatPassword, 0,2);
        grid.add(save, 0, 3);
        grid.setHalignment(save, HPos.CENTER);

        Css.setBackground(grid);
        Scene scene = new Scene(grid, 300, 300);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Method that checks if your new password is correct.
     * @return Return a boolean thats reflect the status of the password
     */
    public static boolean changePassword(){
        if(newPassword.getText().equals("") || repeatPassword.getText().equals("")){
            ConfirmBox.displayWarning("Warning", "You have to enter a new password");
            return false;
        }else if(!(newPassword.getText().equals(repeatPassword.getText()))){
            ConfirmBox.displayWarning("Warning", "Your passwords have to be equal");
            return false;
        }else{
            if(Authentication.changePassword(newPassword.getText())){
                ConfirmBox.displayWarning("Success", "Your password was successfully changed!");
            }
        }
        return true;
    }

    /**
     * Method to put the games played, won and a win percentage in a VBox
     * @return a VBox containing three labels
     */
    private static VBox wins(){

        VBox vbox = new VBox(5);

        Label gamesPlayed = new Label("Games Played:     " + DBConnection.getGamesPlayed());
        Css.setStyle(gamesPlayed);
        Label gamesWon = new Label("Games Won:         " + DBConnection.getGamesWon());
        Css.setStyle(gamesWon);

        double winPercentage;
        double gplayed = (double)DBConnection.getGamesPlayed();
        double gwon = (double)DBConnection.getGamesWon();
        if(gplayed == 0){
            winPercentage = 0;
        }else{
            winPercentage = (gwon/gplayed)*100;
        }

        Label winPercent = new Label("Win Percentage: " + String.format("%.0f", winPercentage) + "%");
        Css.setStyle(winPercent);
        vbox.getChildren().addAll(gamesPlayed, gamesWon, winPercent);
        return vbox;
    }
}
