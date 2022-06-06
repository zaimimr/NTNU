import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class oppg_1 {

  public static void main(String[] args) {
    Graph g = null;
    try {
      BufferedReader br = new BufferedReader(new FileReader("input.txt"));
      String line = br.readLine();
      String[] readNum = line.split(" ");
      g = new Graph(Integer.parseInt(readNum[0]));
      while ((line = br.readLine()) != null) {
        readNum = line.split(" ");
        g.addEdge(Integer.parseInt(readNum[0]), Integer.parseInt(readNum[1]));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(g.toString());

    g.BFD(5);

    System.out.println("Node  Forgj Dist");
    for (int i = 0; i < g.V; i++) {
      if(g.info[i][0] != -1){
        System.out.println(i + "     " + g.info[i][0] + "     " + g.info[i][1]);
      } else {
        System.out.println(i + "           " + g.info[i][1]);
      }
    }
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
    adj = new LinkedList[this.V];
    for (int i = 0; i < this.V; i++) {
      adj[i] = new LinkedList();
    }
    info = new int[v][2];
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
    info[s][1] = 0;
    info[s][0] = -1;
    queue.push(s);
    while (!queue.empty()) {
      s = queue.next();
      Node temp = adj[s].head;
      while (temp != null) {
        int n = temp.value;
        temp = temp.next;
        if (!visited[n]) {
          info[n][0] = s;
          info[n][1] = (info[s][1]+1);
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
    Node temp = this.head;
    String s = temp.value + " ";
    while (temp.next != null) {
      temp = temp.next;
      s += temp.toString();
    }
    return s;
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
