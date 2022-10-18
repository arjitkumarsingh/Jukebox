package com.niit.jukebox.jukebox;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class JukeboxPlayer {
    Long currentFrame;          // to store current position
    static Clip clip;
    static String status = "";              // current status of clip
    AudioInputStream audioInputStream;
    static String filePath;

    // constructor to initialize streams and clip
    public JukeboxPlayer() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));         // create AudioInputStream object
            clip = AudioSystem.getClip();               // create clip reference
            clip.open(audioInputStream);                // open audioInputStream to the clip
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}