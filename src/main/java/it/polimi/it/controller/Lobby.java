package it.polimi.it.controller;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Game;
import it.polimi.it.model.User;
import it.polimi.it.network.client.ClientInterface;
import it.polimi.it.network.server.RMIImplementation;
import it.polimi.it.network.server.ServerTCP;
import it.polimi.it.network.server.VirtualView;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Lobby implements Serializable {

    private static final long serialVersionUID = 2013868577459101390L;
    private ArrayList<User> userList;
    private ArrayList<Game> gameList;
    private ArrayList<GameController> gameControllerList;

    private int gameCounterID;

    private User user;

    private ServerTCP serverTCP;
    private RMIImplementation serverRMI;

    public Lobby() {
        userList = new ArrayList<>();
        gameList = new ArrayList<>();
        gameControllerList = new ArrayList<>();
        this.gameCounterID = 0;
    }

    public User createUser(String  nickname) throws ExistingNicknameException, EmptyNicknameException, RemoteException {

       // User user;
        if(nickname == null || nickname.length() ==0){
            throw new EmptyNicknameException("Your nickname is too short"); //mandare messaggio a view
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
                    Optional<Integer> disconnectedGameID = gameControllerList.stream()
                            .filter(gc -> gc.getPlayerList().stream()
                                    .anyMatch(u -> u.getNickname().equals(nickname)))
                            .map(GameController::getGameID)
                            .findFirst();

                    user.get().setInGame(true);

                    //Re send the robe for the view

                    int gameID = user.get().getGame().getGameid();

                    List<GameController> findGameController = gameControllerList.stream().filter(gameController -> gameController.getGame().getGameid() == gameID).collect(Collectors.toList());

                    GameController gc = findGameController.get(0);
                    Game g = gc.getGame();

                    gc.resetGame(user.get(), gameID);

                }else{
                    throw  new ExistingNicknameException("This nickname already exists!");//mandare messaggio a view
                }

            }
        }
        //return (User) userList.stream().map(user -> user.getNickname()).filter(name -> name.equals(nickname));
        //return userList.get(userList.size()-1);
        return user;
    }

    public GameController createGame(User user, int playerNumber, ClientInterface client) throws WrongPlayerException {

        if(playerNumber < 2 || playerNumber > 4){
            throw new WrongPlayerException("Wrong number of players"); //mandare messaggio a view
        }


        //fai il controllo: l'user che crea il game deve esistere ed essere nella lista, vedi metodo sotto
        VirtualView virtualView = new VirtualView();
        virtualView.setServerRMI(this.serverRMI);
        virtualView.setServerTCP(this.serverTCP);
        Game game = new Game(playerNumber, user, this.gameCounterID,virtualView, client);
        user.setInGame(true);
        gameList.add(game);
        GameController gameContr = new GameController(game, this);
        gameControllerList.add(gameContr);
        this.gameCounterID++;

        return gameContr;
    }

    public GameController joinGame(User user, int gameID, ClientInterface client) throws InvalidIDException, WrongPlayerException, IllegalValueException, RemoteException {

        List<Game> findGame = gameList.stream().filter(game -> game.getGameid() == gameID).collect(Collectors.toList());
        if(gameID<gameCounterID && !findGame.isEmpty()){
            if(findGame.get(0).getCurrentPlayersNum()<findGame.get(0).getNumplayers()){
                    findGame.get(0).setClient(client);
                    findGame.get(0).joinGame(user);
                    if(!user.getInGame()){
                        user.setInGame(true);
                    }
                    if (findGame.get(0).getNumplayers()==findGame.get(0).getCurrentPlayersNum()){
                        //starto effettivamente il game
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


    public ArrayList<User> getUserList(){
        return userList;
    }

    int getGameCounterID(){
        return gameCounterID;
    }

    Game getGame(int ID) throws InvalidIDException {
        for(Game game : gameList){
            if(game.getGameid()==ID){
                return game;
            }
        }
        throw new InvalidIDException("This ID does not exists");
    }



    GameController getGameController(int gameID) throws InvalidIDException{
        for (GameController gameContr: gameControllerList){
            if (gameContr.getGame().getGameid()==gameID){
                return gameContr;
            }
        }
        throw new InvalidIDException("This ID does not exists");
    }



    public void notifyEndGame(int gameID) throws InvalidIDException {

        gameList.remove(getGame(gameID));
        gameControllerList.remove(getGameController(gameID));
    }

    public void setServerRMI(RMIImplementation serverRMI) {
        this.serverRMI = serverRMI;
    }

    public void setServerTCP(ServerTCP serverTCP) {
        this.serverTCP = serverTCP;
    }
}
