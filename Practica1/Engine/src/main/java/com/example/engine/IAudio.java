package com.example.engine;

public interface IAudio {
    public ISound newSound(String file);
    public ISound getSound(String id);
    public void playSound(ISound sound);
    public void stopSound(ISound sound);
}
