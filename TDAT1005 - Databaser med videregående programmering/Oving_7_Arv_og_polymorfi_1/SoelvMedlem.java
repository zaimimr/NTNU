import java.time.*;
class SoelvMedlem extends BonusMedlem{
  public SoelvMedlem(int medlNr, Personalia pers, LocalDate innmeldtDato, int poeng){
    super(medlNr, pers, innmeldtDato,poeng);
  }

    public int getMedlnr(){
      return super.getMedlnr();
    }

    public Personalia getPers(){
      return super.getPers();
    }


    public LocalDate getInnmeldtDato(){
      return super.getInnmeldtDato();
    }

    public int getPoeng(){
      return super.getPoeng();
    }

    public int finnKvalPoeng(LocalDate d1){
      return super.finnKvalPoeng(d1);
    }

    @Override
    public void registrerPoeng(int p){
      super.registrerPoeng((int)(p * 1.2));
    }
}
