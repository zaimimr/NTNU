import static javax.swing.JOptionPane.*;

class Stjerne{

  public static void main(String[] args){
    int antall;
/*
    do{
      antall= Integer.parseInt(showInputDialog("Velg antall linjer"));

        for(int y = 0; y < antall; y++){
          for (int x = 0; x < antall; x++) {
            if(x <= y){
              System.out.print("*");
            }else{
              System.out.print(" ");
            }
          }
          System.out.println();
        }
   }while (antall>0);
*/

     antall= Integer.parseInt(showInputDialog("Velg antall linjer"));

       for(int y = 0; y < antall; y++){
         for (int x = 0; x < antall*2; x++) {
           if(x <= antall+=y && x >= antall-=y ){
             System.out.print("*");
           }else{
             System.out.print(" ");
           }
         }
         System.out.println();
       }

  }
}
