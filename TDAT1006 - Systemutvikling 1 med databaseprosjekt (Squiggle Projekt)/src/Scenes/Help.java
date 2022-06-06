package Scenes;

import CSS.Css;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Class that opens a window where you can read the user manual
 */
public class Help extends Scenes{
    private GridPane grid;
    private final double WIDTH = 400, HEIGHT = 500;

    /**
     * Constructor for the help scene
     * @param WIDTH width of the scene
     * @param HEIGHT height of the scene
     */
    public Help(double WIDTH, double HEIGHT){
        super(WIDTH, HEIGHT);
        start();
    }
    /**
     * Method which displays a pop up window, where you can read the  manual
     */
    public void start() {
        Stage stage = new Stage();
        stage.setTitle("Help - User Manual");

        grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);

        Text message = new Text("\n   Quick start:\n" +
                "\n" +
                "   1. The first thing you need to do is to register a new user. \n" +
                "   You do this by clicking the 'register new user' button. \n" +
                "   Then you fill out the textboxes with your personal information. \n" +
                "   When you have registered your user, you will receive an email \n" +
                "   verifying your new user.\n" +
                "   2. Now you will be taken back to the log-in-page and can log in \n" +
                "   with your new username and password.\n" +
                "   3. You are now at the main menu. Here you can choose between \n" +
                "   quitting, entering the 'my page' or starting a new game.\n" +
                "   4. When selecting the 'Join Game' button, you will be sent to \n" +
                "   the drawing canvas which is your gaming arena. Here you can \n" +
                "   play with your friends when they arrive at the drawing \n" +
                "   canvas as well.\n" +
                "   5. When you are playing you will find yourself in one of two \n " +
                "   states. Ether you are the drawer, or you are the guesser. \n" +
                "   The drawer can use the drawing tools to draw the given word,\n" +
                "   while the rest of the players guess what the word is, by \n" +
                "   writing the guesses in the live-chat.\n" +
                "   6. If you guess correct word, both you and the drawer will \n" +
                "   receive point.\n" +
                "\n" +
                "   Registration\n" +
                "\n   When selecting the 'register new user' button you enter the \n" +
                "   registration page. Here you need to register a username. The \n" +
                "   username can be any word or number that is not already \n" +
                "   registered or longer than 20 characters. Then you need to \n" +
                "   register your email and password.\n " +
                "\n   When the registration is completed you \n" +
                "   will receive an email that verifies your new account.\n" +
                "   With you new username and password you can log in to the \n" +
                "   game. You do this at the log in page, which is the first \n" +
                "   page you enter when starting the application.\n" +
                "\n   Main menu\n" +
                "\n   At the main menu you have five buttons. The log out button \n" +
                "   takes you back to the page you came from. The quit button \n" +
                "   gives you an option to quit the whole application. A pop-up box \n" +
                "   will appear and ask if you are sure you want to quit.\n" +
                "\n   The options button is to be find at every scene in the \n" +
                "   application. In the options you can customize the application \n" +
                "   to your desire. \n" +
                "   You can change the background color and the font size.\n" +
                "   In 'My Page' you can se your username and email. Here you \n" +
                "   also have the choice to change your password. Your avatar is \n" +
                "   also displayed at the page, and you can toggle thru the \n" +
                "   different avatars and change if desired.\n" +
                "\n   Game\n" +
                "\n   The meaning of the game SQUIGGLE is to draw and guess word. \n" +
                "   The game chooses words and who will draw and guess. The \n" +
                "   drawer is the only person who knows the word, while the \n" +
                "   rest of the players only can see how many letters the word \n" +
                "   contains. The drawer has several tools to his possession. \n" +
                "   The drawer can choose between four different sizes of the \n" +
                "   drawing-brush and an eraser. You can also draw in any color\n" +
                "   you want.\n" +
                "\n   The players that are guessing will se what the drawer are \n" +
                "   drawing live. When the players want to guess the word, they \n" +
                "   simply write their suggestion in the live-chat at the right \n" +
                "   side of the canvas. If the guess is false, the guess will \n" +
                "   appear as a normal chat message. If the guess is correct \n" +
                "   the player will get a confirmation that the guess was \n" +
                "   correct. The other players will receive the same message \n" +
                "   and therefor be able to continue guessing without knowing \n" +
                "   the correct word. When the round ends, the game is over and \n" +
                "   the scores will be received. The player that guessed the \n" +
                "   correct word the fastest, win the most points. \n" +
                "   The drawer will also win points on the basis of the number of \n" +
                "   players that managed to guess the correct word. \n");

        message.setFont(new Font(11.6));

        ScrollPane text = new ScrollPane();
        text.setFitToWidth(true);
        text.setContent(message);
        text.setPrefWidth(360);

        grid.add(text,0, 0);

        Button backButton = new Button("Back");
        Css.setStyle(backButton);
        backButton.setPrefWidth(100);
        backButton.setOnAction(e -> { stage.close();});

        grid.add(backButton, 0, 1);
        GridPane.setHalignment(backButton, HPos.CENTER);
        GridPane.setMargin(backButton, new Insets(5, 0, 10, 0));
        GridPane.setMargin(text, new Insets(10, 0, 0, 0));

        Scene scene = new Scene(grid);
        Css.setBackground(grid);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setHeight(HEIGHT);
        stage.setWidth(WIDTH);
        stage.setScene(scene);
        stage.show();
    }
}