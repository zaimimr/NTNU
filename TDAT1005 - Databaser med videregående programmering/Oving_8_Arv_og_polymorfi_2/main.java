import java.io.*;
class main{
  private static final String filnavn = "Tribuner.ser";
  public static void main(String[] args) {
      Tribune[] tribuner = new Tribune[4];
      Billett[][] billetter = new Billett[4][];
      tribuner[0] = new StaaTribune("Ståtibune 1", 20, 20);
      tribuner[1] = new StaaTribune("Ståtibune 2", 15, 25);
      tribuner[2] = new SitteTribune("Sittetribune", 50, 50, 5);
      tribuner[3] = new VIPTribune("VIP lounge", 15, 100, 3, 5);

//      tribuner = lesFraFil(filnavn);

      billetter[0] = tribuner[0].kjopBillett(11);
      billetter[1] = tribuner[1].kjopBillett(new String[]{"Zaim", "Juni", "Nikolai", "Tiril", "Kasper", "Helene"}); //6
      billetter[2] = tribuner[2].kjopBillett(10);
      billetter[3] = tribuner[3].kjopBillett(new String[]{"Espen", "Johan", "Audun"}); // 3

      printUtBilletter(billetter);
      tribuner = SorterTabellIntekt(tribuner);
      printUtTribuner(tribuner);
      skrivTilFil(filnavn, tribuner);
  }

  public static void printUtBilletter(Billett[][] berer){
    int teller;
    for(Billett[] ber : berer){
      teller = 0;
      for(Billett b : ber){
        System.out.println(b.toString());
        teller++;
      }
      System.out.println("Antall billetter solgt: " + teller);
    }
  }

  public static void printUtTribuner(Tribune[] ter){
    for(Tribune t : ter){
      System.out.println();
      System.out.println(t.toString());
    }
  }

  public static Tribune[] SorterTabellIntekt(Tribune[] ter){
    if(ter.length == 0) return null;
    if(ter.length == 1) return ter;


    Tribune t = null;
    int index;
    for(int i = 0; i < ter.length-1; i++){
      index = i;
      for(int j = i+1; j < ter.length; j++){
        if(ter[index].compareTo(ter[j]) < 0){
          index = j;
        }
      }
      t = ter[index];
      ter[index] = ter[i];
      ter[i] = t;
    }
    return ter;
  }

  public static boolean skrivTilFil(String filNavn, Tribune[] ter){
    try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filNavn))){
      oos.writeObject(ter);
      return true;
    }catch(IOException e){
      e.printStackTrace();
    }
    return false;
  }

  public static Tribune[] lesFraFil(String filNavn){
    try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filNavn))){
      Tribune[] lestFraFil = ((Tribune[]) ois.readObject());
      return lestFraFil;
    }catch(IOException e){
      e.printStackTrace();
    }catch(ClassNotFoundException e){
      e.printStackTrace();
    }
    return null;
  }
}
