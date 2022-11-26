package com.example.aengine;

import android.content.res.AssetManager;


import java.util.ArrayList;
import java.util.Iterator;

public class AAudio  {
    private AssetManager assetManager;
    private ASound backgroundMusic;
    public AAudio(AssetManager aM){ this.assetManager = aM;}

    public ASound newSound(String file) { return new ASound(file, this.assetManager);}

    public void playSound(ASound sound) { sound.play();}

    public void stopSound(ASound sound) { sound.stop(); }

    public void pauseSound(ASound sound) { sound.pause();}

    public void setLooping(ASound sound, boolean looping) { ((ASound)sound).setLooping(looping); }

    public void setVolume(ASound sound, float volume) { sound.setVolume(volume); }

    public void setBackgroundMusic(ASound backgroundMusic) { this.backgroundMusic = backgroundMusic; }

    public void pauseAllSounds(){ //this.backgroundMusic.pause();
         }

    public void resumeAllSounds(){ //this.backgroundMusic.play();
         }
}
