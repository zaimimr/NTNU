import lejos.hardware.sensor.*;
import lejos.hardware.port.*;
import lejos.robotics.SampleProvider;

public class UltraSonicSensor {

  private static SensorModes sensor;
  private static SampleProvider dataSampler;
  private float[] datas;


  public UltraSonicSensor (Port port){
    sensor = new EV3UltrasonicSensor(port);
    dataSampler = sensor.getMode("Distance");
    datas = new float[dataSampler.sampleSize ()];
  }

  public float getDistance (){
    dataSampler.fetchSample(datas,0);
    return datas[0];
  }

  public boolean foundObject(float maxDistance){
    dataSampler.fetchSample(datas,0);
    float distance = datas[0];
    if(distance <= maxDistance){return true;}
    else{return false;}
  }

  
}
