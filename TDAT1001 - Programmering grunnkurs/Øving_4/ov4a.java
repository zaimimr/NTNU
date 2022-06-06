import static javax.swing.JOptionPane.*;

class ov4a{

static Valuta[] valutas = new Valuta[3];
static String[] valNavn = new String[4];
static String[] valg = {"Til NOK", "Fra NOK", "Avbryt"};

  public static void generateMenu(){
    valutas[0] = new Valuta("Dollar", 0.118929);
    valutas[1] = new Valuta("Euro", 0.102757622);
    valutas[2] = new Valuta("SEK", 1.08195961);
    valNavn[0] = valutas[0].getName();
    valNavn[1] = valutas[1].getName();
    valNavn[2] = valutas[2].getName();
    valNavn[3] = "Avslutt";
  }

  public static void kalkulasjon(){
    while(true){
       int x = showOptionDialog(null, "Velg Onsket valuta","Valuta",DEFAULT_OPTION, PLAIN_MESSAGE, null,valNavn,valNavn[0]);

       if(x==3) break;
         int y = showOptionDialog(null, "Onsket veksel","Veksle",DEFAULT_OPTION, PLAIN_MESSAGE, null,valg,valg[0]);
         double tall=0;

         if(y!=2){
           String in = showInputDialog("Veksle");

           if (in!=null){
             tall = Double.parseDouble(in);

            switch (y){
              case 0: showMessageDialog(null,valutas[x].convertToN(tall));
              continue;
              case 1: showMessageDialog(null,valutas[x].convertFromN(tall));
              continue;
            }
          }
        }
     }
  }

  public static void main(String[] args){
    generateMenu();
    kalkulasjon();
  }

}
