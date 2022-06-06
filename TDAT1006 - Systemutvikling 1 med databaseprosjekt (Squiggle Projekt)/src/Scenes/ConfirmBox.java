package Scenes;

import CSS.Css;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Class that creates confirmation boxes, with custom title and message
 */
class ConfirmBox{
    private static boolean answer;
    private static final double WIDTH = 300, HEIGHT = 190;
    /**
     * Method which displays a pop up window, thats makes you confirm your choice before you quit
     * @param title sets the title of the window
     * @param message A custom message which displays as a label
     */
    static boolean display(String title, String message){
        Stage stage = new Stage();

        Label label = new Label();
        label.setText(message);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font("Cooper Black", FontWeight.BOLD, 22));
        label.setStyle("-fx-text-fill: white;");

        Button yesButton = new Button("Yes");
        Css.setStyle(yesButton);

        Button noButton = new Button("No");
        Css.setStyle(noButton);

        yesButton.setOnAction(e -> {
            answer = true;
            stage.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            stage.close();
        });

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(yesButton, noButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(40, 0,0,0));

        GridPane grid = new GridPane();
        Css.setBackground(grid);

        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.add(label, 0,0,2,1);
        grid.add(hbox,0,1, 2, 2);

        Scene scene = new Scene(grid);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setScene(scene);
        stage.showAndWait();

        return answer;
    }

    /**
     * Method which displays a pop up window, thats makes you click OK
     * @param title sets the title of the window
     * @param message A custom message which displays as a label
     */
    public static void displayWarning(String title, String message){
        Stage stage = new Stage();
        stage.setMinWidth(600);

        Label label = new Label();
        label.setText(message);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font("Cooper Black", FontWeight.BOLD, 22));
        label.setStyle("-fx-text-fill: white;");

        Button okBtn = new Button("Ok");
        Css.setStyle(okBtn);

        okBtn.setOnAction(e -> {
            stage.close();
        });

        GridPane grid = new GridPane();
        Css.setBackground(grid);

        grid.setVgap(30);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.add(label, 0,0,2,1);
        grid.add(okBtn,1,1, 2, 2);
        grid.setHalignment(okBtn, HPos.CENTER);
        grid.setValignment(okBtn, VPos.CENTER);

        Scene scene = new Scene(grid);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
