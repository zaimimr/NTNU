package com.example.zaimi.oving_6_server;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server extends Thread {

    private final String TAG;
    private final int PORT;

    private Action onRecieve;

    public Server(String tag, int port) {
        TAG = tag;
        PORT = port;
    }

    public void setOnRecieve(Action a) {
        onRecieve = a;
    }

    public void run() {

        ServerSocket ss = null;
        Socket s = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try{
            Log.i(TAG, "Starting server...");
            ss = new ServerSocket(PORT);

            Log.i(TAG, ss.getInetAddress().getHostAddress() + " - " + ss.getInetAddress().getHostName());
            Log.i(TAG, "Socked initalized, waiting for client...");
            s = ss.accept();
            Log.v(TAG, "Client connected!");
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));


            onRecieve.run("Client connected!");
            out.println("Connected to server!");

            String res = in.readLine();
            Log.d(TAG, "Client says: " + res);

            while(true) {
                //in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                String result = in.readLine();
                //Log.v(TAG, "IN: " + result);
                handleRequest(s, out, result);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        finally {
            close(out, in, s, ss);
        }
    }

    public void handleRequest(Socket s, PrintWriter out, String request) {

        if(request == null) {
            return;
        }

        String input = "Recieved: " + request + "\n";

        Pattern regex = Pattern.compile("\\d+");
        Matcher matcher = regex.matcher(request);
        Log.v(TAG,input);

        if(matcher.find()) {
            try{
                int a = Integer.parseInt(matcher.group());

                if(!matcher.find()) {
                    throw new NumberFormatException("Could not read numbers");
                }
                int b = Integer.parseInt(matcher.group());
                int result = a + b;
                onRecieve.run(input + "Sending: " + result);
                out.println(String.valueOf(result));
            }
            catch (Exception e) {
                e.printStackTrace();
                onRecieve.run(input + "Could not read numbers");
                out.println("Could not read numbers");
            }
        }
    }

    private void close(AutoCloseable... closeables) {
        try{
            for (AutoCloseable closeable : closeables) {
                if(closeable != null) {
                    closeable.close();
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
