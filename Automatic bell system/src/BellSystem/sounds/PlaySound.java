package BellSystem.sounds;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.audio.AudioPlayer;

import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PlaySound {

    /////////////////////////////////////////////////////////////////////

    public void playTrack(String track) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File file = new File(track);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    }


}

