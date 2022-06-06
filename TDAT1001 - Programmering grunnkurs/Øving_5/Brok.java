class Brok{

  int teller;
  int nevner;

  public Brok(int teller, int nevner){
    this.teller = teller;

    if(nevner !=0){
      this.nevner = nevner;
    } else{
      throw new IllegalArgumentException("nevner kan ikke være 0");
    }

  }

  public Brok(int teller){
    this.teller = teller;
    this.nevner = 1;
  }

  public int getTeller(){
    return teller;
  }
  public int getNevner(){
    return nevner;
  }

  public void summer(Brok b){
    if(this.nevner != b.getNevner()){
      this.teller = this.teller*b.getNevner()+b.getTeller()*this.nevner;
      this.nevner *= b.getNevner();
    } else {
      this.teller +=b.getTeller();
    }
  }

  public void subtraher(Brok b){
    if(this.nevner != b.getNevner()){
      this.teller = this.teller*b.getNevner()-b.getTeller()*this.nevner;
      this.nevner *= b.getNevner();
    } else {
      this.teller -=b.getTeller();
    }
  }

  public void divider(Brok b){
    this.teller *= b.getNevner();
    this.nevner *= b.getTeller();
  }

  public void multipliser(Brok b){
    this.teller *=b.getTeller();
    this.nevner *=b.getNevner();
  }

  public void forkort(){
    for( int i = 2; i < this.nevner/2; i++){
      if(this.teller % i == 0){
        if(this.nevner % i == 0){
          this.teller = this.teller / i;
          this.nevner = this.nevner / i;
        }
      }
    }
  }

  public String toString(){
    return this.teller + " / " + this.nevner;
  }
}
