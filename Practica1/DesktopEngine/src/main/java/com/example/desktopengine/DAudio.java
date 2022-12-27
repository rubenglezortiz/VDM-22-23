package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.ISound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class DAudio implements IAudio {
    private String path = "./Assets/Sounds/";
    private String backgroundMusic;
    private final HashMap<String, ISound> sounds;

    public DAudio(){
        this.sounds = new HashMap<>();
    }

    @Override
    public void newSound(String file) {
        if(this.sounds.containsKey(file)) return;
        DSound sound = new DSound(this.path + file);
        this.sounds.put(file,sound);
    }

    @Override
    public void playSound(String key) {
        try {
            if (this.sounds.containsKey(key))
                Objects.requireNonNull(this.sounds.get(key)).play();
            else throw new IOException("!!!!!!!!!!!!!!Key audio does not exist!!!!!!!!!!!!!!");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void stopSound(String key) {
        try {
            if (this.sounds.containsKey(key))
                Objects.requireNonNull(this.sounds.get(key)).stop();
            else throw new IOException("!!!!!!!!!!!!!!Key audio does not exist!!!!!!!!!!!!!!");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void pauseSound(String key) {
        try {
            if (this.sounds.containsKey(key))
                Objects.requireNonNull(this.sounds.get(key)).pause();
            else throw new IOException("!!!!!!!!!!!!!!Key audio does not exist!!!!!!!!!!!!!!");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void setLooping(String key, boolean looping) {
        try {
            if (this.sounds.containsKey(key)){
                int n;
                if (looping) n = -1;
                else n = 0;
                loopSound((DSound)Objects.requireNonNull(this.sounds.get(key)),n);
            }
            else throw new IOException("!!!!!!!!!!!!!!Key audio does not exist!!!!!!!!!!!!!!");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void setVolume(String key, float volume){
        try {
            if (this.sounds.containsKey(key))
                Objects.requireNonNull(this.sounds.get(key)).setVolume(volume);
            else throw new IOException("!!!!!!!!!!!!!!Key audio does not exist!!!!!!!!!!!!!!");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void setBackgroundMusic(String backgroundMusic) {
        if(this.sounds.containsKey(backgroundMusic)) this.backgroundMusic = backgroundMusic;
    }

    public void loopSound(DSound sound, int n){ sound.loop(n);}

    //public boolean soundIsRunning(DSound sound){ return sound.isRunning(); }
}