/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *Třída pro Audio hry
 * @author Vojta
 */
/**
 * Třída pro nepřátelské jednotky
 * @author Jirka
 */
public class Audio {

    public static void playSound(String sound) {
        try {
            String soundName = sound;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-30.0f); // Reduce volume by 10 decibels.
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void playSoundNoControl(String sound) {
        try {
            String soundName = sound;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void playSoundShot(String sound) {
        try {
            String soundName = sound;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-40.0f); // Reduce volume by 10 decibels.
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
