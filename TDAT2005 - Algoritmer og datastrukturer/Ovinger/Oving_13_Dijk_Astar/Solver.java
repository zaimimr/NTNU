import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solver {
    private final int radiusJorda = 6371000;
    private Node[] noder;
    private HashMap<String, Integer> map;
    ;

    /**
     * Utregningsmetode for å finne avstand til utregning av A* da den bruker denne til utførelse av algoritmen.
     * Denne lengden er avstanden fra noden algoritmen currently er på og til sluttnoden. (Gitt i oppgaveteksten)
     */
    public double avstand (Node n1, Node n2) {
        double sin_bredde = Math.sin((((n1.breddegrad * Math.PI) / 180)-((n2.breddegrad* Math.PI) / 180))/2.0);
        double sin_lengde = Math.sin((((n1.lengdegrad * Math.PI) /180)-((n2.lengdegrad * Math.PI) / 180))/2.0);
        return (41701090.90909090909090909091*Math.asin(Math.sqrt(
                sin_bredde*sin_bredde+Math.cos(n1.breddegradRad)*Math.cos(n2.breddegradRad)*sin_lengde*sin_lengde)));
    }

    /**
     * Leser alle noder fra noder.txt fila inn i en node array
     */
    public void lesNoderFraFil(String filnavn){
        try{
            BufferedReader br = new BufferedReader(new FileReader(filnavn));

            StringTokenizer st = new StringTokenizer(br.readLine());
            int antallNoder = Integer.parseInt(st.nextToken());
            noder = new Node[antallNoder];
            Node enNode;
            for(int i = 0; i < antallNoder; i++){
                st = new StringTokenizer(br.readLine());
                enNode = new Node();
                enNode.nodenr = Integer.parseInt(st.nextToken());
                enNode.breddegrad = Double.parseDouble(st.nextToken());
                enNode.lengdegrad = Double.parseDouble(st.nextToken());
                enNode.breddegradRad = (enNode.breddegrad * Math.PI) / 180;
                noder[enNode.nodenr] = enNode;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Leser alle kanter fra kanter.txt fila og legges inn i noder lista på current node sin kantliste
     */
    public void lesKanterFraFil(String filnavn){
        try{
            BufferedReader br = new BufferedReader(new FileReader(filnavn));
            StringTokenizer st = new StringTokenizer(br.readLine());
            int antallKanter = Integer.parseInt(st.nextToken());
            Kant enKant;
            for(int i = 0; i < antallKanter; i++){
                st = new StringTokenizer(br.readLine());

                enKant = new Kant();
                enKant.franode = noder[Integer.parseInt(st.nextToken())]; // Kantens fra node
                enKant.tilnode = noder[Integer.parseInt(st.nextToken())]; // Kantens til node
                enKant.kjøretid = Integer.parseInt(st.nextToken()); // Kantens kjøretid
                enKant.lengde = Integer.parseInt(st.nextToken()); // Kantens lengde
                enKant.fartsgrense = Integer.parseInt(st.nextToken());
                noder[enKant.franode.nodenr].kantListe.add(enKant); //Legger kanten inn i current node sin kantliste
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Leser alle noder fra noder.txt fila inn i en node array
     */
    public void lesPlasserFraFil(String filnavn){
        try{
            map = new HashMap<>();
            BufferedReader br = new BufferedReader(new FileReader(filnavn));
            String line = br.readLine();
            while ((line = br.readLine()) != null)   {
              String[] data = line.split("\\s+");
              map.put(data[2].replaceAll("\"", ""), Integer.parseInt(data[0]));
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dijkstras(Node startNode, Node sluttNode){
        PriorityQueue<Node> prioritetsQ = new PriorityQueue<>((start, goal) -> {
            if(start.lengdeFraStart > goal.lengdeFraStart) return 1;
            if(start.lengdeFraStart < goal.lengdeFraStart) return -1;
            return 0;
        });

        Node currentNode = startNode;
        currentNode.lengdeFraStart = 0;
        int besokt = 0;

        while(currentNode.nodenr != sluttNode.nodenr){
            for(int i = 0; i < currentNode.kantListe.size(); i++){
                double nyLengde = currentNode.kantListe.get(i).kjøretid + currentNode.lengdeFraStart;

                if(currentNode.kantListe.get(i).tilnode.lengdeFraStart > nyLengde){ // Hvis current node sin kant(i) lengde fra start er lengre enn ny lengde
                    currentNode.kantListe.get(i).tilnode.lengdeFraStart = nyLengde; // Sett til ny lengde
                    currentNode.kantListe.get(i).tilnode.komFra = currentNode; // Til node til current node sin kant kom-fra-node settes
                    prioritetsQ.add(currentNode.kantListe.get(i).tilnode); // Legger til noden som er funnet i PQ
                }
            }
            besokt++;
            currentNode = prioritetsQ.poll(); // Henter ny node fra PQ
            if(currentNode.nodenr == sluttNode.nodenr){
                skrivKjøreLengde(currentNode);
            }
        }
        System.out.println("Antall noder besøkt: " + besokt);
    }

    /**
     * A* algoritme for å finne raskeste vei fra startNode til sluttNode.
     * Noden med lavest kost er den som blir valgt som ny current node å dra til neste.
     * Altså noden nærmest mål (sluttnoden) vil undersøkes først.
     */
    public void aStar(Node startNode, Node sluttNode){
        PriorityQueue<Node> prioritetsQ = new PriorityQueue<>((start, goal) -> {
            if(start.lengdeAStar > goal.lengdeAStar) return 1;
            if(start.lengdeAStar < goal.lengdeAStar) return -1;
            return 0;
        });

        Node currentNode = startNode;

        currentNode.lengdeTilMål = avstand(currentNode, sluttNode);
        currentNode.lengdeAStar = currentNode.lengdeTilMål; // Ekstra variabel som A* bruker i algoritme for å finne neste node i forhold til retning
        int besokt = 0;
        while(currentNode != null && currentNode.nodenr != sluttNode.nodenr ){
            for(int i = 0; i < currentNode.kantListe.size(); i++){
                if(currentNode.kantListe.get(i).tilnode.lengdeTilMål == -1){ // -1 er startavstand, så da har ikke currnoden kant(i) sin til-node noen lengde til mål ennå - den settes så her
                    currentNode.kantListe.get(i).tilnode.lengdeTilMål = avstand(currentNode.kantListe.get(i).tilnode, sluttNode);
                    currentNode.kantListe.get(i).tilnode.lengdeAStar = currentNode.kantListe.get(i).tilnode.lengdeTilMål + currentNode.kantListe.get(i).tilnode.lengdeFraStart;
                }
                double nyLengde = currentNode.kantListe.get(i).kjøretid + currentNode.lengdeFraStart;
                if(nyLengde < currentNode.kantListe.get(i).tilnode.lengdeFraStart){
                    currentNode.kantListe.get(i).tilnode.lengdeFraStart = nyLengde;
                    currentNode.kantListe.get(i).tilnode.komFra = currentNode;
                    currentNode.kantListe.get(i).tilnode.lengdeAStar = currentNode.kantListe.get(i).tilnode.lengdeTilMål + currentNode.kantListe.get(i).tilnode.lengdeFraStart;
                    prioritetsQ.add(currentNode.kantListe.get(i).tilnode);
                }
            }
            if(!currentNode.besøkt){
                currentNode.besøkt = true;
            }
            currentNode = prioritetsQ.poll();
            besokt++;
            if(currentNode.nodenr == sluttNode.nodenr){
                skrivKjøreLengde(currentNode);
            }

        }
        System.out.println("Antall noder besøkt: " + besokt + "\n");
    }
    private void skrivKjøreLengde(Node sisteNode){
        int tid = (int) sisteNode.lengdeFraStart;
        System.out.println("------------------KJØRETIDTID:------------------");
        int antTimer = tid/360000;
        int antMin = (tid%360000)/(6000);
        int antSek = (tid - antTimer * 360000 - antMin * 6000) / 100;
        System.out.println("Kjøretid: " + antTimer + " time(r) " + antMin + " min " + antSek + " sekunder\n" );

    }

    public static void main(String[] args){
        Solver solver = new Solver();
        solver.lesNoderFraFil("./noder.txt");
        solver.lesKanterFraFil("./kanter.txt");
        solver.lesPlasserFraFil("./interessepkt.txt");
        Node fraNode = solver.noder[solver.map.get("Trondheim")];
        Node tilNode = solver.noder[solver.map.get("Oslo")];
        fraNode.lengdeFraStart = 0;


        System.out.println("LEST NODER OG KANTER FRA FIL \n------------\n--------\n\n");

        Date start = new Date();
        double tid;
        Date slutt;

        //solver.aStar(fraNode, tilNode);
        solver.dijkstras(fraNode, tilNode);
        slutt = new Date();

        tid = (double) slutt.getTime()-start.getTime();
        System.out.println("MILLISEKUNDER BRUKT PÅ ALGORITME:" + tid);

        Node enNode = tilNode;
        int antallNoder = 0;
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("path.txt"));
            while(enNode != null){
                writer.write(enNode.breddegrad + "," + enNode.lengdegrad + "\n");
                enNode = enNode.komFra;
                antallNoder++;
            }
            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("\nAntall noder i korteste vei: " + antallNoder);
        solver.skrivKjøreLengde(tilNode);



    }
}
