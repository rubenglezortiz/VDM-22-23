package com.example.aengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import java.io.IOException;

public class ASound  {
    private MediaPlayer mPlayer;

    public ASound(String file, AssetManager assetManager){
        this.mPlayer = new MediaPlayer();
        this.mPlayer.reset();
        try {
            AssetFileDescriptor afd = assetManager.openFd(file);
            this.mPlayer.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());
            this.mPlayer.prepare();
        } catch (IOException e) {
            System.err.println("Couldn't load audio file");
            e.printStackTrace();
        }
    }

    public void play() { this.mPlayer.start(); }

    public void stop() { this.mPlayer.stop();}

    public void pause(){
        this.mPlayer.pause();
    }

    public void setVolume(float volume) { this.mPlayer.setVolume(volume, volume); }

    public void setLooping(boolean looping){ this.mPlayer.setLooping(looping);}
}
