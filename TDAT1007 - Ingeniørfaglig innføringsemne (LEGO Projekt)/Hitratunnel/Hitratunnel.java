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




class Hitretunnel {

	public static void main(String[] arg) throws Exception {

		try{

			Brick brick = BrickFinder.getDefault();
			Port s1 = brick.getPort("S1"); // fargesensor
			Port s2 = brick.getPort("S2"); // trykksensor
			Port s4 = brick.getPort("S4"); // lydsensor

			EV3 ev3 = (EV3) BrickFinder.getLocal();
			TextLCD lcd = ev3.getTextLCD();
			Keys keys = ev3.getKeys();

			/*Definerer fargesensor*/
			EV3ColorSensor fargesensor = new EV3ColorSensor(s1); // ev3-fargesensor
			SampleProvider fargeLeser = fargesensor.getMode("RGB");  // svart = 0.01..
			float[] fargeSample = new float[fargeLeser.sampleSize()];  // tabell som innholder avlest verdi

			/* Definerer en trykksensor */
			SampleProvider trykksensor = new EV3TouchSensor(s2);
			float[] trykkSample = new float[trykksensor.sampleSize()]; // tabell som inneholder avlest verdi

			/*Definerer lydsensor*/
			NXTSoundSensor lydsensor = new NXTSoundSensor(s4);
			SampleProvider lydLeser = lydsensor.getDBMode();
			float[] lydSample = new float[lydLeser.sampleSize()]; // tabell som inneholder avlest verdi

			// Setter hastighet på roboten
			Motor.A.setSpeed(0);
			Motor.C.setSpeed(200);
			//Børstemotor
			Motor.B.setSpeed(300);

			boolean fortsett = true;
			while (fortsett){ 	// Fortsett så lenge roboten ikke treffer noe

				Motor.B.forward();
				int svartTeller = 0;

				fargeLeser.fetchSample(fargeSample, 0);

				if (fargeSample[0] > 0.04 && fargeSample[1] < 0.04){
					Motor.A.forward();
					Motor.C.forward();
				}else{
					svartTeller++;
					if(svartTeller > 0){
						Motor.A.rotate(-210);
						Motor.C.rotate(210);
						while (Motor.A.isMoving()) Thread.yield();
					}
				}

				lydLeser.fetchSample(lydSample, 0);
				System.out.println("Lyd: " + lydSample[0]);

				if (lydSample[0]> 0.005){
					Motor.A.stop();
					Motor.C.stop();
					Thread.sleep(1000);
				}
				trykksensor.fetchSample(trykkSample, 0);
				if (trykkSample[0] > 0){
					System.out.println("Avslutter");
					fortsett = false;
				}
				
			}
		} catch(Exception e){
			System.out.println("Feil: " + e);
		}
	}
}
