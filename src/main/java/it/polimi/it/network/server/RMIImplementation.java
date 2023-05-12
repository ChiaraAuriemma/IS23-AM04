package it.polimi.it.network.server;

import it.polimi.it.Exceptions.*;
import it.polimi.it.controller.GameController;
import it.polimi.it.controller.Lobby;
import it.polimi.it.Exceptions.WrongListException;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;

public class RMIImplementation extends UnicastRemoteObject implements ServerInterface, Serializable {

    private static final long serialVersionUID = -2905395065429128985L;
    private Lobby lobby;
    private HashMap<User,GameController> userGame;
    private int port;

    private HashMap<User, ClientInterface> userRMI;

    private Registry registry;
    public RMIImplementation() throws RemoteException{
        this.userGame = new HashMap<>();
        this.userRMI = new HashMap<>();

    }

    public void startServer(int port) throws RemoteException {
        System.out.println("Server RMI started");
        this.port = port;
        registry = LocateRegistry.createRegistry(port);
        try {
            registry.bind("server_RMI", this);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Server RMI ready");

    }

    public ClientInterface getUserRMI (User user){
        return userRMI.get(user);
    }


    public User login(ClientInterface cr , String username) throws RemoteException, ExistingNicknameException, EmptyNicknameException {
        User user;
        synchronized (lobby) {
            user = lobby.createUser(username);
        }
        userRMI.put(user, cr);
        return user;
    }

    public int createGame(User user,int playerNumber, ClientInterface client) throws RemoteException, WrongPlayerException {
        GameController gc;
        synchronized (lobby){
            gc = lobby.createGame(user,playerNumber, client);
        }
        userGame.put(user,gc);
        return gc.getGameID();
    }

    public void joinGame(User user,int id, ClientInterface client) throws RemoteException, InvalidIDException, WrongPlayerException, IllegalValueException {
        GameController gc;
        synchronized (lobby) {
            gc = lobby.joinGame(user, id, client);
        }
        userGame.put(user, gc);
    }

    public void tilesNumMessage(User user,int numTiles) throws RemoteException, WrongPlayerException, WrongListException, IllegalValueException {
        GameController gc = userGame.get(user);
        synchronized (gc){
            gc.getFromViewNTiles(user,numTiles);
        }
        //numTiles è il valore scelto dall'utente (v. javadoc GameController)
    }

    public void selectedTiles(User user,List<Tile> choosenTiles) throws RemoteException, WrongPlayerException {
        GameController gc = userGame.get(user);
        synchronized (gc){
            gc.getTilesListFromView(user,choosenTiles);
        }
    }

    public void chooseColumn (User user,int columnNumber) throws RemoteException, InvalidIDException, IllegalValueException {
        GameController gc = userGame.get(user);
        synchronized (gc){
            gc.getColumnFromView(user,columnNumber);
        }
    }

    public void setLobby(Lobby lobby){
        this.lobby=lobby;
    }


}
