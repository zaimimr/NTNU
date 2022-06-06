class Resturant{
  private String name;
  private int etabYear;
  private Bord tables;

  public Resturant(String name, int year, int nrTable){
      this.name = name;
      this.etabYear = year;
      this.tables = new Bord(nrTable);
  }

  public String getName(){
    return this.name;
  }

  public void setName(String name){
    this.name = name;
  }

  public int getYear(){
    return this.etabYear;
  }

  public int getAge(){
    return (2019 - this.etabYear);
  }

  public int freeTables(){
    return tables.freeTables();
  }

  public int busyTables(){
    return tables.busyTables();
  }

  public void reserveTable(String name, int nrTable){
    for(int i = 0; i < nrTable; i++){
      tables.reserveTable(name);
    }
  }

  public int[] findTable(String name){
    return tables.getTables(name);
  }

  public void cleanTable(int[] list){
    for(int i : list){
      tables.cleanTable(i);
    }
  }

  public String toString(){
    return name + " \n" + tables.toString();
  }
}
