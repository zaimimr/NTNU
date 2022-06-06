package Components;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.concurrent.CountDownLatch;


/**
 * Toast display is displayed as information for a couple of seconds
 */

public class Toast{
    private static double WIDTH, HEIGHT;
    private static Stage s;

    /**
     * Constructor making i
     * @param stage reference to the stage where the toast will be displayed
     * @param WIDTH the width of the stage
     * @param HEIGHT the height of the stage
     * The width and height parameters are used to translate the toast to the correct position
     */
    public  Toast(Stage stage, double WIDTH, double HEIGHT){
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        s = stage;
    }


    /**
     * Used for displaying the toast with a specified text
     * @param toastMsg String that will be displayed in the toast
     * @param toastDelay Int value of the total time it will be displayed
     * @param fadeInDelay Int value of fade in delay
     * @param fadeOutDelay Int value of fade out delay
     */
    public static void makeText(String toastMsg, int toastDelay, int fadeInDelay, int fadeOutDelay)    {
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
                                    Stage toastStage=new Stage();
                                    toastStage.initOwner(s);
                                    toastStage.setResizable(false);
                                    toastStage.initStyle(StageStyle.TRANSPARENT);

                                    StackPane root = new StackPane();
                                    root.setStyle("-fx-background-radius: 20; " +
                                            "-fx-background-color: rgba(0, 0, 0, 0.2); " +
                                            "-fx-padding: 25px;");
                                    root.setOpacity(0);

                                    root.setAlignment(Pos.CENTER);
                                    root.setTranslateY(HEIGHT-HEIGHT*0.25);
                                    root.setTranslateX(0);

                                    Text text = new Text(toastMsg);
                                    text.setFont(Font.font("Ariel", 20));
                                    text.setFill(Color.RED);

                                    root.getChildren().add(text);

                                    Scene scene = new Scene(root);
                                    scene.setFill(Color.TRANSPARENT);
                                    toastStage.setScene(scene);
                                    toastStage.show();

                                    Timeline fadeInTimeline = new Timeline();
                                    KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDelay),
                                            new KeyValue (toastStage.getScene().getRoot().opacityProperty(), 1));
                                    fadeInTimeline.getKeyFrames().add(fadeInKey1);
                                    fadeInTimeline.setOnFinished((ae) ->{
                                        new Thread(() -> {
                                            try{
                                                Thread.sleep(toastDelay);
                                            }
                                            catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Timeline fadeOutTimeline = new Timeline();
                                            KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay),
                                                    new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
                                            fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
                                            fadeOutTimeline.setOnFinished((aeb) -> toastStage.close());
                                            fadeOutTimeline.play();
                                        }).start();
                                    });
                                    fadeInTimeline.play();
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