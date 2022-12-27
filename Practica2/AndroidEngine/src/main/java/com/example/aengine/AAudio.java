package com.example.aengine;

import android.content.res.AssetManager;


import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class AAudio {
    private final AssetManager assetManager;
    private final HashMap<String, ASound> sounds;
    private String backgroundMusic;

    public AAudio(AssetManager aM){
        this.sounds = new HashMap<>();
        this.assetManager = aM;
    }

    public void newSound(String file) {
        if(this.sounds.containsKey(file)) return;
        ASound sound = new ASound(file, this.assetManager);
        this.sounds.put(file,sound);
    }

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

    public boolean isPlaying(String key){
        boolean isPlaying = false;
        try {
            if (this.sounds.containsKey(key))
               isPlaying = this.sounds.get(key).isPlaying();
            else throw new IOException("!!!!!!!!!!!!!!Key audio does not exist!!!!!!!!!!!!!!");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
        return isPlaying;
    }

    public void setLooping(String key, boolean looping){
        try {
            if (this.sounds.containsKey(key))
                Objects.requireNonNull(this.sounds.get(key)).setLooping(looping);
            else throw new IOException("!!!!!!!!!!!!!!Key audio does not exist!!!!!!!!!!!!!!");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

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

    public void setBackgroundMusic(String backgroundMusic) {
        if(this.sounds.containsKey(backgroundMusic)) this.backgroundMusic = backgroundMusic;
    }

    public void pauseAllSounds(){
       if(this.sounds.containsKey(this.backgroundMusic)) Objects.requireNonNull(this.sounds.get(this.backgroundMusic)).pause();
   }

    public void resumeAllSounds(){
        if(this.sounds.containsKey(this.backgroundMusic)) Objects.requireNonNull(this.sounds.get(this.backgroundMusic)).play();
    }
}
