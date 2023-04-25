package it.polimi.it.network.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTCP {

    private int port;
    private String ip;

    private Socket serverSocket;
    public ClientTCP(int port, String ip){
        this.port = port;
        this.ip = ip;
    }

    public void startClient(){
        try{
            serverSocket = new Socket(ip,port);

            //continuo
        } catch (UnknownHostException e) {
            System.out.println("Don't know about host " + ip);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Couldn't get I/O for the connection to " + ip);
            System.exit(1);
        }
    }

}
