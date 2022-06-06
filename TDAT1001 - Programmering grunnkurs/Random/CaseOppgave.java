

// 350 + (1.18*(12600-350x))

class CaseOppgave{


  public static void main(String[]args){

    int startBelop = 12600; //fast
    final double rente = 0.18; //fast
    int avdrag = 350; //fast
    int mnd = startBelop/avdrag; //fast

    double belopRente; //1.18*(12600-350*x)
    double saldo = startBelop;
    double sumRente = 0;
     double betaltMnd;
    double betaltTotal = 0;

    for (int i = 1; i <= mnd; i++) {
      belopRente = rente * saldo;
      saldo -= avdrag;
      sumRente += belopRente;
      betaltMnd = avdrag + belopRente;
      betaltTotal += betaltMnd;

      System.out.println("Måned " + i + ": Betale: " + betaltMnd + ", rente: " + belopRente
      + ", TotalRente: " + sumRente + ", Saldo " + saldo + ", Betalt alt i alt: " + betaltTotal);
    }
  }

}
