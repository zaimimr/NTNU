
class ov7a{

  public static void main (String[] args){
   Temperatur temp = new Temperatur("November");
    int[] midtDag = temp.midTempDag();
    System.out.println("MidtTemp Dager:");
    for (int d = 0; d < temp.getDager(); d++) {
      System.out.print("Dag " + d + ": ");
      System.out.println(midtDag[d]);
    }

    int[]midtTime = temp.midTempTime();
    System.out.println("MidtTemp Timer:");
    for (int t = 0; t < temp.getTimer(); t++) {
      System.out.print("Time " + t + ": ");
      System.out.println(midtTime[t]);
    }

    int midtMan = temp.midTemp();
    System.out.println("MidtTemp Maaned:");
    System.out.println(temp.getNavn() + ": " +  midtMan);

    System.out.println("MidtTemp Sortert:");
    int[] sorter = temp.sorter();
    for (int s = 0; s < 5; s++) {
      switch (s){
        case 0: System.out.println(">-5 : ");
        break;
        case 1: System.out.println("-5 < 0: ");
        break;
        case 2: System.out.println("0 < 5: ");
        break;
        case 3: System.out.println("5 < 10: ");
        break;
        case 4: System.out.println("10 <: ");
        break;
        default:
        break;
      }
      System.out.println(sorter[s]);
    }

  }
}
