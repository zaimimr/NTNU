import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import static javax.swing.JOptionPane.showInputDialog;

public class main extends Application {

    private  ListView<Valuta> fromLV;
    private  ListView<Valuta> toLV;
    private Label footer;

    private Valuta fromValuta = null;
    private Valuta toValuta = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gp = new GridPane();
        Scene sc = new Scene(gp, 300,250);
        addUI(gp);

        fromLV.setOnMouseClicked(e -> {
            fromValuta = fromLV.getSelectionModel().getSelectedItem();
            converter();
        });
        toLV.setOnMouseClicked(e -> {
            toValuta = toLV.getSelectionModel().getSelectedItem();
            converter();
        });


        primaryStage.setScene(sc);
        primaryStage.setTitle("Valuta converter");
        primaryStage.show();
    }

    public void converter(){
        if(!toLV.getSelectionModel().isEmpty() && !fromLV.getSelectionModel().isEmpty() && toLV.getSelectionModel().getSelectedIndex() != fromLV.getSelectionModel().getSelectedIndex()){

            double value = Double.parseDouble(showInputDialog("Oppgi beløp"));
            double exchange = toValuta.convert(fromValuta, value);
            footer.setText(String.format("%.2f", value) + " " + fromValuta.getName() + " = " + String.format("%.2f", exchange) + " " + toValuta.getName());
            fromLV.getSelectionModel().select(-1);
            toLV.getSelectionModel().select(-1);
        }else{

        }
    }

    public void addUI(GridPane gp){
        Label header = new Label("Velg fravaluta og tilvaluta fra listene:");
        gp.add(header, 0,0, 2, 1);

        Label from = new Label("Fra valuta:");
        gp.add(from, 0,1);

        Label to = new Label("Til valuta:");
        GridPane.setHalignment(to, HPos.RIGHT);
        gp.add(to, 1,1);

        fromLV= new ListView<>();
        addValutas(fromLV);
        gp.add(fromLV,0,2);

        toLV = new ListView<>();
        addValutas(toLV);
        gp.add(toLV,1,2);

        footer = new Label();
        gp.add(footer, 0,3, 2, 1);
    }

    public void addValutas(ListView<Valuta> list){
        list.getItems().addAll(new Valuta("Euro", 8.10, 1), new Valuta("US Dollar", 6.23, 1),
                new Valuta("Britiske pund", 12.27, 1), new Valuta("Svenske kroner", 88.96, 100),
                new Valuta("Danske kroner", 108.75, 100), new Valuta("Yen", 5.14, 100),
                new Valuta("Islandske kroner", 9.16, 100), new Valuta("Norske kroner", 100, 100));
    }


}
