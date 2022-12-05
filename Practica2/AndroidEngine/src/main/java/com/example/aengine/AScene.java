package com.example.aengine;

import android.os.Bundle;
import android.view.View;

public abstract class AScene  {
    protected abstract void setUpScene();

    public abstract void update();

    public abstract void render();

    public abstract void handleInputs();

    public abstract void saveScene(Bundle outState);

    public abstract void saveSceneInFile(View myView);

    public abstract void restoreScene(Bundle savedInstanceState, AndroidEngine engine);

    public abstract  void restoreSceneFromFile(View myView);
}
