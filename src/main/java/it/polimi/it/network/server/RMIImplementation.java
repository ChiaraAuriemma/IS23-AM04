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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RMIImplementation extends UnicastRemoteObject implements ServerInterface, Serializable {

    private static final long serialVersionUID = -2905395065429128985L;
    private Lobby lobby;
    private HashMap<String,GameController> userGame;
    private int port;

    private HashMap<String, ClientInterface> userRMI;

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
        return userRMI.get(user.getNickname());
    }




    public String login(ClientInterface cr , String username) throws RemoteException, ExistingNicknameException, EmptyNicknameException {
        User user;
        synchronized (lobby) {
            user = lobby.createUser(username);
        }
        userRMI.put(user.getNickname(), cr);
        return user.getNickname();
    }

    public int createGame(String username,int playerNumber, ClientInterface client) throws RemoteException, WrongPlayerException {
        GameController gc;
        synchronized (lobby){
            gc = lobby.createGame(username,playerNumber);
            gc.getGame().getVirtualView().setUserRMI(username, client);
        }
        userGame.put(username,gc);
        return gc.getGameID();
    }

    public int joinGame(String username,int id, ClientInterface client) throws RemoteException, InvalidIDException, WrongPlayerException, IllegalValueException {
        GameController gc;
        synchronized (lobby) {
            lobby.getGameController(id).getGame().getVirtualView().setUserRMI(username , client);
            gc = lobby.joinGame(username, id);
        }
        userGame.put(username, gc);
        return gc.getGameID();
    }

    public void tilesNumMessage(String username,int numTiles) throws RemoteException, WrongPlayerException, WrongListException, IllegalValueException {
        GameController gc = userGame.get(username);
        synchronized (gc){
            gc.getFromViewNTiles(username,numTiles);
        }
        //numTiles è il valore scelto dall'utente (v. javadoc GameController)
    }

    public void selectedTiles(String username, List<Tile> choosenTiles) throws RemoteException, WrongPlayerException, WrongListException {
        GameController gc = userGame.get(username);
        synchronized (gc){
            ArrayList<Tile> chosenTiles = new ArrayList<>(choosenTiles);
            gc.getTilesListFromView(username,chosenTiles);
        }
    }

    public void chooseColumn (String username,int columnNumber) throws RemoteException, InvalidIDException, IllegalValueException {
        GameController gc = userGame.get(username);
        synchronized (gc){
            gc.getColumnFromView(username,columnNumber);
        }
    }

    public void chatMessage(String username, String chatMessage) throws RemoteException {
        //aggiungo il messaggio al model
        GameController gc = userGame.get(username);
        synchronized (gc) {
            gc.pushChatMessage(chatMessage);
        }
    }

    public void setLobby(Lobby lobby){
        this.lobby=lobby;
    }


}
