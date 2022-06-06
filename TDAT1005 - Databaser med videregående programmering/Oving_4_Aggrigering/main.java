import static javax.swing.JOptionPane.*;

class main {
  private static Konferansesenter ks;
  public static void main(String[] args) {
    ks = new Konferansesenter();

    String[] option = {"Registrer rom", "Reserver rom", "toString", "Finn Rom", "Avslutt"};

    while(true){
      int valg = showOptionDialog(null, "Velg ønsket operasjon", "Valg", DEFAULT_OPTION,  INFORMATION_MESSAGE, null, option, option[0]);

      switch(valg){
        case 0:
          int nr = Integer.parseInt(showInputDialog("Skriv inn rom nr"));
          int str = Integer.parseInt(showInputDialog("Størrelse på rommet"));
          if(ks.registrerRom(nr, str)){
            showMessageDialog(null, "Registrering fullført");
            System.out.println(ks.toString());
          }else{
            showMessageDialog(null, "Registrering misslykket");
          }
        break;
        case 1:
          if(ks.antallRom() < 0){
            showMessageDialog(null, "Ingen registrerte rom");
            break;
          }
          long time1 = Long.parseLong(showInputDialog("Fra tid: yyyymmddttmm"));
          long time2 = Long.parseLong(showInputDialog("Til tid: yyyymmddttmm"));
          int antall = Integer.parseInt(showInputDialog("Antall som skal bruke rommet"));
          String navn = showInputDialog(null, "Kundens navn");
          String tlf = showInputDialog(null, "Kundens telefonnr");
          if(ks.reserverRom(new Tidspunkt(time1), new Tidspunkt(time2), antall, new Kunde(navn, tlf))){
            showMessageDialog(null, "Reservering av rom fullfort");
          }else{
            showMessageDialog(null, "Reservering misslykket");
          }
        break;
        case 2:
          if(ks.antallRom() < 0){
            showMessageDialog(null, "Ingen registrerte rom");
            break;
          }
          showMessageDialog(null,ks.toString());
        break;
        case 3:
          if(ks.antallRom() < 0){
            showMessageDialog(null, "Ingen registrerte rom");
            break;
          }
          int index = Integer.parseInt(showInputDialog("Finn rom på romnr"));
          showMessageDialog(null,ks.romNr(index).toString());
        break;
        case 4:
          System.exit(0);
        break;
        default:
          System.exit(0);
        break;
      }
    }
  }
}
