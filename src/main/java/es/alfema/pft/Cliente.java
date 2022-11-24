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
                if(!ip.getText().equals("")){
                    ipString = ip.getText();
                }
                if (!port.getText().equals("")){
                    portString = port.getText();
                }

                System.out.println(ipString + " " + portString);
                try (Socket echoSocket = new Socket(ipString, Integer.parseInt(portString));
                     ObjectInputStream ois = new ObjectInputStream(echoSocket.getInputStream())) {

                    t1 = new Thread(bT);

                    System.out.println("Conexión hecha");
                    Posicion pos = null;
                    while ((pos = (Posicion) ois.readObject()) != null){
                        if(bT == null){
                            bT = new BallTask(imagen, 100, pos, true);
                            t1.start();
                        }else{
                            System.out.println(pos);
                            bT.setPosition(pos);
                        }
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
