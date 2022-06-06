import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.NXTTouchSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;

public class PressSensor{

  private static SampleProvider dataSampler;
  private static float[] data;

  public PressSensor(Port port){
    dataSampler = new EV3TouchSensor(port);
    data = new float[dataSampler.sampleSize()];
  }

  public boolean Trykk(){
    if(data != null && data.length > 0){
      dataSampler.fetchSample(data, 0);
      if (data[0] > 0){
        return true;
      }
      return false;
    }throw new IllegalArgumentException("Sample er null eller 0");
  }
}
