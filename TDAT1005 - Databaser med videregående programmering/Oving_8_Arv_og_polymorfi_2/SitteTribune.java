import java.io.*;
class SitteTribune extends Tribune implements Serializable{
  private int[] antOpptatt;
  public SitteTribune(String navn, int kapasitet, int pris, int antallRad){
    super(navn, kapasitet, pris);
    antOpptatt = new int[antallRad];
  }

  public int finnAntallSolgteBilletter(){
    int antallBilletter = 0;
    for(int i : antOpptatt){
      antallBilletter += i;
    }
    return antallBilletter;
  }

  public int finnInntekt(){
    return finnAntallSolgteBilletter() * super.getPris();
  }

  public Billett[] kjopBillett(int billetterKjop){
    if(super.getKapasitet() - finnAntallSolgteBilletter() < billetterKjop){
      return null;
    }
    Billett[] billetter = new Billett[billetterKjop];
    int rekkeStr = super.getKapasitet()/antOpptatt.length;
    int index = 0;
    for(int i = 1; i < antOpptatt.length; i++){
      if(antOpptatt[i] >= rekkeStr){
        index = i;
        break;
      }
    }
    int plass = antOpptatt[index] + 1;
    for(int i = 0; i < billetter.length; i++){
      billetter[i] = new SitteplassBillett(super.getTribunenavn(), super.getPris(), index, plass);
      plass++;
      antOpptatt[index]++;
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
