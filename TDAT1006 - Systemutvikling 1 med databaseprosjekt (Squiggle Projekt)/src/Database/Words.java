package Database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class that updates the wordlist in the database with word from a txt document
 */
public class Words {
    /**
     * Method that reads from a file and add the words to the database
     * @param args Main method
     */
    public static void main(String[] args) {
        String wordlist = "src/Database/wordlist.txt";
        try {
            FileReader readConnection = new FileReader(wordlist);
            BufferedReader readWordlist = new BufferedReader(readConnection);
            String listRead;
            String[] words;
            DBConnection.createLib();
            while((listRead = readWordlist.readLine())!= null){
                words = listRead.split("-");
                for(String s : words) {
                    DBConnection.insertIntoDB(s.trim());
                }
            }
            readWordlist.close();
            readConnection.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("IOException");
        }
    }
}
