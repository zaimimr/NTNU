/* This class has static methods so we wont have to make objects of this class to use its methods */
package Database;

import Components.GameLobbyComponents.GameLogicComponents;
import Components.GameLobbyComponents.LiveChatComponents;
import Components.Player;
import Components.UserInfo;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Class containing all the different methods which involves connection to the database
 */
public class DBConnection {
    /**
     * Method which changes the password of an user
     * @param userID the userID of the user
     * @param hash hashing of the password
     * @param salt salting of the password
     * @return true or false depending on whether the change was successful or not
     */
    public static boolean changePassword(int userID, String hash, String salt){
        Connection con = null;
        PreparedStatement prepStmt = null;
        try{
            con = HikariCP.getCon();
            String query = "update USERS set password = ?, salt = ? where userID = ?";
            prepStmt = con.prepareStatement(query);
            prepStmt.setString(1, hash);
            prepStmt.setString(2, salt);
            prepStmt.setInt(3, userID);
            prepStmt.executeUpdate();
            return true;

        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }finally{
            closeConnection(con, prepStmt, null);
        }
    }


    /**
     * Method which registers a new user
     * @param userName username of the user
     * @param hash hashing of password
     * @param salt salting of password
     * @param userEmail the email of the user
     * @param avatarID choosen avatarID by the user
     */
    public static void registerUser(String userName, String hash, String salt, String userEmail, int avatarID) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = HikariCP.getCon();
            String query = "START TRANSACTION;";
            prepStmt = con.prepareStatement(query);
            prepStmt.executeUpdate();

            query = "INSERT INTO USERS VALUES (0, ?, ?, ?, ?, ?, 0)";
            prepStmt = con.prepareStatement(query);
            prepStmt.setString(1, userName);
            prepStmt.setString(2, hash);
            prepStmt.setString(3, salt);
            prepStmt.setString(4, userEmail);
            prepStmt.setInt(5, avatarID);
            prepStmt.executeUpdate();

            query = "INSERT INTO STATS VALUES(LAST_INSERT_ID(), 0, 0)";
            prepStmt = con.prepareStatement(query);
            prepStmt.executeUpdate();

