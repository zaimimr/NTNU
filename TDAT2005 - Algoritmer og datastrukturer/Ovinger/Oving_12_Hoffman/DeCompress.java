import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.*;

class DeCompress{
  public static void main(String[] args) {
		String fileName = "7zip.txt";
    int[] value = new int[256];
    int fLenLen, fLen, antZLen, antZ;
    try{
			BufferedReader inn = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
			fLenLen = Character.getNumericValue((char) inn.read());
			String s = "";
			for (int i = 0; i < fLenLen; i++) {
				s += (char) inn.read();
			}
      fLen = Integer.parseInt(s);
			antZLen = Character.getNumericValue((char) inn.read());
			s = "";
			for (int i = 0; i < antZLen; i++) {
				s += (char) inn.read();
			}
			antZ = Integer.parseInt(s);
			int read = 0;
			while (fLen != read){
				char c = (char) inn.read();
				read++;
				String ss = "";
				int nr = Character.getNumericValue(inn.read());
				read++;
				for (int j = 0; j < nr; j++) {
					ss+=(char)inn.read();
					read++;
				}
				value[c] = Integer.parseInt(ss);
			}

			String text = "";
			int character = 0;
			while((character = inn.read()) != -1) {
				if(character < 0) {
					character = 255-character;
				}
				char c = (char) character;
				text += String.format("%8s", Integer.toBinaryString((byte)c & 0xFF)).replace(' ', '0');
			}
			text = text.substring(0, text.length()-antZ);
			inn.close();

			ArrayList<Node> n = getUsedValues(value);
			Heap heap = new Heap(n);

			String nyText = "";

			int i = 0;
			for (int j = i; j <= text.length(); j++) {
				for(Node no : heap.bank){
					if(j != text.length()){
						if(!text.substring(i, j+1).equals(no.s)) {
							if(text.substring(i, j).equals(no.s)){
								nyText+= no.c;
								i = j;
							}
						}
					}else{
						if(text.substring(i, j).equals(no.s)){
							nyText+= no.c;
							i = j;
						}
					}
				}
			}
			OutputStream oos = new FileOutputStream(new File("output.txt"));
			BufferedOutputStream bos = new BufferedOutputStream(oos);
			DataOutputStream os = new DataOutputStream(bos);
			os.write(nyText.getBytes());
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

