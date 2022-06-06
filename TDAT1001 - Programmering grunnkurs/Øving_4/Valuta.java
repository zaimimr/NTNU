
  class Valuta {

    Oline o = new Oline("Oline");
    

    o.getNavn();
    o.regntilnok(tall)

    String name;
    double exRate;

    public Valuta(String name,double exRate){
      this.name = name;
      this.exRate = exRate;
    }

    public String getName(){
      return name;
    }
    public double getExRate(){
      return exRate;
    }

    public double convertToN(double Val){
      return Math.round(Val/exRate*100)/100D;
    }
    public double convertFromN(double NOK){
      return Math.round(NOK * exRate*100)/100D;
    }

}
