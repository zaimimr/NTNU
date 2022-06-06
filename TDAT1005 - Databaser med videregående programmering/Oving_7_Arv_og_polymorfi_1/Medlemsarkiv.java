import java.util.*;
import java.time.*;
class Medlemsarkiv{

  public ArrayList<BonusMedlem> medlemmer;

  public Medlemsarkiv(){
    medlemmer = new ArrayList<>();
  }

  public int finnPoeng(int medlNr, String passord){
    if(medlemmer.isEmpty()) return -1;
    for(BonusMedlem bm : medlemmer){
      if(bm.getMedlnr() == medlNr && bm.getPers().okPassord(passord)){
        return bm.getPoeng();
      }
    }
    return -1;
  }

  public boolean registrerPoeng(int medlNr, int poeng){
    if(medlemmer.isEmpty()) return false;
    for(BonusMedlem bm : medlemmer){
      if(bm.getMedlnr() == medlNr){
        bm.registrerPoeng(poeng);
        return true;
      }
    }
    return false;
  }

  public int nyMedlem(Personalia pers, LocalDate innmeldt){
    int medNr = finnLedigNr();
    medlemmer.add(new BasicMedlem(medNr, pers, innmeldt));
    return medNr;
  }

  private int finnLedigNr(){
    int tilfTall = 0;
    boolean funnet = true;
    do{
      funnet = false;
      tilfTall = (int) (Math.random() * 1000) + 1;
      for(BonusMedlem bm : medlemmer){
        if(bm.getMedlnr() == tilfTall){
          funnet = true;
        }
      }
    }while(funnet);
    return tilfTall;
  }

  public boolean sjekkMedlemmer(){
    BonusMedlem bm = null;
    int bmPoeng = 0;
    LocalDate date = LocalDate.of(2008, 2, 11); //LocalDate.now();
    for(int i = 0; i < medlemmer.size(); i++){
      bm = medlemmer.get(i);
      bmPoeng = bm.finnKvalPoeng(date);
      if(bmPoeng >= 75000 && (bm instanceof BasicMedlem || bm instanceof SoelvMedlem)){
        medlemmer.set(i,new GullMedlem(bm.getMedlnr(), bm.getPers(), bm.getInnmeldtDato(), bm.getPoeng()));
      }else if(bmPoeng >= 25000 && bm instanceof BasicMedlem){
        medlemmer.set(i,new SoelvMedlem(bm.getMedlnr(), bm.getPers(), bm.getInnmeldtDato(), bm.getPoeng()));
      }
    }
    return true;
  }

  public static void main(String[] args) {
    Medlemsarkiv m = new Medlemsarkiv();

    Personalia ole = new Personalia("Olsen", "Ole", "ole.olsen@dot.com", "ole");
    Personalia tove = new Personalia("Hansen", "Tove", "tove.hansen@dot.com", "tove");
    LocalDate testdato = LocalDate.of(2008, 2, 10);

    int med1 = m.nyMedlem(ole, testdato);
    int med2 = m.nyMedlem(tove, testdato);

    boolean test1 = false;
    boolean test2 = false;

    if(m.medlemmer.get(0) instanceof BasicMedlem && m.medlemmer.get(1) instanceof BasicMedlem){
      System.out.println("Test 1 fullført");
      test1 = true;
    }

    m.registrerPoeng(med1, 25001);
    m.registrerPoeng(med2, 75001);

    m.sjekkMedlemmer();

    if(m.medlemmer.get(0) instanceof SoelvMedlem && m.medlemmer.get(1) instanceof GullMedlem){
      System.out.println("Test 2 fullført");
      test2 = true;
    }
    if(test1 && test2){
      System.out.println("Det fungerer!!");
    }
  }
}
