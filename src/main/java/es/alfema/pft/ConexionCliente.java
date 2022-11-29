package es.alfema.pft;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConexionCliente extends Thread{
    private ServerSocket socketServidor;
    private BallTask bT;
    private boolean conectado;
    private boolean rep;

    public ConexionCliente (ServerSocket socketServidor, BallTask bT){
        this.socketServidor = socketServidor;
        this.bT = bT;
    }

    @Override
    public void run() {
        super.run();
        rep = true;
        try (Socket socketComunicacion = socketServidor.accept()) {
            System.out.printf("Cliente conectado desde %s:%d.\n", socketComunicacion.getInetAddress().getHostAddress(), socketComunicacion.getPort());

            try (OutputStream os = socketComunicacion.getOutputStream();
                 ObjectOutputStream oosCliente = new ObjectOutputStream(os);) {
                System.out.println("he llegado");
                conectado = true;
                while (true) {
                    Posicion pos = new Posicion(bT.getPosition().myX, bT.getPosition().myY);
                    oosCliente.writeObject(pos);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getCon(){
        return conectado;
    }

    public boolean getRep(){
        return rep;
    }
}
