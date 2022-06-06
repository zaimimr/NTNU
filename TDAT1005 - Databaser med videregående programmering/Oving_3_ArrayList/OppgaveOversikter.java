class OppgaveOversikt{

	private Student[] studenter;
  private int antStud;

  public OppgaveOversikt(){
		studenter = new Student[5];
		antStud = 0;
	}


	public int finnAntStud(){
		return antStud;
	}

	public boolean regNyStudent(String navn){
		for(int i = 0; i < studenter.length; i++){
			if(studenter[i].equals(navn)){
				return false;
			}
		}
		if(antStud == studenter.length){
			Student[] nyTab = new Student[studenter.length + 5];
			for (int i = 0; i < antStud; i++){
				nyTab[i] = studenter[i];
			}
			studenter = nyTab;
		}

		studenter[antStud] = new Student(navn);
		antStud++;
		return true;
	}

	public int finnAntOppgaver(int stud){
		if(studenter[stud].getNavn() == null){
			return -1;
		}
		return studenter[stud].getAntOppg();
	}

	public boolean okAntOppg(String stud, int okning){
		for(int i = 0; i<studenter.length; i++){
			if(studenter[i].getNavn().equals(stud)){
				studenter[i].okAntOppg(okning);
				return true;
			}
		}
		return false;
	}

	public String[] finnAlleNavn(){
		String[] navn = new String[studenter.length];
		for(int i = 0; i<studenter.length; i++){
			navn[i] = studenter[i].getNavn();
		}
		return navn;
	}

	public String toString(){
		for(int i = 0; i<studenter.length; i++){
			return studenter.toString() + "\n";
		}
	}
}
