
class NyString{

  String text;

  public NyString(String text){
    this.text = text;
  }

  public String forkorting(){
    String[] ord = text.split(" ");
    String forkort = "";
    for(int i = 0; i < ord.length; i++){
      forkort += ord[i].charAt(0);
    }
    return forkort;
  }

  public String fjernTegn(char c){
    return text.replaceAll(""+c,"");
  }

}
