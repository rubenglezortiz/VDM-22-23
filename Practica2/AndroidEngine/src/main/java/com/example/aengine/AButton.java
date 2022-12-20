package com.example.aengine;
import java.io.Serializable;


public class AButton implements Serializable {
    private final String name;
    private final AColor backgroundColor;

    public enum horizontalAlignment{LEFT, HMIDDLE, RIGHT, NO_ALIGNMENT}
    public enum verticalAlignment{TOP, VMIDDLE, BOTTOM, NO_ALIGNMENT}

    private horizontalAlignment hAlign;
    private verticalAlignment vAlign;
    private float posX, posY;
    private float offsetX, offsetY;
    private float width, height;
    private int screenWidth, screenHeight;

    //Botones alineados
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

        //DESAJUSTE POR DEBAJO DE LA PANTALLA
        if (this.vAlign == verticalAlignment.BOTTOM){
            this.offsetY -= graphics.logicToRealScale(30.0f); //desajuste
        }

        newPosition(graphics);


    }

    //Contrusctora de botónes lógicos
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

    public boolean checkCollision(AGraphics graphics, float coordX, float coordY) {
        float xPos, yPos, w, h;
        xPos = this.posX;
        yPos = this.posY;
        w = this.width;
        h = this.height;

        if (isLogic()){
            xPos = graphics.logicToRealX(xPos);
            yPos = graphics.logicToRealY(yPos);
            w = graphics.logicToRealScale(w);
            h = graphics.logicToRealScale(h);
        }

        return (coordX >= xPos &&
                coordX <= xPos + w &&
                coordY >= yPos &&
                coordY <= yPos + h);
    }

    public void  changePosition(float x, float  y)
    {
        this.posX = x;
        this.posY = y;
    }

    public boolean isLogic(){
        return (vAlign == verticalAlignment.NO_ALIGNMENT && hAlign == horizontalAlignment.NO_ALIGNMENT);
    }

    //Cambia la posición del botón según su alineación.
    private void newPosition(AGraphics graphics){
        // COORD X
        this.screenWidth = graphics.getWidth();
        switch(hAlign){
            case HMIDDLE:
                this.posX = ((this.screenWidth / 2) - (this.width / 2)) + this.offsetX;
                break;
            case RIGHT:
                this.posX = (this.screenWidth - this.width) + this.offsetX;
                break;
            case LEFT:
                this.posX = this.offsetX;
                break;
            default: //case NO_ALIGNMENT
                break;
        }

        //COORD Y
        this.screenHeight = graphics.getHeight();
        switch(vAlign){
            case VMIDDLE:
                this.posY = ((this.screenHeight / 2) - this.height) + this.offsetY;
                break;
            case BOTTOM:
                this.posY = (this.screenHeight - this.height) + this.offsetY;
                break;
            case TOP:
                this.posY = this.offsetY;
                break;
            default: //case NO_ALIGNMENT
                break;
        }
    }

    public void changeButton(AGraphics graphics, float offX, float offY, float w, float h){
        this.offsetX = offX;
        this.offsetY = offY;
        this.width = w;
        this.height = h;

        //DESAJUSTE POR DEBAJO DE LA PANTALLA
        if (this.vAlign == verticalAlignment.BOTTOM){
            this.offsetY -= graphics.logicToRealScale(30.0f); //desajuste
        }

        this.newPosition(graphics);
    }
}
