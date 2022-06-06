import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.server.ExportException;
import java.util.Scanner;

public class ThreadClientHandler extends Thread {
    private Socket socket;

    public ThreadClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            /* �pner en forbindelse for kommunikasjon med tjenerprogrammet */
            InputStreamReader readerConnection = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(readerConnection);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            //Scanner sc = new Scanner(System.in);

            //Enkel tjener oppgave
            System.out.println("Koblet til");

            String number1 = reader.readLine();
            String number2 = reader.readLine();
            String operator = reader.readLine();




            while (!number1.equals("") || !number2.equals("") || !operator.equals("")) {
                if (operator.equals("+")) {
                    int answer = Integer.parseInt(number1) + Integer.parseInt(number2);
                    writer.println("The answer was: " + answer);
                    System.out.println(number1 + " " + operator + " " + number2 + " = " + answer);

                } else if (operator.equals("-")) {
                    int answer = Integer.parseInt(number1) - Integer.parseInt(number2);
                    writer.println("The answer was: " + answer);
                    System.out.println(number1 + " " + operator + " " + number2 + " = " + answer);
                } else {
                    System.out.println("Unvalid operator");
                    writer.println("Unvalid operator");

                }

                number1 = reader.readLine();
                number2 = reader.readLine();
                operator = reader.readLine();


            }

            readerConnection.close();
            writer.close();
        } catch(Exception ioe) {
            ioe.printStackTrace();
        }

    }


}
