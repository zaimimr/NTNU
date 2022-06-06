import java.time.*;
class BonusMedlem{
  private final int medlNr;
  private final Personalia pers;
  private final LocalDate innmeldtDato;
  private int poeng = 0;

  public BonusMedlem(int medlNr, Personalia pers, LocalDate innmeldtDato, int poeng){
    this.medlNr = medlNr;
    this.pers = pers;
    this.innmeldtDato = innmeldtDato;
    this.poeng = poeng;
  }

  public int getMedlnr(){
    return this.medlNr;
  }
  public Personalia getPers(){
    return this.pers;
  }

  public LocalDate getInnmeldtDato(){
    return this.innmeldtDato;
  }

  public int getPoeng(){
    return this.poeng;
  }

  public int finnKvalPoeng(LocalDate d1){
    int aarMellom = Period.between(innmeldtDato,d1).getYears();
    int dagerMellom = Period.between(innmeldtDato,d1).getDays();
    dagerMellom += aarMellom * 365;
    if(dagerMellom < 365){
      return this.poeng;
    }
    return 0;
  }

  public boolean okPassord(String passord){
    return pers.okPassord(passord);
  }

  public void registrerPoeng(int p){
    this.poeng += p;
  }

}
