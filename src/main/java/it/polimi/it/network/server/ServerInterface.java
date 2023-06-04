package it.polimi.it.network.server;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.client.RemoteInterface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInterface extends Remote, Serializable {

    public String login(RemoteInterface cr, String username) throws RemoteException, ExistingNicknameException, EmptyNicknameException, InvalidIDException;

    public int createGame(String username, int playerNumber, RemoteInterface client) throws RemoteException, WrongPlayerException;

    public int joinGame(String username,int id, RemoteInterface client) throws IOException, InvalidIDException, WrongPlayerException, IllegalValueException;

    public void tilesNumMessage(String username,int numTiles) throws IOException, WrongPlayerException, IllegalValueException, InvalidIDException;

    public void selectedTiles(String username,List<Tile> choosenTiles) throws IOException, WrongPlayerException, WrongListException, IllegalValueException, InvalidIDException;

    public void chooseColumn (String username,int columnNumber) throws IOException, InvalidIDException, IllegalValueException;

    void chatMessage(String chatMessage, String message) throws RemoteException;

    void chatPrivateMessage(String sender, String chatMessage, String receiver) throws RemoteException;
}