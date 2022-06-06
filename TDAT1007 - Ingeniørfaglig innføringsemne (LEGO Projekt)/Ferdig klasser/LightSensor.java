import lejos.hardware.sensor.*;
import lejos.hardware.port.*;
import lejos.hardware.SampleProvider;

public class LightSensor{

	private static 	NXTLightSensor sensor;
	private static SampleProvider dataSampler;
	private static float[] data;

	public LightSensor(Port port){

		sensor = new NXTLightSensor(port);
		dataSampler = sensor.getAmbientMode ();
		data = new float[dataSampler.sampleSize ()];
	}

	public float getIntensity (){
		dataSampler.fetchSample(data,0);
		return data[0];
	}


}
