 import static javax.swing.JOptionPane.*;
import java.util.*;
import java.io.*;
import java.io.IOException;
import java.io.Serializable;

class H2015 implements Serializable{
  public static Arrangement a = null;
  private static final String fil =  "arrangement.ser";
  public static void main(String[] args){
    //a = lesFraFil(fil);
    while(true){
      String[] alternativ = {"Lag nytt Arrangement", "Registrer ny idrett", "Avslutt"};
      int valg = showOptionDialog(null, "Valg", "valg", DEFAULT_OPTION, INFORMATION_MESSAGE,null, alternativ, alternativ[0]);
      switch(valg){
        case 0:
        if(a == null){
          a = new Arrangement("SL", 2);
        }else{
          int bekreftelse = showConfirmDialog(null, "Advarsel", "Advarsel" ,YES_NO_OPTION, INFORMATION_MESSAGE);
          if(bekreftelse == 0){
            a = null;
          }
        }
        break;
        case 1:
        if(a == null){
          showMessageDialog(null, "Arrangement eksistere ikke");
          break;
        }
        try{
          String navn = showInputDialog("Idrettens navn");
          int tall = Integer.parseInt(showInputDialog("Antall idretter"));
          Idrettsgren ig = new Idrettsgren(navn, tall);
          if(a.regNyIdrett(ig)){
            showMessageDialog(null, "registreringen gikk bra");
          }else{
            showMessageDialog(null, "registreringen gikk galt");
          }
        }catch(InputMismatchException | ClassCastException e){
          e.printStackTrace();
          showMessageDialog(null, "Input feil");
        }
        break;
        case 2:
          System.exit(0);
        break;
      }
    }
  }

  public static Arrangement lesFraFil(String filnavn){
    FileInputStream fr = null;
    ObjectInputStream ois = null;
    try{
      fr = new FileInputStream(filnavn);
      ois = new ObjectInputStream(fr);
      Arrangement ar = (Arrangement) ois.readObject();
      return ar;
    }catch(FileNotFoundException e){
      e.printStackTrace();
    }catch(IOException e){
      e.printStackTrace();
    }catch(ClassNotFoundException e){
      e.printStackTrace();
    }finally{
      try{
        fr.close();
        ois.close();
      }catch(IOException e){
        e.printStackTrace();
      }
    }
    return null;
  }
}

class Ovelse{
  private String beskrivelse;
  private String kjonn;

  public Ovelse(String beskrivelse, String kjonn){
    this.beskrivelse = beskrivelse;
    this.kjonn = kjonn;
  }

  public String getBeskrivelse(){
    return this.beskrivelse;
  }

  public void setBeskrivelse(String beskrivelse){
    this.beskrivelse = beskrivelse;
  }

  public String getKjonn(){
    return this.kjonn;
  }

  public void setKjonn(String kjonn){
    this.kjonn = kjonn;
  }

  public String toString(){
    return this.beskrivelse + " " + this.kjonn;
  }

  public boolean sjekkLike(Ovelse o){
    if(this.beskrivelse.equals(o.getBeskrivelse()) &&
    this.kjonn.toLowerCase().equals(o.getKjonn().toLowerCase())){
      return false;
    }
    return true;
  }
}

class Idrettsgren{
  private final String grenNavn;
  private final Ovelse[] ovelser;
  private int antallOvelser;

  public Idrettsgren(String navn, int maks){
    this.grenNavn = navn;
    this.ovelser = new Ovelse[maks];
    this.antallOvelser = 0;
  }

  public Idrettsgren(String navn, Ovelse[] ovelser){
    this.grenNavn = navn;
    this.ovelser = ovelser;
    this.antallOvelser = ovelser.length;
  }

  public String getGrenNavn(){
    return this.grenNavn;
  }

  public Ovelse[] getOvelser(){
    return this.ovelser;
  }

  public String toString(){
    String output = "";
    for(Ovelse o : ovelser){
      output += o.getBeskrivelse() + "\n";
    }
    return output;
  }

  public boolean regNyOvelse(Ovelse ovelse){
    //Sjekker om det er noe i ovelse
    if(ovelse == null){
      return false;
    }
    //Om det er plass til flere idretter
    if(ovelser.length - antallOvelser <= 0){
      return false;
    }
    //
    for(Ovelse o : ovelser){
      if(o != null){
        if(ovelse.sjekkLike(o)){
          return false;
        }
      }
    }
    ovelser[antallOvelser] = new Ovelse(ovelse.getBeskrivelse(), ovelse.getKjonn());
    antallOvelser++;
    return true;
  }
}

class Arrangement{
  private final String navn;
  private final Idrettsgren[] idretter;
  private int antallIdretter;

  public Arrangement(String navn, int maksIdretter){
    this.navn = navn;
    this.idretter = new Idrettsgren[maksIdretter];
    antallIdretter = 0;
  }

  public String toString(){
    String output = "";
    for(Idrettsgren ig : idretter){
      output += ig.getGrenNavn() + "\n";
      for(Ovelse o : ig.getOvelser()){
        output += o.getBeskrivelse() + "\n";
      }
      output += "\n";
    }
    return output;
  }

  public boolean regNyIdrett(Idrettsgren idrett){
    if(idrett == null){
      return false;
    }

    if(idretter.length - antallIdretter <= 0){
      return false;
    }
      for(Idrettsgren ig : idretter){
        if(ig != null){
          if(idrett.getGrenNavn().equals(ig.getGrenNavn())){
            return false;
          }
        }
      }
    idretter[antallIdretter] = new Idrettsgren(idrett.getGrenNavn(), idrett.getOvelser().length);
    antallIdretter++;
    return true;
  }
}
