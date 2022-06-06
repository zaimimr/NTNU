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
import lejos.hardware.Sound;
import java.io.File;

class Batmobil{
// Metoder for hastighetssetning
	public static void motorHSpeed(int x){
		Motor.A.setSpeed(x);
		Motor.B.setSpeed(x);
	}
	public static void motorVSpeed(int x){
		Motor.C.setSpeed(x);
		Motor.D.setSpeed(x);
	}
// Metoder for kjøring
	public static void motorHDrive(boolean b){
		if(b == true){
			Motor.A.forward();
			Motor.B.forward();
		} else{
			Motor.A.backward();
			Motor.B.backward();
		}
	}
	public static void motorVDrive(boolean b){
		if(b == true){
			Motor.C.forward();
			Motor.D.forward();
		} else{
			Motor.C.backward();
			Motor.D.backward();
		}
	}

	public static void main(String[] arg) throws Exception {
		try{
//Initialisering av Brikken
			Brick brick = BrickFinder.getDefault();
//Initialisering av protene
			Port sensFarge = brick.getPort("S2");
			Port sensPress = brick.getPort("S1"); //trykksensor
//Initialisering av ev3, knapper og skjerm
			EV3 ev3 = (EV3) BrickFinder.getLocal();
			TextLCD lcd = ev3.getTextLCD();
			Keys keys = ev3.getKeys();
//Vente til knapp er trykket
			lcd.drawString("Begynn Race", 0, 1);
		  keys.waitForAnyPress();
//Initialisering av fargesensor
			EV3ColorSensor fargesensor = new EV3ColorSensor(sensFarge);
			SampleProvider fargeLeser = fargesensor.getMode("RGB");
			float[] fargeSample = new float[fargeLeser.sampleSize()];
//Initialisering av trykksensor
			SampleProvider trykksensor = new EV3TouchSensor(sensPress);
			float[] trykkSample = new float[trykksensor.sampleSize()];
//Sette motor hastighet
			motorHSpeed(700);
			motorVSpeed(700);
//Initialisering av lokke variabel og sving teller (i)
			boolean lokke = true;
			int i = 0;

			while(lokke){
				//Begynner lesing av farger
				fargeLeser.fetchSample(fargeSample, 0);
				//hvis den finner svart
				if(fargeSample[0] < 0.035){
					motorHDrive(true);
					motorVDrive(true);
					//hvis den ikke finner svart
				}else if(fargeSample[0] > 0.07){
					//hvis ingen til høyre, sving til venstre
					if (i == 15){
						motorHSpeed(300);
						motorVSpeed(150);
						motorHDrive(true);
						motorVDrive(false);
						Thread.sleep(20);
						//sving først til høyre
					}else{
						motorHSpeed(150);
						motorVSpeed(300);
						motorHDrive(false);
						motorVDrive(true);
						Thread.sleep(20);
						//teller
						i++;
					}
					//kjører rett frem og setter i = 0
				}else{
					Thread.sleep(10);
					motorHSpeed(700);
					motorVSpeed(700);
					motorHDrive(true);
					motorVDrive(true);
					i = 0;
				}
			}
      if (trykkSample != null && trykkSample.length > 0){
        trykkSensor.fetchSample(trykkSample, 0);
    	 	if (trykkSample[0] > 0){
    			System.out.println("Avslutter");
    		 	lokke = false;
    		}
    	}else System.out.println("Sample er null eller 0");

		}catch(Exception e){
			System.out.println("Feil: " + e);
			e.printStackTrace();
		}
	}
}
