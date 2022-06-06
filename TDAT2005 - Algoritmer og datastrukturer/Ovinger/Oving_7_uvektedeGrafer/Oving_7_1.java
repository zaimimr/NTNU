import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

class Oving_7_1 {

  public static void main(String[] args) {
    Graph g = null;
    try {
      BufferedReader br = new BufferedReader(new FileReader("input.txt"));
      String line = br.readLine();
      String[] readNum = line.split("\\s+");
      g = new Graph(Integer.parseInt(readNum[0]));
      while ((line = br.readLine()) != null) {
        readNum = line.split("\\s+");
        g.addEdge(Integer.parseInt(readNum[0]), Integer.parseInt(readNum[1]));
      }
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    System.out.println(g.toString());

    g.BFD(6);
    sortByDist(g.info, 2);

    System.out.println("Node  Forgj Dist");

    for (int i = 0; i < g.V; i++) {
      if (g.info[i][1] != -1) {
        System.out.println(g.info[i][0] + "     " + g.info[i][1] + "     " + g.info[i][2]);
      } else {
        System.out.println(g.info[i][0] + "           " + g.info[i][2]);
      }
    }
    //--------------------------------------------------------------------------------
    // int start = findNodeByName("Kalvskinnet");
    // int end = findNodeByName("Moholt");
    // System.out.println("Start " + start + " end " + end);
    // if(start != -1 && end != -1){
    //   g.BFD(start);
    //   System.out.println(g.info[end][0] + "     " + g.info[end][1] + "     " + g.info[end][2]);
    //   System.out.println("Avstand fra Kalvskinnet(37774) til Moholt(18058) er " + g.info[end][2] + " kryss");
    // }
  }

  public static int findNodeByName(String name){
    try {
      BufferedReader br = new BufferedReader(new FileReader("L7Skandinavia-navn.txt"));
      String line = br.readLine();
      while ((line = br.readLine()) != null) {
        if(line.contains(name)){
          return Integer.parseInt(line.split("	")[0]);
        }
      }
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    return -1;
  }

  public static void sortByDist(int arr[][], int col) {
    Arrays.sort(arr, new Comparator<int[]>() {
      @Override
      public int compare(final int[] entry1, final int[] entry2) {
        if (entry1[col] > entry2[col])
          return 1;
        else
          return -1;
      }
    });
  }
}

class Graph {
  LinkedList adj[];
  int V;
  Stack s;
  int G;
  int[][] info;

  Graph(int v) {
    this.V = v;
    s = new Stack();
    adj = new LinkedList[v];
    for (int i = 0; i < v; i++) {
      adj[i] = new LinkedList();
    }
    info = new int[v][3];
    for (int i = 0; i < v; i++) {
      info[i][0] = i;
      info[i][1] = -1;
    }
  }

  void addEdge(int v, int w) {
    if (v <= this.V) {
      adj[v].push(w);
    }
  }

  void BFD(int s) {
    boolean visited[] = new boolean[V];
    Stack queue = new Stack();
    visited[s] = true;
    info[s][0] = s;
    info[s][1] = -1;
    info[s][2] = 0;
    queue.push(s);
    while (!queue.empty()) {
      s = queue.next();
      Node temp = adj[s].head;
      while (temp != null) {
        int n = temp.value;
        temp = temp.next;
        if (!visited[n]) {
          info[n][0] = n;
          info[n][1] = s;
          info[n][2] = (info[s][2] + 1);
          visited[n] = true;
          queue.push(n);
        }
      }
    }
  }

  public String toString() {
    String s = "";
    for (int i = 0; i < this.V; i++) {
      s += "Node " + i + ": " + adj[i].toString() + "\n";
    }
    return s;
  }
}

class LinkedList {
  Node head;

  public LinkedList() {
  }

  void push(int n) {
    if (this.head == null) {
      this.head = new Node(n);
      return;
    }
    Node temp = this.head;
    while (temp.next != null) {
      temp = temp.next;
    }
    temp.next = new Node(n);
  }

  public String toString() {
    if (this.head != null) {
      Node temp = this.head;
      String s = temp.value + " ";
      while (temp.next != null) {
        temp = temp.next;
        s += temp.toString();
      }
      return s;
    }
    return null;
  }
}

class Node {
  int value;
  Node next;

  public Node(int v) {
    this.value = v;
  }

  public String toString() {
    return this.value + " ";
  }
}

class Stack {
  ArrayList<Integer> stack;

  Stack() {
    stack = new ArrayList<>();
  }

  void push(int i) {
    stack.add(i);
  }

  void pop() {
    stack.remove(stack.size() - 1);
  }

  int next() {
    int x = stack.get(0);
    stack.remove(0);
    return x;
  }

  boolean empty() {
    return stack.size() == 0;
  }

  ArrayList<Integer> getStack() {
    return stack;
  }
}
