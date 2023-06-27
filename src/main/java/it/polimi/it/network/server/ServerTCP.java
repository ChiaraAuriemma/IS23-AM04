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

/**
 * Class that implements the methods for the TCP connection
 */
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


    /**
     * Starts the TCP Server
     */
    @Override
    public void run(){
        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("Server TCP started");

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
        System.out.println("Server TCP ready");

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                executor.submit(new ClientTCPHandler( lobby, this));
            } catch(IOException e) {
                break;
            }
        }
        executor.shutdown();
    }


    /**
     * Getter method for a TCP User
     * @param user is the instance of the User class
     * @return the corresponding Socket
     * @throws NotTcpUserException .
     */
    public Socket getUserTCP(User user) throws NotTcpUserException {
        if(userTCP.containsKey(user.getNickname())){
            return userTCP.get(user.getNickname());
        }else{
            throw new NotTcpUserException("this user doesn't have a TCP connection");
        }
    }


    /**
     * Setter method, registers a newly connected player and his socket
     * @param user is the player
     * @param socket is his socket.
     */
    public void setUserTCP(User user, Socket socket){
        userTCP.put(user.getNickname(),socket);
    }


    /**
     * Setter method for the Lobby instance
     * @param lobby .
     */
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }


    /**
     * Getter method for the
     * @return client Socket.
     */
    public Socket getClientSocket() {
        return clientSocket;
    }


    /**
     * Removes a TCP user that disconnected from the server.
     * @param user is the player that has to be disconnected.
     */
    public void removeUserTCP(String user){
        this.userTCP.remove(user);
    }
}