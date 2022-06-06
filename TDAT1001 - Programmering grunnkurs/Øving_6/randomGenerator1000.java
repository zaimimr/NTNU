import static javax.swing.JOptionPane.*;
import java.util.*;

class randomGenerator1000{
  static int total = 1000;

  public static void stjerne (int tall){
    tall = Math.round(tall / (total/100));
    for(int s = 0; s < tall; s++){
      System.out.print("*");
    }
  }

  public static void main(String[] args){
    Random rand = new Random();
    int[] antall = new int[total];
    for (int i = 0; i < 1000; i++){
      int tall = rand.nextInt(10);
      antall[i] = tall;
    }

    for(int x = 0; x < 10; x++){
      int teller = 0;
      for (int i = 0; i < 1000; i++){
        if(antall[i] == x) teller++;

      }

//      System.out.print(x + " - " + teller + " ");

      System.out.print(x + " - " + antall[x] + " ");

      stjerne(teller);
      System.out.println();
    }
  }

}
