package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

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
        soundURL[12] = getClass().getResource("/resources/sound/gameover.wav");
        soundURL[13] = getClass().getResource("/resources/sound/stairs.wav");
        soundURL[14] = getClass().getResource("/resources/sound/sleep.wav");
        soundURL[15] = getClass().getResource("/resources/sound/blocked.wav");
        soundURL[16] = getClass().getResource("/resources/sound/parry.wav");
        soundURL[17] = getClass().getResource("/resources/sound/speak.wav");
        soundURL[18] = getClass().getResource("/resources/sound/merchant.wav");
        soundURL[19] = getClass().getResource("/resources/sound/dungeon.wav");
        soundURL[20] = getClass().getResource("/resources/sound/chipwall.wav");
        soundURL[21] = getClass().getResource("/resources/sound/dooropen.wav");
        soundURL[22] = getClass().getResource("/resources/sound/FinalBattle.wav");
    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
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
    public void checkVolume(){
        switch (volumeScale){
            case 0: volume = -80f ;break;
            case 1: volume = -20f ;break;
            case 2: volume = -12f ;break;
            case 3: volume = -5f ;break;
            case 4: volume = 1f ;break;
            case 5: volume = 6f ;break;
        }
        fc.setValue(volume);
    }
}
