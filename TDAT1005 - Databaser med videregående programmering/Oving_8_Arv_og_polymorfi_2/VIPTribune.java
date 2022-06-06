import java.io.*;
class VIPTribune extends SitteTribune implements Serializable{
  private String[][] tilskuer; // tabellstørrelse: antall rader * antall plasser pr rad

  public VIPTribune(String navn, int kapasitet, int pris, int antallRad, int antallSeter){
    super(navn, kapasitet, pris, antallRad);
    if(antallRad*antallSeter != kapasitet) throw new IllegalArgumentException("kapasitet stemmer ikke");
    tilskuer = new String[antallRad][antallSeter];
  }

  public int finnAntallSolgteBilletter(){
    int antallBilletter = 0;
    for(String[] sArr : tilskuer){
      for(String s : sArr){
        if(s != null){
          antallBilletter++;
        }
      }
    }
    return antallBilletter;
  }

  public int finnInntekt(){
    return finnAntallSolgteBilletter() * super.getPris();
  }

  public Billett[] kjopBillett(int ant){
    return null;
  }

  public int compareTo(Tribune t){
    return Integer.compare(finnInntekt(), t.finnInntekt());
  }

  public Billett[] kjopBillett(String[] navn){
    if(super.getKapasitet() - finnAntallSolgteBilletter() < navn.length){
      return null;
    }
    Billett[] billetter = new Billett[navn.length];
    int index = -1;
    int teller;
    for(int x = 0; x < tilskuer.length; x++){
      teller = 0;
      for(int y = 0; y < tilskuer[x].length; y++){
        if(tilskuer[x][y] == null){
          teller++;
        }
      }
      if(teller >= navn.length){
        index = x;
        break;
      }
    }
    if(index == -1) return null;
    teller = 0;
    for(int i = 0; i < tilskuer[index].length; i++){
      if(tilskuer[index][i] == null){
        tilskuer[index][i] = navn[teller];
        billetter[teller] = new SitteplassBillett(super.getTribunenavn(),super.getPris(), index, i+1);
        teller++;
        if (teller == navn.length) return billetter;
      }
    }
    return null;
  }

  public String toString(){
    return "Tribunenavn: " + super.getTribunenavn() + ", kapasitet: " + super.getKapasitet() + ", pris: " + super.getPris() +
    ", antall solgte billetter: " + finnAntallSolgteBilletter() + ", inntekt: " + finnInntekt();
  }

}
