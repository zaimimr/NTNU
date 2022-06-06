import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SocketServerWeb {
    public static void main(String[] args) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Listening on port 8080");
        while(true) {
            try(Socket client = serverSocket.accept()) {
                InputStreamReader readConn = new InputStreamReader(client.getInputStream());
                BufferedReader reader = new BufferedReader(readConn);
                PrintWriter writer = new PrintWriter(client.getOutputStream(), true);

                String list = "";

                String header = reader.readLine();
                while(!header.equals("")) {
                    list += "<li>" + header + "</li>";
                    header = reader.readLine();
                }

                System.out.println(list);

                String httpResponse = "HTTP/1.0 200 OK\nContent-Type: text/html; charset=utf-8\n\n\n" +
                        "<HTML><BODY>" +
                        "<H1> Hilsen. Du har koblet deg opp til min enkle web-tjener </h1>\n" +
                        "Header fra klient er:" +
                        "<ul>" +
                        list
                        +
                        "</ul>" +
                        "</BODY></HTML>";

                client.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                reader.close();
                writer.close();
            }





        }


    }
}
