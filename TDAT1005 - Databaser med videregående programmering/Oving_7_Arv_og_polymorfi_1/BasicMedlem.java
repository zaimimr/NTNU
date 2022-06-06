import java.time.*;
class BasicMedlem extends BonusMedlem{
  public BasicMedlem(int medlNr, Personalia pers, LocalDate innmeldtDato){
    super(medlNr, pers, innmeldtDato, 0);
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
      super.registrerPoeng(p);
    }
}
