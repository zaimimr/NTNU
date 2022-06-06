package Components.Threads;

import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;

import java.io.File;

import static javafx.scene.media.MediaPlayer.INDEFINITE;

/**
 * Class containing methods for playing and stopping music
 */
public class Music {

    private static Task task;
    private static File file;
    private static int choosenSong;
    public static AudioClip audio;

    /**
     * Method which plays music in its own separate thread
     */
    public static void playMusic(int song) {
        if (song == 0) {
            choosenSong = 0;
            file = new File("resources/music/music1.wav");
        }
        if (song == 1) {
            choosenSong = 1;
            file = new File("resources/music/SickoMode.wav");
        }
        audio = new AudioClip(file.toURI().toString());

        task = new Task() {
            @Override
            protected Object call() throws Exception {
                int s = INDEFINITE;
                audio.setVolume(1.5f);
                audio.setCycleCount(s);
                audio.play();
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    /**
     * Method which returns the choosen song
     * @return the choosen song
     */
    public static int getChoosenSong() {
        return choosenSong;
    }

    /**
     * Method which stops the music if music is playing
     */
    public static void stopMusic() {
        if (audio.isPlaying()) {
            audio.stop();
        }
        if (task != null) {
            task.cancel(true);
        }
    }
}
