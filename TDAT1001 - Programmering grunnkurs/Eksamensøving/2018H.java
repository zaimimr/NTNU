//siden det skal leses til fil, må classen være serializabe
class Soppart implements java.io.Serializable{ //ikke nødvendig, mer for min egen del

  //Klassens immutable objektvariabler
  private final String navn;
  private final String beskrivelse;
  private final boolean giftig;

  //klassens ene konstrultør, med objektvariabel som parameter
  public Soppart(String navn, String beskrivelse, boolean giftig){
    this.navn = navn;
    this.beskrivelse = beskrivelse;
    this.giftig = giftig;
  }

  //Klassens andre konstruktør, med Soppart objekt som parameter.
  //Bruker parameter sine tilgangsmetoder til å tildele this sine
  //variabler en verdi lik parameter-objektet
  public Soppart(Soppart sopp){
    this.navn = sopp.getNavn();
    this.beskrivelse = sopp.getBeskrivelse();
    this.gifitg = sopp.getGiftighet();
  }

  public String getNavn(){
    return this.navn;
  }

  public String getBeskrivelse(){
    return this.beskrivelse;
  }

  public boolean getGiftighet(){
    return this.giftig;
  }

  //equals metode som sjekker om to objekter er like, basert på deres navn
  public boolean equals(Object obj){
    //hvis objektet er lik dette objektet er de like
    if(obj == this) return true;
    //hvis objektet ikke er av klassetypen sopp er de ikke like
    if(!(obj instanceof Soppart)) return false;
    //caster objektet til Soppart og bruker .equals()-metoden for å sammenligne navn stingen
    Soppart sopp = (Soppart) obj;
    return this.navn.equals(sopp.getNavn());
  }

  //Første tanke: Sjekker om sokestringen er lik en av setningene i beskrivelsen ved å splitte beskrivelsen for hvert "." og sammenligner med .equals() metode
  /*
  public boolean sokIBeskrivelse(String sokeString){
  String[] setning = this.beskrivelse.split(".");
  for(String s : setning){
  if(sokeString.equals(s)) return true;
}
return false;
}
*/

//Etter spm til faglærer ble oppgaven oppklart ved at søkestrengen er hele beskrivelsen, så sammenligner med om den er lik beskrivelsen. Antar at store/små bokstaver er like for sokestreng og beskrivelse.
public boolean sokIBeskrivelse(String sokeString){
  if(sokeString.equals(this.beskrivelse)) return true;
  return false;
}
//Tostring for finere kode i soppregister klassen, ikke en del av oppgaven
public String toString(){
  String output = this.navn + " " + this.beskrivelse + " ";
  if(this.giftig){
    outut += "Giftig\n";
  }else{
    output += "Matsopp\n";
  }
  return output;
}

}
//siden det skal leses til fil, må classen være serializabe
class Soppregister implements java.io.Serializable{
  private Soppart[] sopper;
  //definerer en int = 10, utifra informasjon gitt i oppgavetekst
  private final int utvid = 10;
  //oppretter en int for å holde orden over antall registrerte
  private int antallRegistrert;

  public Soppregister(){
    this.sopper = new Soppart[utvid];
    this.antallRegistrert = 0;
  }

