import java.io.*;
class StaaTribune extends Tribune implements Serializable{
  private int antSolgteBilletter;

  public StaaTribune(String navn, int kapasitet, int pris){
    super(navn, kapasitet, pris);
    antSolgteBilletter = 0;
  }

  public int finnAntallSolgteBilletter(){
    return antSolgteBilletter;
  }

  public int finnInntekt(){
    return finnAntallSolgteBilletter() * super.getPris();
  }

  public Billett[] kjopBillett(int billetterKjop){
    if(super.getKapasitet() - finnAntallSolgteBilletter() < billetterKjop){
      return null;
    }
    Billett[] billetter = new Billett[billetterKjop];
    for(int i = 0; i < billetter.length; i++){
      billetter[i] = new StaaplassBillett(super.getTribunenavn(), super.getPris());
      antSolgteBilletter++;
    }
    return billetter;
  }

  public Billett[] kjopBillett(String[] navn){
    return kjopBillett(navn.length);
  }

  public int compareTo(Tribune t){
    return Integer.compare(finnInntekt(), t.finnInntekt());
  }

  public String toString(){
    return super.toString() + ", antall solgte billetter: " + finnAntallSolgteBilletter() + ", inntekt: " + finnInntekt();
  }
}
