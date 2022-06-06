class OppgaveOversikt{
  private Student[] studenter;
  private int antStud;

  public OppgaveOversikt() {
    studenter = new Student[5];
    antStud = 0;
  }

  public int finnAntStud() {
    return antStud;
  }

  public boolean regNyStudent(String navn){
    if(sjekkListe(navn)){
      if(studenter.length == antStud){
        utvidTabell();
      }
      studenter[antStud] = new Student(navn, 0);
      antStud++;
      return true;
    }else{
      return false;
    }
  }

  public boolean sjekkListe(String navn){
    for(Student s : studenter){
      if(s != null){
        if(navn.equals(s.getNavn())){
          return false;
        }
      }
    }
    return true;
  }

  public void utvidTabell(){
    Student[] nyTab = new Student[studenter.length + 5];
    for (int i = 0; i < antStud; i++) {
      nyTab[i] = studenter[i];
    }
    studenter = nyTab;
  }

  public int finnAntOppgaver(String navn){
    for(Student s : studenter){
      if(s != null){
        if(navn.equals(s.getNavn())){
          return s.getAntOppg();
        }
      }
    }
    return -1;
  }

  public boolean okAntOppg(String navn, int okning){
    for(Student s : studenter){
      if(s != null){
        if(navn.equals(s.getNavn())){
          s.setAntOppg(s.getAntOppg()+okning);
          return true;
        }
      }
    }
    return false;
  }


  public String[] finnAlleNavn(){
    String[] output = new String[antStud];
    for(int i = 0; i < antStud; i++){
      output[i] = studenter[i].getNavn();
    }
    return output;
  }

  public String toString(){
    String output = "Antall Studenter: " + antStud + "\n";
    for(Student s : studenter){
      if(s != null){
        output += "Navn: " + s.getNavn() + ", antall oppgaver løst: " + s.getAntOppg() + "\n";
      }
    }
    return output;
  }
}
