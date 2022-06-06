import java.util.*;
class Konferansesenter{
  ArrayList<Rom> rom;

  public Konferansesenter(){
    rom = new ArrayList<>();
  }

  public boolean reserverRom(Tidspunkt fraTid, Tidspunkt tilTid, int antall,  Kunde kunde){
    for(Rom r : rom){
      if(antall <= r.getStr()){
        if(r.reserverRom(fraTid, tilTid, kunde)){
          return true;
        }
      }
    }
    return false;
  }

  public boolean registrerRom(int nr, int str){
    for(Rom r : rom) {
      if (r.getRomnr() == nr){
        return false;
      }
    }
    rom.add(new Rom(nr, str));
    return true;
  }

  public int antallRom(){
    return rom.size();
  }

  public Rom romIndx(int index){
    if(index >= rom.size() && index < 0)throw new IllegalArgumentException("Ikke gyldig index");
    return rom.get(index);
  }

  public Rom romNr(int nr){
    for(Rom r : rom){
      if(r.getRomnr() == nr){
        return r;
      }
    }
    return null;
  }

  public String toString(){
    String output = "Liste av reservasjoner: \n";
    for(int i = 0; i < rom.size(); i++){
      output += rom.get(i).toString() + "\n";
    }
    return output;
  }
}
