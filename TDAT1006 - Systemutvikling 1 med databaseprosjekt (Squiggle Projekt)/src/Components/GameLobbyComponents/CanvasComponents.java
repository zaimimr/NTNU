package Components.GameLobbyComponents;

import Components.UserInfo;
import Database.DBConnection;
import Scenes.MainMenu;
import Scenes.MainScene;
import Scenes.Options;
import CSS.Css;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static Components.Threads.Timers.stopHeartBeat;

/**
 * Class that deals with the canvas ingame
 */
public class CanvasComponents {
    private static ToggleButton draw;
    private static ToggleButton erase;
    private static ColorPicker cp;
    public static Canvas canvas;
    private static GraphicsContext gc;
    private static int WIDTH = 600, HEIGHT = 450;
    private static Color color = Color.rgb(255,255,255);


    /**
     * Adds the drawing UI at the bottom of the display
     *
     * @return HBOX HorisontaloBox with all the buttons and options
     */
    public static HBox addDrawingUI() {
        HBox hb = new HBox();

        draw = new ToggleButton("Draw");
        erase = new ToggleButton("Erase");
        Css.setStyle(draw);
        Css.setStyle(erase);
        ToggleButton[] tools = {draw, erase};
        ToggleGroup tgTools = new ToggleGroup();
        for (ToggleButton tool : tools) {
            tool.setMinWidth(50);
            tool.setToggleGroup(tgTools);
            tool.setCursor(Cursor.HAND);
        }
        tgTools.selectToggle(draw);

        ToggleButton lineWidth1 = new ToggleButton("1");
        ToggleButton lineWidth2 = new ToggleButton("2");
        ToggleButton lineWidth3 = new ToggleButton("3");
        ToggleButton lineWidth4 = new ToggleButton("4");
        Css.setStyle(lineWidth1);
        Css.setStyle(lineWidth2);
        Css.setStyle(lineWidth3);
        Css.setStyle(lineWidth4);
        ToggleButton[] penSize = {lineWidth1, lineWidth2, lineWidth3, lineWidth4};
        ToggleGroup tgLineWidth = new ToggleGroup();
        for (ToggleButton tb : penSize) {
            tb.setMinWidth(50);
            tb.setToggleGroup(tgLineWidth);
            tb.setCursor(Cursor.HAND);
        }
        tgLineWidth.selectToggle(lineWidth1);

        Button leaveButton = new Button("Leave game");
        Button optionButton = new Button("Options");
        Css.setStyle(leaveButton);
        Css.setStyle(optionButton);


        Region leave = new Region();
        hb.setHgrow(leave, Priority.ALWAYS);
        Region spacer = new Region();
        hb.setHgrow(spacer, Priority.ALWAYS);

        cp = new ColorPicker();
        cp.setValue(Color.BLACK);
        Css.setStyle(cp);
        hb.getChildren().addAll(leaveButton,leave, draw, erase, cp, lineWidth1, lineWidth2, lineWidth3, lineWidth4, spacer, optionButton);
        hb.setPrefWidth(60);
        hb.setAlignment(Pos.CENTER);


        File pencilFile = new File("resources/icons/pencil.png");
        Image pencil = new Image(pencilFile.toURI().toString());
        File rubberFile = new File("resources/icons/rubber.png");
        Image rubber = new Image(rubberFile.toURI().toString());
        ImageCursor penCur = new ImageCursor(pencil, 40, pencil.getHeight()-40);
        ImageCursor rubCur = new ImageCursor(rubber,10,rubber.getHeight()-80);


        cp.setOnAction(e-> {
            cp.setValue(cp.getValue());
        });

        draw.setOnAction(e->{
            canvas.setCursor(penCur);
        });
        erase.setOnAction(e->{
            canvas.setCursor(rubCur);
            gc.setStroke(color);
            gc.setLineWidth(12);
        });

        lineWidth1.setOnAction(e->{
            gc.setLineWidth(1);
        });
        lineWidth2.setOnAction(e->{
            gc.setLineWidth(4);
        });
        lineWidth3.setOnAction(e->{
            gc.setLineWidth(6);
        });
        lineWidth4.setOnAction(e->{
            gc.setLineWidth(10);
        });

        leaveButton.setOnAction(e -> {
            DBConnection.exitGame();
            stopHeartBeat();
            MainScene.mm = new MainMenu(MainScene.getWIDTH(), MainScene.getHEIGHT());
            MainScene.setScene(MainScene.mm);
            MainScene.gl = null;
            GameLogicComponents.setCurrentRound(1);
        });

        optionButton.setOnAction(e -> new Options(MainScene.getWIDTH(), MainScene.getHEIGHT()));
        return hb;
    }

    public static GraphicsContext getGc() {
        return gc;
    }

    /**
     * Adds the canvas itself, where the drawing/viewing is done.
     * @return HBox HorizontalBox with the canvas
     */
    public static HBox addCanvasUI(boolean drawing){
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);
        canvas = new Canvas(WIDTH-20, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,WIDTH, HEIGHT);
        gc.setLineWidth(1);
        hb.getChildren().addAll(canvas);
        if(drawing) {
            WritableImage wim = canvasSnapshot(canvas);
            byte[] blob = imageToByte(wim);
            DBConnection.setRandomWord();
            DBConnection.uploadImage(blob, DBConnection.getRandomWord());
            //uploadImage();
            // Makes new instance of game
        }else{
            setImage();
        }
        return hb;
    }
    public static void makeDrawable(GraphicsContext gcon) {
        if (UserInfo.getDrawRound() == GameLogicComponents.getCurrentRound()) {
            canvas.setOnMousePressed(e -> {
                if (draw.isSelected()) {
                    gcon.setStroke(cp.getValue());
                }
                gcon.beginPath();
                gcon.lineTo(e.getX(), e.getY());
            });
            canvas.setOnMouseDragged(e -> {
                gcon.lineTo(e.getX(), e.getY());
                gcon.stroke();
            });
            canvas.setOnMouseReleased(e -> {
                gcon.lineTo(e.getX(), e.getY());
                gcon.stroke();
                gcon.closePath();
            });
        }
    }


    /**
     * Uploads an updated version of drawing to Database
     */
    public static void updateImage() {
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
                                    WritableImage wim = canvasSnapshot(canvas);
                                    new Thread(()->{
                                        DBConnection.updateImage(imageToByte(wim));
                                    }).start();
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

    /**
     * snapshots the canvas and returns WritableImage
     * @return WritableImage Image of the canvas
     */
    private static WritableImage canvasSnapshot(Canvas canvas) {
        WritableImage writableImage = new WritableImage(WIDTH, HEIGHT);
        SnapshotParameters spa = new SnapshotParameters();
        return canvas.snapshot(spa, writableImage);
    }

    /**
     * Method that turns image into byte[], this is then uploaded as blob
     * @param image WritableImage that is to be uploaded to database
     * @return byte[] List of bytes that can be uploaded
     */
    private static byte[] imageToByte(WritableImage image) {
        BufferedImage bufferimage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferimage, "jpg", output );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }

    /**
     * Converts blob back to image, paints this at canvas
     */
    public static void setImage(){
        try {
            BufferedImage bi = ImageIO.read(DBConnection.getImage());
            if(bi != null){
                Image img = SwingFXUtils.toFXImage(bi, null);
                gc.drawImage(img, 0,0);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
