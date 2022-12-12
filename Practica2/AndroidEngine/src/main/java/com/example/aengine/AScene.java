package com.example.aengine;

import android.os.Bundle;
import android.view.View;

import java.net.DatagramPacket;

public abstract class AScene  {
    //protected abstract void setUpScene();

    public abstract void update(AndroidEngine engine);

    public abstract void render(AGraphics graphics);

    public abstract void handleInputs(AGraphics graphics, AInput input);

    public abstract void saveScene(Bundle outState);

    public abstract void saveSceneInFile(View myView);

    public abstract void restoreScene(Bundle savedInstanceState, AndroidEngine engine);

    public abstract  void restoreSceneFromFile(View myView);
}
