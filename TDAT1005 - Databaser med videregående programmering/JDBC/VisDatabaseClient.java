import static javax.swing.JOptionPane.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

class VisDatabaseClient {
  public static void main(String[] args) throws Exception {
    String brukernavn = "zuimran";
    String passord = "xaXIMlNC"; //xaXIMlNC
    String table = "person";

    String databasedriver = "com.mysql.jdbc.Driver";
    Class.forName(databasedriver);

    String databasenavn = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + brukernavn + "?user=" + brukernavn + "&password=" + passord;
    try(Connection forbindelse = DriverManager.getConnection(databasenavn)){

      Statement setning = forbindelse.createStatement();

      ResultSet res = setning.executeQuery("SELECT * FROM " + table);
      while (res.next()) {
        int persNr = res.getInt("persnr");
        String fornavn = res.getString("fornavn");
        String etternavn = res.getString("etternavn");
        System.out.println(persNr + ": " + fornavn + " " + etternavn);
      }

      res.close();
      setning.close();
    }catch(SQLException e){
      System.out.println("SQL feil" + e);
    }
  }
}
