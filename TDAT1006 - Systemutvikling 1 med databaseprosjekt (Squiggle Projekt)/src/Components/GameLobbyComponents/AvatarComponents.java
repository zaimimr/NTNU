package Components.GameLobbyComponents;
import Components.Player;
import Database.DBConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Class that adds the Avatar ListView
 */
public class AvatarComponents {
    public static ListView<String> listView;
    public static ArrayList<Player> players;
    public static ObservableList data;

    /**
     * Adds all the UI, shows list of online players
     *
     * @see ListView
     * @return VBox VerticalBox filled with players
     */
    public static VBox addAvatarUI() {
        VBox vb = new VBox();
        data = FXCollections.observableArrayList();
        listView = new ListView<>();
        listView.setPrefHeight(450);
        listView.setPrefWidth(200);
        setIntoLV();
        listView.setItems(data);
        vb.getChildren().add(listView);

        return vb;
    }

    /**
     * Method for fetching correct avatar image
     * @param i Image ID in the resources folder
     * @return Image avatar Image
     */
    private static Image getAvatar ( int i){
        File file = new File("resources/avatars/" + i + ".jpg");
        Image image = new Image(file.toURI().toString());
        return image;
    }

    /**
     * Inserts Players into the ListView
     *
     */
    private static void setIntoLV(){
        players = DBConnection.getPlayers();
        for(Player p : players) {
                data.add(p.getUsername());
        }
        listView.setCellFactory(param -> new ListCell<>() {
            private ImageView iv = new ImageView();
            @Override
            public void updateItem(String userName, boolean empty) {
                super.updateItem(userName, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    for(Player p : players){
                        if(userName.equals(p.getUsername())){
                            iv.setImage(getAvatar(p.getAvatarID()));
                            iv.setFitHeight(50);
                            iv.setFitWidth(50);
                        }
                        setText(userName + ", score: " + DBConnection.getPointsByUserID(DBConnection.getUserID(userName)));
                        setGraphic(iv);
                    }
                }
            }
        });
    }

    /**
     * Updates the ObservableList with new Players that might have joined
     *
     * @see ObservableList
     */
    public static void updateData() {
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
                                    listView.getItems().clear();
                                    setIntoLV();
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
