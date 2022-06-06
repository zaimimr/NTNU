/* FolgLinje.java  GS - 2012-01-20

 * Program som gj�r at en enkel robot f�lger en sort linje
 * Du trenger en enkel robot som kan svinge
 * en lyssensor koblet til sensor 1 - pekende nedover
 * en trykksensor koblet til sensor 2 - pekende rett fram i g� retningen
 */
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.robotics.SampleProvider;

public class FolgLinje_enkel{

  public static void main (String[] arg) throws Exception  {

	// Definerer sensorer:
	Brick brick = BrickFinder.getDefault();
    Port s1 = brick.getPort("S1"); // fargesensor
 	Port s2 = brick.getPort("S2"); // trykksensor

	EV3ColorSensor fargesensor = new EV3ColorSensor(s1); // ev3-fargesensor
	SampleProvider fargeLeser = fargesensor.getMode("RGB");  // svart = 0.01..
	float[] fargeSample = new float[fargeLeser.sampleSize()];  // tabell som innholder avlest verdi

	/* Definerer en trykksensor */
	SampleProvider trykksensor = new EV3TouchSensor(s2);
	float[] trykkSample = new float[trykksensor.sampleSize()]; // tabell som inneholder avlest verdi

	// Setter hastighet p� roboten
    Motor.A.setSpeed(400);
    Motor.C.setSpeed(400);

    Motor.B.setSpeed(900);  // vifte arm

	// Beregn verdi for svart
	int svart = 0;
	for (int i = 0; i<100; i++){
		fargeLeser.fetchSample(fargeSample, 0);
		svart += fargeSample[0]* 100;
	}
	svart = svart / 100 + 5;
	System.out.println("Svart: " + svart);

  

	boolean fortsett = true;

	while (fortsett){ 	// Fortsett s� lenge roboten ikke treffer noe
	   fargeLeser.fetchSample(fargeSample, 0);
       if (fargeSample[0]*100 > svart){   // sjekk sort linje
       	  Motor.A.forward();
          Motor.B.forward();        // viftearm
          Motor.C.stop();  		// snu i  200 millisekund
          Thread.sleep(100);
          System.out.println("hvit");
       } else {
		   // Kj�r framover
		   Motor.A.forward();
		   Motor.C.forward();
		   System.out.println("svart");
	   }

		trykksensor.fetchSample(trykkSample, 0);
	 	if (trykkSample[0] > 0){
			System.out.println("Avslutter");
		 	fortsett = false;
		}

 	    Motor.A.stop();
        Motor.C.stop();
        Motor.B.stop();
    }
  }
}

/*

*/
