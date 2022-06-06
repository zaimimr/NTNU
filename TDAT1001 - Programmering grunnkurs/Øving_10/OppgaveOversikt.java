class OppgaveOversikt{

  private Student[] studenter;
  private int antStud = 0;

  public OppgaveOversikt(){
    studenter = new Student[1];
  }

  public int antStudenter(){
    return this.antStud;
  }

  public int lostOppgaver(Student s){
    return s.getAntOppg();
  }

  public void nyStudent(Student s){
    studenter[antStud] = s;
    antStud++;
    expand();
  }

  public void expand() {
    Student[] newArray = new Student[studenter.length + 1];
    System.arraycopy(studenter, 0, newArray, 0, studenter.length);
    studenter = newArray;
  }

  public void okOppg(Student s, int oking){
    s.okAntOppg(oking);
  }

  public int getOppgLost(int antOpg){
    int teller = 0;
    for (int i = 0; i < studenter.length-1; i++) {
      if(studenter[i].getAntOppg() >= antOpg){
         teller ++;
      }
    }
    return teller;
  }

  public String toString(){
    return "Det er " + antStud + " Studenter";
  }
}
