import java.util.ArrayList;

public class Main {

}

abstract class Person {
    private int timeLonn;
    private int ansiennitet;

    Person(int timeLonn, int ansiennitet){
        this.timeLonn = timeLonn;
        this.ansiennitet = 2017 - ansiennitet;
    }

    int getTimeLonn() {
        return timeLonn;
    }

    int getAnsiennitet() {
        return ansiennitet;
    }

    abstract double beregnKostnad();
}

class Ingenior extends Person {

    public Ingenior(int timeLonn, int ansiennitet) {
        super(timeLonn, ansiennitet);
    }

    @Override
    double beregnKostnad() {
        int a = super.getAnsiennitet();
        if (a >= 0 && a <= 2) {
            return 1.6 * super.getTimeLonn();
        } else if (a >= 3 && a <= 5) {
            return 1.8 * super.getTimeLonn();
        } else {
            return 2.1 * super.getTimeLonn();
        }
    }

}

class Adm extends Person {

    public Adm(int timeLonn, int ansiennitet) {
        super(timeLonn, ansiennitet);
    }

    @Override
    double beregnKostnad() {
        return 1.4 * super.getTimeLonn();
    }
}

class Aktivitet {
    private int aktID;
    private int kostIng;
    private int kostAdm;
    private ArrayList<TimePerson> aktivitetListe = new ArrayList<>();

    public Aktivitet(int aktID, int kostIng, int kostAdm) {
        this.aktID = aktID;
        this.kostIng = kostIng;
        this.kostAdm = kostAdm;
    }

    void registrerTimer(Person p, int uke, int timer) {
        for(TimePerson tp : aktivitetListe) {
            if (tp.compareTo(p, uke)) {
                tp.addTimer(timer);
                return;
            }
        }
        aktivitetListe.add(new TimePerson(p, uke, timer));
    }

    int totalKostIng(){
        int totTimer = 0;
        for(TimePerson tp : aktivitetListe) {
            if (tp.getP() instanceof Ingenior) {
                totTimer += tp.getTimer();
            }
        }
        return totTimer * kostIng;
    }

    int totalKostAdm(){
        int totTimer = 0;
        for(TimePerson tp : aktivitetListe) {
            if (tp.getP() instanceof Adm) {
                totTimer += tp.getTimer();
            }
        }
        return totTimer * kostAdm;
    }

    public int finnEstTotal() {
        return estKostnadIng + estKostnadAdm;
    }
    public double finnTotalForbruk() {
        return totalKostIng() + totalKostAdm();
    }

}

class TimePerson {
    private Person p;
    private int uke;
    private int timer;

    TimePerson(Person p, int uke, int timer) {
        this.p = p;
        this.uke = uke;
        this.timer = timer;
    }

    Person getP() {
        return p;
    }

    int getTimer() {
        return timer;
    }

    void addTimer(int timer) {
        this.timer += timer;
    }

    boolean compareTo(Person per, int u) {
        return per == this.p && u == this.uke;
    }
}

class Prosjektfase {
    private ArrayList<Aktivitet> aktiviteter = new ArrayList<Aktivitet>();
    public double finnRestBudsjett() {
        // hvor mye er igjen av budsjettet for denne prosjektfasen?
        // denne metoden skal du programmere etterhvert
    }
}
