import static javax.swing.JOptionPane.*;
import java.util.*;

class MinRandom{

  static Random rand = new Random();

  public static int nesteHeltall(int nedre, int ovre){ //intervallet[nedre,ovre]
    return nedre + rand.nextInt(ovre-nedre);
  }

  public static double nesteDesimaltall(double nedre, double ovre){ // intervallet <endre,over>
    return nedre + rand.nextDouble()*ovre-nedre;
  }

  public static void main(String[] args){
    System.out.println(nesteHeltall(0,10));
    System.out.println(nesteHeltall(5,10));
    System.out.println(nesteHeltall(5,15));
    System.out.println(nesteHeltall(-5,5));
    System.out.println(nesteDesimaltall(0.0,10.0));
    System.out.println(nesteDesimaltall(0.5,10.5));
    System.out.println(nesteDesimaltall(5.3,15.9));
    System.out.println(nesteDesimaltall(-3.5,4.6));
  }
}
