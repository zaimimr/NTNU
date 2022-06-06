import java.util.*;

class Temperatur{

  int dager = 30;
  int timer = 24;
  int midTemp;
  int[][] temperatur;
  String manedNavn;
  Random rand = new Random();

  public Temperatur(String navn){
    this.manedNavn = navn;
    temperatur = new int[dager][timer];
    setTemp();
  }

  public void setTemp(){
    for (int d = 0; d < dager; d++){
      for(int t = 0; t < timer; t++){
        temperatur[d][t] = nesteHeltall(-20,30);
      }
    }
  }

  public int nesteHeltall(int nedre, int ovre){ //intervallet[nedre,ovre]
    return nedre + rand.nextInt(ovre-nedre);
  }
//a
  public int[] midTempDag(){
    int[] midTempDag = new int[dager];
    for (int d = 0; d < dager; d++){
      midTemp = 0;
      for(int t = 0; t < timer; t++){
        midTemp += temperatur[d][t];
      }
      midTemp /= timer;
      midTempDag[d] = midTemp;
    }
    return midTempDag;
  }
//b
  public int[] midTempTime(){
    int[] midTempTime = new int[timer];
    for(int t = 0; t < timer; t++){
      midTemp = 0;
      for(int d = 0; d < dager; d++){
        midTemp += temperatur[d][t];
      }
      midTemp /= dager;
      midTempTime[t] = midTemp;
    }
    return midTempTime;
  }
//c
  public int midTemp(){
    midTemp = 0;
    for (int d = 0; d < dager; d++) {
      for (int t = 0; t < timer; t++) {
        midTemp+=temperatur[d][t];
      }
    }
    midTemp/=(dager*timer);
    return midTemp;
  }
//d
  public int[] sorter(){
    int[] midTempDag = midTempDag();
    int[] Sorter = new int[5];

    for (int d = 0; d < dager; d++) {
      if(midTempDag[d] < -5){
        Sorter[0] ++;
      }else if(midTempDag[d] >= -5 && midTempDag[d] < 0){
        Sorter[1] ++;
      }else if(midTempDag[d] >= 0 && midTempDag[d] < 5){
        Sorter[2] ++;
      }else if(midTempDag[d] >= 5 && midTempDag[d] <= 10){
        Sorter[3] ++;
      }else if(midTempDag[d] > 10){
        Sorter[4] ++;
      }
    }
    return Sorter;
  }

  public int getDager(){
    return dager;
  }
  public int getTimer(){
    return timer;
  }
  public String getNavn(){
    return manedNavn;
  }
  public int[][]getTemp(){
    return temperatur;
  }
}
