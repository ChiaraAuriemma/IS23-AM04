package it.polimi.it.network.server;

import it.polimi.it.Exceptions.*;
import it.polimi.it.controller.GameController;
import it.polimi.it.controller.Lobby;
import it.polimi.it.Exceptions.WrongListException;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.RemoteInterface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RMIImplementation implements ServerInterface, Serializable {

    private static final long serialVersionUID = -2905395065429128985L;
    private Lobby lobby;
    private HashMap<String,GameController> userGame;
    private int port;
    private Timer timer;
    private HashMap<String, Boolean> pongs;
    private HashMap<String, RemoteInterface> userRMI;
    private boolean first = false;
    private Registry registry;

    /**
     * Constructor method for the class.
     * @throws RemoteException .
     */
    public RMIImplementation() throws RemoteException{
        this.userGame = new HashMap<>();
        this.userRMI = new HashMap<>();
        this.pongs = new HashMap<>();
        new Thread(this::disconnectionTimer).start();
    }


    /**
     * Method that starts the RMI server given
     * @param port the port number.
     * @throws RemoteException .
     */
    public void startServer(int port) throws RemoteException {
        System.out.println("Server RMI started");
        this.port = port;
        ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(this,port);
        registry = LocateRegistry.createRegistry(port);
        try {
            registry.bind("server_RMI", stub);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Server RMI ready");
    }


    /**
     * Method called by an RMI client when he needs to perform a login.
     * @param cr is the client's RemoteInterface
     * @param username is the username that the player chose.
     * @return the same nickname string to confirm the login.
     * @throws RemoteException ,
     * @throws ExistingNicknameException ,
     * @throws EmptyNicknameException ,
     * @throws InvalidIDException .
     */
    public String login(RemoteInterface cr , String username) throws RemoteException, ExistingNicknameException, EmptyNicknameException, InvalidIDException {
        User user;
        synchronized (lobby) {
            user = lobby.createUser(username);
        }
        if(user.getInGame()){
            user.getGame().getVirtualView().setUser(username, cr);
            user.getGame().getVirtualView().removeDisconnection(user.getNickname());
            userGame.put(user.getNickname(),lobby.getGameController(user.getGame().getGameid()));
            userGame.get(user.getNickname()).resetGame(user);
        }
        userRMI.put(user.getNickname(), cr);
        pongs.put(user.getNickname(), true);
        return user.getNickname();
    }


    /**
     * Method invoked by an RMI client in order to create a new Game.
     * @param username is the player's nickname
     * @param playerNumber is the number of players that the host wants to join to the game.
     * @param client is the RemoteInterface of who invoked the method.
     * @return the GameID of the newly created game.
     * @throws RemoteException .
     * @throws WrongPlayerException .
     */
    public int createGame(String username,int playerNumber, RemoteInterface client) throws RemoteException, WrongPlayerException {
        GameController gc;
        synchronized (lobby){
            gc = lobby.createGame(username,playerNumber);
            gc.getGame().getVirtualView().setUser(username, client);
        }
        userGame.put(username,gc);
        return gc.getGameID();
    }


    /**
     * Method invoked by an RMI client in order to join a Game.
     * @param username is the player's nickname
     * @param id is the ID of the game that the player wants to join.
     * @param client is the RemoteInterface of who invoked the method.
     * @return the GameID of the newly created game.
     * @throws IOException .
     * @throws InvalidIDException .
     * @throws WrongPlayerException .
     * @throws IllegalValueException .
     */
    public int joinGame(String username,int id, RemoteInterface client) throws IOException, InvalidIDException, WrongPlayerException, IllegalValueException {
        GameController gc;
        synchronized (lobby) {
            lobby.getGameController(id).getGame().getVirtualView().setUser(username , client);
            gc = lobby.joinGame(username, id);
        }
        userGame.put(username, gc);
        return gc.getGameID();
    }


    /**
     * Method invoked by an RMI client in order to choose the number of tiles to be taken from the board.
     * @param username is the player's nickname
     * @param numTiles is the number of tiles that the player wants to retrieve from the LivingRoom Board.
     * @throws IOException .
     * @throws WrongPlayerException .
     * @throws IllegalValueException .
     * @throws InvalidIDException .
     */
    public void tilesNumMessage(String username,int numTiles) throws IOException, WrongPlayerException, IllegalValueException, InvalidIDException {
        GameController gc = userGame.get(username);
        synchronized (gc){
            gc.getFromViewNTiles(username,numTiles);
        }
    }


    /**
     * Method invoked by an RMI client in order to take some tiles from the Board.
     * @param username is the player's nickname
     * @param choosenTiles is the list of tiles that the player chose to take from the LivingRoom Board.
     * @throws IOException .
     * @throws WrongPlayerException .
     * @throws WrongListException .
     * @throws IllegalValueException .
     * @throws InvalidIDException .
     * @throws WrongTileException .
     */
    public void selectedTiles(String username, List<Tile> choosenTiles) throws IOException, WrongPlayerException, WrongListException, IllegalValueException, InvalidIDException, WrongTileException {
        GameController gc = userGame.get(username);
        synchronized (gc){
            ArrayList<Tile> chosenTiles = new ArrayList<>(choosenTiles);
            gc.getTilesListFromView(username,chosenTiles);
        }
    }


    /**
     * Method invoked by an RMI client in order to put the tiles in a certain column of the Shelfie.
     * @param username is the player's nickname
     * @param columnNumber is the chosen column.
     * @throws IOException .
     * @throws InvalidIDException .
     * @throws IllegalValueException .
     */
    public void chooseColumn (String username,int columnNumber) throws IOException, InvalidIDException, IllegalValueException {
        GameController gc = userGame.get(username);
        synchronized (gc){
            gc.getColumnFromView(username,columnNumber);
        }
    }


    /**
     * Method invoked by an RMI client in order to send a chat message.
     * @param username is the player's nickname
     * @param chatMessage is the message.
     * @throws RemoteException .
     */
    public void chatMessage(String username, String chatMessage) throws RemoteException {
        GameController gc = userGame.get(username);
        synchronized (gc) {
            gc.pushChatMessage(chatMessage);
        }
    }


    /**
     * Method invoked by an RMI client in order to send a private chat message.
     * @param sender is the sender's username.
     * @param chatMessage is the message
     * @param receiver is the receiver's username.
     * @throws RemoteException .
     * @throws IllegalValueException .
     */
    @Override
    public void chatPrivateMessage(String sender, String chatMessage, String receiver) throws RemoteException, IllegalValueException {
        GameController gc = userGame.get(sender);
        synchronized (gc) {
            gc.pushChatPrivateMessage(sender, chatMessage, receiver);
        }
    }



    /**
     * Method that notifies to the lobby that the player with
     * @param username as nickname disconnected from the game.
     */
    public void disconnect_user(String username) {
        lobby.disconnect_user(username);
    }


    /**
     * Disconnection Timer.
     * Pings the client every 15 seconds in order to check if the player is still online.
     */
    private void disconnectionTimer(){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for(String user: pongs.keySet()){
                    if(pongs.get(user)){
                        try{
                            RemoteInterface clientRMI = userRMI.get(user);
                            clientRMI.ping();
                            System.out.println(" is still connected, SADLY");
                        } catch (RemoteException e) {
                            pongs.put(user, false);
                            if(userGame.containsKey(user) && userGame.get(user).getUser(user).getInGame()){
                                if(userGame.get(user).getCurrentPlayer() == userGame.get(user).getPlayerNumber(userGame.get(user).getUser(user))){
                                    try {
                                        userGame.get(user).turnDealer();
                                    } catch (InvalidIDException | IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                                lobby.disconnect_user(user);
                                System.out.println(" disconnected");
                            }
                        }
                    }
               }
            }
        };
        timer.schedule(timerTask, 1000, 5000);
    }


    /**
     * Getter method for an RMI user's RemoteInterface
     * @param user as the research parameter
     * @return a RemoteInterface instance.
     */
    public RemoteInterface getUserRMI (User user){
        return userRMI.get(user.getNickname());
    }


    /**
     * Setter method for an instance of
     * @param lobby class.
     */
    public void setLobby(Lobby lobby){
        this.lobby=lobby;
    }

}