  //Siden det står i oppgaveteksten at det skal både leses og skrives til fil, kan man definere en ny Soppregister objekt med en tidligere liste
  //Velger komposisjon som beskrevet tidligere og derfor tar en dyp kopi av listen for å ha mest mulig kontroll
  /*
  public Soppregister(Soppart[] liste){
  this.sopper = new Soppart[liste.length];
  for(int i = 0; i < liste.length; i++){
  this.sopper[i] = new Soppart(liste[i].getNavn(), liste[i].getBeskrivelse(), liste[i].getGiftighet())
}
this.antallRegistrert = liste.length;
}
*/

public boolean registrerNySopp(Soppart sopp){
  //sjekker om objektet er null;
  if(sopp == null) return false;
  //Sjekker om listen er lang nok, hvis ikke -> utvid med 10;
  //hvis det er ingenting i listen, bare legg til;
  if(antallRegistrerte == 0) return leggTil(sopp);
  //bruker for:each loop til å gå gjennom listen, sjekker om den er lik en annen sopp i listen
  for(Soppart s : sopper){
    if(s != null){
      if(sopp.equals(s)){
        return false;
      }
    }else{
      break;
    }
  }
  //utvidListe er egenmetode
  if(antallRegistrerte == sopper.length) utvidListe();
  //ettersom den har bestått alle testene legger jeg den inn i listen.
  return leggTil(sopp);
}

public void utvidListe(){
  Soppart[] kopi = new Soppart[sopper.length + this.utvid];
  for(int i = 0; i < antallRegistrert; i++){
    kopi[i] = sopper[i];
  }
  sopper = kopi;
}

public boolean leggTil(Soppart sopp){
  sopper[antallRegistrert] = new Soppart(sopp.getNavn(), sopp.getBeskrivelse(), sopp.getGiftighet());
  antallRegistrert++;
  return true;
}

public Soppart[] spiseligSopper(){
  //velger å definere en like stor liste som den opprinnelige listen, fordi det er mer tungt for maskinen å utvide listen, enn å ha en liste med tomme plasser.
  Soppart[] spiselig = new Soppart[antallRegistrert];
  int spiseligRegistrert = 0;

  for(int i = 0; i < antallRegistrert; i++){
    //siden jeg definerte giftighet basert på true/false, vil false gi verdi at den er spiselig
    if(!(sopper[i].getGiftighet)){
      spiselig[spiseligRegistrert] = new Soppart(sopper[i].getNavn(), sopper[i].getBeskrivelse(), sopper[i].getGiftighet());
      spiseligRegistrert++;
    }
  }
  return trimListe(spiselig, spiseligRegistrert);
}
//trimmer bort de tomme plassene i listen, slik at metoden over spiseligSopp returnerer en full liste
public Soppart[] trimListe(Soppart[] spiseligListe, int reg){
  Soppart[] kopi = new Soppart[reg-1];
  for(int i = 0; i < reg; i++){
    kopi[i] = spiseligListe[i];
  }
  return kopi;
}

public String toString(){
  String output = "Registrerte Sopparter (Navn Beskrivelse Spiselig): \n";
  for(int i = 0; i < antallRegistret; i++){
    output += (i+1) + ": ";
    //Opprettet en toString i classen Soppart (Side 2, og nederst av siden)
    output += sopper[i].toString();
  }
  return output;
}

public String sokeIBeskrivelse(String sokeString){
  String output = "Sopper med matchende beskrivelse:\n";
  int teller = 1;
  for(int i = 0; i < antallRegistret; i++){
    if(sopper[i].sokIBeskrivelse(sokeString)){
      output += teller + ": ";
      output += sopper[i].toString();
      teller++;
    }
  }
  return output;
}
}

class 2018H{

  public static void main(String[] args){

    String filnavn = "soppregister.ser";
    Soppregister register = lesRegFraFil(filnavn);
    if (register == null){
      register = opprettNyttRegister();   // metode som opppretter ett tomt register
    }

    String[] muligheter = {"List alle", "List matsopper", "Legg til ny", "Søk", "Avslutt"};
    final int LIST_ALLE = 0;
    final int LIST_MATSOPPER = 1;
    final int REG_SOPP = 2;
    final int SOK = 3;
    final int AVSLUTT = 4;

    int valg = showOptionDialog(null, "Velg", "Eksamen des 2018",  YES_NO_OPTION,              INFORMATION_MESSAGE, null, muligheter, muligheter[0]);
    while (valg != AVSLUTT){
      switch (valg){
        case LIST_ALLE:
        /*Anta at koden eksisterer*/
        break;

        case LIST_MATSOPPER:
        /*Anta at koden eksisterer*/
        break;

        case REG_SOPP:
        String navn = showInputDialog("Navn til soppen");
        String beskrivelse = showInputDialog("Legg til beskrivelse");
        boolean giftig;
        int valg = showConfirmDialog(null, "Er soppen giftig", "Eksamen 2018", YES_NO_OPTION);
        if(valg == 0){
          giftig = true;
        }else{
          giftig = false;
        }
        if(register.registrerNySopp(new Soppart(navn, beskrivelse, giftig))){
          showMessageDialog(null, "Registrering var en suksess");
        }else{
          showMessageDialog(null, "Skjedde en feil i registrering");
        }
        break;

        case SOK:
        String sokeString = showInputDialog("Vennligst oppgi onsket sokestring");
        String resultat = register.sokIBeskrivelse(sokeString);
        showMessageDialog(null, resultat);
        break;

        default: break;
      }
      valg = showOptionDialog(null, "Velg", "Eksamen des 2018", YES_NO_OPTION, INFORMATION_MESSAGE, null, muligheter, muligheter[0]);
    }
    skrivRegTilfil(filnavn,register);
  }
  public Soppregister lesRegFraFil(String filNavn){
    java.io.FileInputStream fis = null;
    java.io.ObjectInputStream ois = null;
    try{
      fis = new FileInputStream(filNavn);
      ois = new ObjectInputStream(fis);
      Soppregister register = (Soppregister) ois.readObject();
      return register;
    }catch(FileNotFoundException e){
      e.printStackTrace();
      return null;
    }
    catch(IOException e){
      e.printStackTrace();
      return null;
    }
    catch(NullPointerException e){
      e.printStackTrace();
      return null;
    }catch(ClassNotFoundException e){
      e.printStackTrace();
    }finally{
      try{
        fis.close();
        ois.close();
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }
}
