
class Oving_2 {
  public static double method1(double x, int n) {
    if (n == 0) {
      return 1;
    } else if ( n > 0) {
      return x * method1(x, n-1);
    }
    return 0;
  }
  //kompleksiteten til algoritmen er Θ(n)
  public static double method2(double x, int n) {
    if (n == 0) {
      return 1;
    } else if ( n %2 == 1) {
      return x * method2(x*x, (n-1)/2);
    } else if ( n %2 == 0) {
      return method2(x*x, n/2);
    }
    return 0;
  }
  //kompleksiteten til algoritmen er Θ(log2(n)
  public static double javaPow(double x, int n) {
    return Math.pow(x, n);
  }

  public static void main(String[] args) {
    double grunntall = 2435;
    int eksponent = 451;
    int ant = 10000;

    double nr1 = 0;
    long start1;
    long endtime1 ;
    long totTime1 = 0 ;
    for(int i = 0; i < ant; i++){
      start1 = System.nanoTime();
      nr1 = method1(grunntall, eksponent);
      endtime1 = System.nanoTime();
      totTime1 += (endtime1 - start1);
    }
    totTime1/=ant;

    double nr2 = 0;
    long start2;
    long endtime2 ;
    long totTime2 = 0 ;
    for(int i = 0; i < ant; i++){
      start2 = System.nanoTime();
      nr2 = method2(grunntall, eksponent);
      endtime2 = System.nanoTime();
      totTime2 += (endtime2 - start2);
    }
    totTime2/=ant;

    double nr3 = 0;
    long start3;
    long endtime3 ;
    long totTime3 = 0 ;
    for(int i = 0; i < ant; i++){
      start3 = System.nanoTime();
      nr3 = javaPow(grunntall, eksponent);
      endtime3 = System.nanoTime();
      totTime3 += (endtime3 - start3);
    }
    totTime3/=ant;

    System.out.println("Method 1: " + nr1 + " RunTime: " + totTime1 + " ns");
    System.out.println("Method 2: " + nr2 + " RunTime: " + totTime2 + " ns");
    System.out.println("Method 3: " + nr3 + " RunTime: " + totTime3 + " ns");

  }
}