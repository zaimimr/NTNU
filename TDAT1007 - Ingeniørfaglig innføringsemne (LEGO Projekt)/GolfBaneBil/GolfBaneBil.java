
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
import lejos.robotics.navigation.DifferentialPilot;


class GolfBaneBil{

  public static void main(String[] args) throws Exception{

    try{
      Brick brick = BrickFinder.getDefault();
      Port sensPress = brick.getPort("S1"); //trykksensor
      Port sensUS = brick.getPort("S2");    //UltraLysSensor

      EV3 ev3 = (EV3) BrickFinder.getLocal();
      TextLCD lcd = ev3.getTextLCD();
      Keys keys = ev3.getKeys();

      lcd.drawString("Begynn letingen", 0, 1);
      keys.waitForAnyPress();

      //Trykksensor
      SampleProvider trykkSens = new EV3TouchSensor(sensPress);
      float[] trykkSample = new float[trykkSens.sampleSize()];

      //UltraLysSensor
      EV3UltrasonicSensor ultraSens = new EV3UltrasonicSensor(sensUS);
      SampleProvider ultraLeser = ultraSens.getDistanceMode();
      float[] ultraSample = new float[ultraLeser.sampleSize()];

      boolean lokke = true;

      Motor.A.setSpeed(450);  // sett hastighet (toppfart = 900)
      Motor.C.setSpeed(450);

      //  golfBil.setTravelSpeed(450);

      while(lokke){
        Motor.A.forward();  // Start motor A - kj�r framover
        Motor.C.forward();

        if (ultraSample != null && ultraSample.length > 0){
          ultraLeser.fetchSample(ultraSample, 0);
          lcd.drawString("Avstand: " + ultraSample[0], 0,5);
          if(ultraSample[0] < 0.3){
            Motor.A.backward();
            Motor.C.backward();
            Thread.sleep(500);
            Motor.A.rotate(90);
            Motor.C.stop();
            while (Motor.A.isMoving()) Thread.yield();
          }
        }else System.out.println("Sample er null eller 0");

        if (trykkSample != null && trykkSample.length > 0){
          trykkSens.fetchSample(trykkSample, 0);
      	 	if (trykkSample[0] > 0){
      			System.out.println("Avslutter");
      		 	lokke = false;
      		}
        }else System.out.println("Sample er null eller 0");


      }
    } catch(Exception e){
      System.out.println("Feil: " + e);
    }
  }
}
