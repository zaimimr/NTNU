import static javax.swing.JOptionPane.*;

class Opg_Array{

  public static void skuddAr(int[] maaned){
    int ar = Integer.parseInt(showInputDialog("Skriv inn aar: "));
    if (ar % 100 == 0 && ar % 400 == 0 && ar >= 0){
      showMessageDialog(null, ar + " er et skuddaar");
      maaned[2] = 29;
    }
    else if (ar % 100 != 0 && ar % 4 == 0 && ar >= 0){
      showMessageDialog(null, ar + " er et skuddaar");
      maaned[2] = 29;
    }else{
      showMessageDialog(null, ar + " er ikke et skuddaar");
      maaned[2] = 28;
    }
  }

  public static void main(String[] args){
    int[] maaned = {31,28,31,30,31,30,31,31,30,31,30,31};
    skuddAr(maaned);

  }
}
