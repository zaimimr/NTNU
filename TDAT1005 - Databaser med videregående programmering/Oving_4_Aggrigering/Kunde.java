/**
 * Kunde.java
 * Inneholder kundedata.
 */
class Kunde {
  private final String navn;
  private final String tlf;

  public Kunde(String navn, String tlf) {
    if (navn == null || navn.trim().equals("")) {
      throw new IllegalArgumentException("Navn må oppgis.");
    }
    if (tlf == null || tlf.trim().equals("")) {
      throw new IllegalArgumentException("Tlf må oppgis.");
    }
    this.navn = navn.trim();
    this.tlf = tlf.trim();
  }

  public String getNavn() {
    return navn;
  }

  public String getTlf() {
    return tlf;
  }

  public String toString() {
    return navn + ", tlf " + tlf;
  }
}
