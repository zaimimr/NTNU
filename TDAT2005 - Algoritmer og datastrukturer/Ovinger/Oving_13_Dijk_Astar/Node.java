
import java.util.ArrayList;

public class Node {
    private final int UENDELIG = 1000000000;
    int nodenr;
    double breddegrad;
    double lengdegrad;
    boolean besøkt = false;
    double breddegradRad;

    Node komFra;
    double lengdeFraStart = UENDELIG;
    double lengdeTilMål = -1;

    double lengdeAStar;

    ArrayList<Kant> kantListe = new ArrayList<>(); // Liste av kanter til denne noden

    public double getCosBredde(){
        return (breddegrad * Math.PI) / 180;
    }

    public double getCosLengde(){
        return (lengdegrad * Math.PI) / 180;
    }
}