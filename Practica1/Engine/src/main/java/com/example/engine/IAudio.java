package com.example.engine;

public interface IAudio {
    public void newSound(String file);
    public void playSound(String key);
    public void stopSound(String key);
    public void pauseSound(String key);
    public void setLooping(String key, boolean looping);
    public void setVolume(String key, float volume);
    void setBackgroundMusic(String backgroundMusic);
}
