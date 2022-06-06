import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class HashTabell {
  private HashNode[] arr;
  private int kollisjon;

  public HashTabell(int length) {
    arr = new HashNode[length/2];
    kollisjon = 0;
  }

  public int hashFunc(String s) {
    int sum = 0;
    int counter = 1;
    for (char c : s.toCharArray()) {
      sum += c * counter;
      counter++;
    }
    return sum % arr.length;
  }

  public void push(String s) {
    int index = hashFunc(s);
    if (arr[index] == null) {
      arr[index] = new HashNode(s);
    } else {
      HashNode temp = arr[index];
      String last = "Kollisjon på index " + index + ":" + temp.data + " -> ";
      kollisjon ++;
      while (temp.next != null) {
        temp = temp.next;
        kollisjon ++;
        last += temp.data + " -> ";
      }
      System.out.println(last + s);
      temp.setNext(new HashNode(s));
    }
  }

  public HashNode find(String s) {
    try {
      int index = hashFunc(s);
      HashNode temp = arr[index];
      String last = "Finnkollisjon på index " + index + ": ";
      while (!temp.next.data.equals(s)) {
        temp = temp.next;
        last += temp.data + " -> ";
      }
      temp = temp.next;
      System.out.println(last + temp.data + " <- Funnet!!");
      return temp;
    } catch (NullPointerException e) {
      return null;
    }
  }

  public String analyse() {
    int counter = 0;
    for (HashNode hn : arr) {
      if (hn != null) {
        counter++;
      }
    }
    int pers = finnAntallPers();
    return counter + " av " + arr.length + " er brukt. Lastfaktor er: " + (double) counter/arr.length +". \nNr av kollisjoner: " + kollisjon + ", nr av personer: " + pers + " , gjennomsnitt: " + (double)kollisjon/pers;
  }

  public int finnAntallPers() {
    int pers = 0;
    for(HashNode h : arr) {
      if(h != null){
        pers ++;
        while(h.next != null){
          h = h.next;
          pers ++;
        }
      }
    }
    return pers;
  }

  public static void main(String[] args) {
    ArrayList<String> temp = new ArrayList<>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(new File("./navn.txt")));
      String line;
      while ((line = br.readLine()) != null) {
        temp.add(line);
      }
      br.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    HashTabell ht = new HashTabell(temp.size());
    for (String s : temp) {
      ht.push(s);
    }
    HashNode find = ht.find("Grande,Trym");
    if (find != null) {
      System.out.println(find.data);
    } else {
      System.out.println("Person ikke funnet");
    }


    System.out.println(ht.analyse());
  }
}