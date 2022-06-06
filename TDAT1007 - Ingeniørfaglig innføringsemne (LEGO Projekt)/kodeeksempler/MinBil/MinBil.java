/*    MinBil.java G.S 2011 - 08 - 24
* Program som styrer en bil med 2 motorer. Bilen oppfører seg slik:
* 1. kjør framover
* 2. Rygg
* 3. Sving høyre
* 4. Endre hastighet på motorene
* 5. Kjør framover igjen
*/

import lejos.hardware.motor.*;
import lejos.hardware.lcd.*;

public class MinBil
{
  public static void main (String[] args)  throws Exception
  {
     Motor.A.setSpeed(450);  // sett hastighet (toppfart = 900)
	 Motor.C.setSpeed(450);

     // Kjør framover
     System.out.println("Fram 2000 ms");
     Motor.A.forward();  // Start motor A - kjør framover
     Motor.C.forward();  // Start motor C - kjør framover
     Thread.sleep(2000); // Vent i 1000 ms før vi går videre i koden

     // Rygg
     LCD.clear();		 // Rens lcd-skjermen
     System.out.println("Bak 2000");
     Motor.A.backward();
     Motor.C.backward();
     Thread.sleep(2000);

     // Sving
     LCD.clear();
     System.out.println("Snu 180 grader");
     Motor.A.rotate(180);  // roter 180 gr med motor A
     Motor.C.stop();
     while (Motor.A.isMoving()) Thread.yield();  // vent til rotasjon er ferdig

     // Kjør framover
     LCD.clear();
     System.out.println("Fram 1000");
     Motor.A.forward();
     Motor.C.forward();

     Thread.sleep(1000);
     Motor.A.stop();
     Motor.C.stop();
  }
}
