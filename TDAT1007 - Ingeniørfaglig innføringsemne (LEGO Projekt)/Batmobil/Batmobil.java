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

class Batmobil{
	public static void main(String[] arg) throws Exception {
		try{

			Brick brick = BrickFinder.getDefault();

			Port sensFarge = brick.getPort("S2");
			Port sensPress = brick.getPort("S1"); //trykksensor

			EV3 ev3 = (EV3) BrickFinder.getLocal();
			TextLCD lcd = ev3.getTextLCD();
			Keys keys = ev3.getKeys();

			lcd.drawString("Begynn Race", 0, 1);
			keys.waitForAnyPress();

			EV3ColorSensor fargesensor = new EV3ColorSensor(sensFarge);
			SampleProvider fargeLeser = fargesensor.getMode("RGB");
			float[] fargeSample = new float[fargeLeser.sampleSize()];

			SampleProvider trykksensor = new EV3TouchSensor(sensPress);
			float[] trykkSample = new float[trykksensor.sampleSize()];

			Motor.A.setSpeed(700);
			Motor.B.setSpeed(700);
			Motor.C.setSpeed(700);
			Motor.D.setSpeed(700);

			boolean lokke = true;
			int i = 0;

			while(lokke){
				fargeLeser.fetchSample(fargeSample, 0);
				System.out.println(fargeSample[0]);

				if(fargeSample[0] < 0.035){
					Motor.A.forward();
					Motor.B.forward();
					Motor.C.forward();
					Motor.D.forward();
				}else if(fargeSample[0] > 0.07){
					if (i == 15){
						Motor.A.setSpeed(300);
						Motor.B.setSpeed(300);
						Motor.C.setSpeed(150);
						Motor.D.setSpeed(150);
						Motor.A.forward();
						Motor.B.forward();
						Motor.C.backward();
						Motor.D.backward();

						Thread.sleep(20);
					}else{


						Motor.A.setSpeed(150);
						Motor.B.setSpeed(150);
						Motor.C.setSpeed(300);
						Motor.D.setSpeed(300);



						Motor.A.backward();
						Motor.B.backward();
						Motor.C.forward();
						Motor.D.forward();
						Thread.sleep(20);
						i++;
					}
				}else{

					Thread.sleep(10);
					Motor.A.setSpeed(700);
					Motor.B.setSpeed(700);
					Motor.C.setSpeed(700);
					Motor.D.setSpeed(700);
					Motor.A.forward();
					Motor.B.forward();
					Motor.C.forward();
					Motor.D.forward();
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

		} catch(Exception e){
			System.out.println("Feil: " + e);
			e.printStackTrace();
		}
	}
}
