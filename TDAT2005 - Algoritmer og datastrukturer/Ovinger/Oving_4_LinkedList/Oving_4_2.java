import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class Stakk {
  private ArrayList<Character> tab;
  private int antall;

  public Stakk() {
    tab = new ArrayList<Character>();
    antall = 0;
  }

  public boolean tom() {
    return antall == 0;
  }

  public void push(char c) {
    tab.add(c);
    antall++;
    System.out.println("Legger til: " + c);
    System.out.println(tab.toString());
  }

  public void pop(char c) {
    if (!tom()){
      System.out.println("Fjerner: " + c);
      tab.remove(--antall);
      System.out.println(tab.toString());
    }
  }

  public char sjekkStakk() {
    if (!tom()){
      return tab.get(antall - 1);
    }
    return 0;
  }

  public ArrayList<Character> getList() {
    return tab;
  }

  public static void main(String[] args) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(new File("./Oving_4_1.java")));
      String line;
      Stakk s = new Stakk();
      while ((line = br.readLine()) != null) {
        for (char c : line.toCharArray()) {
          if (c == '{') {
            s.push(c);
          }
          if (c == '(') {
            s.push(c);
          }
          if (c == '[') {
            s.push(c);
          }
          if (c == '}' || c == ']' || c == ')') {
            close(s, c);
          }
        }
      }
      System.out.println("Tabellen er tom: " + s.tom());
      br.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public static boolean close(Stakk s, char c) {
    char t = s.sjekkStakk();
    if (t == '{' && c == '}') {
      s.pop(c);
      return true;
    }
    if (t == '(' && c == ')') {
      s.pop(c);
      return true;
    }
    if (t == '[' && c == ']') {
      s.pop(c);
      return true;
    }
    return false;
  }

}
