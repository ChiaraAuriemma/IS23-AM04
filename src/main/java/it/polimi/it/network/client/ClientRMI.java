package it.polimi.it.network.client;

import it.polimi.it.model.Tiles.Tile;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientRMI extends Remote {
    //qui devo mettere solo i metodi visibili dal server (quindi tolgo startclient e login)
    public void startClient() throws IOException, NotBoundException;
    public void login(String userName) throws RemoteException;

    public void takeableTiles(List<List<Tile>> choosableTilesList);
}
