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

        //método que hace update de la imagen
        valueProperty().addListener(new ChangeListener<Posicion>() {
            @Override
            public void changed(ObservableValue<? extends Posicion> observableValue, Posicion posicion, Posicion t1) {
                imageView.setLayoutX(t1.getX());
                imageView.setLayoutY(t1.getY());
            }
        });
    }

    /**
     * Inicio de la acción del Thread
     * Hará una comprobación de si ha comenzado, posteriormente en el servidor comenzará a aumentar la posición suya
     * si es el cliente obviara ese paso. Luego esperará unos segundos. Y hará el update de la imagen por último
     * @return
     * @throws Exception
     */
    @Override
    protected Posicion call() throws Exception {
        while (!isCliente){
            boolean start = position.getStart();
            if(start){
                controlMove();
                position.aumXY();
                Thread.sleep(waiting);
                updateValue(new Posicion(position.getX(),position.getY()));
            }else{
                updateValue(new Posicion(position.getX(),position.getY()));
            }
        }
        return null;
    }

    /**
     * Método que utiliza el cliente para posicionar la imagen una vez creada el thread
     * @param pos
     */
    public void setPosition(Posicion pos){
        updateValue(pos);
    }

    /**
     * Método que cambia el booleano de start/stop
     */
    public void changeStartStop(){
        position.changeStartStop();
    }

    /**
     * Método que controla las direcciones de la pelota
     */
    public void controlMove(){
        if(500 < position.myY) {
            position.setMoveY(Direcciones.UP);
        }
        if(0 > position.myY) {
            position.setMoveY(Direcciones.DOWN);
        }
        if(550 < position.myX) {
            position.setMoveX(Direcciones.LEFT);
        }
        if(0 > position.myX) {
            position.setMoveX(Direcciones.RIGHT);
        }
    }

    public Posicion getPosition(){
        return position;
    }
}
