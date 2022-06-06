public class Oving_1 {
  public static void main(String[] args) {
    int list[] = {-1, 3, -9, 2, 2, -1, 2, -1};
    long startTime = System.nanoTime();
    for (int i = 0; i < 1000; i++) {
          profitFinder(list);

    }
    long endTime   = System.nanoTime();
    long totalTime = (endTime - startTime) /1000;
    System.out.println("run time: " + totalTime + " milli");

  }

  public static String profitFinder(int list[]) {
    int earning = 0;
    int currentMax = 0;
    int buy = -1;
    int sell = -1;
    int len = list.length;
    for (int n = 0; n < len; n++) {
      earning = 0;
      for (int m = n; m < len; m++) {
        earning += list[m];
        if(earning > currentMax) {
          currentMax = earning;
          buy = n;
          sell = m+1;
        }
      }
    }
    return "Earning: " + currentMax + ", Buy date: " + buy + ", Sell date: " + sell;
  }
}
// kompleksiteten til algoritmen er Θ(n^2), på grunn av at vi har en
// løkke som går gjennom hele listen, med en indre løkke som er avhengig av n.
// Dermed vil vi få n * C*n som blir C*n^2. Siden vi fjerner alle konstanter
// vil kompleksiteten bli Θ(n^2).

// Får mellom 80 til 120 millisek på testing.