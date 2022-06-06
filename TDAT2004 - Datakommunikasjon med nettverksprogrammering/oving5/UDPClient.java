import java.io.*;
import java.net.*;

class UDPClient {
  public static void main(String args[]) throws Exception {
    InetAddress IPAddress = InetAddress.getByName("localhost");
    DatagramSocket clientSocket = new DatagramSocket();
    BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];
    while(true) {
      String sentence = inFromUser.readLine();
      sendData = sentence.getBytes();
      if(sentence.equals("exit")) {
        break;
      }
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
      clientSocket.send(sendPacket);
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
      String modifiedSentence = new String(receivePacket.getData());
      System.out.println("FROM SERVER:" + modifiedSentence);
    }
    clientSocket.close();
  }
}