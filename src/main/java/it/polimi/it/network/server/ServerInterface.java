package it.polimi.it.network.server;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.client.ClientInterface;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInterface extends Remote, Serializable {

    public String login(ClientInterface cr, String username) throws RemoteException, ExistingNicknameException, EmptyNicknameException;

    public int createGame(String username, int playerNumber, ClientInterface client) throws RemoteException, WrongPlayerException;

    public int joinGame(String username,int id, ClientInterface client) throws RemoteException, InvalidIDException, WrongPlayerException, IllegalValueException;

    public void tilesNumMessage(String username,int numTiles) throws RemoteException, WrongPlayerException, IllegalValueException, InvalidIDException;

    public void selectedTiles(String username,List<Tile> choosenTiles) throws RemoteException, WrongPlayerException, WrongListException, IllegalValueException, InvalidIDException;

    public void chooseColumn (String username,int columnNumber) throws RemoteException, InvalidIDException, IllegalValueException;

    void chatMessage(String chatMessage, String message) throws RemoteException;
}