package com.example.androidengine;

import com.example.engine.IAudio;
import com.example.engine.ISound;

public class AudioEngine implements IAudio {
    @Override
    public ISound newSound(String file) {
        return null;
    }

    @Override
    public ISound getSound(String id) {
        return null;
    }

    @Override
    public void playSound(ISound sound) {

    }

    @Override
    public void stopSound(ISound sound) {

    }
}
