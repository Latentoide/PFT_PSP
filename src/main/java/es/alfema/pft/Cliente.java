package es.alfema.pft;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;


public class Cliente {

    @FXML
    private ImageView imagen;

    @FXML
    private Pane scene;

    @FXML
    private TextField ip;

    @FXML
    private TextField port;

    private String ipString = "127.0.0.1";
    private String portString = "8000";
    private Thread t1;
    private BallTask bT;
    @FXML
    protected void connect() throws InterruptedException {
        Thread action = new Thread(new Runnable() {
            @Override
            public void run() {
                //si no pone el usuario ninguna ip y host se pone automáticamente
                if(!ip.getText().equals("")){
                    ipString = ip.getText();
                }
                if (!port.getText().equals("")){
                    portString = port.getText();
                }

                System.out.println(ipString + " " + portString);

                //Comienza el intento de conexión
                try (Socket echoSocket = new Socket(ipString, Integer.parseInt(portString));
                     ObjectInputStream ois = new ObjectInputStream(echoSocket.getInputStream())) {

                    //Crea un hilo que moverá la pelota
                    t1 = new Thread(bT);
                    System.out.println("Conexión hecha");
                    Posicion pos = null;
                    bT = new BallTask(imagen, 10, pos, true);
                    t1.start();

                    //si el servidor a escrito algo lo recogeremos y moverá la pelota
                    while ((pos = (Posicion) ois.readObject()) != null){
                        bT.setPosition(pos);
                    }

                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        });
        action.start();
    }
}
