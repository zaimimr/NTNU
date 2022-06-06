/*
 * VisDatabaseClient.java
 *
 * Programmet kopler seg til databasetjener, og henter ut innholdet i tabellen Person.
 *
 */
import static javax.swing.JOptionPane.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
class Oving1 {
  public static void main(String[] args) throws Exception {
    String brukernavn = "zuimran";  // DataLeser, se nedenfor
    String passord = "xaXIMlNC"; //DataLeser.lesPassord(); //xaXIMlNC
    String table = "boktittel";

    String databasedriver = "com.mysql.jdbc.Driver";
    Class.forName(databasedriver);  // laster inn driverklassen

    String databasenavn = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + brukernavn + "?user=" + brukernavn + "&password=" + passord;
    try(Connection forbindelse = DriverManager.getConnection(databasenavn)){

      Statement setning = forbindelse.createStatement();

      ResultSet res = setning.executeQuery("SELECT * FROM " + table);
      while (res.next()) {
        String isbn = res.getString("isbn");
        String forfatter = res.getString("forfatter");
        String tittel = res.getString("tittel");
        System.out.println(isbn + ": " + forfatter + " " + tittel);
      }

      res.close();
      setning.close();
    }catch(SQLException e){
      System.out.println("SQL feil" + e);
    }
  }
}

class DataLeser {
  /**
   * Leser passord fra brukeren.
   * Teksten "trimmes" før den returneres til klienten.
   */
  public static String lesPassord() {
    JLabel jPassword = new JLabel("Passord: "); // forenklet: http://www.asjava.com/swing/joptionpane-showinputdialog-with-password/
    JTextField password = new JPasswordField();
    Object[] obj = {jPassword, password};
    showConfirmDialog(null, obj, "Please input password for JOptionPane showConfirmDialog", OK_CANCEL_OPTION);
     return password.getText().trim();
  }

  /**
   * Leser en tekst fra brukeren. Blank tekst godtas ikke.
   * Teksten "trimmes" før den returneres til klienten.
   */
  public static String lesTekst(String ledetekst) {
    String tekst = showInputDialog(ledetekst);
    while (tekst == null || tekst.trim().equals("")) {
      showMessageDialog(null, "Du må oppgi data.");
      tekst = showInputDialog(ledetekst);
    }
    return tekst.trim();
  }
}


/* Utskrift fra programmet:
100: Ole Hansen
101: Anne Grethe Ås
102: Jonny Hansen
*/
