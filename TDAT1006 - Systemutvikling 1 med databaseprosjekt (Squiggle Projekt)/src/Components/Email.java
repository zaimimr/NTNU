package Components;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * Email class for sending confirm email to registered users
 *
 */

public class Email {
    //Information needed in order to connect to the email
    private static final String username = "calfskingames@gmail.com";
    private static final String password = "admin123admin";


    /**
     * Method which sends registration email to the user
     * @param to the email which will receive the registration complete-mail
     */
    public static void sendEmail(String to) {
        String host = "smtp.gmail.com";

        //Connecting to the email server
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", host);
        properties.put("mail.smtp.user", username);
        properties.put("mail.smtp.password", password);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");

        //Creating a session in order to be able to send email
        Session session = Session.getDefaultInstance(properties,null);

        try {
            MimeMessage message = new MimeMessage(session);

            //Defining which email we're sending from
            message.setFrom(new InternetAddress(username));

            //Defining which email we're sending to
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            //Setting the subject of the email
            message.setSubject("Welcome to Calfskin Games");

            //Setting the message of the email

            //ADD the username the user registered with
            message.setText("Hello. You are now registred");

            //Sending the messaage
            Transport transport = session.getTransport("smtp");
            transport.connect(host, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        }catch(MessagingException me) {
            me.printStackTrace();
        }
    }
}
