package it.polimi.it.controller;

import it.polimi.it.Exceptions.*;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.PossibleColors;
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
    }

    //suppongo che la partita sia già stata startata con 4 giocatori

    /**
     * Method used to control which player needs to move in the current turn
     * @throws IllegalValueException exception used when an illegal value is given in input
     * @throws InvalidIDException exception used when the game ID is wrong or non-existent
     */
    void turnDealer() throws InvalidIDException, IllegalValueException, RemoteException {

        game.getVirtualView().viewUpdate();

        if(this.endGame && this.currentPlayer == 0){
            game.getVirtualView().viewUpdate();

            game.pointsFromAdjacent();

            //manda alla view i punteggi finali (passo points)
            lobby.notifyEndGame(gameID);
        }else{

            if(currentPlayer!=game.getNumplayers()-1) {
                currentPlayer++;
            }else{
                currentPlayer=0;
            }
            firstOperation();

            //faccio tutte le cose del turno,
            //aggiorno i punti
        }

    }


    /**
     * Method to get the maximum possible number of tiles that can be selected from the board
     * Calls the view to ask the payer about how many tiles the player wants to retrieve
     */
    public void firstOperation() throws IllegalValueException, RemoteException {

        //faccio i controlli su qual è il massimo num di tile prendibili
        this.maxTile = playerList.get(currentPlayer).maxValueOfTiles();
        game.getVirtualView().viewUpdate();


        //passo alla view, faccio scegliere il numero che vuole l'utente
        ///askFromView(maxTile);/////////////////////////////
    }


    /**
     * Gets from the view the number of tiles that the current player wants to retrieve from the board
     * The method also sends to the view the list of lists of tiles that the user can choose
     *  in order to highlight them on the board.
     * @param chosenNumber is the input from the user
     */
    public void getFromViewNTiles(String user, int chosenNumber) throws WrongPlayerException, WrongListException, RemoteException, IllegalValueException {
        if(user.equals(playerList.get(currentPlayer).getNickname())){
            this.playerList.get(currentPlayer).choosableTiles(chosenNumber);

            //messaggio alla view per far scegliere un altro valore


            //mandare alla view la lista di liste di tile che sono prendibili
            ///highlightView(playerList.get(currentPlayer).choosableTiles(chosenNumber));
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
    public void getTilesListFromView(String user, List<Tile> chosenList) throws WrongPlayerException {
        if(user.equals(playerList.get(currentPlayer).getNickname()) && validTilesCheck(chosenList)) {
            currentTilesList.clear();
            /*for (Tile t : chosenList) {
                currentTilesList.add(new Tile(t.getRow(), t.getColumn(), PossibleColors.valueOf(t.getColor())));
            }*/
            currentTilesList = new ArrayList<>(chosenList);
            //dico ad user che tile sono state scelte
            try {
                possibleColumnArray = playerList.stream().filter(curr -> Objects.equals(curr.getNickname(), user)).collect(Collectors.toList()).get(0).chooseSelectedTiles(currentTilesList);
            } catch (RemoteException | WrongTileException e) {
                //messaggio a view per far scegliere altre tiles
            }
        }else {
            throw new WrongPlayerException("It's not your turn");
        }
        //passa alla view le possibleColumns, dato che sono state eliminate dalla board
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
    //oltre a scegliere la colonna dove metterle, ordina le Tile per come le vuole nella colonna
    public void getColumnFromView(String user, int col) throws IllegalValueException, InvalidIDException, RemoteException {
        if(!possibleColumnArray[col]){
           System.out.println("Invalid column choice"); //da far vedere a view
        }
        endGame = playerList.stream().filter(curr -> Objects.equals(curr.getNickname(), user)).collect(Collectors.toList()).get(0).insertTile(col, currentTilesList);
       // endGame = playerList.get(currentPlayer).insertTile(col, currentTilesList);
            //messaggio per la view

        game.pointCount(playerList.get(currentPlayer));
        if(this.endGame){
            game.endGame(playerList.get(currentPlayer));
        }
        // manda alla view l'immagine della nuova shelfie da visualizzare e i nuovi punteggi, col referimento all'user
        game.getVirtualView().viewUpdate();
        turnDealer();
    }


    /**
     * Method used to initialize the parameters of the game, called before the firs turn of the game
     * Calls the view in order to display the initial board and the cards that have been extracted randomly
     */
    public void firstTurnStarter() throws IllegalValueException, RemoteException {
        //pesca le carte, dispone i giocatori

        //tolgo: playerList = game.getPlayerList();//sto salvando la lista di giocatori già ordinata come deve essere
        playerList = game.randomPlayers();
        //view : dare la sedia al primo giocatore

        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }

        game.drawCommonCrads();
        //view: mostro alla view
        //view: mostro alla view chi ha il sofà d'inizio

        //ho ottenuto l'ordine dei giocatori, inizio i turni effettivi
        currentPlayer=0;

        /////////////////////////////////////////////////
        //
        //CHIAMARE LA VIEW PER PASSARE LA BOARD INIZIALE CON TILES E CARTE INIZIALIZZATE
        //
        //////////////////////////////////////////////////

        firstOperation();
    }

    /**
     * Getter method
     * @return the reference to the game controlled by this GameController
     */
    public Game getGame(){
        return this.game;
    }

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

    public List<User> getPlayerList() {
        return playerList;
    }

    public void resetGame(User user, int gameID) throws RemoteException {
        //game.getVirtualView().resetAfterDisconnection(user.getNickname(), gameID);
    }
}
