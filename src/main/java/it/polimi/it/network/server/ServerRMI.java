package it.polimi.it.network.server;

import it.polimi.it.controller.Exceptions.ExistingNicknameException;
import it.polimi.it.controller.Exceptions.InvalidIDException;
import it.polimi.it.controller.Exceptions.WrongPlayerException;
import it.polimi.it.controller.Lobby;
import it.polimi.it.model.Exceptions.WrongListException;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientRMI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface ServerRMI extends Remote {

    User login(ClientRMI cr,String username) throws RemoteException, ExistingNicknameException;

    int createGame(User user, int playerNumber) throws RemoteException, WrongPlayerException;

    void joinGame(User user,int id) throws RemoteException, InvalidIDException, WrongPlayerException;

    void tilesNumMessage(User user,int numTiles) throws RemoteException, WrongListException, WrongPlayerException,IndexOutOfBoundsException;

    void selectedTiles(User user,List<Tile> choosenTiles) throws RemoteException, WrongPlayerException;

    void chooseColumn (User user,int columnNumber, List<Tile> orderedTiles) throws RemoteException, InvalidIDException;

}