import static javax.swing.JOptionPane.*;
class tilAfro{
  public static void main(String[] args){
    String[] valg = {"Ja", "Nei"};
    int x = showOptionDialog(null, "Kan du trene kl 7-8 tiden?","Trene",DEFAULT_OPTION, PLAIN_MESSAGE, null,valg,valg[0]);
    int tid=0;
    switch(x){
      case 0:
      showMessageDialog(null, "Nice!!");
      tid = 7;
      break;
      case 1:
      tid = Integer.parseInt(showInputDialog("Naar kan du?"));
      break;
      default:
      break;
    }
    showMessageDialog(null,"Da trener vi " + tid + "-" + (tid+1) + " tiden");
  }
}
