package com.example.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import com.example.engine.ISound;

import java.io.IOException;

public class ASound implements ISound {

    MediaPlayer mPlayer;
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

    @Override
    public void play() {  mPlayer.start(); }

    @Override
    public void stop() { mPlayer.stop(); }

    public void setLooping(boolean looping){ this.mPlayer.setLooping(looping);}


}