            query = "COMMIT;";
            prepStmt = con.prepareStatement(query);
            prepStmt.executeUpdate();
        } catch (SQLSyntaxErrorException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, null);
        }
    }


    /**
     * Method which looks for input in the given column in the database
     * @param columnName name of the column
     * @param input input in that specific column
     * @return boolean depending on the outcome
     */
    public static boolean exists(String columnName, String input) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try{
            con = HikariCP.getCon();
            String query = "SELECT " + columnName + " FROM USERS WHERE " + columnName + "=?;";
            prepStmt = con.prepareStatement(query);
            prepStmt.setString(1, input);
            res = prepStmt.executeQuery();
            return res.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
        return false;
    }

    /**
     * Method which updates a user's logged in status
     * @param username username of the user
     * @param loggedIn logged in or logged out
     */
    public static void setLoggedIn(String username, int loggedIn) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = HikariCP.getCon();
            String query = "UPDATE USERS SET loggedIn=? WHERE userName=?;";
            prepStmt = con.prepareStatement(query);
            prepStmt.setInt (1, loggedIn);
            prepStmt.setString(2, username);
            prepStmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, null);
        }
    }

    /**
     * Gets the salt of a users password. Used for comparing passwords
     * @param username of the user
     * @return the salt of the password to the user
     */
    public static String getSalt(String username) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try {
            con = HikariCP.getCon();
            String query = "SELECT salt FROM USERS WHERE userName=?;";
            prepStmt = con.prepareStatement(query);
            prepStmt.setString(1, username);
            res = prepStmt.executeQuery();
            if(res.next()) {
                String salt = res.getString("salt");
                return salt;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
        return null;
    }

    /**
     * Method which checks whether a user is logged in or not
     * @param username username of that user
     * @return boolean depending on the outcome
     */
    public static boolean getLoggedIn(String username) {
        boolean loggedIn = false;
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try {
            con = HikariCP.getCon();
            String query = "SELECT loggedIn FROM USERS WHERE userName=?;";
            prepStmt = con.prepareStatement(query);
            prepStmt.setString(1, username);
            res = prepStmt.executeQuery();
            res.next();
            int num = res.getInt("loggedIn");
            loggedIn = (num == 0 ? false : true);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
        return loggedIn;
    }

    /**
     * General method for closing a connection
     * @param con Connection to ble closed
     * @param stmt Statement to be closed
     * @param res ResultSet to be closed
     */
    public static void closeConnection(Connection con, Statement stmt, ResultSet res) {
        try {
            if (res != null) {
                res.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the avatarID in the database
     * @param userID userID of the user
     * @param index index of that avatar
     */
    public static void setAvatarID(int userID, int index) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = HikariCP.getCon();
            String query = "UPDATE USERS SET avatarID=? WHERE userID=?";
            prepStmt = con.prepareStatement(query);
            prepStmt.setInt(1, index);
            prepStmt.setInt(2, userID);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, null);
        }
    }

    /**
     * Gets the userID of the username
     * @param username username of the user
     * @return the userID of the username
     */
    public static int getUserID(String username) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try {
            con = HikariCP.getCon();
            String query = "SELECT userID FROM USERS WHERE userName=?;";
            prepStmt = con.prepareStatement(query);
            prepStmt.setString (1, username);
            res = prepStmt.executeQuery();
            if (res.next()) {
                return res.getInt("userID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
        return 0;
    }

    /**
     * Removes a user from GAME table when user quits or game is over
     */
    public static void exitGame() {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = HikariCP.getCon();
            String query = "DELETE FROM GAME WHERE userID = ?";
            prepStmt = con.prepareStatement(query);
            prepStmt.setInt(1, UserInfo.getUserID());
            prepStmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, null);
        }
    }

    /**
     * Creates a new LIBRARY
     */
    public static void createLib() {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = HikariCP.getCon();
            String dropTable = "" + "DROP TABLE LIBRARY;";
            String createTable = "" + "CREATE TABLE LIBRARY( "
                    + "wordID INT(4) PRIMARY KEY AUTO_INCREMENT, "
                    + "    word VARCHAR(30) "
                    + ");";
            prepStmt = con.prepareStatement(dropTable);
            prepStmt.executeUpdate();
            prepStmt = con.prepareStatement(createTable);
            prepStmt.executeUpdate();
        } catch (SQLSyntaxErrorException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, null);
        }
    }

    /**
     * Inserts word into the database
     * @param word the word to be inserted
     */
    public static void insertIntoDB(String word) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = HikariCP.getCon();
            String insert = "INSERT INTO LIBRARY VALUE (default, \"" +  word + "\");";
            prepStmt = con.prepareStatement(insert);
            prepStmt.executeUpdate();
        } catch (SQLSyntaxErrorException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, null);
        }
    }

    /**
     * Inserts a new message into the CHAT table
     * @param message the message to be inserted
     */
    public static void insertMessage(String message) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = HikariCP.getCon();
            if (!(LiveChatComponents.checkWord(message))) {
                String query = "INSERT INTO CHAT VALUE (default, ?, ?);";
                prepStmt = con.prepareStatement(query);
                prepStmt.setInt (1, UserInfo.getUserID());
                prepStmt.setString (2, message);
                prepStmt.executeUpdate();
            } else {
                String query = "INSERT INTO CHAT VALUE (default, ?, ?);";
                String username = getUsername(UserInfo.getUserID());
                prepStmt = con.prepareStatement(query);
                prepStmt.setInt (1, 0);
                prepStmt.setString (2, username + " guessed correctly!");
                prepStmt.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, null);
        }
    }

    /**
     * Method which cleans the CHAT table in the database
      */
    public static void cleanChat() {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = HikariCP.getCon();
                String query = "DELETE FROM CHAT;";
                prepStmt = con.prepareStatement(query);
                prepStmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, null);
        }
    }

    /**
     * Method which resets the correctGuess column in the database
     */
    public static void resetCorrectGuess() {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = HikariCP.getCon();
            String query = "update GAME set correctGuess = 0;";
            prepStmt = con.prepareStatement(query);
            prepStmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt,null);
        }
    }

    /**
     * Method which gets the new messages sent to the database
     * @return all new messages in a StringBuilder
     */
    public static StringBuilder getNewMessages() {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try {
            con = HikariCP.getCon();
            int highestID = getHighestChatID();
            int tempHighestChatID = UserInfo.getTempHighestChatID();
            String query = "SELECT input, userID FROM CHAT where ChatID > " + tempHighestChatID + " and ChatID <= " + highestID + ";";
            prepStmt = con.prepareStatement(query);
            res = prepStmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (res.next()) {
                if (!(res.getString("input").equals("")) && !(LiveChatComponents.checkWord(res.getString("input")))) {
                    int userId = res.getInt("userID");
                    if (userId == 0) {
                        sb.append(res.getString("input"));
                        sb.append("\n");
                    } else {
                        sb.append(getUsername(userId) + ": " + res.getString("input"));
                        sb.append("\n");
                    }
                }
            }
            UserInfo.setTempHighestChatID(getHighestChatID());
            return sb;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
        return null;
    }

    /**
     * Method which gets the highest chatID in the CHAT table. Necessary for getting the new messages from the chat
     * @return the highest chat ID
     */
    public static int getHighestChatID() {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        int result = -1;
        try {
            con = HikariCP.getCon();
            String query = "select max(ChatID) from CHAT";
            prepStmt = con.prepareStatement(query);
            res = prepStmt.executeQuery();
            while (res.next()) {
                result = res.getInt("max(ChatID)");
            }
            return result;
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt,res);
        }
        return -1;
    }

    /**
     * Gets the username of an given userID
     * @param userId the userID of the user
     * @return the username
     */
    public static String getUsername(int userId) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try {
            con = HikariCP.getCon();
            String query = "SELECT userName FROM USERS WHERE userID =?";
            prepStmt = con.prepareStatement(query);
            prepStmt.setInt(1, userId);
            res = prepStmt.executeQuery();
            String output = "";
            while (res.next()) {
                output = res.getString("username");
            }
            return output;
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
        return null;
    }

    /**
     *  Method which gets the email of an useriD
     * @param userId the userID of an user
     * @return the mail of that particular user
     */
    public static String getUserEmail(int userId) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try {
            con = HikariCP.getCon();
            String query = "SELECT userMail FROM USERS WHERE userID=?";
            prepStmt = con.prepareStatement(query);
            prepStmt.setInt(1, userId);
            res = prepStmt.executeQuery();
            String output = "";
            if (res.next()) {
                output = res.getString("userMail");
            }
            return output;
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
        return null;
    }

    /**
     * Method which gets the amount of players in game
     * @return Amount of players in game
     */
    public static int getAmtPlayer(){
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try {
            con = HikariCP.getCon();
            String query = "SELECT COUNT(userID) FROM GAME;";
            prepStmt = con.prepareStatement(query);
            res = prepStmt.executeQuery();
            if (res.next()) {
                return res.getInt("COUNT(userID)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
        return 0;
    }

    /**
     * Method which gets the amount of point the user has
      * @return amount of points
     */
    public static int getPoints(){
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try {
            con = HikariCP.getCon();
            String query = "SELECT points FROM GAME where userID =" + UserInfo.getUserID();
            prepStmt = con.prepareStatement(query);
            res = prepStmt.executeQuery();
            if (res.next()) {
                return res.getInt("points");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
        return 0;
    }

    /**
     * Method which gets amount of points to a specific useriD
     * @param userID the useriD of the user
     * @return amount of points to that user
     */
    public static int getPointsByUserID(int userID){
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try {
            con = HikariCP.getCon();
            String query = "SELECT points FROM GAME where userID =" + userID;
            prepStmt = con.prepareStatement(query);
            res = prepStmt.executeQuery();
            if (res.next()) {
                return res.getInt("points");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
        return 0;
    }

    /**
     * Method which updates amount of points to the user
      * @param addPoints how many points to be added
     */
    public static void updatePoints(int addPoints){
        Connection con = null;
        PreparedStatement prepStmt = null;
        int oldPoints = getPoints();
        int newPoints = oldPoints + addPoints;
        try {
            con = HikariCP.getCon();
            String query = "UPDATE GAME SET points = " + newPoints +" WHERE userID =" + UserInfo.getUserID();
            prepStmt = con.prepareStatement(query);
            prepStmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, null);
        }
    }

    /**
     * Method which updates the avatarID of a user
     * @param userID the userID of the user to be updated
     */
    public static void updateAvatarID(int userID) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try {
            con = HikariCP.getCon();
            String query = "SELECT avatarID FROM USERS WHERE userID=?;";
            prepStmt = con.prepareStatement(query);
            prepStmt.setInt(1, userID);
            res = prepStmt.executeQuery();
            if (res.next()) {
                UserInfo.setAvatarID(res.getInt("avatarID"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
    }

    /**
     * Method which gets an list of all the players in game
     * @return list of all the players
     */
    public static ArrayList<Player> getPlayers() {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        ArrayList<Player> players = new ArrayList<Player>();
        try {
            con = HikariCP.getCon();
            String query = "SELECT userName, userID, avatarID, points FROM GAME JOIN USERS USING(userID);";
            prepStmt = con.prepareStatement(query);
            res = prepStmt.executeQuery();
            while (res.next()) {
                int userID = res.getInt("userID");
                String userName = res.getString("userName");
                int avatarID = res.getInt("AvatarID");
                double points = res.getDouble("points");
                players.add(new Player(userName, userID, avatarID, points));
            }
            return players;
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
        return null;
    }

    /**
     * Method which updated the correctGuess column to a user
     * @param userID useriD of the user
     */
    public static void setCorrectGuess(int userID){
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try{
            con = HikariCP.getCon();
            String query = "update GAME set correctGuess = 1 where userID=" + userID;
            prepStmt = con.prepareStatement(query);
            prepStmt.executeUpdate(query);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            closeConnection(con, prepStmt, res);
        }
    }

    /**
     * Method which gets how many players who have guessed correctly in game
     * @return the amount of correct guesses
     */
    public static int getAmtCorrect(){
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try {
            con = HikariCP.getCon();
            String query = "SELECT SUM(correctGuess) FROM GAME;";
            prepStmt = con.prepareStatement(query);
            res = prepStmt.executeQuery();
            if (res.next()) {
                return res.getInt("SUM(correctGuess)");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeConnection(con, prepStmt, res);
        }
    }

    /**
     * Method which uploads an image to the database
     * @param blob the image in byte format
     * @param word the game word
     */
    public static void uploadImage(byte[] blob, String word) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        try {
            con = HikariCP.getCon();
            //String query = "INSERT INTO DRAW VALUES (default, ?, ?, DATE_ADD(NOW(), INTERVAL 140 SECOND));";
            // Must also be changed in timers class timer 4
            String query = "INSERT INTO DRAW VALUES (default, ?, ?, DATE_ADD(NOW(), INTERVAL ? SECOND), default);";
            prepStmt = con.prepareStatement(query);
            prepStmt.setString(1, word);
            prepStmt.setBlob(2, new SerialBlob(blob));
            prepStmt.setInt(3, GameLogicComponents.gameTime);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, null);
        }
    }

    /**
     * Method which updates an image in the database
     * @param blob the image in byte format
     */
    public static void updateImage(byte[] blob){
        Connection con = null;
        PreparedStatement prepStmt = null;
        try{
            con = HikariCP.getCon();
            String query = "UPDATE DRAW SET drawing = ? ORDER BY gameID DESC LIMIT 1;";
            prepStmt = con.prepareStatement(query);
            prepStmt.setBlob(1, new SerialBlob(blob));
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, null);
        }
    }

    /**
     * Gets the newest image from the database
     * @return the image of the database
     */

    public static InputStream getImage(){
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        InputStream input = null;
        try{
            con = HikariCP.getCon();
            String query = "SELECT drawing FROM DRAW ORDER BY gameID DESC LIMIT 1;";
            prepStmt = con.prepareStatement(query);
            res = prepStmt.executeQuery();
            if (res.next()){
                input = res.getBlob("drawing").getBinaryStream();
                return input;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * Sets a random word to the newest game
     */
    public static void setRandomWord(){
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try{
            con = HikariCP.getCon();
            String wordQuery = "UPDATE DRAW SET word = (SELECT word FROM LIBRARY ORDER BY RAND() LIMIT 1) ORDER BY gameID DESC LIMIT 1;";
            prepStmt = con.prepareStatement(wordQuery);
            prepStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(con, prepStmt, res);
        }
    }

    /**
     * Gets the random word from the draw table in the database
     * @return the random word
     */
    public static String getRandomWord(){
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try{
            con = HikariCP.getCon();
            String wordQuery = "SELECT word FROM DRAW ORDER BY gameID DESC LIMIT 1;";
            prepStmt = con.prepareStatement(wordQuery);
            res = prepStmt.executeQuery();
            if(res.next()){
                return res.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(con, prepStmt, res);
        }
        return null;
    }


    /**
     * Gets the timer from the DRAW table in the database
     * @return the timer in date format
     */
    public static Date getDrawTimer() {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        Date date = null;
        try {
            con = HikariCP.getCon();
            String query = "SELECT timer FROM DRAW ORDER BY gameID DESC LIMIT 1";
            prepStmt = con.prepareStatement(query);
            res = prepStmt.executeQuery();
            if (res.next()) {
                Date time = res.getTime("timer");
                Date date1 = res.getDate("timer");
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(date1);
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(time);

                cal1.set(Calendar.HOUR_OF_DAY, cal2.get(Calendar.HOUR_OF_DAY));
                cal1.set(Calendar.MINUTE, cal2.get(Calendar.MINUTE));
                cal1.set(Calendar.SECOND, cal2.get(Calendar.SECOND));
                cal1.add(Calendar.SECOND, 3600);
                date = cal1.getTime();
            }
            return date;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
        return null;
    }

    /**
     * Method which makes a user join the game
     */

    public static void joinGame() {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try {
            con = HikariCP.getCon();
            String query = "SELECT COALESCE(MAX(playerNr),0) as max FROM GAME";
            prepStmt = con.prepareStatement(query);
            res = prepStmt.executeQuery();
            int currentMax = 0;
            if (res.next()) {
                currentMax = res.getInt("max") + 1;
            }
            query = "INSERT INTO GAME VALUES(?, ?, ?, ?);";
            prepStmt = con.prepareStatement(query);
            prepStmt.setInt(1, UserInfo.getUserID());
            prepStmt.setInt(2, 0);
            prepStmt.setInt(3, 0);
            prepStmt.setInt(4, currentMax);
            prepStmt.executeUpdate();
            UserInfo.setDrawRound(currentMax);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }

    }

    /**
     * Method which checks if a player is going to draw
     * @param currentRound the current round of the game
     * @return true if player is drawing, false if guesser
     */
    public static boolean playerToDraw(int currentRound) {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try {
            con = HikariCP.getCon();
            String query = "SELECT userID FROM GAME WHERE playerNr = ?;";
            prepStmt = con.prepareStatement(query);
            prepStmt.setInt(1, currentRound);
            res = prepStmt.executeQuery();
            if (res.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
        return false;
    }

    /**
     * Method which gets max round of the game
     * @return the max round
     */
    public static int getMaxRound() {
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try {
            con = HikariCP.getCon();
            String query = "SELECT MAX(playerNr) AS maxNr FROM GAME;";
            prepStmt = con.prepareStatement(query);
            res = prepStmt.executeQuery();
            if (res.next()) {
                return res.getInt("maxNr");
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, prepStmt, res);
        }
        return -1;
    }

    /**
     * Gets the amount of games played for a specific player
     * @return amount of games played by the user
     */
    public static int getGamesPlayed(){
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try{
            con = HikariCP.getCon();
            String query = "SELECT gamesPlayed AS gplayed FROM STATS WHERE userID =?;";
            prepStmt = con.prepareStatement(query);
            prepStmt.setInt(1, UserInfo.getUserID());
            res = prepStmt.executeQuery();
            if(res.next()){
                return res.getInt("gplayed");
            }
            return 0;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(con, prepStmt, res);
        }
        return -1;
    }

    /**
     * Gets the amount of games won for a specific user
     * @return amount of games won by the user
     */
    public static int getGamesWon(){
        Connection con = null;
        PreparedStatement prepStmt = null;
        ResultSet res = null;
        try{
            con = HikariCP.getCon();
            String query = "SELECT gamesWon AS gwon FROM STATS WHERE userID = ?;";
            prepStmt = con.prepareStatement(query);
            prepStmt.setInt(1, UserInfo.getUserID());
            res = prepStmt.executeQuery();
            if(res.next()){
                return res.getInt("gwon");
            }
            return 0;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(con, prepStmt, res);
        }
        return -1;
    }

    /**
     * Method that updates the amount of games won and played in the table STATS
     * @param won checks if a player has won the game
     */
    public static void updateStats(boolean won){
        Connection con = null;
        PreparedStatement prepStmt = null;
        try{
            con = HikariCP.getCon();
            String query = "UPDATE STATS SET gamesPlayed = gamesPlayed + 1 WHERE userID =?;";
            prepStmt = con.prepareStatement(query);
            prepStmt.setInt(1, UserInfo.getUserID());
            prepStmt.executeUpdate();
            if(won){
                query = "UPDATE STATS SET gamesWon = gamesWon + 1 WHERE userID =?;";
                prepStmt = con.prepareStatement(query);
                prepStmt.setInt(1, UserInfo.getUserID());
                prepStmt.executeUpdate();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(con, prepStmt, null);
        }
    }
}
