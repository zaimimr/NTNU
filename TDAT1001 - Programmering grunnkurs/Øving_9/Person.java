
public class Person{

  private final String forNavn;
  private final String etterNavn;
  private final int fodeAr;

  public Person(final String fornavn, final String etternavn, final int ar){
    this.forNavn = fornavn;
    this.etterNavn = etternavn;
    this.fodeAr = ar;
  }

  public String getForNavn(){
    return this.forNavn;
  }
  public String getEtterNavn(){
    return this.etterNavn;
  }
  public int getFodeAr(){
    return this.fodeAr;
  }

}
