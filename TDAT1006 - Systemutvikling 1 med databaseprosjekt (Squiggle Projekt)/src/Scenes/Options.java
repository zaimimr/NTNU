package Scenes;

import Components.Threads.Music;
import CSS.Css;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
/**
 * Class that create and opens a popup window where you can turn on/off the music
 */
public class Options extends Scenes {
    private GridPane gridPane;

    /**
     * Constructor of the Options page
     * @param WIDTH width of the scene
     * @param HEIGHT height of the scene
     */
    public Options(double WIDTH, double HEIGHT) {
        super(WIDTH, HEIGHT);
        openOptions();
    }

    /**
     * Method thats creates the popup window
     */
    public void openOptions(){
        Stage window = new Stage();
        window.setTitle("Options");

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.TOP_CENTER);

        Label optionsLabel = new Label("Options");
        Css.setHeaderStyle(optionsLabel);
        optionsLabel.setPadding(new Insets(10,10,10,10));
        gridPane.add(optionsLabel, 0,0);

        Label musicLabel = new Label("Music");
        Css.setStyle(musicLabel);
        gridPane.add(musicLabel, 0,3);
        CheckBox musicCheckBox = new CheckBox();

        Label sickoModeLabel = new Label("SICKO MODE");
        Css.setStyle(sickoModeLabel);
        gridPane.add(sickoModeLabel, 0,4);
        CheckBox sickoModeCheckBox = new CheckBox();

        if (Music.audio.isPlaying()) {
            if(Music.getChoosenSong() == 0) {
                musicCheckBox.setSelected(true);
                sickoModeCheckBox.setSelected(false);
            }
            if (Music.getChoosenSong() == 1) {
                musicCheckBox.setSelected(false);
                sickoModeCheckBox.setSelected(true);
            }
        } else {
            musicCheckBox.setSelected(false);
        }
        gridPane.add(musicCheckBox, 1,3);
        gridPane.add(sickoModeCheckBox,1,4);
        musicCheckBox.setOnAction(e -> {
            if (musicCheckBox.isSelected()) {
                sickoModeCheckBox.setSelected(false);
                Music.stopMusic();
                Music.playMusic(0);
            } else {
                Music.stopMusic();
            }
        });
        sickoModeCheckBox.setOnAction(e -> {
            if (sickoModeCheckBox.isSelected()) {
                musicCheckBox.setSelected(false);
                Music.stopMusic();
                Music.playMusic(1);
            } else {
                Music.stopMusic();
            }
        });


        Button submitButton = new Button("Submit");
        submitButton.setPrefWidth(100);
        Css.setStyle(submitButton);

        submitButton.setOnAction(e -> {
            window.close();

        });

        gridPane.add(submitButton, 0,5, 2,1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20,0,20,0));

        File file = new File("resources/SquiggleTheme.png");
        Image image = new Image(file.toURI().toString());
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        gridPane.setBackground(background);

        Scene scene = new Scene(gridPane, 300, 300);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.show();
    }
}