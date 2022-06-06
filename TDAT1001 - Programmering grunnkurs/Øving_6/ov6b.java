import static javax.swing.JOptionPane.*;

class ov6b{

  public static void main(String[] args){

    String test;
    Tekstanalyse tester;
    String bokstav;

    do{
      test = showInputDialog("Skriv inn en tekst");
      System.out.println(test);
      tester = new Tekstanalyse(test);
      System.out.println("Det er " + tester.antallUlike() + " ulike tegn i teksten.");
      System.out.println("Det er " + tester.antallBokstaver() + " bokstaver i hele teksten.");
      System.out.println("Det er " + tester.erIkkeBokst() + "% som ikke er bokstaver.");
      bokstav = showInputDialog("Velg en bokstav");
      System.out.println("Det er " + tester.antallGanger(bokstav) + " " + bokstav + "-er i teksten.");
//    System.out.println("Det er " + tester.flestGanger() + " som kommer flest ganger i teksten.");
      tester.flestGanger();
      System.out.println();

    }while(test != null);

  }

}
