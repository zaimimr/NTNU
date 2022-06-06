import static javax.swing.JOptionPane.*;
class ov2a{
  public static void main(String[] args){
    int ar = Integer.parseInt(showInputDialog("Skriv inn aar: "));
    if (ar % 100 == 0 && ar % 400 == 0 && ar >= 0){
      showMessageDialog(null, ar + " er et skuddaar");
    }
    else if (ar % 100 != 0 && ar % 4 == 0 && ar >= 0){
      showMessageDialog(null, ar + " er et skuddaar");
    }else{
      showMessageDialog(null, ar + " er ikke et skuddaar");
    }

  }
}
