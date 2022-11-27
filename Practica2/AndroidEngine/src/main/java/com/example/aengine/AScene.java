package com.example.aengine;

import android.os.Bundle;

public abstract class AScene  {
    protected abstract void setUpScene();

    public abstract void update();

    public abstract void render();

    public abstract void handleInputs();

    public abstract void saveScene(Bundle outState);

    public abstract void restoreScene(Bundle savedInstanceState, AndroidEngine engine);
}
