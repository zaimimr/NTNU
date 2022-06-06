import java.io.*;
import java.io.Serializable;
import static javax.swing.JOptionPane.*;
import java.util.*;
import java.lang.*;

class V2015 implements Serializable{
  public static void main(String[] args){
    Mobil mob = null;
    String[] alternativ = {"Ny Mobil", "Registrer Visittkort", "Vis info", "Avslutt"};
    while(true){
    int valg = showOptionDialog(null, "Menu", "Menu", DEFAULT_OPTION, INFORMATION_MESSAGE, null, alternativ, alternativ[0]);
    switch(valg){
      case 0:
      try{
        if(mob == null){
          String modell = showInputDialog("Modell?");
          String tlfnr = showInputDialog("Mobilnr?");
          int max = Integer.parseInt(showInputDialog("Maks kontakter"));
          mob = new Mobil(modell, tlfnr, max);
        }else{
          int ja = showConfirmDialog(null, "Advarsel", "Advarsel", YES_NO_OPTION);
          if(ja == 0){
            mob = null;
          }else{
            break;
          }
        }
        }catch(InputMismatchException e){
          e.printStackTrace();
        }catch(NullPointerException e){
          e.printStackTrace();
        }
      break;
      case 1:
      try{
        if(mob != null){
        String fnavn = showInputDialog("ForNavn");
        String enavn = showInputDialog("EtterNavn");
        String tlfM = showInputDialog("tlfMobil");
        String tlfJ = showInputDialog("TlfJobb");
        String ep = showInputDialog("Epost");
        if(mob.regVisittkort(new Visittkort(new Navn(fnavn, enavn), tlfM, tlfJ, ep))){
          showMessageDialog(null, "Registrering gikk bra");
        }else{
          showMessageDialog(null, "Registreing gikk ikke");
        }
      }else{
        showMessageDialog(null, "Mobil eksisterer ikke");
      }
      }catch(InputMismatchException e){
        e.printStackTrace();
      }catch(NullPointerException e){
        e.printStackTrace();
      }catch(Exception e){
        e.printStackTrace();
      }
      break;
      case 2:
      if(mob != null){
        String show = mob.toString();
        if(show != null){
          showMessageDialog(null, show);
        }else{
          showMessageDialog(null, "Ingenting å vise");
        }
      }
      break;
      default:
      System.exit(0);
      break;
    }
  }
  }

  public static Mobil lesFraFil(String filnavn){
    FileInputStream fis = null;
    ObjectInputStream ois = null;
    try{
      fis = new FileInputStream(filnavn);
      ois = new ObjectInputStream(fis);
      Mobil mob = (Mobil)ois.readObject();
      return mob;
    }catch(FileNotFoundException e){
      e.printStackTrace();
      return null;
    }catch(IOException e){
      e.printStackTrace();
      return null;
    }catch(ClassNotFoundException e){
      e.printStackTrace();
      return null;
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

class Navn implements Serializable{
  private final String forNavn;
  private final String etterNavn;

  public Navn(String fornavn, String etternavn){
    this.forNavn = fornavn;
    this.etterNavn = etternavn;
  }
  public String getForNavn(){
    return this.forNavn;
  }
  public String getEtterNavn(){
    return this.etterNavn;
  }

  public String toString(){
    return this.etterNavn + ", " + this.forNavn;
  }

  public boolean equals(Object obj)

  public boolean equals(Object obj){
    if(obj == null){return false;}
    if(!(obj instanceof Navn)){return false;}
    if(this == obj){return true;}

    Navn buffer = (Navn) obj;

    return (buffer.getForNavn().toLowerCase().equals(this.forNavn.toLowerCase()) &&
    buffer.getEtterNavn().toLowerCase().equals(this.etterNavn.toLowerCase()));
  }

  public int compareTo(Navn n){
    return toString().toLowerCase().compareTo(n.toString().toLowerCase());
  }
}

class Visittkort implements Serializable{
  private Navn navn;
  private String tlfMobil;
  private String tlfJobb;
  private String epost;

  public Visittkort(Navn navn, String tlfMob, String tlfJobb, String epost){
    this.navn = new Navn(navn.getForNavn(), navn.getEtterNavn());
    this.tlfMobil = tlfMob;
    this.tlfJobb = tlfJobb;
    this.epost = epost;
  }

  public Navn getNavn(){
    return this.navn;
  }

  public String getTlfMobil(){
    return this.tlfMobil;
  }

  public String getTlfJobb(){
    return this.tlfJobb;
  }

  public String getEpost(){
    return this.epost;
  }
  
  public boolean equals(Object obj){
		if (this == obj) return true;

    if (obj instanceof Visitkort){
			Visitkort vk = (Visitkort)obj;
			if (vk.getNavn().equals(getNavn())) return true; // lik dersom navn er lik
		}
		return false;
	}

  public String toString(){
    String output = navn.toString() + "\n";
    output += "Mobiltlf: " + this.tlfMobil + "\n";
    output += "Tlf jobb: " + this.tlfJobb + "\n";
    output += "Epost: " + this.epost;
    return output;
  }
}

class Mobil implements Serializable{
  private String modell;
  private String tlf;
  private final Visittkort[] liste;
  private int antallVisittkort;

  public Mobil(String modell, String tlf, int maks){
    this.modell = modell;
    this.tlf = tlf;
    this.liste = new Visittkort[maks];
    antallVisittkort = 0;
  }

  public String toString(){
    if(liste == null){return null;}
    String output = this.modell +", " + this.tlf + "\n";
    for(Visittkort vk : liste){
      if(vk != null){
        output+= vk.toString() + "\n";
        output+= "-------------------------- \n";
      }
    }
    return output;
  }

  public boolean regVisittkort(Object obj){
    if(obj == null){
      return false;
    }
    if(!(obj instanceof Visittkort)){
      return false;
    }
    if(liste.length <= antallVisittkort){
      return false;
    }
    Visittkort vk = (Visittkort) obj;
    for(Visittkort v : liste){
      if(v.equals(vk)){
        return false;
      }
    }

    liste[antallVisittkort] = new Visittkort(vk.getNavn(), vk.getTlfMobil(), vk.getTlfJobb(), vk.getEpost());
    return true;
  }

  public Visittkort[] sorter(){
    if(liste == null){return null;}
    if(antallVisittkort < 1){return null;}
    Visittkort[] kopi = new Visittkort[liste.length];

    for(int i = 0; i < antallVisittkort; i++){
      kopi[i] = new Visittkort(liste[i].getNavn(), liste[i].getTlfMobil(), liste[i].getTlfJobb(), liste[i].getEpost());
    }

    if(antallVisittkort == 1){return kopi;}
    int minIndex = 0;
    for(int i = 0; i < kopi.length - 1; i++){
       minIndex = i;
      for(int j = i+1; j < kopi.length; j++){
        if(kopi[minIndex].getNavn().compareTo(kopi[j].getNavn()) > 0){
          minIndex = j;
        }
      }
      if(minIndex != i){
        Visittkort buffer = kopi[i];
        kopi[i] = kopi[minIndex];
        kopi[minIndex] = buffer;
      }
    }
    return kopi;
  }
}
