package com.example.androidengine;

import android.content.res.AssetManager;

import com.example.engine.IAudio;
import com.example.engine.ISound;

public class AudioEngine implements IAudio {

    AssetManager assetManager;
    @Override
    public ISound newSound(String file) {
        return new ASound(file, assetManager);
    }

    @Override
    public ISound getSound(String id) {
        return null;
    }

    @Override
    public void playSound(ISound sound) { sound.play(); }

    @Override
    public void stopSound(ISound sound) { sound.stop(); }

    public void setLooping(ASound sound, boolean looping) { sound.setLooping(looping); }

}
