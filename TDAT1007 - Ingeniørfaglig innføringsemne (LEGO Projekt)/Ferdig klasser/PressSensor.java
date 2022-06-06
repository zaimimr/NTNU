public class PressSensor{

  private static SampleProvider dataSampler;
  private static float[] data;

  public PressSensor(Port port){
    dataSampler = new EV3TouchSensor(port);
    data = new float[dataSampler.sampleSize()];
  }

  public boolean killSwitch(){
    trykksensor.fetchSample(data, 0);
    if (data[0] > 0){
      return false;
    }
    return true
  }

}
