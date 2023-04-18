package it.polimi.it.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerRMI {

    private int port;

    private ServerSocket serverSocket;
    public ServerRMI(int portNumber){
        this.port = portNumber;
    }

    public void startServer(){
        ExecutorService executor = Executors.newCachedThreadPool();  //crea un pool di thread che si autogestiscono

        System.out.println("Server RMI started");

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server RMI ready");


        while (true) {
            try {
                Socket clientSocket = serverSocket.accept(); //aspetta che qualcuno si colleghi
                executor.submit(new ClientRMIHandler(clientSocket)); //l'oggetto all'interno deve essere Runnable
            } catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
        executor.shutdown();
    }
}