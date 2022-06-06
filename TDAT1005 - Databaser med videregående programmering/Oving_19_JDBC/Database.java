public class Database {
  // All information needed to connect to the database
  private final String username = "zuimran";
  private final String password = "xaXIMlNC";
  private final String driver = "com.mysql.cj.jdbc.Driver";
  private final String dbUrl = "jdbc:mysql://mysql.stud.idi.ntnu.no:3306/" + username + "?user=" + username + "&password=" + password;

  // Standard JDBC components
  private Connection con;


  public Database() {
    getCon();
  }

  // Use this whenever you want to connect to the database
  public Connection getCon() {
      try{
          Class.forName(driver);
          con = DriverManager.getConnection(dbUrl);
      } catch (SQLException e) {
          e.printStackTrace();
      } catch (ClassNotFoundException e) {
          e.printStackTrace();
      }
      return con;
  }

  public void closeConnection() {
      try {
          if (con != null) {
              con.close();
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

}
