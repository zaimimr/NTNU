import static javax.swing.JOptionPane.*;

class ov1b{

  public static void main(String[] args){
    int timer = Integer.parseInt(showInputDialog("Timer: "));
    int minutter = Integer.parseInt(showInputDialog("Minutter: "));
    int sekunder = Integer.parseInt(showInputDialog("Sekunder: "));

    int totalsek = timer*3600 + minutter*60 + sekunder;

    showMessageDialog(null, timer + "t : " + minutter + "min : " + sekunder + "sek = " + totalsek + "sek");
  }

}
