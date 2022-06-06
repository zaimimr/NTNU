import static javax.swing.JOptionPane.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class DatabaseClient {
/////////////////////////////////////////////////////////////////////////////////////

  private static final String brukernavn = "zuimran";
  private static final String passord = "xaXIMlNC"; //xaXIMlNC
  private static String table = null;
/////////////////////////////////////////////////////////////////////////////////////

  public static void main(String[] args) throws Exception {

    String databaseDriver = "com.mysql.jdbc.Driver";
    Class.forName(databaseDriver);

    String databaseName = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/" + brukernavn + "?user=" + brukernavn + "&password=" + passord;

    Connection con = DriverManager.getConnection(databaseName);
    Statement stmt = con.createStatement();
    client(con, stmt);
    stmt.close();
  }
/////////////////////////////////////////////////////////////////////////////////////

  public static void client(Connection con, Statement stmt) throws SQLException{
    final int CREATE_TABLE = 0;
    final int INSERT = 1;
    final int QUERY = 2;
    final int EXIT = 3;

    final String[] knapper = {"Create table", "Insert new data", "Read list", "Exit"};

    int valg = -1;
/////////////////////////////////////////////////////////////////////////////////////
    do{
      valg = showOptionDialog(null, "Client", "SQL", DEFAULT_OPTION, INFORMATION_MESSAGE, null, knapper, knapper[0]);
      switch (valg){
/////////////////////////////////////////////////////////////////////////////////////
        case CREATE_TABLE:
          String tableName = showInputDialog("Name of table");
          String sql = "CREATE TABLE IF NOT EXISTS " + tableName +" (";
          String variabler = null;
          while(true){
            variabler = showInputDialog("Name - Type - Function");
            if(variabler == null || variabler.equals("")){
              break;
            }
            sql += "\n" + variabler + ",";
          }
          sql = sql.substring(0,sql.length()-1);
          sql += ");";
          stmt.execute(sql);
        break;
/////////////////////////////////////////////////////////////////////////////////////
        case INSERT:
          table = getTable(con);
          if(table == null) break;
          ArrayList<String> list = getTableTypenName(con);
          String query = InputToTable(con, list);
          stmt.executeUpdate(query);

        break;
/////////////////////////////////////////////////////////////////////////////////////
        case QUERY:
          table = getTable(con);
          ResultSet res = stmt.executeQuery("SELECT * FROM " + table);
          while (res.next()) {
            System.out.println((String) res.getObject(1));
          }
          res.close();
        break;
      }
    }while(valg != EXIT);
  }
/////////////////////////////////////////////////////////////////////////////////////

  public static String getTable(Connection con) throws SQLException{
    DatabaseMetaData m = con.getMetaData();
    ResultSet res = m.getTables(null, null, null, new String[]{"TABLE"});
    ArrayList<String> list = new ArrayList<>();
    String nameFromSQL = null;
    while(res.next()){
      nameFromSQL = res.getString("TABLE_NAME");
      list.add(nameFromSQL);
    }
    String[] array = list.toArray(new String[list.size()]);
    String List_name = (String) showInputDialog(null, "Pick table", "Table", QUESTION_MESSAGE, null, array, array[0]);
    res.close();
    return List_name;
  }
/////////////////////////////////////////////////////////////////////////////////////

  public static ArrayList<String> getTableTypenName(Connection con) throws SQLException{
    DatabaseMetaData m = con.getMetaData();
    ResultSet resCol = m.getColumns(null, null, table, null);
    ArrayList<String> types = new ArrayList<>();
    while(resCol.next()){
      System.out.println(resCol.getString("TYPE_NAME"));
      types.add(resCol.getString("TYPE_NAME"));
      types.add(resCol.getString("COLUMN_NAME"));
    }
    resCol.close();
    return types;
  }
/////////////////////////////////////////////////////////////////////////////////////

  public static String InputToTable(Connection con, ArrayList<String> list) throws IllegalArgumentException{
    String query = "INSERT INTO " + table + " VALUE (";
    String type = null;
    String name = null;
    for(int i = 0; i < list.size(); i += 2){
      type = list.get(i);
      name = list.get(i+1);
      if(type.equals("INT")){
        int input = Integer.parseInt(showInputDialog("Legg inn " + type + " for " + name));
        query += " " + input + ",";
      } else if(type.equals("VARCHAR") || type.equals("CHAR") || type.equals("TEXT")){

        query += " '" + showInputDialog("Legg inn " + type + " for " + name) + "',";

      }else if (type.equals("BIT")){
        if(showConfirmDialog(null,"Legg inn " + type + " for " + name,
        type + " " + name,
        YES_NO_OPTION) == YES_OPTION){
          query += " TRUE,";
        }else{
          query += " FALSE,";
        }
      }
    }
    query = query.substring(0,query.length()-1);
    query += ")";
    return query;
  }
/////////////////////////////////////////////////////////////////////////////////////

}
