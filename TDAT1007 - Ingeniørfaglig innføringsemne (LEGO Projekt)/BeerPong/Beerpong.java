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
import java.lang.Object.*;

class Beerpong{
	public static void main(String[] arg) throws Exception {
		try{

			Brick brick = BrickFinder.getDefault();

			Port Avstand = brick.getPort("S3");
			Port Avloser = brick.getPort("S2");

			EV3 ev3 = (EV3) BrickFinder.getLocal();
			TextLCD lcd = ev3.getTextLCD();
			Keys keys = ev3.getKeys();

			PressSensor press =  new PressSensor(Avloser);
			JunisFineKode avstand = new JunisFineKode(Avstand);

			int kraft;
			int thread = 200;

			lcd.drawString("Kjor", 0, 3);
			while(true){

				if(press.Trykk()){
						int kraft = (int)(218.94/(1-0.15*Math.exp(1.53 * avstand.gjennomsnittAvstand())));
						if(kraft > 230){
							kast(kraft);
							lcd.drawString("Avstand: " + avstand.gjennomsnittAvstand(), 0, 1);
							lcd.drawString("Kraft: " + kraft, 0, 2);
					}else{
						System.out.println("Koppen er for nærme");
					}
				}
			}
		} catch(Exception e){
			System.out.println("Feil: " + e);
			e.printStackTrace();
		}
	}

	public void kast(int kraft){
//Kast
		Motor.A.setSpeed(kraft);
		Motor.B.setSpeed(kraft);
		Motor.A.backward();
		Motor.B.backward();
		Thread.sleep(thread);
		Motor.A.stop(true);
		Motor.B.stop(true);
//Reset
		Thread.sleep(500);
		Motor.A.forward();
		Motor.B.forward();
		Thread.sleep(thread);
		Motor.A.stop(true);
		Motor.B.stop(true);
//Reload
		Motor.A.flt();
		Motor.B.flt();
	}
}
