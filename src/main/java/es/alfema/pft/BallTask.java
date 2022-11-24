package es.alfema.pft;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public class BallTask extends Task<Posicion> implements Serializable {
    private Posicion position;
    private ImageView imageView;
    private long waiting;
    private boolean isCliente;

    public BallTask(ImageView ima, long waiting, Posicion pos, boolean isCliente) {
        this.waiting = waiting;

        this.position = pos;

        this.imageView = ima;

        this.isCliente = isCliente;

        valueProperty().addListener(new ChangeListener<Posicion>() {
            @Override
            public void changed(ObservableValue<? extends Posicion> observableValue, Posicion posicion, Posicion t1) {
                imageView.setLayoutX(t1.getX());
                imageView.setLayoutY(t1.getY());
            }
        });
    }

    @Override
    protected Posicion call() throws Exception {
        while (true){
            boolean start = position.getStart();
            if(start){
                controlMove();
                if(!isCliente){
                    position.aumXY();
                }
                Thread.sleep(waiting);
                //System.out.println(position.getX() + " " + position.getY());
                double x = position.myX;
                double y = position.myY;
                updateValue(new Posicion(x,y));
            }else{
                System.out.println(start);
            }
        }
    }

    public void setPosition(Posicion pos){
        updateValue(pos);
    }

    public void changeStartStop(){
        position.changeStartStop();
    }

    public void controlMove(){
        if(550 < position.myY) {
            System.out.println("Arriba");
            position.setMoveY(Direcciones.UP);
        }
        if(0 > position.myY) {
            System.out.println("Abajo");
            position.setMoveY(Direcciones.DOWN);
        }
        if(550 < position.myX) {
            System.out.println("Izq");
            position.setMoveX(Direcciones.LEFT);
        }
        if(0 > position.myX) {
            System.out.println("Der");
            position.setMoveX(Direcciones.RIGHT);
        }
    }

    public Posicion getPosition(){
        return position;
    }
}
