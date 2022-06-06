import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {


    public static void main(String[] args) throws IOException {
        int PORTNR = 1337;
        Socket connection = null;
        BufferedReader reader = null;
        PrintWriter printer = null;


        try {

            ServerSocket socket = new ServerSocket(PORTNR);
            System.out.println("New server");

            while (true) {
                connection = socket.accept();
                new ThreadClientHandler(connection).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }


    }


}
