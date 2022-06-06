import java.io.*;
abstract class Tribune implements Serializable{
  private final String tribunenavn;
  private final int kapasitet;
  private final int pris;

  public Tribune(String navn, int kapasitet, int pris){
    if (navn == null || navn.trim().equals("")) {
      throw new IllegalArgumentException("Tribunenavn må oppgis.");
    }
    if (kapasitet <= 0 || pris <= 0){
      throw new IllegalArgumentException("Int må være over 0");
    }
    this.tribunenavn = navn.trim();
    this.kapasitet = kapasitet;
    this.pris = pris;
  }

  public String getTribunenavn() {
    return tribunenavn;
  }
  public int getKapasitet() {
    return kapasitet;
  }
  public int getPris() {
    return pris;
  }

  public abstract int finnAntallSolgteBilletter();

  public abstract int finnInntekt();

  public abstract Billett[] kjopBillett(int ant);
  public abstract Billett[] kjopBillett(String[] navn);

  public abstract int compareTo(Tribune t);

  public String toString(){
    return "Tribunenavn: " + tribunenavn + ", kapasitet: " + kapasitet + ", pris: " + pris;
  }


}
