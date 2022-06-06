/* Programmet skriver en tekst p� skjermen til EV3en, venter 500 ms.
   Dernest skrives en ny tekst og EV3en venter til det trykkes p� en knapp f�r
   programmet avsluttes.
 */
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.*;
import lejos.hardware.lcd.*;

public class HelloWorld {

       public static void main(String[] args)  throws Exception{
              System.out.println("Hei EV3 - du er kulest n�r du virker!");
              Thread.sleep(500);
              EV3 ev3 = (EV3) BrickFinder.getLocal();
              TextLCD lcd = ev3.getTextLCD();
              Keys keys = ev3.getKeys();
              lcd.drawString("Hello World", 4, 4);
              //keys.waitForAnyPress();

              Motor.A.setSpeed(900);
              Motor.C.setSpeed(900);

              for(int i = 0; i < 4; i++){
                //Kjør fremover
                LCD.clear();
                System.out.println("Kjør frem 3 sek");
                Motor.A.forward();
                Motor.C.forward();
                Thread.sleep(3000);

                //Sving
                LCD.clear();
                System.out.println("snu 90*");
                Motor.A.rotate(180);
                Motor.C.stop();
                while(Motor.A.isMoving()) Thread.yield();
            }
            Thread.sleep(1000);
            Motor.A.stop();
            Motor.C.stop();

            lcd.drawString("Goodbye World", 4, 4);

       }
}
