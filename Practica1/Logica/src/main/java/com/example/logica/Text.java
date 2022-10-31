package com.example.logica;

import com.example.engine.IColor;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IInput;

public class Text extends GameObject {
    IFont font;
    IColor color;
    String text;
    int textSize;

    public Text(int x, int y, int w, int h, String text_, IGraphics graphics){
        super(x,y,w,h);
        font = graphics.newFont("font.TTF",false);
        this.text = text_;
        this.textSize =100;
    }

    public void render(IGraphics graphics){
        graphics.setFont(font);
        graphics.drawText(this.text, this.x, this.y, this.textSize, graphics.newColor(0,0,0,255) );
        graphics.drawRectangle(this.x, this.y, this.w, this.h, graphics.newColor(0,200,0,255));
    }

    public void handleInputs(IInput.Event event) {
        switch (event.type) {
            case TOUCH_PRESSED:
                System.out.println("jjjj");
                if(((IInput.MouseInputEvent)event).mouseButton == 1){
                   if (checkCollisions(((IInput.MouseInputEvent)event).x, ((IInput.MouseInputEvent)event).y)){
                       System.out.println("CAMBIO DE ESCENA");
                   };
                }
                break;
            default:
                break;
        }
    }

    public void setText(String newText){
        this.text = newText;
    }


}
