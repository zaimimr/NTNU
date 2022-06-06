class Bokstaveringsalfabet{
String alfabetNavn;
String[] alfabet;

public Bokstaveringsalfabet(Strng navn, String bakstalfa, String separator){
  this.alfabetNavn = navn;
  this.alfabet = bakstalfa.split(separator);
}

public Bokstaveringsalfabet(String navn,String filnavn){
  this.navn = navn;
  if(lesAlfabetFraFil(filnavn) != null){
    alfabet = lesAlfabetFraFil(filnavn);
  }
}

String toString(){
  String tostring = "";
  for(String s : alfabet){
    tostring += s + " "
  }
  return tostring;
}

public boolean leggTilOrd(String boksAlfa){

  //sjekker om det er noe i boksAlfa
  if(boksAlfa == null) return false;
  //Sjekker om den eksisterer fra før av
  for(String s: alfabet){
      if(boksAlfa.equals(s)){
        return false;
      }
  }
  if(utvidTabell){
    alfabet[alfabet.length-1] = boksAlfa;
    return true;
  }
  return false;
}

public boolean utvidTabell(){
  String[] buffer = new String[alfabet.length+1];
  for(int i = 0; i < alfabet.length; i++){
    buffer[i] = alfabet[i];
  }
  alfabet = buffer;
  return true;
}

public boolean endreFonetBeskrivelse(String bokstav, String beskrivelse){
  if(bokstav.length != 1){
    return false;
  }

  if(!(beskrivelse.toUpperCase().charAt(0).equal(bokstav.toUpperCase().charAt(0)))){
    return false;
  }

  for(int i = 0; i < alfabet.length; i++){
    if(alfabet[i].toUpperCase().charAt(0).equals(bokstav.toUpperCase().charAt(0))){
      alfabet[i] = beskrivelse;
    }
  }
  return true;
}

public boolean sorterAlfabetisk(){
  if(liste != null || !(liste.length > 1)){
    return false;
  }

  int sjekk;
  for(int i = 0; i < liste.lenght-1; i++){
    sjekk = i;
    for(int k = i+1; k < liste.lenght; k++){
      if(liste[i].toUpperCase.compareTo(liste[k].toUpperCase) > 0){
        sjekk = k;
      }
    }
    if(sjekk != i){
      String buffer = liste[buffer];
      liste[buffer] = liste[i];
      liste[i] = buffer;
    }
  }
  return true;
}

public String millaSnakk(String ord){

  if(ord == null) return null;

  ord = ord.toUpperCase();
  String output = ord + ":";

  for(Char c : ord.toCharArray()){
    boolean sjekk = true;

    for(String s : liste){

      if(s.toUpperCase.charAt(0).equals(c)){
        output += " " + s;
        sjekk = false;
        break;
      }

    }

    if(sjekk){
      output += " Ukjent";
    }

  }
  return output;
}


public boolean skrivAlfabetTilFil(String filnavn, String separator){
  String output = separator + "\n";
  for(Sting s : liste){
    output += s + separator;
  }
  FileWrite fw = null;
  BufferWrite bf = null;
  try{
    fw = new FileWrite(filnavn, true);
    bf = new BufferWrite(fw);
    bf.write(output);
    return true;
  }catch (IOException | Exception e) {
    e.printStackTrace();
    return false;
  }finally{
    if(fw != null) fw.close();
    if(bf != null) bf.close();
  }

}

public String[] lesAlfabetFraFil(String filnavn){
try{
  FileRead fr = new FileReader(filnavn);
  BufferRead br = new BufferRead(fr);
  String separator = null;
  String alpha = null;
  if(br.readLine()!=null){
    separator = br.readLine();
  }
  if(br.readLine()!=null){
    alpha = br.readLine();
  }
  return alpha.split(separator);
}catch(IOException | FileNotFoundExcetion | NullPointerException e){
  e.printStackTrace();
}
}

//--------------------------------------

public static void main (String[] args){

String[] muligheter = {"Nytt alfabet","Legg til ord", "Bokstaver ord", "Skriv alfabet til fil", "Skriv alfabetet på skjerm","Sorter alfabetet", "Avslutt"};
	final int NYTT_ALFABET = 0;
	final int LEGG_TIL_ORD = 1;
	final int BOKSTAVER_ORD = 2;
	final int SKRIV_TIL_FIL = 3;
	final int LIST_ALFATBET = 4;
	final int SORTER = 5;
	final int AVSLUTT = 6;

	int valg = showOptionDialog(null, "Velg", "Eksamen juni 2017", YES_NO_OPTION, INFORMATION_MESSAGE, null, muligheter, muligheter[0]);

  Bokstaveringsalfabet ba = null;

	while (valg != AVSLUTT){
		switch (valg){
			case NYTT_ALFABET:
/* OPPGAVE 2 a) skal inn her */
      String[] valger = {"Fra Fil", "skriv"};
      int sup = showOptionDialog(null, "Velg", "s", YES....., INFORMATION_MESSAGE, null, valger, valger[0]);
      switch(valger){
        case 0:
          ba = new Bokstaveringsalfabet(scanner navn, scanner filnavn);
          break;
        case 1:
          ba = new Bokstaveringsalfabet(scanner navn, scanner separator, scanner ordstring);
          break;
      }
			break;
			case LEGG_TIL_ORD:
			break;
			case BOKSTAVER_ORD:
            ShowMessageDialog(null, millaSnakk(scanner ord));
			break;
			case SKRIV_TIL_FIL: // ikke en del av oppgaven
			break;
			case LIST_ALFATBET:
			break;
			case SORTER:
			break;
			default: break;
		}
valg = showOptionDialog(null, "Velg", "Eksamen juni 2017", YES_NO_OPTION,
 			INFORMATION_MESSAGE, null, muligheter, muligheter[0]);
	}
}

}