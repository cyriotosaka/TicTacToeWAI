//package connectfour;
//
//
//import javax.sound.sampled.*;
//import java.io.IOException;
//import java.net.URL;
//
//public class MusicPlayer {
//    private Clip clip;
//
//    public void play(String filePath) {
//        try {
//            URL url = getClass().getClassLoader().getResource(filePath);
//            if (url == null) {
//                System.err.println("Couldn't find file: " + filePath);
//                return;
//            }
//            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
//            clip = AudioSystem.getClip();
//            clip.open(audioInputStream);
//            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music
//            clip.start();
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void stop() {
//        if (clip != null && clip.isRunning()) {
//            clip.stop();
//        }
//    }
//}

package connectfour;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class MusicPlayer {
    private Clip clip;

    public void play(String filePath) {
        try {
            URL url = getClass().getClassLoader().getResource(filePath);
            if (url == null) {
                System.err.println("Couldn't find file: " + filePath);
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}