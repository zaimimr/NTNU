import java.util.*;

class OppgaveOversikt2{

    private ArrayList<Student> studenter;

    public OppgaveOversikt2() {
      studenter = new ArrayList<Student>();
    }

    public int finnAntStud() {
      return studenter.size();
    }

    public boolean regNyStudent(String navn){
      if(sjekkListe(navn)){
        studenter.add(new Student(navn, 0));
        return true;
      }else{
        return false;
      }
    }

    public boolean sjekkListe(String navn){
      for(Student s : studenter){
          if(navn.equals(s.getNavn())){
            return false;
          }
      }
      return true;
    }

    public int finnAntOppgaver(String navn){
      for(Student s : studenter){
          if(navn.equals(s.getNavn())){
            return s.getAntOppg();
          }
      }
      return -1;
    }

    public boolean okAntOppg(String navn, int okning){
      for(Student s : studenter){
          if(navn.equals(s.getNavn())){
            s.setAntOppg(s.getAntOppg()+okning);
            return true;
        }
      }
      return false;
    }


    public String[] finnAlleNavn(){
      String[] output = new String[studenter.size()];
      for(int i = 0; i < studenter.size(); i++){
        output[i] = studenter.get(i).getNavn();
      }
      return output;
    }

    public String toString(){
      String output = "Antall Studenter: " + studenter.size() + "\n";
      for(Student s : studenter){
        if(s != null){
          output += "Navn: " + s.getNavn() + ", antall oppgaver løst: " + s.getAntOppg() + "\n";
        }
      }
      return output;
    }

}
