package it.polimi.it.network.server;

import it.polimi.it.Exceptions.*;
import it.polimi.it.controller.GameController;
import it.polimi.it.controller.Lobby;
import it.polimi.it.Exceptions.WrongListException;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientInterface;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;

public class RMIImplementation extends UnicastRemoteObject implements ServerRMI {

    private Lobby lobby;
    private HashMap<User,GameController> userGame;
    private int port;

    private HashMap<User, ClientInterface> userRMI;

    private Registry registry;
    public RMIImplementation(Lobby lobby) throws RemoteException{
        this.lobby = lobby;
        this.userGame = new HashMap<>();
        this.userRMI = new HashMap<>();

    }

    public void startServer(int port) throws RemoteException, AlreadyBoundException {
        this.port = port;
        registry = LocateRegistry.getRegistry(port);
        registry.bind("server_RMI", this);

    }

    public ClientInterface getUserRMI (User user){
        return userRMI.get(user);
    }

    public User login(ClientInterface cr , String username) throws RemoteException, ExistingNicknameException {
        User user = lobby.createUser(username);
        userRMI.put(user,cr);
        return user;
    }

    public int createGame(User user,int playerNumber) throws RemoteException, WrongPlayerException {
        GameController gc = lobby.createGame(user,playerNumber);
        userGame.put(user,gc);
        return gc.getGameID();
    }

    public void joinGame(User user,int id) throws RemoteException, InvalidIDException, WrongPlayerException, IllegalValueException {
        GameController gc = lobby.joinGame(user,id);
        userGame.put(user, gc);
    }

    public void tilesNumMessage(User user,int numTiles) throws RemoteException, WrongListException, IndexOutOfBoundsException, WrongTurnException, WrongListException, IllegalValueException {
        GameController gc = userGame.get(user);
        gc.getFromViewNTiles(user,numTiles);
            //numTiles è il valore scelto dall'utente (v. javadoc GameController)
    }

    public void selectedTiles(User user,List<Tile> choosenTiles) throws RemoteException, WrongPlayerException {
        GameController gc = userGame.get(user);
        gc.getTilesListFromView(user,choosenTiles);
    }

    public void chooseColumn (User user,int columnNumber) throws RemoteException, InvalidIDException, IllegalValueException {
        GameController gc = userGame.get(user);
        gc.getColumnFromView(user,columnNumber);
    }



}
