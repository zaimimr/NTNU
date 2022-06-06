import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.port.*;
import lejos.robotics.SampleProvider;

public class SoundSensor {

	private static NXTSoundSensor sensor;
	private static SampleProvider dataSampler;

	private static float[] data;

	public SoundSensor (Port port){
		sensor = new NXTSoundSensor(port);
		dataSampler = sensor.getDBMode ();
		data = new float[dataSampler.sampleSize()];
	}

	public float getDB (){
		dataSampler.fetchSample(data,0);
		return data[0];
	}


}
