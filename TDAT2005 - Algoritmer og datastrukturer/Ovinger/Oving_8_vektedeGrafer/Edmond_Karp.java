import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

class Edmond_Karp {

  public static void main(String[] args) {
    Graph g = null;
    try {
      BufferedReader br = new BufferedReader(new FileReader("input1.txt"));
      String line = br.readLine();
      String[] readNum = line.split("\\s+");
      g = new Graph(Integer.parseInt(readNum[0]));
      while ((line = br.readLine()) != null) {
        readNum = line.split("\\s+");
        g.addEdge(Integer.parseInt(readNum[0]), Integer.parseInt(readNum[1]), Integer.parseInt(readNum[2]));
      }
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    g.EdmondsKarp(g, 0, 1);
  }
}

class Graph {
  Node adj[];
  int V;
  Stack s;
  int G;

  Graph(int v) {
    this.V = v;
    s = new Stack();
    adj = new Node[v];
    for (int i = 0; i < v; i++) {
      adj[i] = new Node(i);
    }
  }

  void addEdge(int v, int w, int k) {
    if (v <= this.V) {
      adj[v].addKant(new Kant(adj[w], k));
      adj[w].addKant(new Kant(adj[v], 0));
    }
  }

  boolean BFD(int s, int t, int[] par) {
    boolean visited[] = new boolean[V];
    Stack queue = new Stack();
    visited[s] = true;
    par[s] = -1;
    queue.push(s);
    while (!queue.empty()) {
      s = queue.next();
      for (Kant k : adj[s].kants) {
        int n = k.next.value;
        if (!visited[n] && k.kapasitet > 0) {
          queue.push(n);
          par[n] = s;
          visited[n] = true;
        }
      }
    }
    return visited[t];
  }

  void EdmondsKarp(Graph g, int s, int t){
    int maxFlow = 0;
    int[] par = new int[this.V];
    int u;
    int minFlow = Integer.MAX_VALUE;
    while(g.BFD(s, t, par)){
      for(int i = t; i != s; i = par[i]){
        u = par[i];
        for (Kant k : adj[u].kants) {
          if(k.next.value == i){
            System.out.print( i + " <- ");
            minFlow = Math.min(minFlow, k.kapasitet);
          }
        }
      }
      System.out.println(s + ", Okning " + minFlow);
      for(int i = t; i != s; i = par[i]){
        u = par[i];
        for (Kant k : adj[u].kants) {
          if(k.next.value == i){
            k.kapasitet -= minFlow;
          }
        }
        for (Kant k : adj[i].kants) {
          if(k.next.value == u){
            k.kapasitet += minFlow;
          }
        }
      }
      maxFlow += minFlow;
    }
    System.out.println("Maxflow " + maxFlow);
  }

  public String toString() {
    String s = "";
    for (int i = 0; i < this.V; i++) {
      s += "Node " + i + ": " + adj[i].toString();
      for (Kant k : adj[i].kants) {
        s += k.toString() + ", ";
      };
      s += "\n";
    }
    return s;
  }
}

class Node {
  int value;
  ArrayList<Kant> kants;

  public Node(int v) {
    this.value = v;
    this.kants = new ArrayList<>();
  }

  public void addKant(Kant k) {
    this.kants.add(k);
  }

  public String toString() {
    return this.value + " ";
  }
}

class Kant {
  Node next;
  int kapasitet;

  public Kant(Node next, int kapasitet) {
    this.next = next;
    this.kapasitet = kapasitet;
  }

  public String toString() {
    return "Kant: kapasitet - " + this.kapasitet + " neste - " + this.next.value;
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