package com.example.aengine;

import android.content.res.AssetManager;


import java.io.IOException;
import java.util.HashMap;

public class AAudio {
    private AssetManager assetManager;

    private HashMap<String, ASound> sounds;
    public AAudio(AssetManager aM){
        this.sounds = new HashMap<>();
        this.assetManager = aM;
    }

    public void newSound(String file) {
        if(this.sounds.containsKey(file)) return;
        ASound sound = new ASound(file, this.assetManager);
        this.sounds.put(file,sound);
    }

    public void playSound(ASound sound) { sound.play();}

    public void playSound(String key) {
        try {
            if (this.sounds.containsKey(key))
                this.sounds.get(key).play();
            else throw new IOException("!!!!!!!!!!!!!!Key audio does not exist!!!!!!!!!!!!!!");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public void stopSound(ASound sound) { sound.stop(); }

    public void pauseSound(ASound sound) { sound.pause();}

    public void setLooping(ASound sound, boolean looping) { ((ASound)sound).setLooping(looping); }

    public void setLooping(String key, boolean looping){
        try {
            if (this.sounds.containsKey(key))
                this.sounds.get(key).setLooping(looping);
            else throw new IOException("!!!!!!!!!!!!!!Key audio does not exist!!!!!!!!!!!!!!");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public void setVolume(ASound sound, float volume) { sound.setVolume(volume); }
    public void setVolume(String key, float volume){
        try {
            if (this.sounds.containsKey(key))
                this.sounds.get(key).setVolume(volume);
            else throw new IOException("!!!!!!!!!!!!!!Key audio does not exist!!!!!!!!!!!!!!");
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public void setBackgroundMusic(ASound backgroundMusic) {
        //this.backgroundMusic = backgroundMusic;
    }

    public void pauseAllSounds(){ //this.backgroundMusic.pause();
         }

    public void resumeAllSounds(){ //this.backgroundMusic.play();
         }
}
