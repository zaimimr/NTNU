import static javax.swing.JOptionPane.*;

class ov9{

  static int antallp = 0;
  static int antalla = 0;
  static String[] alternativ = {"Ja", "Nei"};
  static String[] valg = {"Lag ny", "Vis Ansatt", "Endre"};
  static String[] endre = {"Maneds Lonn", "Skatte Prosent"};
  static String[] persL;
  static String[] arbL;
  static Person[] personliste = new Person[1];
  static ArbTaker[] arbliste = new ArbTaker[1];

  public static void lagPerson(){
    String fn;
    String en;

    do{
      fn = showInputDialog("Fornavn: ");
    }while(!(fn instanceof String));

    do{
      en = showInputDialog("Etternavn: ");
    }while(!(en instanceof String));

    int ar = skrivTall("FodselsAr: ");
    Person pers = new Person(fn, en, ar);

    personliste[antallp] = pers;
    antallp++;
    expandP();
  }

  public static void lagAnsatt(){

    fyllPL();
    int x  = showOptionDialog(null, "Velg person", "Person", DEFAULT_OPTION, PLAIN_MESSAGE, null, persL, persL[0]);

    if(!(x == personliste.length-1)){
      int asAr = skrivTall("Ansettelses Ar: ");
      int monlon = skrivTall("Moneds Lonn: ");
      int skatpro = skrivTall("Skatte prosent: ");
      ArbTaker a = new ArbTaker(personliste[x], asAr, monlon, skatpro);

      arbliste[antalla] = a;
      antalla++;
      expandA();
    }else{
      lagPerson();
    }

  }

  public static void expandP() {
    Person[] newArray = new Person[personliste.length + 1];
    System.arraycopy(personliste, 0, newArray, 0, personliste.length);
    personliste = newArray;
  }

  public static void expandA() {
    ArbTaker[] newArray = new ArbTaker[arbliste.length + 1];
    System.arraycopy(arbliste, 0, newArray, 0, arbliste.length);
    arbliste = newArray;
  }

  public static int skrivTall(String tekst){
    do{
      try{
        int tall = Integer.parseInt(showInputDialog(tekst));
        return tall;
      }catch(Exception e){}
    }while(true);
  }

  public static void fyllPL(){
    persL = new String[personliste.length];
    for (int i = 0; i < personliste.length-1; i++){
      persL[i] = personliste[i].getForNavn() + " " + personliste[i].getEtterNavn();
    }
    persL[personliste.length-1] = "Lag ny person";
  }

  public static void fyllAL(){
    arbL = new String[arbliste.length-1];
    for (int i = 0; i < arbliste.length-1; i++){
      arbL[i] = arbliste[i].formelNavn();
    }
  }

  public static int visAns(){
    fyllAL();
    return showOptionDialog(null, "Velg ansatt", "ansatt", DEFAULT_OPTION, PLAIN_MESSAGE, null, arbL, arbL[0]);
  }

  public static void endre(){
    int a = visAns();
    int e  = showOptionDialog(null, "Endre " + arbliste[a].formelNavn(), "Person", DEFAULT_OPTION, PLAIN_MESSAGE, null, endre, endre[0]);
    switch(e){
      case 0:
      arbliste[a].setManedsLonn(skrivTall("Sett ny manedslonn"));
      break;
      case 1:
      arbliste[a].setSkatteProsent(skrivTall("Sett ny skatte prosent"));
      break;
    }
  }

  public static void main(String[] args){
    boolean lagP = true;
    while(true){
      if(personliste[0] == null){
        do{
          int x  = showOptionDialog(null, "Vil du lage Person?", "Person", DEFAULT_OPTION, PLAIN_MESSAGE, null, alternativ, alternativ[0]);
          switch(x){
            case 0: lagPerson(); break;
            case 1: lagP = false; break;
            default: System.exit(0);
          }
        }while(lagP);
      }

      int x = showOptionDialog(null, "Ansatte", "Ansatt", DEFAULT_OPTION, PLAIN_MESSAGE, null, valg, valg[0]);
      switch(x){
        case 0:
        if(personliste[0] == null){
          break;
        }else{
          lagAnsatt();
          break;
        }
        case 1:
        if(arbliste[0] == null){
          showMessageDialog(null, "Det er ingen ansatt enda");
          break;
        }else{
          int a = visAns();
          showMessageDialog(null, "Navn: " + arbliste[a].formelNavn() + "\nAlder: " + arbliste[a].alder() + " ar" +
          "\nAnsatt nr: " + arbliste[a].getArbTakerNr() + "\nLonn: " + (arbliste[a].bruttoLonn() - arbliste[a].skatt()) + " kr");
          break;
        }
        case 2:
        if(arbliste[0] == null){
          showMessageDialog(null, "Det er ingen ansatt enda");
          break;
        }else{
          endre();
          break;
        }
        default: System.exit(0);
      }
    }
  }
}
