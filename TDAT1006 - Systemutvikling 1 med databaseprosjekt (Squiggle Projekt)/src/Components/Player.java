/**
 * Player class
 *
 * Stores information about players, used in avatar listview ingame
 *
 * @author kaspervg
 */

package Components;

/**
 * Player class containing information and method on each player
 */
public class Player implements Comparable<Player>{
    private String username;
    private int userID;
    private int avatarID;
    private double points;

    /**
     * Contructor of the player class
     *
     * @param username  The player's username
     * @param userID    The user's ID
     * @param avatarID  The user's avatarID
     * @param points    A player's score
     */
    public Player(String username, int userID, int avatarID, double points){
        this.username = username;
        this.userID = userID;
        this.avatarID = avatarID;
        this.points = points;
    }

    /**
     * Gets username of player object
     *
     * @return  Username of player
     */
    public String getUsername(){
        return username;
    }

    /**
     * Gets userID of player object
     *
     * @return  userID of player
     */
    public int getUserID(){
        return userID;
    }

    /**
     * Gets avatarID of player object
     *
     * @return  avatarID of player
     */
    public int getAvatarID(){
        return avatarID;
    }

    /**
     * Gets amount of points a player object has
     *
     * @return  amount of points a player has
     */
    public double getPoints(){
        return points;
    }

    /**
     * Method comparing two players by they score
     * @param o player comparing to
     * @return -1, 0 or 1 depending on the points of the players
     */
    @Override
    public int compareTo(Player o) {
        int cmp = this.getPoints() > o.getPoints() ? -1 : this.getPoints() < o.getPoints() ? +1 : 0;
        return cmp;
    }
}
