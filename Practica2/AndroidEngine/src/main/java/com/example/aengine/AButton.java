package com.example.aengine;
import java.io.Serializable;


public class AButton implements Serializable {

    public enum horizontalAlignment{LEFT, HMIDDLE, RIGHT, NO_ALIGNMENT}
    public enum verticalAlignment{TOP, VMIDDLE, BOTTOM, NO_ALIGNMENT}

    private String name;
    private horizontalAlignment hAlign;
    private verticalAlignment vAlign;
    private float offsetX, offsetY;
    private float width, height;
    private float posX, posY;
    private AColor backgroundColor;

    public AButton(AGraphics graphics, String buttonName, horizontalAlignment hA, verticalAlignment vA, float offX, float offY,
                   float w, float h, AColor bgColor) {
        this.name = buttonName;
        this.hAlign = hA;
        this.vAlign = vA;
        this.offsetX = offX;
        this.offsetY = offY;
        this.width = w;
        this.height = h;
        this.backgroundColor = bgColor;

        newPosition(graphics);
    }

    public AButton(String buttonName, float x, float y, float w, float h, AColor bgColor){
        this.name = buttonName;
        this.posX = x;
        this.posY = y;
        this.width = w;
        this.height = h;
        this.backgroundColor = bgColor;

        //NO ALIGNMENT
        this.hAlign = horizontalAlignment.NO_ALIGNMENT;
        this.vAlign = verticalAlignment.NO_ALIGNMENT;
        this.offsetX = this.offsetY = 0;
    }

    public float getPosX() { return this.posX; }

    public float getPosY() { return this.posY; }

    public float getWidth() { return this.width; }

    public float getHeight() { return this.height; }

    public String getName() { return this.name; }

    public AColor getBackgroundColor() { return this.backgroundColor; }

    public boolean checkCollision(float coordX, float coordY) {
        return (coordX >= this.posX &&
                coordX <= this.posX + this.width &&
                coordY >= this.posY &&
                coordY <= this.posY + this.height);
    }

    public void  changePosition(float x, float  y)
    {
        this.posX = x;
        this.posY = y;
    }

    //Cambia la posición del botón según su alineación.
    private void newPosition(AGraphics graphics){
        // COORD X
        int screenWidth = graphics.getWidth();
        switch(hAlign){
            case HMIDDLE:
                this.posX = ((screenWidth / 2) - (this.width / 2)) + this.offsetX;
                this.posX = graphics.realToLogicX(this.posX);
                break;
            case RIGHT:
                this.posX = (screenWidth - this.width)  + this.offsetX;
                this.posX = graphics.realToLogicX(this.posX);
                break;
            case LEFT:
                this.posX = this.offsetX;
                this.posX = graphics.realToLogicX(this.posX);
                break;
            default: //case NO_ALIGNMENT
                break;
        }

        //COORD Y
        int screenHeight = graphics.getHeight();
        switch(vAlign){
            case VMIDDLE:
                this.posY = ((screenHeight / 2) - this.height) + this.offsetY;
                this.posY = graphics.realToLogicY(this.posY);
                break;
            case BOTTOM:
                this.posY = (screenHeight - this.height) + this.offsetY;
                this.posY = graphics.realToLogicY(this.posY);
                break;
            case TOP:
                this.posY = this.offsetY;
                this.posY = graphics.realToLogicY(this.posY);
                break;
            default: //case NO_ALIGNMENT
                break;
        }
    }
}
