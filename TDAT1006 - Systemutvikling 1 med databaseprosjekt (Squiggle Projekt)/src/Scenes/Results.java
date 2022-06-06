package Scenes;

import Components.Player;
import Components.UserInfo;
import Database.DBConnection;
import CSS.Css;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class thats show the result of the game after a game is playes
 */
public class Results extends Scenes{
    private static Label header;
    private static Label userIDLbl;
    private static Label pointsLbl;
    private static Label placementLbl;
    private static ArrayList<Player> players = DBConnection.getPlayers();
    private static Button mmBtn;

    /**
     * Contstructor of results page
     * @param WIDTH width of the scene
     * @param HEIGHT height of the scene
     */
    public Results(double WIDTH, double HEIGHT){
        super(WIDTH, HEIGHT);
        addUIControls(getGp());
    }

    /**
     * Method that creats the scene
     * @param gp extendes the gridpane from the MainScene
     */
    public void addUIControls(GridPane gp){
        header = new Label("Results");
        Css.setHeaderStyle(header);
        gp.add(header, 1, 0, 1, 1);
        gp.setHalignment(header, HPos.CENTER);
        gp.setValignment(header, VPos.CENTER);
        gp.setGridLinesVisible(false);


        Collections.sort(players);

        if (players.get(0).getUserID() == UserInfo.getUserID()) {
            DBConnection.updateStats(true);
        } else {
            DBConnection.updateStats(false);
        }
        HBox hboxes[] = new HBox[players.size()];
        ImageView[] images = new ImageView[players.size()];

        for(int i = 0; i < players.size(); i++){
            hboxes[i] = new HBox(15);
            int avatarID = players.get(i).getAvatarID();
            images[i] = new ImageView(getAvatar(avatarID));
            images[i].setFitWidth(50);
            images[i].setFitHeight(50);

            userIDLbl = new Label("Player: " + players.get(i).getUsername());
            Css.setStyle(userIDLbl);
            pointsLbl = new Label("Points: " + players.get(i).getPoints());
            Css.setStyle(pointsLbl);
            placementLbl = new Label(Integer.toString(i+1));
            Css.setStyle(placementLbl);

            hboxes[i].getChildren().addAll(placementLbl, userIDLbl, pointsLbl);
            gp.add(hboxes[i], 1, i+1, 1, 1);
            gp.setHalignment(hboxes[i], HPos.CENTER);
            gp.setValignment(hboxes[i], VPos.CENTER);
            gp.add(images[i], 0, i+1, 1, 1);
        }

        mmBtn = new Button("Main menu");
        Css.setStyle(mmBtn);
        gp.add(mmBtn, 1, 7, 1, 1);
        mmBtn.setOnAction(e -> {
            DBConnection.exitGame();
            MainScene.mm = new MainMenu(super.getWIDTH(), super.getHEIGHT());
            MainScene.setScene(MainScene.mm);
            MainScene.rs = null;
        });
    }
}
