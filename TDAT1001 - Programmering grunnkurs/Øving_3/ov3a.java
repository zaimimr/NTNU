import static javax.swing.JOptionPane.*;

class ov3a{

  public static void main(String[] args){

    int gange = Integer.parseInt(showInputDialog("Skriv inn et tall: "));
    int gange2 = Integer.parseInt(showInputDialog("Skriv inn et tall: "));

    for (int o = gange; o <= gange2; o++){
      for ( int i = 1; i <= 10; i++){
        System.out.println(o + " x " + i + " = " + o*i);
      }
    }

  }

}
