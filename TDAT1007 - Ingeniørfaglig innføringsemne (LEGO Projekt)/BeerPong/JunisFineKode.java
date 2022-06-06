import lejos.hardware.sensor.*;
import lejos.hardware.port.*;
import lejos.robotics.SampleProvider;

public class JunisFineKode {

 	private static SensorModes sensor; //Ultrasonic Sensor
 	private static SampleProvider dataSampler;
 	private float[] datas;
  private static int antallMalinger = 200;


 	public JunisFineKode (Port port){
		sensor = new EV3UltrasonicSensor(port);
		dataSampler = sensor.getMode("Distance");
		datas = new float[dataSampler.sampleSize ()];
  	}

	public float getDistance (){
		dataSampler.fetchSample(datas,0);
		return datas[0];
	}

	public float gjennomsnittAvstand(){
		float sum = 0;
		int i;
		for (i = 0; i < antallMalinger; i++){
			sum += getDistance();
		}
		return sum/i;
	}
}
