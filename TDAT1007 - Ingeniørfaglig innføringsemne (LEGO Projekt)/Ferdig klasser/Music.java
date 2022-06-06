import lejos.hardware.Audio;
import lejos.hardware.ev3.EV3;
import java.io.File;

public class Music {

	private static String FILENAME;
	private static final int VOLUME = 100;
	private static Audio audio;

	public Music (String fileName, EV3 ev3){
		this.FILENAME = fileName;
		this.audio = ev3.getAudio ();
	}

	public void Play (){
		try{
			float time = audio.playSample(new File(FILENAME),VOLUME);
			System.out.println(time);
		}
		catch(Exception e){
			System.out.println("Could not play file: " + FILENAME + "\n" + e.getMessage ());
		}
	}

	public Audio getAudioSource (){
		return audio;
	}


}
