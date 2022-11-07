package com.example.aengine;

import android.content.res.AssetManager;

import com.example.engine.IAudio;
import com.example.engine.ISound;

import java.util.ArrayList;
import java.util.Iterator;

public class AAudio implements IAudio {
    private AssetManager assetManager;
    private ISound backgroundMusic;
    public AAudio(AssetManager aM){ this.assetManager = aM;}

    @Override
    public ISound newSound(String file) { return new ASound(file, this.assetManager);}

    @Override
    public ISound getSound(String id) {
        return null;
    }

    @Override
    public void playSound(ISound sound) { sound.play();}

    @Override
    public void stopSound(ISound sound) { sound.stop(); }

    public void pauseSound(ISound sound) { sound.pause();}

    @Override
    public void setLooping(ISound sound, boolean looping) { ((ASound)sound).setLooping(looping); }

    @Override
    public void setVolume(ISound sound, float volume) { sound.setVolume(volume); }

    @Override
    public void setBackgroundMusic(ISound backgroundMusic) {
        this.backgroundMusic = backgroundMusic;
    }

    public void pauseAllSounds(){ this.backgroundMusic.pause();}

    public void resumeAllSounds(){ this.backgroundMusic.play(); }
}
