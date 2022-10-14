package com.example.desktopengine;

import com.example.engine.IAudio;

public class AudioEngine implements IAudio {
    public ISound newSound()
    {
        return new ISound();
    }
}