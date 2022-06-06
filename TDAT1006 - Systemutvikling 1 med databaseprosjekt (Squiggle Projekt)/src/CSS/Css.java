package CSS;


import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.File;

/**
 * Class with CSS styling methods
 */
public class Css {
    /**
     * Method that styles the tooltips
     * @param tooltip the tooltip that get styled
     */
    public static void setStyle(Tooltip tooltip){
        tooltip.setStyle("-fx-background-color: cornflowerblue;");
    }

    /**
     * Method that styles textfields
     * @param field the field that gets styled
     */
    public static void setStyle(TextField field) {
        field.setStyle("-fx-background-color: rgb(255,255,255);" +
                "-fx-border-color: #9a4a7c;" +
                "-fx-border-width: 3px;");
    }
    //LABELS//
    /**
     * Method that styles header labels
     * @param label the label that get styled
     */
    public static void setHeaderStyle(Label label) {
        label.setStyle("-fx-text-fill: white;\n" +
                "       -fx-font-size: 24;\n" +
                "       -fx-font-family: \"Gill Sans Ultra Bold\";");
    }

    /**
     * Method that styles labels
     * @param label the label that get styled
     */
    public static void setStyle(Label label) {
        label.setStyle("-fx-text-fill: white;" +
                "-fx-font-size: 14;\n" +
                "-fx-font-family: \"Gill Sans Ultra Bold\";");
    }
    /**
     * Method that styles the error labels
     * @param label the label that get styled
     */
    public static void setErrorStyle(Label label){
        label.setStyle("-fx-font-style: ITALIC;\n" +
                "   -fx-font-family: \"Cooper Black\";\n" +
                "   -fx-text-fill: white;\n" +
                "   -fx-font-size: 17px;");
    }

    /**
     * Method that styles the background to a image
     * @param grid The gridpane that the background is set to
     */
    public static void setBackground(GridPane grid){
        String url = new File("resources/SquiggleTheme.png").toURI().toString();
        grid.setStyle("-fx-background-image: url(" + url + ");");
    }
    //BUTTONS//
    /**
     * Method that styles the selector button
     * @param button The button that get styled
     */
    public static void selectorButton(Button button){
        button.setStyle("-fx-background-color: rgba(255, 255, 255 ,0); " +
                "-fx-font-weight: bold ; " +
                "-fx-font-size: 3em; " +
                "-fx-text-fill: white; " +
                "-fx-font-family: ariel; -fx-cursor: hand");
        button.setPrefHeight(35);
        button.setPrefWidth(25);
    }
    /**
     * Method that styles togglebuttons
     * @param button the button that get styled
     */
    public static void setStyle(ToggleButton button){
        button.setStyle("-fx-padding: 8 15 15 15;\n" +
                "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" +
                "    -fx-background-radius: 8;\n" +
                "    -fx-background-color: \n" +
                "        linear-gradient(from 0% 93% to 0% 100%, #9a4a7c 0%, #9a4a7c 100%),\n" +
                "        #9b119d,\n" +
                "        #d86e3a,\n" +
                "        radial-gradient(center 50% 50%, radius 100%, #f81de9, #c90dee);\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 2,0,0,1 );\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-family: \"Cooper Black\";\n" +
                "     -fx-text-fill: white;\n" +
                "     -fx-pref-height: 20px;\n" +
                "    -fx-font-size: 1.1em;-fx-cursor: hand;");
    }

    /**
     * Method that styles buttons
     * @param button the button that get styled
     */
    public static void setStyle(Button button){
        button.setStyle("-fx-padding: 8 15 15 15;\n" +
                "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" +
                "    -fx-background-radius: 8;\n" +
                "    -fx-background-color: \n" +
                "        linear-gradient(from 0% 93% to 0% 100%, #9a4a7c 0%, #9a4a7c 100%),\n" +
                "        #9b119d,\n" +
                "        #d86e3a,\n" +
                "        radial-gradient(center 50% 50%, radius 100%, #f81de9, #c90dee);\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 2,0,0,1 );\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-family: \"Cooper Black\";\n" +
                "     -fx-text-fill: white;\n" +
                "    -fx-font-size: 1.1em; -fx-cursor: hand");
    }

    /**
     * Method that styles the colorpicker
     * @param colorPicker the colorpicker that get styled
     */
    public static void setStyle(ColorPicker colorPicker){
        colorPicker.setStyle("-fx-padding: 8 15 15 15;\n" +
                "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" +
                "    -fx-background-radius: 8;\n" +
                "    -fx-background-color: \n" +
                "        linear-gradient(from 0% 93% to 0% 100%, #9a4a7c 0%, #9a4a7c 100%),\n" +
                "        #9b119d,\n" +
                "        #d86e3a,\n" +
                "        radial-gradient(center 50% 50%, radius 100%, #f81de9, #c90dee);\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 2,0,0,1 );\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-family: \"Cooper Black\";\n" +
                "     -fx-text-fill: white;\n" +
                "    -fx-font-size: 1.1em;-fx-cursor: hand");
    }
}
