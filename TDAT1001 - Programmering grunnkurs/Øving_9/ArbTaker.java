
public class ArbTaker{

  private Person person;
  private int arbTakerNr;
  private int ansettelseAr;
  private int manedsLonn;
  private int skatteProsent;
  static int nextAnsatt = 1;

  //java.util.GregorianCalendar kalender = new java.util.GregorianCalendar();
  //private int naAr = kalender.get(java.util.Calender.YEAR);
  private int naAr = 2018;
  public ArbTaker(Person pers,  int ansettAr, int monLonn, int skattPro){
    this.person = pers;
    this.arbTakerNr = nextAnsatt;
    this.ansettelseAr = ansettAr;
    this.manedsLonn = monLonn;
    this.skatteProsent = skattPro;
    nextAnsatt++;
  }

  public Person getPerson() {
    return this.person;
  }

  public int getArbTakerNr() {
    return this.arbTakerNr;
  }

  public int getAnsettelseAr() {
    return this.ansettelseAr;
  }

  public int getManedsLonn() {
    return this.manedsLonn;
  }

  public int getSkatteProsent() {
    return this.skatteProsent;
  }

  public void setManedsLonn(int manedsLonn) {
    this.manedsLonn = manedsLonn;
  }

  public void setSkatteProsent(int skatteProsent) {
    this.skatteProsent = skatteProsent;
  }

  public double skatt(){
    return manedsLonn*skatteProsent/100;
  }

  public int bruttoLonn(){
    return manedsLonn*12;
  }

  public double nettoLonn(){
    return ((skatt()*10) + (skatt()/2));
  }

  public String formelNavn(){
    return person.getEtterNavn() + ", " + person.getForNavn();
  }

  public int alder(){
    return naAr - person.getFodeAr();
  }

  public int arAnsatt(){
    return naAr - ansettelseAr;
  }

  public boolean ansattLengerenn(int ar){
    return ansettelseAr > ar;
  }

}
