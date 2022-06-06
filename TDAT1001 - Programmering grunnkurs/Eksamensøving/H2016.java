import static javax.swing.JOptionPane.*;
import java.io.*;
import java.io.Serializable;
import java.util.*;

class H2016{

  public static void main(String[] args){
    String[] muligheter = {"Nytt firma", "Reg Bil", "Avslutt"};
    int valg = showOptionDialog(null, "Velg operasjon", "Eksamen høst 2016",
    YES_NO_OPTION, INFORMATION_MESSAGE, null, muligheter, muligheter[0]);
    String filnavn = "boom";
    UtleieFirma utleieFirma = null;//lesUtleieFirmaFraFil(filnavn);
    while (valg != 2){
      switch (valg){
        case 0:
        // skal ikke fylles ut
        break;
        case 1:
        if (utleieFirma != null){
          String regnr = showInputDialog("Registreingsnummer");
          String motnr = showInputDialog("Motnr");
          String girkasse = showInputDialog("Girkasse");
          String type = showInputDialog("Type");
          String merke = showInputDialog("Merke");
          String dim = showInputDialog("Dimensjon");
          String dektyp = showInputDialog("Type");
          if(utleieFirma.regNyBil(new Bil(regnr, new Motor(motnr, girkasse, type), new Hjul(merke, dim, dektyp)))){
            showMessageDialog(null, "registret");
            break;
          }else{
            showMessageDialog(null, "Feil i registrering");
            break;
          }

        }else{
          showMessageDialog(null,"Ingen firma registret");
        }
        break;
        case 2:
        if (utleieFirma != null){}// skal ikke fylles ut }
        break;
        case 3:
        if (utleieFirma != null){} // skal ikke fylles ut }
        break;
        case 4:
        if (utleieFirma != null){}// skal ikke fylles ut}
        default: break;
      }
      valg = showOptionDialog(null,"Velg operasjon","Eksamen høst 2016",
      DEFAULT_OPTION, PLAIN_MESSAGE, null, muligheter, muligheter[0]);
    }
  }

  public static UtleieFirma lesUtleieFirmaFraFil(String filnavn){
    FileInputStream fis = null;
    ObjectInputStream ois = null;
    try{
      fis = new FileInputStream(filnavn);
      ois = new ObjectInputStream(fis);
      UtleieFirma buffer = (UtleieFirma) ois.readObject();
      return buffer;
    }catch(IOException e){
      e.printStackTrace();
    }catch(ClassNotFoundException e){
      e.printStackTrace();
    }catch(SecurityException e){
      e.printStackTrace();
    }catch(NullPointerException e){
      e.printStackTrace();
    }finally{
      try{
        fis.close();
        ois.close();
      }catch(IOException e){
        e.printStackTrace();
      }
    }
    return null;
  }
}

class Hjul implements Serializable{
  private final String merke;
  private final String dim;
  private final String type;

  public Hjul(String merke, String dim, String type){
    this.merke = merke;
    this.dim = dim;
    this.type = type;
  }

  public String getMerke(){
    return this.merke;
  }

  public String getDim(){
    return this.dim;
  }

  public String getType(){
    return this.type;
  }

  public String toString(){
    return "Merke: " + this.merke + ", Dimensjon: " + this.dim + ", Dekktype: " + this.type;
  }
}

class Motor implements Serializable{
  private final String motornr;
  private final String gir;
  private final String type;

  public Motor(String motornr, String gir, String type){
    this.motornr = motornr;
    this.gir = gir;
    this.type = type;
  }

  public String getMotorNr(){
    return this.motornr;
  }

  public String getGir(){
    return this.gir;
  }

  public String getType(){
    return this.type;
  }

  public String toString(){
    return "Motornr:" + this.motornr + ", Girkasse: " + this.gir + ", Motortype: " + this.type;
  }

  public boolean equals(Object obj){
    if(!(obj instanceof Motor)) return false;
    if(obj == this) return true;
    Motor m = (Motor) obj;
    return (this.motornr.equals(m.getMotorNr()));
  }
}

class Bil implements Serializable{
  private String regnr;
  private Motor motor;
  private Hjul hjuler;

  public Bil(String reg, Motor motor, Hjul hjul){
    this.regnr = reg;
    this.motor = new Motor(motor.getMotorNr(), motor.getGir(), motor.getType());
    this.hjuler = hjul;
  }
  /*
  Ville laget en liste med hjul og sjekket om det er 2 eller 1 lik hjul
  i listen fra før av ved hjelp av en equals.
  I kontroktøeren lager vi en liste med 4 i lengde og fyller inn med hjul.
  */

  public String getRegNr(){
    return this.regnr;
  }

  public Motor getMotor(){
    return new Motor(this.motor.getMotorNr(), this.motor.getGir(), this.motor.getType());
  }

  public Hjul getHjul(){
    return this.hjuler;
  }

  public String toString(){
    return "Registreingsnummer: " + this.regnr + ",\nMotor: " + this.motor.toString() + ",\nHjuler: " + this.hjuler.toString();
  }

  public boolean equals(Object obj){
    if(!(obj instanceof Bil)) return false;
    if(obj == this) return true;
    Bil b = (Bil) obj;
    return (this.regnr.equals(b.getRegNr()) && this.motor.equals(b.getMotor()));
  }

  public int compareTo(Bil b){
    return this.regnr.compareTo(b.getRegNr());
  }
}

class UtleieFirma implements Serializable{
  private String navn;
  private final Bil[] biler;
  private int antallBiler;

  public UtleieFirma(String navn, int maks){
    this.navn = navn;
    this.biler = new Bil[maks];
    this.antallBiler = 0;
  }
  public boolean regNyBil(Bil b){
    if(b == null) return false;
    if(antallBiler == biler.length) return false;
    for(Bil bil : biler){
      if(bil != null){
        if(b.equals(bil)){
          return false;
        }
      }else{
        break;
      }
    }
    biler[antallBiler] = new Bil(b.getRegNr(), b.getMotor(), b.getHjul());
    antallBiler++;
    return true;
  }

  public Bil[] sorter(){
    if(antallBiler <= 0) return null;
    Bil[] kopi = new Bil[biler.length];

    for(int i = 0; i < antallBiler; i++){
      kopi[i] = new Bil(biler[i].getRegNr(), biler[i].getMotor(), biler[i].getHjul());
    }

    if(antallBiler == 1) return kopi;

    int minIndex = 0;
    for(int i = 0; i < antallBiler - 1; i++){
      minIndex = i;
      for(int j = i+1; j < antallBiler; j++){
        if(kopi[minIndex].compareTo(kopi[j]) > 0) minIndex = j;
      }
      if(minIndex != i){
        Bil buffer = kopi[minIndex];
        kopi[minIndex] = kopi[i];
        kopi[i] = buffer;
      }
    }
    return kopi;
  }
}
