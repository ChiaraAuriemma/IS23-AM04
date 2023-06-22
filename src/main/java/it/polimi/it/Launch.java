package it.polimi.it;

import it.polimi.it.network.client.Client;
import it.polimi.it.network.server.Server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

public class Launch {
    public static void main(String[] args) throws AlreadyBoundException, IOException, NotBoundException {
        if(args[0].equalsIgnoreCase("server")){
            Server.main(args);
        }else if(args[0].equalsIgnoreCase("client")){
            String [] specifics = new String[2];
            specifics[0] = args[1];
            specifics[1] = args[2];
            Client.main(specifics);
        }
    }
}
