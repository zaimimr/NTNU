package Scenes;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;

/**
 * Abstract class that makes the foundation of all the scenes
 */
public abstract class Scenes {

    //Object variables
    private static GridPane gp;
    private Scene sc;

    private final int max = new File("resources/avatars/").listFiles().length - 1;

    // Dimensions
    private final double WIDTH;
    private final double HEIGHT;

    /**
     * Constructor for scenes. Used for most of the other scenes in the game
      * @param WIDTH width of the scene
     * @param HEIGHT height of the scene
     */
    public Scenes(double WIDTH, double HEIGHT){
        gp = createGridPane();
        sc = new Scene(gp, WIDTH, HEIGHT);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
    }

    /**
     * Method that gets the gridpane
     * @return returns the gridpane
     */
    public GridPane getGp() {
        return gp;
    }
    /**
     * Method that gets the Scene
     * @return returns the Scene
     */
    public Scene getSc(){
        return sc;
    }

    /**
     * Method that sets the scene
     * @param sc The scene that will be set
     */
    public void setSc(Scene sc) {
        this.sc = sc;
    }


    /**
     * Method that gets the height of the scene
     * @return returns the height
     */
    double getHEIGHT() {
        return HEIGHT;
    }

    /**
     * Method that gets the width of the scene
     * @return returns the width
     */
    double getWIDTH() {
        return WIDTH;
    }

    /**
     * Method that gets the max number of avatars in the resources
     * @return returns the max number
     */
    public int getMax() {
        return max;
    }

    /**
     * Method that creates the Standard GridPane formation
     * @return returns the GridPane
     */
    private GridPane createGridPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();
        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);
        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        // Set the horizontal gap between columns
        gridPane.setHgap(10);
        // Set the vertical gap between rows
        gridPane.setVgap(10);
        // Add Column Constraints
        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(100, 100, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.RIGHT);
        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstrains = new ColumnConstraints(200,200, Double.MAX_VALUE);
        columnTwoConstrains.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);

        File file = new File("resources/SquiggleTheme.png");
        Image image = new Image(file.toURI().toString());
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundimage);
        gridPane.setBackground(background);

        return gridPane;
    }

    /**
     * Method that make a loop when you toggle through the avatars
     * @param counter is the counter
     * @param add is the adder
     * @param min is the first avatar
     * @param max is the last avatar
     * @return return either the first or the last avatar
     */
    int loopAvatar(int counter, int add, int min, int max){
        counter += add;
        if(counter < min){
            counter = max;
        }else if(counter > max){
            counter = min;
        }
        return counter;
    }

    /**
     * Method that creates images from jpg files
     * @param i is the name/index of the file
     * @return returns the Image
     */
    public static Image getAvatar(int i){
        File file = new File("resources/avatars/" + i + ".jpg");
        return new Image(file.toURI().toString());
    }
}
