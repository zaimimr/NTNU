import lejos.hardware.sensor.*;
import lejos.hardware.port.*;
import lejos.robotics.SampleProvider;

public class ColorSensor {

  private static EV3ColorSensor sensor;
  private static SensorMode sensorMode;
  private static SampleProvider dataSampler;
  private float[] data;

  public ColorSensor (Port port){
    sensor = new EV3ColorSensor(port);
    dataSampler = fargesensor.getMode("RGB");  // svart = 0.01..
    data = new float[dataSampler.sampleSize()];  // tabell som innholder avlest verdi
  }

  public float[] getData (){
    dataSampler.fetchSample(data,0);
    return data;
  }

}
