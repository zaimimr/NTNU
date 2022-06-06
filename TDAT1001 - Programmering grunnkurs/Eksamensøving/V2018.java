class Navn{
  private String fornavn;
  private String etternavn;

  public Navn(String fornavn, String etternavn){
    this.fornavn = fornavn;
    this.etternavn = etternavn;
  }
  public void setFornavn(String fornavn){
    this.fornavn = fornavn;
  }
  public void setEtternavn(String etternavn){
    this.etternavn = etternavn;
  }
  public String getFornavn(){
    return this.fornavn;
  }
  public String getEtternavn(){
    return this.etternavn;
  }
  public boolean equals(Object obj){
    if(obj == null){
      return false;
    }
    if(obj == this){
      return true;
    }
    if(obj instanceof Navn){
      Navn n = (Navn) obj;
      if(this.fornavn.equals(n.getFornavn()) && this.etternavn.equals(n.getEtternavn())){
        return true
      }
    }
    return false;
  }
  public String toString(){
    return this.fornavn + " " + this.etternavn;
  }
}

class Kunstverk{
  private final String navn;
  private final String type;
  private final Navn kunstner;
  private final int nr;
  private int kopier;

  public Kunstverk(String navn, String type, Navn kunstner, int nr){
    this.navn = navn;
    this.type = type;
    this.kunstner = kunstner;
    this.nr = nr;
    this.kopier = 0;
  }

  public String getNavn(){return this.navn;}
  public String getType(){return this.type;}
  public Navn getKunstner(){return this.kunstner;}
  public int getNr(){return this.nr;}
  public int getKopier(){return this.kopier;}

  public boolean equals(Object obj){
    if(obj == null){return false;}
    if(obj == this){return true;}
    if(obj instanceof Kunstverk){
      Kunstverk kv = (Kunsverk) obj;
      if(this.nr == kv.getNr
      && this.navn.equals(kv.getNavn())
      && this.kunstner.equals(kv.getKunstner())) return true;
    }
    return false;
  }
}

class Utstilling{
  private String navn;
  private Kunstverk[] list;
  private final String filnavn = "kunstverk.ser";

  public Utstilling(String navn){
    this.navn = navn;
    this.list = lesFraFil(filnavn);
  }

  public String getNavn(){
    return this.navn;
  }

  public Kunstverk[] getKunstverk(){
    return this.list;
  }

  public Kunstverk[] sortert(){
    
  }
}

class V2018{

}
