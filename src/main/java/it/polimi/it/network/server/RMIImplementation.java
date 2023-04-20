package it.polimi.it.network.server;

import it.polimi.it.controller.Exceptions.ExistingNicknameException;
import it.polimi.it.controller.Exceptions.InvalidIDException;
import it.polimi.it.controller.Exceptions.WrongPlayerException;
import it.polimi.it.controller.GameController;
import it.polimi.it.controller.Lobby;
import it.polimi.it.model.Exceptions.WrongListException;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientRMI;

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

    private HashMap<User, ClientRMI> userClient;

    private Registry registry;
    public RMIImplementation(Lobby lobby) throws RemoteException{
        this.lobby = lobby;
        this.userGame = new HashMap<>();
        this.userClient = new HashMap<>();

    }

    public void startServer(int port) throws RemoteException, AlreadyBoundException {
        this.port = port;
        registry = LocateRegistry.getRegistry(port);
        registry.bind("server_RMI", this);
        //metto la lista dei client collegati per rispondere

    }

    public User login(ClientRMI cr ,String username) throws RemoteException, ExistingNicknameException {
        User user = lobby.createUser(username);
        userClient.put(user,cr);
        return user;
    }

    public int createGame(User user,int playerNumber) throws RemoteException, WrongPlayerException {
        GameController gc = lobby.createGame(user,playerNumber);
        userGame.put(user,gc);
        return gc.getGameID();
    }

    public void joinGame(User user,int id) throws RemoteException, InvalidIDException, WrongPlayerException {
        GameController gc = lobby.joinGame(user,id);
        userGame.put(user, gc);
    }

    public void tilesNumMessage(User user,int numTiles) throws RemoteException, WrongListException, WrongPlayerException, IndexOutOfBoundsException {
        GameController gc = userGame.get(user);
        gc.getFromViewNTiles(user,numTiles);
    }

    public void selectedTiles(User user,List<Tile> choosenTiles) throws RemoteException, WrongPlayerException {
        GameController gc = userGame.get(user);
        gc.getTilesListFromView(user,choosenTiles);
    }

    public void chooseColumn (User user,int columnNumber, List<Tile> orderedTiles) throws RemoteException, InvalidIDException {
        GameController gc = userGame.get(user);
        gc.getColumnFromView(user,columnNumber,orderedTiles);
    }

    //------------------------------------------------------------------------------------------------------------------

    public void

}
