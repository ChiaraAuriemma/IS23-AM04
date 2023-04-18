package it.polimi.it.network;

import it.polimi.it.controller.Lobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerTCP {
    private  int port;

    private ServerSocket serverSocket;

    private Lobby lobby;
    public ServerTCP(int portNumber, Lobby lobby){
        this.port = portNumber;
        this.lobby = lobby;
    }

    public void startServer(){
        ExecutorService executor = Executors.newCachedThreadPool();  //crea un pool di thread che si autogestiscono

        System.out.println("Server TCP started");

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server TCP ready");


        while (true) {
            try {
                Socket clientSocket = serverSocket.accept(); //aspetta che qualcuno si colleghi
                executor.submit(new ClientTCPHandler(clientSocket, lobby)); //l'oggetto all'interno deve essere Runnable
            } catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
        executor.shutdown();
    }
}