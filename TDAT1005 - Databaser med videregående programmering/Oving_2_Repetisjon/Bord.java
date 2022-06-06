class Bord{
  private String[] names;

  public Bord(int tables){
    names = new String[tables];
  }

  public String[] getNames(){
    return this.names;
  }

  public int freeTables(){
    int free = 0;
    for(int i = 0; i < names.length; i++){
      if(names[i] == null){
        free++;
      }
    }
    return free;
  }

  public int busyTables(){
    int busy = 0;
    for(int i = 0; i < names.length; i++){
      if(!(names[i] == null)){
        busy++;
      }
    }
    return busy;
  }

  public boolean cleanTable(int table){
    if(!(table >= 0 && table <= names.length-1)){
      throw new IndexOutOfBoundsException("Out of bound");
    }
    if(!(names[table] == null)){
      names[table] = null;
      return true;
    }
    return false;
  }

  public boolean cleanTable(String name){
    if(name == null){
      throw new NullPointerException("Null String");
    }

    for(String table : names){
      if(name.equals(table)){
        table = null;
      }
    }
    return true;
  }

  public boolean reserveTable(String name){
    if(freeTables() == 0){
      return false;
    }

    for(int i = 0; i < names.length; i++){
      if(names[i] == null){
        names[i] = name;
        return true;
      }
    }
    return false;
  }

  public int[] getTables(String name){
    int[] list = new int[busyTables()];
    int counter = 0;
    for(int i = 0; i < names.length; i++){
      if(names[i] != null){
        if(names[i].equals(name)){
          list[counter] = i;
          counter ++;
        }
      }
    }
    return list;
  }

  public String toString(){
    String output = "";
    for(int i = 0; i < names.length; i++){
      output += i + " " + names[i] + "\n";
    }
    return output;
  }
}
