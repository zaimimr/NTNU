import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class Compress{
  public static void main(String[] args) {
		String fileName = "test1.txt";
		int[] value = new int[256];
    try{
			BufferedReader inn = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
			int character = 0;
      while((character = inn.read()) != -1) {
				if(character < 0) character = 255-character;
				char c = (char) character;
				if(c >= 0 && c < 256){
					value[c]++;
				}
			}
			inn.close();
			ArrayList<Node> n = getUsedValues(value);
			ArrayList<String> word = new ArrayList<>();
			Heap heap = new Heap(n);
			byte[] bytes = Files.readAllBytes(Paths.get(fileName));
			String str = "";

			for (int i = 0; i < bytes.length; i++) {
				for (Node n1 : heap.bank) {
					if(bytes[i] == n1.c) {
						str += n1.s;
						if(str.length() % 8 == 0) {
							word.add(str);
							str = "";
						}
					}
				}
			}
			word.add(str);
			OutputStream oos = new FileOutputStream(new File("7zip.txt"));
			BufferedOutputStream bos = new BufferedOutputStream(oos);
			DataOutputStream os = new DataOutputStream(bos);
			int x = 0;
			String frekvens = "";
			for (int i = 0; i < n.size(); i++) {
				frekvens += n.get(i).c+""+String.valueOf(n.get(i).i).length()+""+n.get(i).i;
			}
			int z = 0;
			for (String s : word) {
				z += s.length();
			}
			for (int i = 0; i < 8-z%8; i++) {
				word.set(word.size()-1, word.get(word.size()-1)+"0");
			}

			int frekLength = frekvens.length();
			int antZero = 8-z%8;
			int startLength = String.valueOf(frekLength).length();
			int endLength = String.valueOf(antZero).length();
			os.write(String.valueOf(startLength).getBytes());
			os.write(String.valueOf(frekLength).getBytes());
			os.write(String.valueOf(endLength).getBytes());
			os.write(String.valueOf(antZero).getBytes());
			os.write(frekvens.getBytes());
			String output = "";
			for (String smi : word) {
				String[] words = smi.split("(?<=\\G........)");
				for (String sm : words) {
					// System.out.println(sm);
					int b = Integer.parseInt(sm, 2);
					if(b < 0) b = 255-b;
					// System.out.println(b);
					// System.out.println((char)b);
					// System.out.println(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
					// System.out.println();
					x++;
					output+= (char)b;
				}
			}

			os.write(output.getBytes());

			os.close();
			System.out.println("ZenjRar completed successfully");
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public static ArrayList<Node> getUsedValues(int[] value){
		int[] temp = Arrays.copyOf(value, value.length);
		ArrayList<Node> list = new ArrayList<>();
		for (int j = 0; j < temp.length; j++) {
			if(temp[j] != 0){
				list.add(new Node((char)j, temp[j]));
			}
		}
		return list;
	}
}

class Node{
  char c;
	int i;
	Node l, r;
	int b = 2;
	String s = null;
  Node(char c, int i){
    this.c = c;
		this.i = i;
		this.l = this.r = null;
	}
  Node(char c, String s){
		this.c = c;
		this.s = s;
		this.i = -1;
		this.l = this.r = null;
	}
	Node(Node l, Node r){
		this.c = '\u0000';
		this.i = -1;
		this.l = l;
		this.r = r;
	}
	public String toString() {
		return "\n" + this.c + "(" + (byte)this.c + ") : " + this.s;
	}
}

class Heap{
	ArrayList<Node> m;
	Node heap;
	ArrayList<Node> bank;

	Heap(ArrayList<Node> m){
		this.m = m;
		bank = new ArrayList<>();
		this.generateHeap();
		this.DFSx(this.heap, "");
	}

	void generateHeap(){
		ArrayList<Node> nList = new ArrayList<>(m);
		while(nList.size() > 1){
			Node n1 = null;
			Node n2 = null;
			int min = Integer.MAX_VALUE;
			Node temp = null;
			for (Node n : nList) {
				if(n.i < min) {
					temp = n;
					min = n.i;
				}
			}
			n1 = temp;
			nList.remove(n1);
			temp = null;
			min = Integer.MAX_VALUE;
			for (Node n : nList) {
				if(n.i < min) {
					temp = n;
					min = n.i;
				}
			}
			n2 = temp;
			nList.remove(n2);
			n1.b = 0;
			n2.b = 1;
			Node x = new Node(n1, n2);
			x.i = n1.i+n2.i;
			nList.add(x);
		}
		this.heap = nList.get(0);
	}

	public String DFS(Node n, char c){
		if(n.c == c){
			return n.b+"";
		}
		if(n.l == null && n.r == null) return null;
		String x;
		if((x = DFS(n.l, c)) != null && n.l != null) {
			if(n.b != 2) return n.b+x;
			return x;
		}
		if((x = DFS(n.r, c)) != null && n.r != null) {
			if(n.b != 2) return n.b+x;
			return x;
		}
		return null;
	}

	public String DFSx(Node n, String s){
		if(n.c != '\u0000' && n.l == null && n.r == null){
			bank.add(new Node(n.c, (s+n.b)));
			return null;
		}
		if(n.l == null && n.r == null) return null;
		if(n.b != 2){
			if((DFSx(n.l, s+n.b)) != null && n.l != null) {
				return s+n.b;
			}
			if((DFSx(n.r, s+n.b)) != null && n.r != null) {
				return s+n.b;
			}
		}else{
			if((DFSx(n.l, s)) != null && n.l != null) {
				return s+n.b;
			}
			if((DFSx(n.r, s)) != null && n.r != null) {
				return s+n.b;
			}
		}
		return null;
	}
}

