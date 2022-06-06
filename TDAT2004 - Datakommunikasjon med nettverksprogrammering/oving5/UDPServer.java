import java.io.*;
import java.net.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

class UDPServer {
   public static void main(String args[]) throws Exception {
      DatagramSocket serverSocket = new DatagramSocket(9876);
      byte[] receiveData = new byte[1024];
      byte[] sendData = new byte[1024];
      final ScriptEngineManager engineManager = new ScriptEngineManager();
      final ScriptEngine engine = engineManager.getEngineByName("JavaScript");
      while (true) {
         DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
         serverSocket.receive(receivePacket);
         String sentence = new String(receivePacket.getData());
         System.out.println("RECEIVED: " + sentence);
         InetAddress IPAddress = receivePacket.getAddress();
         int port = receivePacket.getPort();
         String capitalizedSentence;
         try {
            String s = engine.eval(""+sentence.trim().replaceAll(" ", "")).toString();
            String ss = new String(sentence);
            System.out.println(s);
            capitalizedSentence = s;
         } catch (Exception e) {
            capitalizedSentence = "Invalid equation";
         }
         sendData = capitalizedSentence.getBytes();
         DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
         serverSocket.send(sendPacket);
      }
   }
}