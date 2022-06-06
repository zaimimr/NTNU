package Components;

import Database.DBConnection;

/**
 * UserInfo class includes information regarding the user running the program
 */
public class UserInfo {
    // Information about user - gets updated once a user logs in
    private static String userName;
    private static int userID;
    private static int avatarID;
    private static String userEmail;
    private static int tempHighestChatID;
    private static boolean guessedCorrectly = false;
    private static int drawRound;


    /**
     * Constructor which is called whenever a new user is registered
     */
    public UserInfo() {
        this.userName = null;
        this.userID = 0;
        this.avatarID = 0;
        this.userEmail = null;
        this.drawRound = -1;
    }

    /**
     * Gets the round of the draw
     * @return the round as an int
     */
    public static int getDrawRound() {
        return drawRound;
    }

    /**
     * Updates the drawRound
     * @param newRound new round which will be updated
     */
    public static void setDrawRound(int newRound) {
        drawRound = newRound;
    }

    /**
     * Gets the username of the user
     * @return the username of the user
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * Gets the userID of the user
     * @return the userID of the user
     */
    public static int getUserID() {
        return userID;
    }

    /**
     * Gets the avatarID of the user
     * @return the avatarID of the user
     */
    public static int getAvatarID() {
        return avatarID;
    }

    /**
     * Gets the user email of the user
     * @return the email of the user
     */
    public static String getUserEmail() {
        return userEmail;
    }

    /**
     * Method that runs on login, updates UserInfo.userID variable
     * and fetches avatarID from given user
     * @param userId takes the userID in order to recognize the correct user
     */
    public static void initializeUser(int userId) {
        userID = userId;
        DBConnection.updateAvatarID(userId);
        userEmail = DBConnection.getUserEmail(userId);
    }

    /**
     * Sets the username of the user
     * @param newName new username
     */
    public static void setUserName(String newName) {
        userName = newName;
    }


    /**
     * Sets the avatarID of the user
     * @param newID the new avatarID of the user
     */
    public static void setAvatarID(int newID) {
        avatarID = newID;
    }

    /**
     * Gets the tempHighestChatID in the users chat
     * @return the temporary highest chatID
     */
    public static int getTempHighestChatID() {
        return tempHighestChatID;
    }

    /**
     * Sets the temporary highest chatID
     * @param tempHighestChatID new highest chatID
     */
    public static void setTempHighestChatID(int tempHighestChatID) {
        UserInfo.tempHighestChatID = tempHighestChatID;
    }

    /**
     * Gets the guessedCorrectly value of the user
     * @return the boolean guessedCorrectly which specifies whether a user has guessed correct or not in a game
     */
    public static boolean getGuessedCorrectly() {
        return guessedCorrectly;
    }

    /**
     * Sets the guessedCorrectly boolean of a user
     * @param guessedCorrectly the new value of guessedCorrectly
     */
    public static void setGuessedCorrectly(boolean guessedCorrectly) {
        UserInfo.guessedCorrectly = guessedCorrectly;
    }
}
