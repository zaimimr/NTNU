 import static javax.swing.JOptionPane.*;

class ov1c{

  public static void main(String[] args){
    int insek = Integer.parseInt(showInputDialog("Antall sekunder: "));

    int timer = insek / 3600;
    int min = (insek-timer*3600)/60;
    int sek = (insek-timer*3600-min*60);

    showMessageDialog(null, timer + "t : " + min + "min : " + sek + "sek");
  }

}
