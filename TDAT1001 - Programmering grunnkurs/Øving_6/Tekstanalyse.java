import static javax.swing.JOptionPane.*;

class Tekstanalyse{

  int[] antallTegn;
  String tekst;

  public Tekstanalyse(String tekst){

    this.tekst = tekst;
    antallTegn = new int[30];

    start();
    Analyse();
  }

  public void start(){

    for (int i = 0; i < antallTegn.length; i++){
      antallTegn[i] = 0;
    }
  }

  public void Analyse(){

    for (int i = 0; i < tekst.length(); i++){

      char tegn = tekst.charAt(i);
      int verdi = tegn;
    //  System.out.println("UniCode-verdien til tegnet " + tegn + " på posisjon " + i + " er " + verdi);

      if(verdi >= (int)'a' && verdi <=(int)'z'){
        antallTegn[verdi-(int)'a'] ++;
      } else if(verdi >= (int)'A' && verdi <=(int)'Z'){
         antallTegn[verdi-(int)'A'] ++;
      } else if(verdi == 230 || verdi == 198){ // æ / Æ
        antallTegn[26]++;
      } else if(verdi == 248 || verdi == 216){ // ø / Ø
        antallTegn[27]++;
      } else if(verdi == 229 || verdi == 197){ // å / Å
         antallTegn[28]++;
      } else{
         antallTegn[29]++;
      }
    }
  }

  public int antallUlike(){

    int antall = 0;

    for (int i = 0; i < antallTegn.length-1; i++) {
      if(antallTegn[i]!=0){
      //  System.out.println((char)(i+(int)'a'));
        antall++;
      }
    }

    return antall;
  }

  public int antallBokstaver(){
    int antall = 0;

    for (int i = 0; i < antallTegn.length-1; i++) {
      antall += antallTegn[i];
    }

    return antall;
  }

  public double erIkkeBokst(){

    double bokst = antallBokstaver();
    double ikkeBokst = antallTegn[29];

    double prosent = ikkeBokst/(bokst+ikkeBokst)*100;

    return prosent;
  }

  public int antallGanger(String b){

    if(b.length() == 1){

      char tegn = b.charAt(0);
      int verdi = tegn;

      if(verdi >= (int)'a' && verdi <=(int)'z'){
        return antallTegn[verdi-(int)'a'];
      } else if(verdi >= (int)'A' && verdi <=(int)'Z'){
         return antallTegn[verdi-(int)'A'];
      } else if(verdi == 230 || verdi == 198){ // æ / Æ
        return antallTegn[26];
      } else if(verdi == 248 || verdi == 216){ // ø / Ø
        return antallTegn[27];
      } else if(verdi == 229 || verdi == 197){ // å / Å
        return antallTegn[28];
      } else throw new IllegalArgumentException("Error: Velg en bokstan");

    }else throw new IllegalArgumentException("Error: Skriv inn kun en bokstav");
  }

  public void flestGanger(){
    int hoyest = 0;
    int teller = 0;
    char[] tegn = new char[antallTegn.length-1];
    for (int i = 0; i < antallTegn.length-1; i++ ) {
      if(hoyest <= antallTegn[i]){
        hoyest = antallTegn[i];
      }
    }
    for (int x = 0; x < antallTegn.length-1; x++) {
      if( antallTegn[x] == hoyest){
        tegn[teller] = (char) (x + 97);
        System.out.print(tegn[teller] + " ");
        teller++;

      }
    }

  }

}



// a = 97
// z = 122
// A = 65
// Z = 90
// æ = 166
// ø = 184
// å = 165
// Æ = 8224
// Ø = 732
// Å = 8230
