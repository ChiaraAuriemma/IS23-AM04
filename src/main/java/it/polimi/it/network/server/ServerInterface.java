package it.polimi.it.network.server;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInterface extends Remote {

    User login(ClientInterface cr, String username) throws RemoteException, ExistingNicknameException;

    int createGame(User user, int playerNumber) throws RemoteException, WrongPlayerException;

    void joinGame(User user,int id) throws RemoteException, InvalidIDException, WrongPlayerException, IllegalValueException;

    void tilesNumMessage(User user,int numTiles) throws RemoteException, WrongListException, WrongPlayerException, IndexOutOfBoundsException, WrongTurnException, WrongListException, IllegalValueException;

    void selectedTiles(User user,List<Tile> choosenTiles) throws RemoteException, WrongPlayerException;

    void chooseColumn (User user,int columnNumber) throws RemoteException, InvalidIDException, IllegalValueException;

}