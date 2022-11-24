package es.alfema.pft;

import java.io.Serializable;

public class Posicion implements Serializable {
    public double myX;
    public double myY;
    private Direcciones moveX;
    private Direcciones moveY;
    private boolean start;

    public Posicion(double xPos, double yPos) {
        this.myX = xPos;
        this.myY = yPos;
        this.moveX = Direcciones.DOWN;
        this.moveY = Direcciones.LEFT;
        start = true;
    }

    public boolean getStart() {
        return start;
    }

    public void changeStartStop(){
        if(getStart())
            start = false;
        else
            start = true;
    }

    public void setMoveX(Direcciones moveX) {
        this.moveX = moveX;
    }

    public void setMoveY(Direcciones moveY) {
        this.moveY = moveY;
    }

    public double getX(){
        return myX;
    }

    public double getY(){
        return myY;
    }

    public void aumXY(){
        myX += moveX.getVal();
        myY += moveY.getVal();
    }

    @Override
    public String toString() {
        return "X: " + myX + " Y: " + myY;
    }
}
