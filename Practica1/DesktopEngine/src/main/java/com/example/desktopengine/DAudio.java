package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.ISound;

public class DAudio implements IAudio {
    String path = "./Assets/Sounds/";
    private ISound backgroundMusic;

    public DAudio(){}

    @Override
    public ISound newSound(String file) { return new DSound(path + file); }

    @Override
    public void playSound(ISound sound) {
        sound.play();
    }

    @Override
    public void stopSound(ISound sound) { sound.stop();}

    @Override
    public void pauseSound(ISound sound) { sound.pause(); }

    @Override
    public void setLooping(ISound sound, boolean looping) {
        int n;
        if (looping) n = -1;
        else n = 0;
        loopSound(((DSound)sound), n);
    }

    @Override
    public void setVolume(ISound sound, float volume) {
        sound.setVolume(volume);
    }

    @Override
    public void setBackgroundMusic(ISound backgroundMusic) { this.backgroundMusic = backgroundMusic; }

    public void loopSound(DSound sound, int n){ sound.loop(n);}

    //public boolean soundIsRunning(DSound sound){ return sound.isRunning(); }
}