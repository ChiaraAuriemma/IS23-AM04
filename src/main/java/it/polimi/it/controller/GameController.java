package it.polimi.it.controller;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Chat;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameController implements Serializable {

    private static final long serialVersionUID = 9016415749066492223L;


    /**
     * Is the total number of players in the game
     */
    private int numOfPlayers;


    /**
     * Univocal ID for the current game
     */
    private int gameID;



    /**
     * Index of the playerList, represents the player who is able
     * to make a move this turn
     */
    private int currentPlayer=0;


    /**
     * Flag to avoid continuing the game if somebody has already finished
     */
    private boolean endGame;


    /**
     * List of the players, with a maximum size of 4
     * The players contained in the list are already ordered in the same way as they move during the game
     */
    private List<User> playerList;


    /**
     * Reference to the Lobby, used to destroy the game, when this ends
     */
    private Lobby lobby;


    /**
     * Reference to the Game
     */
    private Game game;


    /**
     * List of tiles chosen in the current turn by the current player
     */
    private List<Tile> currentTilesList = new ArrayList<>();


    /**
     * Max number of tiles take-able in a certain turn
     */
    private int maxTile;


    /**
     * Instance of the game's chat
     */
    private Chat chat;


    /**
     * boolean array which represents the empty columns with true
     */
    private boolean[] possibleColumnArray;


    /**
     * Constructor method
     * @param game is the game which this controller references to
     * @param lobbyIn is the reference to the external lobby
     */
    public GameController(Game game, Lobby lobbyIn){
        this.endGame = false;
        this.game=game;
        numOfPlayers = game.getNumplayers();
        gameID = game.getGameid();
        lobby = lobbyIn;
        playerList = new ArrayList<>();
        this.chat = new Chat();
    }


    /**
     * Method used to control which player needs to move in the current turn
     * @throws IllegalValueException exception used when an illegal value is given in input
     * @throws InvalidIDException exception used when the game ID is wrong or non-existent
     */
    void turnDealer() throws InvalidIDException, IllegalValueException, RemoteException {

        if(this.endGame && this.currentPlayer == 0){
            game.getVirtualView().viewUpdate(chat.getCurrentChat());
            game.pointsFromAdjacent();
            lobby.notifyEndGame(gameID);
        }else{
            if(currentPlayer!=game.getNumplayers()-1) {
                currentPlayer++;
            }else{
                currentPlayer=0;
            }
            firstOperation();
        }
    }


    /**
     * Method to get the maximum possible number of tiles that can be selected from the board
     * Calls the view to ask the payer about how many tiles the player wants to retrieve
     */
    public void firstOperation() throws IllegalValueException, RemoteException {
        game.getVirtualView().notifyTurnStart(playerList.get(currentPlayer));
        this.maxTile = playerList.get(currentPlayer).maxValueOfTiles();
        game.getVirtualView().viewUpdate(chat.getCurrentChat());
    }


    /**
     * Gets from the view the number of tiles that the current player wants to retrieve from the board
     * The method also sends to the view the list of lists of tiles that the user can choose
     *  in order to highlight them on the board.
     * @param chosenNumber is the input from the user
     */
    public void getFromViewNTiles(String user, int chosenNumber) throws WrongPlayerException, RemoteException, IllegalValueException {
        if(user.equals(playerList.get(currentPlayer).getNickname())){
            this.playerList.get(currentPlayer).choosableTiles(chosenNumber);
        }else{
            throw new WrongPlayerException("It's not your turn");
        }
    }


    /**
     * Method to get the list of tiles the user has selected from the board,
     * The method also sends to the view the list of 'empty-enough' columns in which the chosen
     *  tiles might be put in.
     * @param chosenList is the list of selected tiles
     * @throws WrongTileException exception used when a wrong tile is selected
     */
    public void getTilesListFromView(String user, List<Tile> chosenList) throws WrongPlayerException, WrongListException {
        if(user.equals(playerList.get(currentPlayer).getNickname())) {
            if(validTilesCheck(chosenList)){
                currentTilesList.clear();
                currentTilesList = new ArrayList<>(chosenList);
                try {
                    possibleColumnArray = playerList.stream().filter(curr -> Objects.equals(curr.getNickname(), user)).collect(Collectors.toList()).get(0).chooseSelectedTiles(currentTilesList);
                } catch (RemoteException | WrongTileException e) {
                    System.out.println(e.getMessage());
                }
            }else{
                throw new WrongListException("You chose badly");
            }
        }else {
            throw new WrongPlayerException("It's not your turn");
        }
    }


    /**
     * Checks if the parameter is present in the List of Lists of Tiles
     * @param chosenList is the chosen List of tiles
     * @return true is the choice is valid
     */
    private boolean validTilesCheck(List<Tile> chosenList) {
        List<List<Tile>> choosable = game.getBoard().choosableTiles(chosenList.size());
        return choosable.stream().anyMatch(list -> new HashSet<>(list).containsAll(chosenList));
    }


    /**
     * Method that gets from the view the column that the player wants to put his chosen tiles and
     *  in which order he wants the tiles to be.
     *  The method also calls the view to display the new points acquired by the player
     * @param col is the column of the shelfie where to put the tiles in
     * @throws WrongTileException exception used when a wrong tile is selected
     */
    public void getColumnFromView(String user, int col) throws IllegalValueException, InvalidIDException, RemoteException {
        if(!possibleColumnArray[col]){
           System.out.println("Invalid column choice");
        }
        endGame = playerList.stream().filter(curr -> Objects.equals(curr.getNickname(), user)).collect(Collectors.toList()).get(0).insertTile(col, currentTilesList);
        game.pointCount(playerList.get(currentPlayer));
        if(this.endGame){
            game.endGame(playerList.get(currentPlayer));
        }
        turnDealer();
    }


    /**
     * Method used to initialize the parameters of the game, called before the firs turn of the game
     * Calls the view in order to display the initial board and the cards that have been extracted randomly
     */
    public void firstTurnStarter() throws IllegalValueException, RemoteException {
        playerList = game.randomPlayers();
        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }
        game.drawCommonCrads();
        currentPlayer=0;
        firstOperation();
    }


    /**
     * Getter method
     * @return the reference to the game controlled by this GameController
     */
    public Game getGame(){
        return this.game;
    }


    /**
     * Getter method
     * @return the list of all the players in the game
     */
    public List<User> getPlayerList() {
        return playerList;
    }


    /**
     * Method to reset the instances of
     * @param user user and
     * @param gameID gameID in case of a player's reconnection
     * @throws RemoteException
     */
    public void resetGame(User user, int gameID) throws RemoteException {
        //game.getVirtualView().resetAfterDisconnection(user.getNickname(), gameID);
    }


    /**
     * Method to insert a new message in the chat records
     * @param chatMessage is the latest chat message
     * @throws RemoteException Exception that might come from connection issues
     */
    public void pushChatMessage(String chatMessage) throws RemoteException {
        chat.newMessage(chatMessage);
        game.getVirtualView().sendChatUpdate(chat.getCurrentChat());
    }



    /****************************************************
     *                                                  *
     *                                                  *
     * Methods inserted just to test purposes           *
     *                                                  *
     *                                                  *
     ****************************************************/
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getGameID() {
        return gameID;
    }

    public int getMaxTile() {
        return maxTile;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public Lobby getLobby() {
        return lobby;
    }

}
