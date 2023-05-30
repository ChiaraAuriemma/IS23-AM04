package it.polimi.it.network.server;

import it.polimi.it.Exceptions.*;
import it.polimi.it.controller.GameController;
import it.polimi.it.controller.Lobby;
import it.polimi.it.Exceptions.WrongListException;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.message.Message;
import it.polimi.it.network.message.MessageType;
import it.polimi.it.network.message.others.PingMessage;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RMIImplementation extends UnicastRemoteObject implements ServerInterface, Serializable {

    private static final long serialVersionUID = -2905395065429128985L;
    private Lobby lobby;
    private HashMap<String,GameController> userGame;
    private int port;
    private Timer timer;

    private HashMap<String, Boolean> pongs;
    private HashMap<String, ClientInterface> userRMI;

    private boolean first = false;

    private Registry registry;
    public RMIImplementation() throws RemoteException{
        this.userGame = new HashMap<>();
        this.userRMI = new HashMap<>();
        this.pongs = new HashMap<>();
        new Thread(this::disconnectionTimer).start();
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

       // new Thread(this::disconnectionTimer).start();
    }

    public ClientInterface getUserRMI (User user){
        return userRMI.get(user.getNickname());
    }




    public String login(ClientInterface cr , String username) throws RemoteException, ExistingNicknameException, EmptyNicknameException, InvalidIDException {
        User user;
        synchronized (lobby) {
            user = lobby.createUser(username);
        }
        if(user.getInGame()){
            user.getGame().getVirtualView().setUserRMI(username, cr);
            lobby.getGameController(user.getGame().getGameid()).resetGame(user);
        }
        userRMI.put(user.getNickname(), cr);
        pongs.put(user.getNickname(), true);
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

    public int joinGame(String username,int id, ClientInterface client) throws IOException, InvalidIDException, WrongPlayerException, IllegalValueException {
        GameController gc;
        synchronized (lobby) {
            lobby.getGameController(id).getGame().getVirtualView().setUserRMI(username , client);
            gc = lobby.joinGame(username, id);
        }
        userGame.put(username, gc);
        return gc.getGameID();
    }

    public void tilesNumMessage(String username,int numTiles) throws IOException, WrongPlayerException, IllegalValueException, InvalidIDException {
        GameController gc = userGame.get(username);
        synchronized (gc){
            gc.getFromViewNTiles(username,numTiles);
        }
        //numTiles è il valore scelto dall'utente (v. javadoc GameController)
    }

    public void selectedTiles(String username, List<Tile> choosenTiles) throws IOException, WrongPlayerException, WrongListException, IllegalValueException, InvalidIDException {
        GameController gc = userGame.get(username);
        synchronized (gc){
            ArrayList<Tile> chosenTiles = new ArrayList<>(choosenTiles);
            gc.getTilesListFromView(username,chosenTiles);
        }
    }

    public void chooseColumn (String username,int columnNumber) throws IOException, InvalidIDException, IllegalValueException {
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

    @Override
    public void chatPrivateMessage(String sender, String chatMessage, String receiver) throws RemoteException {
        GameController gc = userGame.get(sender);
        synchronized (gc) {
            gc.pushChatPrivateMessage(sender, chatMessage, receiver);
        }
    }

    public void setLobby(Lobby lobby){
        this.lobby=lobby;
    }

    public void disconnect_user(String username) {

        //trovare l'istanza dell'utente e mettergli
        //il bool della connessione a false

        lobby.disconnect_user(username);
    }


    //per ogni client va mandato un diverso timer    new Thread(this::disconnectionTimer).start();
    /*private void disconnectionTimer(){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(userRMI.size()>0){

                    //itero su tutti gli user
                   String a[] = new String[]{userRMI.toString()};

                   Set<String> set = userRMI.keySet();

                   for (String user: set){
                       //if(pongs.get(user)==true){//guardo se questo user ha il suo

                       //}
                       if(lobby.checkIfStillConnected(user)){

                           userRMI.get(user).ping();
                       }
                   }


                                    for(Map.Entry<String, ClientInterface>entry: userRMI){

                                    }
                }
                if(pong == true){
                    pong = false;


                    chiamare metodo rmi sui client che resetti
                    System.out.println(" is still connected, SADLY");
                }else{
                    //disconnetti
                    System.out.println(" disconnected");
                }
            }
        };
        timer.schedule(timerTask, 1000, 15000);

                   // public void schedule(TimerTask task, long delay, long period)
                     //   task - task to be scheduled.
                     //   delay - delay in milliseconds before task is to be executed.        1000->1secondo
                     //   period - time in milliseconds between successive task executions.   200000->20 secondi

    }*/

    private void disconnectionTimer(){
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for(String user: pongs.keySet()){

                    if(pongs.get(user)){
                        try{
                            ClientInterface clientRMI = userRMI.get(user);
                            clientRMI.ping();
                            System.out.println(" is still connected, SADLY");
                        } catch (RemoteException e) {
                            pongs.put(user, false);
                            //se il player è già in partita chiamo lo disconnetto
                            if(userGame.containsKey(user) && userGame.get(user).getUser(user).getInGame()){

                                if(userGame.get(user).getCurrentPlayer() == userGame.get(user).getPlayerNumber(userGame.get(user).getUser(user))){

                                    try {
                                        userGame.get(user).turnDealer();
                                    } catch (InvalidIDException | IllegalValueException | IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }
                                //disconnetti dal game
                                lobby.disconnect_user(user);
                                System.out.println(" disconnected");
                            }
                        }
                    }

                    //TODO:
                    //gestisco caso in cui il player è in lobby e si disconnette (forse non c'è bisogno... non si userà quel nickname e basta )
                }
            }
        };
        timer.schedule(timerTask, 1000, 15000);
                    /*
                    public void schedule(TimerTask task, long delay, long period)
                        task - task to be scheduled.
                        delay - delay in milliseconds before task is to be executed.        1000->1secondo
                        period - time in milliseconds between successive task executions.   200000->20 secondi
                     */
    }


}
