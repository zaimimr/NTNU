import Components.Encryptor;
import Components.GameLobbyComponents.GameLogicComponents;
import Components.GameLobbyComponents.LiveChatComponents;
import Components.GameLobbyComponents.WordComponents;
import Components.PointSystem;
import Components.UserInfo;
import Database.DBConnection;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JUnit_test {
    private static JUnit_test instance;

    @BeforeAll
    public static void setUpClass() throws Exception {
        // Create objects of the classes that are to be tested
        UserInfo test = new UserInfo();
        DBConnection db = new DBConnection();
    }

    @AfterAll
    public  static void tearDownClass() {
        // Runs once, after all the test methods have completed
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Runs before each test
        instance = new JUnit_test();
    }

    @AfterEach
    public void tearDown() {
        // Runs after each test
    }

    // Test of given method ......
    @Test
    public void userExistsTest() throws Exception {
        String userName = "admin";
        String columnName = "userName";
        boolean test = DBConnection.exists(columnName, userName);
        boolean expResult = true;
        assertEquals(expResult, test);
    }

    @Test
    public void userNotExistsTest() throws Exception {
        String userName = "thisUserDoesntExist";
        String columnName = "userName";
        boolean test = DBConnection.exists(columnName, userName);
        boolean expResult = false;
        assertEquals(expResult, test);
    }

    @Test
    public void passwordExists() throws Exception {
        String username = "admin";
        String password = "admin";
        String salt = DBConnection.getSalt(username);
        String encryptor = Encryptor.Encryptor(password, salt);
        String hash = Encryptor.getHash(encryptor);
        String columnName = "password";
        boolean test = DBConnection.exists(columnName, hash);
        boolean expResult = true;
        assertEquals(expResult, test);
    }

    @Test
    public void getLoggedInTest() throws Exception {
        boolean test = DBConnection.getLoggedIn("admin");
        boolean expResult = false;
        assertEquals(expResult, test);
    }

    @Test
    public void setLoggedInTest() throws Exception {
        DBConnection.setLoggedIn("admin", 1);
        boolean test = DBConnection.getLoggedIn("admin");
        boolean expResult = true;
        DBConnection.setLoggedIn("admin", 0);
        assertEquals(expResult, test);
    }

    @Test
    public void getUserIDTest() throws Exception {
        String userName = "admin";
        int test = DBConnection.getUserID(userName);
        int expResult = 1;
        assertEquals(expResult, test);
    }

    @Test
    public void getUserNameTest() throws Exception {
        int userId = 1;
        String test = DBConnection.getUsername(userId);
        String expResult = "admin";
        assertEquals(expResult, test);
    }

    @Test
    public void getAmtPlayersTest() throws Exception {
        int test = DBConnection.getAmtPlayer();
        int expResult = 0;
        assertEquals(expResult, test);
    }

    @Test
    public void checkWordTest(){
        String word = "test";
        WordComponents.setWord(word);
        boolean test = LiveChatComponents.checkWord(word);
        boolean expResult = true;
        assertEquals(expResult, test);
    }

    @Test
    public void wrongWordTest(){
        WordComponents.setWord("Test");
        boolean test = LiveChatComponents.checkWord("Wrong");
        boolean expResult = false;
        assertEquals(expResult, test);
    }

    @Test
    public void getCurrentRoundTest(){
        int test = GameLogicComponents.getCurrentRound();
        int expResult = 1;
        assertEquals(expResult, 1);
    }

    @Test
    public void setCurrentRoundTest(){
        GameLogicComponents.setCurrentRound(5);
        int test = GameLogicComponents.getCurrentRound();
        int expResult = 5;
        assertEquals(expResult, test);
    }

    @Test
    public void incrementCurrentRoundTest(){
        GameLogicComponents.setCurrentRound(3);
        GameLogicComponents.incrementRoundCounter();
        int test = GameLogicComponents.getCurrentRound();
        int expResult = 4;
        assertEquals(expResult, test);
    }

    @Test
    public void pointTest(){
        if(DBConnection.getLoggedIn("test")){
            return;
        }else{
            DBConnection.setLoggedIn("test",1);
        }
        UserInfo.setUserName("test");
        int userid = DBConnection.getUserID("test");
        DBConnection.setCorrectGuess(userid);
        PointSystem.setPointsGuesser();
        int test = DBConnection.getPointsByUserID(userid);
        int expResult = 150;
        assertEquals(expResult,test);
        DBConnection.setLoggedIn("test",0);
    }


}
