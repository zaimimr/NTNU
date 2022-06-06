//10.9.1 - Lag en tegning av "Smiley". Legg tegningen først i en applikasjon, deretter i en applet.
import java.awt.*;
import javax.swing.*;
import java.applet.*;

class Vindu extends JFrame{
  private static final long serialVersionUID = 56754325675435L;

  public Vindu(String title){
    setTitle(title);
    setSize(500,500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Tegning tegn = new Tegning();
    add(tegn);
  }
}

//@SuppressWarnings("serial")
class Tegning extends JPanel{
  private static final long serialVersionUID = 56754325675435L;

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    setBackground(Color.ORANGE);
    
    }
}

class ov7b{
    public static void main (String[]args){
      Vindu vindu = new Vindu("Smilefjes");
      vindu.setVisible(true);
    }
}
