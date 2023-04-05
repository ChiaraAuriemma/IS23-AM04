package it.polimi.it.controller;

import it.polimi.it.controller.Exceptions.*;
import it.polimi.it.model.Exceptions.InvalidTileException;
import it.polimi.it.model.Exceptions.WrongListException;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    private ArrayList<User> userList;
    private ArrayList<Game> gameList;

    private int gameCounterID;

    public Lobby(String[] args) {
        userList = new ArrayList<>();
        gameList = new ArrayList<>();
        gameCounterID = 0;

    }

    public User createUser(String  nickname) throws EmptyNicknameException, ExistingNicknameException {

        User user;
        if(nickname.isEmpty()){
            throw new EmptyNicknameException("Non puoi inserire un nickname vuoto");
        }else{
            if(userList.stream()
                    .map(uuser -> uuser.getNickname())
                    .noneMatch(name -> name.equals(nickname))
            ){
                user = new User(nickname);
                userList.add(user);
            }else{
                throw new ExistingNicknameException("Il nickname esiste già");
            }
        }
        //return (User) userList.stream().map(user -> user.getNickname()).filter(name -> name.equals(nickname));
        //return userList.get(userList.size()-1);
        return user;
    }

    public void createGame(User user, int playerNumber) throws IndexOutOfBoundsException, NotExistingUser {

        if(playerNumber < 1 || playerNumber > 4){
            throw new IndexOutOfBoundsException("Il numero di giocatori non è corretto");
        }

        if(userList.size()==0){
            throw new NotExistingUser("Non c'è nessun utente che può creare la partita");
        }
        //pickUser(user); non stai facendo una pick, così togli tutto il riferimento che ti sei passato sul client
        gameList.add(new Game(playerNumber, user, gameCounterID));
        gameCounterID++;

    }

    public void joinGame(User user, int gameID) throws NotExistingUser, InvalidIDException, FullGameException {

        if(gameID<=gameCounterID && gameList.get(gameID).getGameid()==gameID){
            if(gameList.get(gameID).getNumplayers()<4){
                gameList.get(gameID).joinGame(user);//controllare che nessuno abbia scammato con l'user
            }else {
                throw new FullGameException("There are already too many players in this game!");
            }
        }else{
            throw new InvalidIDException("The given game ID does not exists");
        }
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



    /**********************************
     *  fine dei metodi che effettivamente centrano qualcosa con cosa sia una Lobby
     *
     *
     *
     *******************************/

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
