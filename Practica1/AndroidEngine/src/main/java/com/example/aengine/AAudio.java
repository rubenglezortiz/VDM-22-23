package com.example.aengine;

import android.content.res.AssetManager;

import com.example.engine.IAudio;
import com.example.engine.ISound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class AAudio implements IAudio {
    private AssetManager assetManager;
    private String backgroundMusic;
    private final HashMap<String, ASound> sounds;

    public AAudio(AssetManager aM){
        this.sounds = new HashMap<>();
        this.assetManager = aM;
    }

    @Override
    public void newSound(String file) {
        if(this.sounds.containsKey(file)) return;
        ASound sound = new ASound(file, this.assetManager);
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

    public void pauseAllSounds(){
        if(this.sounds.containsKey(this.backgroundMusic)) Objects.requireNonNull(this.sounds.get(this.backgroundMusic)).pause();
    }

    public void resumeAllSounds(){
        if(this.sounds.containsKey(this.backgroundMusic)) Objects.requireNonNull(this.sounds.get(this.backgroundMusic)).play();
    }
}
