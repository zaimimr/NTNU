import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;

final public class Test{

    JFrame frame;
    DrawPanel drawPanel;

    double grav = 1;
    double X = 0;
    double Y = 0;
    double Y0 = 350;
    double X1 = 0;
    double ay = grav;
    double V = 0;
    double vx;
    double vy;
    double alpha = 45 * Math.PI / 180;
    double t  = 0;

    ArrayList<Double> treff = new ArrayList<Double>();

    private void Katapult(){
      while(true){

        V ++;

        double vx = V * Math.cos(alpha);
        double vy = V * Math.sin(alpha);

        frame.repaint();
      }
    }




/*  private void expand() {
      double[] newArray = new double[treff.length + 1];
      System.arraycopy(treff, 0, newArray, 0, treff.length);
      studenter = newArray;
    }
    */

    public static void main(String[] args){
        new Test().go();
    }

    private void go(){
        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel();

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setResizable(false);
        frame.setSize(500, 500);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        X1 = frame.getWidth() - 225;
        moveIt();
    }

    class DrawPanel extends JPanel{
        private static final long serialVersionUID = 1L;

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(Color.RED);
            g.drawString("X: " + (int) X, 10, 10);
            g.drawString("Y: " + (int) Y, 10, 20);
            g.drawString("Tid: " + (int) t, 10, 30);
            g.fillOval((int)X, (int)Y, 30, 30);
            g.drawRect(frame.getWidth()-250,frame.getHeight()-100,50,100);
        }
    }

}
