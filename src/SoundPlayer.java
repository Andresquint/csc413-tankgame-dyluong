package src;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;


public class SoundPlayer {

    private AudioInputStream music;
    private Clip clip;
    private Thread musicThread;
    private Runnable run;
    private boolean isOn = false;
    SoundPlayer(AudioInputStream music){
        this.music = music;
        try {
            this.clip = AudioSystem.getClip();
            this.clip.open(this.music);
             run = new Runnable() {
                @Override
                public void run() {
                    while (isOn) {
                        SoundPlayer.this.clip.loop(-1);
                        try {
                            Thread.sleep(1000 / 144);
                        }catch (Exception ex){}
                    }
                }
            };
            musicThread = new Thread(run);
            musicThread.start();
        } catch (Exception ex) {
            System.out.println("Failed to initialize music. ");
        }
    }

    public void playLoop(){
        isOn = true;
    }

    public void stopLoop(){
        isOn = false;
    }

    public void play(){
        clip.setMicrosecondPosition(0);
        this.clip.start();
    }
    public void stop(){
        this.clip.stop();
        clip.setMicrosecondPosition(0);
    }

}
