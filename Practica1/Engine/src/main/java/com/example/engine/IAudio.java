package com.example.engine;

public interface IAudio {
    public ISound newSound(String file);
    public ISound getSound(String id);
    public void playSound(ISound sound);
    public void stopSound(ISound sound);
    public void pauseSound(ISound sound);
    public void setLooping(ISound sound, boolean looping);
    public void setVolume(ISound sound, float volume);
    void setBackgroundMusic(ISound backgroundMusic);
}
