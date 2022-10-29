package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.ISound;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

public class DAudioEngine implements IAudio {

    public DAudioEngine(){
        //imprimeTiposAudios();
        //Prueba de sonido, en el futuro, ehto se borra hulio
        //ISound startingSound = newSound("E://Juegos/Musica/Moneda.wav");
        //loopSound((DSound) startingSound, 3);
    }

    @Override
    public ISound newSound(String file) { return new DSound(file); }

    @Override
    public ISound getSound(String id) {
        return null;
    }

    @Override
    public void playSound(ISound sound) {  sound.play(); }


    @Override
    public void stopSound(ISound sound) { sound.stop();   }



    public void loopSound(DSound sound, int n){ sound.loop(n);   }



    public boolean soundIsRunning(DSound sound){ return sound.isRunning(); }

    /*private void imprimeTiposAudios(){
        // Obtenemos los tipos
        AudioFileFormat.Type[] tipos = AudioSystem.getAudioFileTypes();

        // y los sacamos por pantalla
        for (AudioFileFormat.Type tipo : tipos)
            System.out.println(tipo.getExtension());
    }*/
}