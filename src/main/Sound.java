package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/resources/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("/resources/sound/coin.wav");
        soundURL[2] = getClass().getResource("/resources/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/resources/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/resources/sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/resources/sound/hitmonster.wav");
        soundURL[6] = getClass().getResource("/resources/sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/resources/sound/swingweapon.wav");
        soundURL[8] = getClass().getResource("/resources/sound/levelUp.wav");
        soundURL[9] = getClass().getResource("/resources/sound/cursor.wav");
        soundURL[10] = getClass().getResource("/resources/sound/burning.wav");
        soundURL[11] = getClass().getResource("/resources/sound/cuttree.wav");
    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e){
        }
    }
    public void play(){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}
