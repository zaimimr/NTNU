package Components;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Encryptor class for encrypting password using hash and salt
 *
 */

public class Encryptor {

    /**
     * Encryption used for the password, method is used for both making and verifying an encrypted password.
     * @param password String password that the user har entered
     * @param saltest String hex values for the salt
     * @return String combination of both "hash|salt", uses splitters to get either
     */

    public static String Encryptor(String password, String saltest) {
        try {
            // Select the message digest for the hash computation -> SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Generate the random salt
            byte[] salt;
            if(saltest == null) {
                SecureRandom random = new SecureRandom();
                salt = new byte[16];
                random.nextBytes(salt);
            }else{
                salt = buildBytes(saltest);
            }
            // Passing the salt to the digest for the computation
            md.update(salt);
            String salted = buildHexString(salt);
            // Generate the salted hash
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            //Hashed password
            String hashed = buildHexString(hashedPassword);
            return hashed + "|" + salted;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Byte-to-Hex converter
     * @param bytes is an array of byte
     * @return String bytes in hex
     */
    private static String buildHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            //Convert from byte to hex
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Hex-to-byte converter
     * @param hex input to get an byte array
     * @return byte[] converted from hex
     */
    private static byte[] buildBytes(String hex) {
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i+=2) {
            int v = Integer.parseInt(hex.substring(i, i + 2), 16);
            b[i/2] = (byte) v;
        }
        return b;
    }

    /**
     * Used to get the hash string from the Encryptore function
     * @param s the output from Encryptor
     * @return String hash
     */
    //Splitt string from Encryptior to Hash using the splitter
    public static String getHash(String s){
        int index = s.indexOf('|');
        if(index < 0){
            return null;
        }
        return s.substring(0, index);
    }
    /**
     * Used to get the salt string from the Encryptore function
     * @param s the output from Encryptor
     * @return String salt
     */
    //Splitt string from Encryptior to Salt using the splitter
    public static String getSalt(String s){
        int index = s.indexOf('|');
        if(index < 0){
            return null;
        }
        return s.substring(index+1, s.length());
    }
}
