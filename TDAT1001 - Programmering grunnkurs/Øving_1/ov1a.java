import static javax.swing.JOptionPane.*;
class ov1a{

  public static void main(String[] args){
    double skallering = 2.54;
    double tommer = Double.parseDouble(showInputDialog("Antall tommer: "));
    double cm = tommer * skallering;

    showMessageDialog(null, tommer + " tommer = " + cm + " cm");
  }

}
