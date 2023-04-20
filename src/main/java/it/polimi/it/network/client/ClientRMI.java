package it.polimi.it.network.client;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRMI extends Remote {

    public void startClient() throws RemoteException, NotBoundException;
    public void login(String userName) throws RemoteException;
}
