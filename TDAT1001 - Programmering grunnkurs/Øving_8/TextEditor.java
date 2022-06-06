
class TextEditor{

  String text;
  String oG;

  public TextEditor(String text){
    this.text = text;
    this.oG = text;
  }

  public int antallOrd(){
    String[] ord = text.split(" ");
    int antall = ord.length;
    return antall;
  }

  public int gjenOrdLengde(){
    String[] ord = text.split(" ");
    int lengde = 0;

    for(int i = 0; i < ord.length; i++){
      lengde += ord[i].length();
    }

    lengde /= ord.length;
    return lengde;
  }

  public int gjenOrdPerPeriode(){
    String[] tegn = text.split("[.\\!\\:\\?]");
    String[] ord = text.split(" ");
    return ord.length / tegn.length;
  }

  public String ordBytte(String finn, String bytt){
    this.text =  text.replaceAll(" " + finn +" "," " + bytt + " ");
    return text;
  }

  public String orginal(){
    return oG;
  }

  public String rage(){
    this.text = text.toUpperCase();
    this.text = text.replaceAll("æ","Æ");
    this.text = text.replaceAll("ø","Ø");
    this.text = text.replaceAll("å","Å");

    return text;
  }


}
