package com.example.engine;

public interface IScene {
    public void update(IEngine engine);
    public void render(IGraphics graphics);
    public void handleInputs(IInput input, IAudio audio);
}
