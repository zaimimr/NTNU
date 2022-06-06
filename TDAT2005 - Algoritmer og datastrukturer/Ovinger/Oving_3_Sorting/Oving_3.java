import java.util.Random;

public class Oving_3 {

    public static int runBubble = 20;
    public static int n = 5000000;

    public static void main(String[] args) {

        int[] t = generateTable(n);
        t = generateTable(n);
        System.out.println(summer(t));
        long startTime = System.nanoTime();
        quickSort(t, 0, t.length-1);
        long endTime = System.nanoTime();
        long time = endTime - startTime;
        System.out.println(summer(t));
        System.out.println((testSort(t)));
        System.out.println("Algorithm uses " + (time/1000000) + " ms");
    }

    public static void quickSort(int[] t, int v, int h) {
        if (h - v > runBubble) {
            int pivot = partition(t, v, h);
            quickSort(t, v, pivot - 1);
            quickSort(t, pivot + 1, h);
        }else {
            bubblesort(t,v,h);
        }
    }

    private static int partition(int[] t, int v, int h) {
        int iv, ih;
        int m = median3sort(t, v, h);
        int dv = t[m];
        swap(t, m, h - 1);
        for (iv = v, ih = h - 1; ; ) {
            while (t[++iv] < dv) ;
            while (t[--ih] > dv) ;
            if (iv >= ih) break;
            swap(t, iv, ih);
        }
        swap(t, iv, h - 1);
        return iv;
    }

    private static int median3sort(int[] t, int v, int h) {
        int m = (v + h) / 2;
        if (t[v] > t[m]) swap(t,v,m);
        if (t[m] > t[h]) {
            swap(t,m,h);
            if(t[v] > t[m]) swap(t,v,m);
        }
        return m;
    }

    private static void bubblesort(int[] t, int v, int h) {
        for (int i = h; i >= v; --i) {
            for (int j = v; j < i; ++j) {
                if (t[j] > t[j+1]) {
                    swap(t,j,j+1);
                }
            }
        }
    }

    private static void swap(int[] t, int a, int b) {
        int temp = t[a];
        t[a] = t[b];
        t[b] = temp;
    }

    private static int[] generateTable(int n) {
        int[] t = new int[n];
        Random random = new Random();
        for (int i = 0; i < t.length; i++) {
            t[i] = random.nextInt(100);
        }
        return t;
    }

    private static String testSort(int[] t) {
        for (int i = 0; i < t.length - 2; i++) {
            if (t[i] > t[i+1]) {
                return "The tabel is not sorted";
            }
        }
        return "The tabel is sorted";
    }

    public static int summer(int[] t){
      int sum = 0;
      for (int i = 0; i < t.length; i++){
        sum += t[i];
      }
      return sum;
    }
}
