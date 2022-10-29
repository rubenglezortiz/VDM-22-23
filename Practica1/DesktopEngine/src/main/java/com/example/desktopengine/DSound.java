package com.example.desktopengine;

import com.example.engine.ISound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class DSound implements ISound {
    Clip clip;
    public DSound(String name){

        try {
            File audioFile = new File(name); //path + filename
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            this.clip = AudioSystem.getClip();
            this.clip.open(audioStream);


        } catch (Exception e) {
            System.err.println("Couldn't load audio file");
            e.printStackTrace();
        }
    }

    @Override
    public void play() {
        this.clip.start();
    }

    @Override
    public void stop() {
        if(isRunning())
            this.clip.stop();
    }

    public boolean isRunning(){
        return this.clip.isRunning();
    }

    public void close(){
        this.clip.close();
    }

    //Reproduce el audio en loop n+1 veces, si se pasa -1, se loopeará indefinidamente
    public void loop(int n){
        this.clip.loop(n);
    }
}
