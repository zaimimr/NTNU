//10.9.1 - Lag en tegning av "Smiley". Legg tegningen først i en applikasjon, deretter i en applet.
import java.awt.*;
import javax.swing.*;

public class ov7b_applet extends JApplet{
	public void init(){
		add(new Tegning());
	}
}

//@SuppressWarnings("serial")
class Tegning extends JPanel{
  private static final long serialVersionUID = 56754325675435L;
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    setBackground(Color.ORANGE);

    g2.translate(getWidth()/2, getHeight()/2);
    g2.rotate(Math.toRadians(90));
    g2.translate(-getWidth()/2, -getHeight()/2);

    g2.setFont(new Font("TimesRoman", Font.PLAIN, 89));
    g2.drawString(":)",60,105);
    g2.drawOval(25,25,125,125);
  }
}
