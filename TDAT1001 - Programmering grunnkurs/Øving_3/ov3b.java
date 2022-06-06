import static javax.swing.JOptionPane.*;

class ov3b{

  public static boolean primTest(int tall){
    int temp;
if (tall == 1){
  return true;
}
    for ( int i = 2; i <= tall/2; i++){
      temp = tall%i;
      if(temp == 0){
        return true;
      }
    }


    return false;
  }

  public static void main(String[] args){

    int inputTall = Integer.parseInt(showInputDialog("Skriv inn et tall: "));

    if(primTest(inputTall)) {
      showMessageDialog(null, inputTall + " er ikke et primtall");
    } else {
      showMessageDialog(null, inputTall + " er et primtall");
     }
  }

}
