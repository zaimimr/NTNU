class test{
  public static String[] tekst = new String[12];
  public static int teller = 0;
  public static void main(String[] args){
    if(merTekst("skjer")){
      for(String s : tekst){
        System.out.println(s);
      }
    }
  }

  public static boolean merTekst(String what){
    tekst[teller] = what;
    return true;
  }
}
