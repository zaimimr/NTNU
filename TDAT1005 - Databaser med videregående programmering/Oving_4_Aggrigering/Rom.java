import java.util.*;
class Rom{
  private final int romnr;
  private final int str;
  private ArrayList<Reservasjon> reservasjoner;

  public Rom(int nr, int str){
    if(nr < 0) throw new IllegalArgumentException("Ikke gyldig med negativ nr");
    if(str < 1) throw new IllegalArgumentException("Ikke gyldig med under 0 plasser");
    this.romnr = nr;
    this.str = str;
    this.reservasjoner = new ArrayList<>();
  }

	public int getRomnr() {
		return this.romnr;
	}

	public int getStr() {
		return this.str;
	}

  public boolean reserverRom(Tidspunkt fraTid, Tidspunkt tilTid, Kunde kunde){
    Reservasjon res = new Reservasjon(fraTid, tilTid, kunde);
    for(Reservasjon r : reservasjoner){
      if(res.overlapp(r.getFraTid(), r.getTilTid())){
        return false;
      }
    }
    reservasjoner.add(res);
    return true;
  }

  public String toString(){
    String output = "Romnr " + romnr + ", Storrelse " + str + "\n";
    output += "Reservasjoner:\n";
    for(Reservasjon r : reservasjoner){
      output += r.toString() + "\n";
    }
    return output;
  }

  public static void main(String[] args) {
    Kunde k = new Kunde("Zaim Imran", "0000100010");

    Rom r1 = new Rom(14, 21); //nr 14, 21 str
    Rom r2 = new Rom(7, 11); //nr 14, 21 str
    Rom r3 = new Rom(19, 3); //nr 14, 21 str
    System.out.println("romnr: " + r1.getRomnr() + ", størrelse: " + r1.getStr());
    System.out.println("romnr: " + r2.getRomnr() + ", størrelse: " + r2.getStr());
    System.out.println("romnr: " + r3.getRomnr() + ", størrelse: " + r3.getStr());

    if(
    (r1.reserverRom(new Tidspunkt(200302011000L), new Tidspunkt(200302011100L),k))
    &&(r1.reserverRom(new Tidspunkt(200302011200L), new Tidspunkt(200302011300L),k))
    &&(r1.reserverRom(new Tidspunkt(200302111200L), new Tidspunkt(200302121300L),k))
    &&(r3.reserverRom(new Tidspunkt(201302011200L), new Tidspunkt(201402011230L),k))
    &&(!r1.reserverRom(new Tidspunkt(200302011100L), new Tidspunkt(200302011300L),k))
    &&(!r1.reserverRom(new Tidspunkt(200301011100L), new Tidspunkt(200303011300L),k))
    &&(!r1.reserverRom(new Tidspunkt(200302010930L), new Tidspunkt(200302011300L),k))
    ){
      System.out.println("\nRom: Test 1 gjennomført\n");
    }
    System.out.println(r1.toString() + "\n" + r3.toString());
  }
}
