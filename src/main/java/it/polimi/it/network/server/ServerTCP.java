package it.polimi.it.network.server;

import it.polimi.it.controller.Lobby;
import it.polimi.it.model.User;
import it.polimi.it.network.server.Exceptions.NotTcpUserException;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerTCP implements Runnable, Serializable{
    private static final long serialVersionUID = 2221472440957074825L;
    private  int port;
    private ServerSocket serverSocket ;
    private HashMap<String, Socket> userTCP;
    private Lobby lobby;

    private Socket clientSocket;
    public ServerTCP(int portNumber){
        this.port = portNumber;
        this.userTCP = new HashMap<>();
    }
@Override
    public void run(){
        ExecutorService executor = Executors.newCachedThreadPool();  //crea un pool di thread che si autogestiscono

        System.out.println("Server TCP started");

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }

        System.out.println("Server TCP ready");


        while (true) {
            try {
                clientSocket = serverSocket.accept(); //aspetta che qualcuno si colleghi
                executor.submit(new ClientTCPHandler( lobby, this)); //l'oggetto all'interno deve essere Runnable
            } catch(IOException e) {
                break; //entro nel caso il server socket venisse chiuso
            }
        }
        executor.shutdown();
    }

    public Socket getUserTCP(User user) throws NotTcpUserException {
        if(userTCP.containsKey(user.getNickname())){
            return userTCP.get(user.getNickname());
        }else{
            throw new NotTcpUserException("this user doesn't have a TCP connection");
        }
    }

    public void setUserTCP(User user, Socket socket){
        userTCP.put(user.getNickname(),socket);
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void removeUserTCP(String user){
        this.userTCP.remove(user);
    }
}