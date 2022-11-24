package es.alfema.pft;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {
    @FXML
    private ImageView imagen;

    @FXML
    private Pane scene;

    double layX;
    double layY;

    long waiting = 100;
    int displacement = 10;

    private Thread t;

    BallTask bT;
    List<Thread> threadList = new LinkedList<>();
    Thread serverThread;

    @FXML
    protected void moveBall() throws InterruptedException {
        if(serverThread == null){
            serverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int numPuerto = 8000;

                    try (ServerSocket socketServidor = new ServerSocket(numPuerto)) {

                        System.out.printf("Creado socket de servidor en puerto %d. Esperando conexiones de clientes.\n", numPuerto);

                        while (true) { // Acepta una conexión de cliente tras otra
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try (Socket socketComunicacion = socketServidor.accept()) {

                                        System.out.printf("Cliente conectado desde %s:%d.\n", socketComunicacion.getInetAddress().getHostAddress(), socketComunicacion.getPort());

                                        try (OutputStream os = socketComunicacion.getOutputStream();
                                             ObjectOutputStream oosCliente = new ObjectOutputStream(os);) {
                                            System.out.println("he llegado");

                                            while (true) {
                                                Posicion pos = new Posicion(bT.getPosition().myX, bT.getPosition().myY);
                                                System.out.println(pos);
                                                oosCliente.writeObject(pos);
                                            }
                                        } catch (Exception e) {
                                            System.out.println(e.getMessage());;
                                        }
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                            t.start();
                            threadList.add(t);
                        }

                    } catch (IOException ex) {

                        System.out.println("Excepción de E/S");

                        ex.printStackTrace();

                        System.exit(1);

                    }
                }
            });
            serverThread.start();
        }
        if(t == null){
            double layX = imagen.getLayoutX();
            double layY = imagen.getLayoutY();
            bT = new BallTask(imagen, waiting, new Posicion(layX, layY), false);
            t = new Thread(bT);
            t.start();
        }else{
            System.out.println("Buenas");
            bT.changeStartStop();
        }

    }
}