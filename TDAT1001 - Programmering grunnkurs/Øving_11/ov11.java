import java.io.*;
import java.util.Scanner;
import javax.swing.JOptionPane.*;

public class ov11{


  private static final String saldoTxt = "saldo.txt";
  private static final String transTxt = "transaksjon.txt";
  static double saldo = 0;
  static double trans = 0;
  static String line = null;

  public static void lesInSaldo(){
    try{
      FileReader fileReader = new FileReader(saldoTxt);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      while((line = bufferedReader.readLine()) != null){
        saldo = Double.parseDouble(line);
      }
      bufferedReader.close();
    }catch(IOException e){
      System.out.println("Error reading file named '" + saldoTxt + "'");
    }
  }

  public static void lesInTrans(){
    try{
      FileReader fileReader = new FileReader(transTxt);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      while((line = bufferedReader.readLine()) != null){
        if(line.charAt(0) == 'U'){
          trans -= Double.parseDouble(line.substring(1));
        }else if(line.charAt(0) == 'I'){
          trans += Double.parseDouble(line.substring(1));
        }else{
          throw new IllegalArgumentException("Feil i transaksjon fil");
        }
      }
      bufferedReader.close();
    }catch(IOException e){
      System.out.println("Error reading file named '" + transTxt + "'");
    }
  }

  public static void skrivNySaldo(){
    try{
      FileWriter filewriter = new FileWriter(saldoTxt);
      BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
      bufferedWriter.write(saldo + "");
      bufferedWriter.close();
    }catch(IOException e){
      System.out.println("Error write file named '" + saldoTxt + "'");
    }
  }

  public static void main(String[] args){
    lesInSaldo();
    lesInTrans();
    if(saldo+trans >= 0){
      saldo += trans;
      skrivNySaldo();
    }
  }

}
