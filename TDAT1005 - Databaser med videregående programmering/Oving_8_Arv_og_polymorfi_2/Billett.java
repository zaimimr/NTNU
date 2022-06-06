/**
 * Klassen Billett med subklasser - 2010-01-18
 * Denne blir delt ut sammen med øvingen
 */
abstract class Billett {
  private final String tribunenavn;
  private final int pris;

  /**
   * Konstruktør:
   * Tribunenavn må oppgis. Ingen krav til pris.
   */
  public Billett(String tribunenavn, int pris) {
    if (tribunenavn == null || tribunenavn.trim().equals("")) {
      throw new IllegalArgumentException("Tribunenavn må oppgis.");
    }
    this.tribunenavn = tribunenavn.trim();
    this.pris = pris;
  }

  public String getTribune() {
    return tribunenavn;
  }

  public int getPris() {
    return pris;
  }

  public String toString() {
    return "Tribune: "+tribunenavn + " Pris: "+pris;
  }
}
