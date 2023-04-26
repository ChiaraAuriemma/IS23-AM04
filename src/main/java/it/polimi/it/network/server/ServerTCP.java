package it.polimi.it.network.server;

import it.polimi.it.controller.Lobby;
import it.polimi.it.model.User;
import it.polimi.it.network.server.ClientTCPHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerTCP {
    private  int port;
    private ServerSocket serverSocket;
    private HashMap<User, Socket> userTCP;
    private Lobby lobby;
    public ServerTCP(int portNumber, Lobby lobby){
        this.port = portNumber;
        this.lobby = lobby;
        this.userTCP = new HashMap<>();
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
                executor.submit(new ClientTCPHandler(clientSocket, lobby, this)); //l'oggetto all'interno deve essere Runnable
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    public Socket getUserTCP(User user) {
        return userTCP.get(user);
    }

    public void setUserTCP(User user, Socket socket){
        userTCP.put(user,socket);
    }
}