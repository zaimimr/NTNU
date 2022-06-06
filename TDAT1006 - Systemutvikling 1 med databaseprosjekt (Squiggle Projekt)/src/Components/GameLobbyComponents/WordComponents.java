package Components.GameLobbyComponents;

import Components.UserInfo;
import Database.DBConnection;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * Class containing the different methods which involves the words players are drawing and guessing
 */
public class WordComponents {
    private static String word;

    /**
     * Method which returns the user interface of the word-part of the game
     * @return HBox containing the user interface
     */
    public static HBox addWordUI(){
        word = DBConnection.getRandomWord();
        HBox hb = new HBox();
        Label wordLabel = new Label("Word: " + showWord());
        wordLabel.setFont(new Font(20));
        wordLabel.setStyle("-fx-text-fill: white");
        hb.getChildren().add(wordLabel);
        return hb;
    }

    /**
     * Gets the active word
     * @return the word in game
     */
    public static String getWord(){
        return word;
    }

    /**
     * Sets a word and is only for test purposes as the other methods only gives a set of random words
     * @param w sets the word we want
     */
    public static void setWord(String w){
        word = w;
    }

    /**
     * Displays the active word
     * @return the active word formated to the game
     */
    public static String showWord(){
        String line = "___  ";
        String space = "   ";
        String period = ".";
        String result ="";
        boolean drawing = UserInfo.getDrawRound() == GameLogicComponents.getCurrentRound();
        String word = getWord();
        if(drawing){
            return word;
        }else{
            for(int i = 0; i < word.length(); i++){
                char letter = word.charAt(i);
                if(letter != ' ') {
                    result += line;
                }else if(letter == '.'){
                    result += period;
                }else{
                    result += space;
                }
            }
            return result;
        }
    }
}
