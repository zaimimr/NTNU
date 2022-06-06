import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

class HashTabell {
  private int[] arr;
  private int kollisjon;

  public HashTabell(int length) {
    arr = new int[(int) Math.pow(2, Math.ceil(Math.log(length)/Math.log(2)))];
    System.out.println(arr.length);
    kollisjon = 0;
  }

  public void push(int x) {
    int h1 = multiHash(x);
    if(arr[h1] == 0){
      arr[h1] = x;
    } else {
      int counter = 1;
      while(counter < arr.length){
        int h2 = (modHash(x)*counter+h1)%(arr.length-1);
        if(arr[h2] == 0){
          arr[h2] = x;
          break;
        } else {
          counter ++;
          kollisjon++;
        }
      }
    }
  }

  public String get(int x){
    int h1 = multiHash(x);
    if(arr[h1] == x){
      return arr[h1] + " found on index "+ h1;
    } else {
      int counter = 1;
      while(counter < arr.length){
        int h2 = (modHash(x)*counter+h1)%(arr.length-1);
        if(arr[h2] == x){
          return arr[h2]+ " found on index "+ h2+ " with " + counter + " jumps";
        } else {
          counter ++;
        }
      }
    }
    return "Not found";
  }

  public int multiHash(int k) {
    double A = k* ((Math.sqrt(5.0)-1.0)/2.0);
    A -= (int) A;
    return (int) (arr.length * Math.abs(A));
  }

  public int modHash(int k) {
    return ((2*Math.abs(k) + 1) % (arr.length-1));
  }
  public static void main(String[] args) {

    int range = 5000000;
    int find = 10543;
    HashTabell ht = new HashTabell(range);

    long start;
    long end;
    long tot = 0;
    int random = 0;
    ht.push(find);
    for(int i = 0; i < range-1; i++) {
      random = (int)(Math.random()*range*10);
      start = System.nanoTime();
      ht.push(random);
      end = System.nanoTime();
      tot += end-start;
    }
    System.out.println("Time to fill my tabel: " + tot/1000000+"ms");
    System.out.println("Kollisjoner: " +ht.kollisjon);
    System.out.println(ht.get(find));
    tot = 0;
    Hashtable<Integer, Integer> h = new Hashtable<Integer, Integer>();
    for(int i = 0; i < range; i++){
      random = (int)(Math.random()*range+1);
      start = System.nanoTime();
      h.put(i, random);
      end = System.nanoTime();
      tot += end-start;
    }
    System.out.println("Time to fill java tabel: " + tot/1000000+"ms");
  }
}