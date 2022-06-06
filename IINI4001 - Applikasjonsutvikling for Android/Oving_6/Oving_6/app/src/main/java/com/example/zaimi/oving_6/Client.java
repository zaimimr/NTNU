package com.example.zaimi.oving_6;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {

    private final static String TAG = "Client";
    private final String IP;
    private final int PORT;

    Socket s = null;
    PrintWriter out = null;
    BufferedReader in = null;

    private boolean isConnected = false;

    private Action onRecieve;

    public Client(String ip, int port) {
        IP = ip;
        PORT = port;
    }

    public void setResponse(Action a) {
        onRecieve = a;
    }

    public void run() {
        if (isConnected) {
            Log.v(TAG, "Client is already connected");
            return;
        }

        try {
            s = new Socket(IP, PORT);
            Log.v(TAG, "Connected to server: " + s.toString());
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            isConnected = true;

            out.println("Hello, I am a client!");

            // Check for data in
            continouslyReadData();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void continouslyReadData() {
        new Thread(() -> {
            while (isConnected) {
                try {
                    if (!in.ready()) {
                        continue;
                    }
                    String result = in.readLine();
                    Log.v(TAG, "Recieved Message: " + result);
                    if (onRecieve != null) {
                        onRecieve.run(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }).start();
    }

    public void send(String value) {
        new Thread(() -> {
            try {
                Log.v(TAG, "Sending value: " + value);
                out.println(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
