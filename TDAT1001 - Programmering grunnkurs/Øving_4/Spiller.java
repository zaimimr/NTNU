

class Spiller{
  int sumPoeng;
  int ID;
  static int nextID = 1;
  java.util.Random terning = new java.util.Random();

  public Spiller(){
    this.sumPoeng = 0;
    this.ID = nextID;
    nextID++;
  }

  public int getSumPoeng(){
    return this.sumPoeng;
  }

  public void setSumPoeng(int poeng){
    this.sumPoeng += poeng;
  }

  public int getID(){
    return this.ID;
  }

  public int kastTerning(){
    return terning.nextInt(6)+1;
  }

  public boolean erFerdig(){
    if(this.sumPoeng == 100){
      return true;
    }
    return false;
  }

}
