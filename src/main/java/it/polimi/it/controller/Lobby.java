package it.polimi.it.controller;

import it.polimi.it.controller.Exceptions.*;
import it.polimi.it.model.Exceptions.IndexOutOfBoundException;
import it.polimi.it.model.Exceptions.InvalidTileException;
import it.polimi.it.model.Exceptions.WrongListException;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Lobby {

    private static ArrayList<User> userList;
    private static ArrayList<Game> gameList;

    private static int gameCounterID;

    public static void main(String[] args) {
        userList = new ArrayList<>();
        gameList = new ArrayList<>();
        gameCounterID = 0;

    }

    public User createUser(String  nickname) throws EmptyNicknameException, ExistingNicknameException {

        if(nickname.isEmpty()){
            throw new EmptyNicknameException("Non puoi inserire un nickname vuoto");
        }else{
            Optional<String> nicknameList = userList.stream()
                    .map(user -> user.getNickname())
                    .filter(name -> name.equals(nickname))
                    .findFirst();

            if(!nicknameList.isPresent()){
                User user = new User(nickname);
                userList.add(user);
            }else{
                throw new ExistingNicknameException("Il nickname esiste già");
            }
        }

        return userList.get(userList.size()-1);
    }

    public static void createGame(User user, int playerNumber) throws IndexOutOfBoundsException, NotExistingUser {

        if(playerNumber < 1 || playerNumber > 4){
            throw new IndexOutOfBoundsException("Il numero di giocatori non è corretto");
        }

        if(userList.size()==0){
            throw new NotExistingUser("Non c'è nessun utente che può creare la partita");
        }

        pickUser(user);

        gameCounterID++;

        gameList.add(new Game(playerNumber, user, gameCounterID));
    }

    public void joinGame(User user, int gameID) throws NotExistingUser, InvalidIDException {

        if(userList.size()==0){
            throw new NotExistingUser("Non c'è nessun utente che può unirsi alla partita");
        }


        if(gameList.stream().map(game -> game.getGameid()).anyMatch(ID -> ID == gameID)){

            Optional<Game> g = gameList.stream()
                    .filter(game -> game.getGameid()==gameID).findFirst();
            g.ifPresent( el -> el.joinGame(user));

            pickUser(user);
        }else{
            throw new InvalidIDException("L'ID inserito non esiste");
        }

    }

    private static void pickUser(User user){
        userList.remove(user);
    }

    ArrayList<User> getUserList(){
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

        throw new InvalidIDException("L'ID inserito non esiste");
    }

    List<List<Tile>> choosableTiles(int tilesNum, int gameID, int playerNumber) throws WrongPlayerException {

        try {
            if (playerNumber != getGame(gameID).getOrderPointer()){
                throw new WrongPlayerException("Non è il tuo turno");
            }
        }catch (InvalidIDException e){
            throw new RuntimeException(e);
        }

        try{

            int max = getGame(gameID).getPlayer(playerNumber).maxValueOfTiles();

            if(max >= tilesNum && tilesNum >= 1){
                return getGame(gameID).getPlayer(playerNumber).choosableTiles(tilesNum);
            }else{
                throw new IndexOutOfBoundsException("Il numero di tiles non è selezionabile");
            }
        }catch (InvalidIDException | WrongListException e) {
            throw new RuntimeException(e);
        }
    }

    boolean[] chooseSelectedTiles(List<Tile> chosen, int gameID, int playerNumber) throws WrongPlayerException {

        try {
            if (playerNumber != getGame(gameID).getOrderPointer()){
                throw new WrongPlayerException("Non è il tuo turno");
            }
        }catch (InvalidIDException e){
            throw new RuntimeException(e);
        }

        try{
            return getGame(gameID).getPlayer(playerNumber).chooseSelectedTiles(chosen);
        } catch (InvalidIDException | InvalidTileException e) {
            throw new RuntimeException(e);
        }
    }


}
