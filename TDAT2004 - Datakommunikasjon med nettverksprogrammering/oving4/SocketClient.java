import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.net.InetAddress;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        final int PORTNR = 1337;

        /* Bruker en scanner til � lese fra kommandovinduet */
        Scanner readerCMD = new Scanner(System.in);
        InetAddress IP=InetAddress.getLocalHost();


        /* Setter opp forbindelsen til tjenerprogrammet */
        Socket connection = new Socket(IP, PORTNR);
        System.out.println("N� er forbindelsen opprettet på IP: " + IP +  " og Portnummer: " + PORTNR);

        /* �pner en forbindelse for kommunikasjon med tjenerprogrammet */
        InputStreamReader readerConnection = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(readerConnection);
        PrintWriter writer = new PrintWriter(connection.getOutputStream(), true);


        String number1 = readerCMD.nextLine();
        String number2 = readerCMD.nextLine();
        String operator = readerCMD.nextLine();


        while (!number1.equals("") || !number2.equals("") || !operator.equals("")) {
            writer.println(number1);  // sender teksten til tjeneren
            writer.println(number2);  // sender teksten til tjeneren
            writer.println(operator);  // sender teksten til tjeneren
            System.out.println("Answer: " + reader.readLine());
            number1 = readerCMD.nextLine();
            number2 = readerCMD.nextLine();
            operator = readerCMD.nextLine();

        }


        /* Lukker forbindelsen */
        reader.close();
        writer.close();
        connection.close();
    }
}
