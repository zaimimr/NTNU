/* SensorTest.java  GS - 2015-08-20

Program som tester bruk av trykk-sensor og farge-sensor (ev3).
Programmet g�r i l�kke og leser av og skriver avlest-farge p� EV3ens LCD-skjerm.
L�kken/ programemt avsluttes n�r trykksensoren trykkes p�.

*/

import lejos.hardware.motor.*;
import lejos.hardware.lcd.*;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.NXTTouchSensor;
import lejos.hardware.sensor.NXTLightSensor;
import lejos.hardware.sensor.NXTColorSensor;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.NXTUltrasonicSensor;
import lejos.hardware.port.Port;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.Keys;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.*;


public class SensorTest{
	public static void main (String[] arg) throws Exception  {
		try{

			Brick brick = BrickFinder.getDefault();
			Port s1 = brick.getPort("S1"); // fargesensor
			Port s2 = brick.getPort("S2"); // trykksensor
			Port s3 = brick.getPort("S3"); // ultrasonisksensor

			EV3 ev3 = (EV3) BrickFinder.getLocal();
			TextLCD lcd = ev3.getTextLCD();
			Keys keys = ev3.getKeys();

			lcd.drawString("Trykk for starte", 0, 1);
			keys.waitForAnyPress();

			/* Definerer en fargesensor og fargeAvleser */
			EV3ColorSensor fargesensor = new EV3ColorSensor(s1); // ev3-fargesensor
			SampleProvider fargeLeser = fargesensor.getMode("RGB");  // svart = 0.01..
			//SampleProvider fargeLeser = fargesensor.getColorIDMode();  // OBS: Funker ikke - f�r arrayfeil. can identify 8 unique colors (NONE, BLACK, BLUE, GREEN, YELLOW, RED, WHITE, BROWN).

			float[] fargeSample = new float[fargeLeser.sampleSize()];  // tabell som innholder avlest verdi

			/* Definerer en trykksensor */
			SampleProvider trykksensor = new EV3TouchSensor(s2);
			float[] trykkSample = new float[trykksensor.sampleSize()]; // tabell som inneholder avlest verdi

			/* Definerer en ultrasonisksensor */
			EV3UltrasonicSensor ultraSensor = new EV3UltrasonicSensor(s3);
			SampleProvider ultraLeser = ultraSensor.getDistanceMode();
			float[] ultraSample = new float[ultraLeser.sampleSize()]; // tabell som inneholder avlest verdi

			boolean fortsett  = true;

			while(fortsett) {
				fargeLeser.fetchSample(fargeSample, 0);  // hent verdi fra fargesensor
				lcd.drawString("Farge: " + fargeSample[0], 0, 3);
				//System.out.println("Farge: " + fargeSample[0]);

				ultraLeser.fetchSample(ultraSample, 0);
				lcd.drawString("Avstand: " + ultraSample[0], 0,5);

				if (trykkSample != null && trykkSample.length > 0){
					trykksensor.fetchSample(trykkSample, 0);
					if (trykkSample[0] > 0){
						System.out.println("Avslutter");
						fortsett = false;
					}
				}else System.out.println("Sample er null eller 0");
			} // while
		}catch(Exception e){
			System.out.println("Feil: " + e);
		} //try-catch
	} // main
} // class SensorTest

/*

*/
