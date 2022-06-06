import static javax.swing.JOptionPane.*;

  class ov4b{

    static Spiller[] spillere;
    static boolean erFerdig = false;

    public static void genererSpillere(){
      int antall = Integer.parseInt(showInputDialog("Velg antall spillere"));
      spillere = new Spiller[antall];

      for (int i = 0; i < antall; i++){
        spillere[i] = new Spiller();
      }
    }

    public static void spillet(){
      int teller = 0;
      while(!erFerdig){

          for (int s = 0; s < spillere.length;s++){

              if(spillere[s].getSumPoeng() < 100){
                spillere[s].setSumPoeng(spillere[s].kastTerning());
              } else{
                spillere[s].setSumPoeng(-spillere[s].kastTerning());
              }

              teller++;
              if(spillere[s].erFerdig() == true){
                erFerdig = true;
                javax.swing.JOptionPane.showMessageDialog(null,"Vinneren er " + spillere[s].getID() + " med " + spillere[s].getSumPoeng() +  " poeng, med antall runder_ " + teller);
                break;
              }

          }
      }
    }

      public static void main(String[] args){
        genererSpillere();
        spillet();
      }

  }
