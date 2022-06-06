import java.io.*;
import java.util.*;
import static javax.swing.JOptionPane.*;

class main {
  private static Resturant re;

  public static void main(String[] args) {
    re = new Resturant("Resturant", 1990, 7);
    String[] option = {"Reserver bord", "Finn bord","Frigi rekke bord", "Avslutt"};
    while(true){
      int valg = showOptionDialog(null, "Velg ønsket operasjon", "Valg", DEFAULT_OPTION,  INFORMATION_MESSAGE, null, option, option[0]);
      switch(valg){
        case 0:
          String navn = showInputDialog("Navn");
          int antall = Integer.parseInt(showInputDialog("Antall"));
          re.reserveTable(navn, antall);
        break;
        case 1:
          String navne = showInputDialog("Navn");
          showMessageDialog(null, Arrays.toString(re.findTable(navne)));
        break;
        case 2:
          int[] clean = new int[re.busyTables()];
          int teller = 0;
          while(true){
            int bord = Integer.parseInt(showInputDialog("bord"));
            if(bord == -1) break;
            clean[teller] = bord;
            teller++;
          }
          re.cleanTable(clean);
        break;
        case 3:
          System.exit(0);
        break;
        default:
          System.exit(0);
        break;
      }
    }
  }
}
