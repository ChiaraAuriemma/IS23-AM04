package it.polimi.it.network.server;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientInterface;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInterface extends Remote, Serializable {

    public User login(ClientInterface cr, String username) throws RemoteException, ExistingNicknameException, EmptyNicknameException;

    public int createGame(User user, int playerNumber, ClientInterface client) throws RemoteException, WrongPlayerException;

    public int joinGame(User user,int id, ClientInterface client) throws RemoteException, InvalidIDException, WrongPlayerException, IllegalValueException;

    public void tilesNumMessage(User user,int numTiles) throws RemoteException, WrongPlayerException, WrongListException, IllegalValueException;

    public void selectedTiles(User user,List<Tile> choosenTiles) throws RemoteException, WrongPlayerException;

    public void chooseColumn (User user,int columnNumber) throws RemoteException, InvalidIDException, IllegalValueException;

}