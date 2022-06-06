import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

public class Katapult_Visualisering {

    JFrame frame;
    DrawPanel drawPanel;

    double grav = 1;
    double X = 0;
    double Y = 0;
    double Y0 = 350;
    double X1 = 0;
    double ay = grav;
    double V = 15;
    double vx;
    double vy;
    int angle = 30;
    double alpha = angle * Math.PI / 180;
    double t = 0;

    ArrayList<Double> treffFart = new ArrayList<Double>();
    ArrayList<Double> treffTid = new ArrayList<Double>();

    public static void main(String[] args) {
        new Katapult_Visualisering().go();
    }

    private void go() {
        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel();

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setResizable(false);
        frame.setSize(500, 500);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        X1 = frame.getWidth() - 225;
        //moveIt();
        Katapult();

    }

    class DrawPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        int a, b;
        int width;
        int height;

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(Color.RED);
            g.drawString("X: " + (int) X, 10, 10);
            g.drawString("Y: " + (int) Y, 10, 20);
            g.drawString("Tid: " + (int) t, 10, 30);
            g.drawString("Fart: " + (int) V, 10, 40);
            g.fillOval((int) X, (int) Y, 30, 30);
            drawCup(g);
            g.drawLine(0, b + height, frame.getWidth(), b + height);
        }

        public void drawCup(Graphics g) {
            a = frame.getWidth() - 100;
            b = frame.getHeight() - 120;
            height = 75;
            width = 50;
            g.drawRect(a, b, width, height);
        }
    }

    private void Katapult() {
        for (V = 0; V < 20; V++) {
            for (angle = 20; angle < 90; angle++){
                for (t = 0; t < 40; t += 1) {
                    alpha = angle * Math.PI / 180;
                    X = V * Math.cos(alpha) * t;
                    Y = (Y0 - (V * Math.sin(alpha) * t) + (ay * t * t / 2));

                    if (Y <= (frame.getHeight() - 100) && X >= (frame.getWidth() - 250) && X <= (frame.getWidth() - 200)) {
                        treffFart.add(V);
                        treffTid.add(t);
                        System.out.print((int) V + " " + (int) t);
                    }

                    if (Y >= frame.getHeight() - 10) {
                        break;
                    }

                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    frame.repaint();
                }
        }
    }

}



    // vx = V * Math.cos(alpha);
    // vy = V * Math.sin(alpha);

    private void moveIt(){
        while (!(Y <= (frame.getHeight()-100) && X >= (frame.getWidth() - 250) && X <= (frame.getWidth()-200))){
            X = V * Math.cos(alpha) * t;
            Y = (Y0 - (V*Math.sin(alpha) * t) + (ay * t * t / 2));
            t+=0.1;
            try{
                Thread.sleep(10);
            }catch (Exception e){e.printStackTrace();}
            frame.repaint();
        }
    }

    }
