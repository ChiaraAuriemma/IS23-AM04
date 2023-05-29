package it.polimi.it.controller;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Game;
import it.polimi.it.model.User;
import it.polimi.it.network.server.RMIImplementation;
import it.polimi.it.network.server.ServerTCP;
import it.polimi.it.network.server.VirtualView;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Lobby implements Serializable {

    private static final long serialVersionUID = 2013868577459101390L;


    /**
     * List of all the Users connected to the application
     */
    private ArrayList<User> userList;


    /**
     * List of all the instances of the Games
     * [Multiple games can be started simultaneously]
     */
    private ArrayList<Game> gameList;


    /**
     * List of all the instances of the Games' Controllers
     * [Multiple games can be started simultaneously]
     */
    private ArrayList<GameController> gameControllerList;


    /**
     * Counter, used to know how many games in total have been started since the launch of the server
     */
    private int gameCounterID;


    /**
     * Instance of a User
     */
    private User user;


    /**
     * Instance of the TCP Server
     */
    private ServerTCP serverTCP;


    /**
     * Instance of the TCP Server
     */
    private RMIImplementation serverRMI;


    /**
     * Constructor of the class
     */
    public Lobby() {
        userList = new ArrayList<>();
        gameList = new ArrayList<>();
        gameControllerList = new ArrayList<>();
        this.gameCounterID = 0;
    }


    /**
     * Method that check if the nickname is already used, check if the nickname is used by a client that disconnected and create a new user if neither of the previous check is true
     * @param nickname is the username of the user wrote by the client
     * @return the initialized user
     * @throws ExistingNicknameException if the nickname is already used
     * @throws EmptyNicknameException if the nickname is empty
     * @throws RemoteException if there is a problem in RMI
     */
    public User createUser(String  nickname) throws ExistingNicknameException, EmptyNicknameException, RemoteException {
        if(nickname == null || nickname.length() ==0){
            throw new EmptyNicknameException("You have to write something");
        }else{
            if(userList.stream()
                        .map(currentUser -> currentUser.getNickname())
                        .noneMatch(name -> name.equals(nickname)))
            {
                user = new User(nickname);
                userList.add(user);
            }else{
                Optional<User> user = userList.stream()
                        .filter(name -> name.getNickname().equals(nickname))
                        .findFirst();
                if(!user.get().getInGame()) {
                    /*Optional<Integer> disconnectedGameID = gameControllerList.stream()
                            .filter(gc -> gc.getPlayerList().stream()
                                    .anyMatch(u -> u.getNickname().equals(nickname)))
                            .map(GameController::getGameID)
                            .findFirst();*/
                    user.get().setInGame(true);

                    int gameID = user.get().getGame().getGameid();
                    List<GameController> findGameController = gameControllerList.stream().filter(gameController -> gameController.getGame().getGameid() == gameID).collect(Collectors.toList());

                    GameController gc = findGameController.get(0);
                    gc.resetGame(user.get());
                }else{
                    throw new ExistingNicknameException("This nickname already exists!");//mandare messaggio a view
                }
            }
        }
        return user;
    }


    /**
     * Method used to create a new game with a game controller and a virtual view
     * @param username is the nickname of the user who wants to create a game
     * @param playerNumber is the number of player that have to join to start the game
     * @return the game controller of the game
     * @throws WrongPlayerException if the user write a player number <2 or >4
     */
    public GameController createGame(String username, int playerNumber) throws WrongPlayerException {
        if(playerNumber < 2 || playerNumber > 4){
            throw new WrongPlayerException("Wrong number of players"); //mandare messaggio a view
        }

        VirtualView virtualView = new VirtualView();
        virtualView.setServerRMI(this.serverRMI);
        virtualView.setServerTCP(this.serverTCP);

        Game game = new Game(playerNumber, userList.get(userList.indexOf(userList.stream().filter(user -> Objects.equals(user.getNickname(), username)).collect(Collectors.toList()).get(0))), this.gameCounterID,virtualView);
        user.setInGame(true);
        gameList.add(game);
        GameController gameContr = new GameController(game, this);
        gameControllerList.add(gameContr);
        this.gameCounterID++;

        return gameContr;
    }


    /**
     * Method used to join an already created game using the game ID
     * @param username is the nickname of the user that wants to join a game
     * @param gameID is the unique identifier of an already created game
     * @return the game controller of the game
     * @throws InvalidIDException if the client write a game ID that not exist
     * @throws WrongPlayerException if there is too much player in the game
     * @throws IllegalValueException if the client write a game ID that not exist
     * @throws RemoteException if there is some problem with RMI
     */
    public GameController
    joinGame(String username, int gameID) throws InvalidIDException, WrongPlayerException, IllegalValueException, IOException {

        List<Game> findGame = gameList.stream().filter(game -> game.getGameid() == gameID).collect(Collectors.toList());
        if(gameID<gameCounterID && !findGame.isEmpty()){
            if(findGame.get(0).getCurrentPlayersNum()<findGame.get(0).getNumplayers()){
                findGame.get(0).joinGame(userList.get(userList.indexOf(userList.stream().filter(u -> Objects.equals(u.getNickname(), username)).collect(Collectors.toList()).get(0))));

                if(!userList.get(userList.indexOf(userList.stream().filter(u -> Objects.equals(u.getNickname(), username)).collect(Collectors.toList()).get(0))).getInGame()){
                    userList.get(userList.indexOf(userList.stream().filter(u -> Objects.equals(u.getNickname(), username)).collect(Collectors.toList()).get(0))).setInGame(true);
                }

                if (findGame.get(0).getNumplayers()==findGame.get(0).getCurrentPlayersNum()){
                    gameControllerList.get(gameList.indexOf(findGame.get(0))).firstTurnStarter();
                }
                return gameControllerList.get(gameList.indexOf(findGame.get(0)));
            }else{
                throw new WrongPlayerException("There are already too many players in this game!");
            }
        }else{
            throw new InvalidIDException("The given game ID does not exists");
        }
    }

    /**
     * Getter method that given the
     * @param ID id of a game
     * @return the instance of the game that contains that gameID
     * @throws InvalidIDException if the given gameID is invalid
     */
    Game getGame(int ID) throws InvalidIDException {
        for(Game game : gameList){
            if(game.getGameid()==ID){
                return game;
            }
        }
        throw new InvalidIDException("This ID does not exists");
    }


    /**
     * Getter method that given the
     * @param gameID id of a game
     * @return the instance of the game-controller that contains that gameID
     * @throws InvalidIDException if the given gameID is invalid
     */
    public GameController getGameController(int gameID) throws InvalidIDException{
        for (GameController gameContr: gameControllerList){
            if (gameContr.getGame().getGameid()==gameID){
                return gameContr;
            }
        }
        throw new InvalidIDException("This ID does not exists");
    }


    /**
     * Method used to notify that the game is finished and destroy the game and the respective game controller
     * @param gameID is the unique identifier of an already created game
     * @throws InvalidIDException if the give game ID is not existing
     */
    public void notifyEndGame(int gameID) throws InvalidIDException {
        gameList.remove(getGame(gameID));
        gameControllerList.remove(getGameController(gameID));
    }


    /**
     * Setter method for the
     * @param serverRMI rmi server
     */
    public void setServerRMI(RMIImplementation serverRMI) {
        this.serverRMI = serverRMI;
    }


    /**
     * Setter method for the
     * @param serverTCP tcp server
     */
    public void setServerTCP(ServerTCP serverTCP) {
        this.serverTCP = serverTCP;
    }

    public void disconnect_user(String username) {

        //trovare l'istanza dell'utente e mettergli
        //il bool della connessione a false


        //se il player è già in partita chiamo il turndealer???

       userList.get(userList.indexOf(userList
                        .stream().filter(u -> Objects.equals(u.getNickname(), username))
                        .collect(Collectors.toList()).get(0)))
                        .setInGame(false);

        userList.get(userList.indexOf(userList
                .stream().filter(u -> Objects.equals(u.getNickname(), username))
                .collect(Collectors.toList()).get(0)))
                .getGame().getVirtualView().insertDisconnection(username);

       System.out.println("Player " + username + " got disconnected - lobby \n");

    }

}
