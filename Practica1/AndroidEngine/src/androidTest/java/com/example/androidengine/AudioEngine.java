package com.example.androidengine;

import com.example.engine.IAudio;

public class AudioEngine implements IAudio {
    public ISound newSound()
    {
        return new ISound();
    }
}
