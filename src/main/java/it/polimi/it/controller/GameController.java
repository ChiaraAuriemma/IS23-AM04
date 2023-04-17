package it.polimi.it.controller;

import it.polimi.it.controller.Exceptions.IllegalValueException;
import it.polimi.it.controller.Exceptions.InvalidIDException;
import it.polimi.it.controller.Exceptions.WrongTileException;
import it.polimi.it.model.Exceptions.InvalidTileException;
import it.polimi.it.model.Exceptions.WrongListException;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GameController {

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
    private List<Tile> currentTilesList;


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
    }

    //suppongo che la partita sia già stata startata con 4 giocatori

    /**
     * Method used to control which player needs to move in the current turn
     * @throws WrongListException exception used when an illegal list of tiles is selected
     * @throws IllegalValueException exception used when an illegal value is given in input
     * @throws InvalidTileException exception used when a wrong tile is selected
     * @throws InvalidIDException exception used when the game ID is wrong or non-existent
     */
    void turnDealer() throws InvalidIDException {

        if(this.endGame && this.currentPlayer == 0){

            List<Integer> points = game.pointsFromAdjacent();

            //manda alla view i punteggi finali (passo points)
            lobby.notifyEndGame(gameID);
        }else{

            if(currentPlayer!=game.getNumplayers()) {
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
    public void firstOperation(){

        //faccio i controlli su qual è il massimo num di tile prendibili
        this.maxTile = playerList.get(currentPlayer).maxValueOfTiles();

        //passo alla view, faccio scegliere il numero che vuole l'utente
        ///askFromView(maxTile);/////////////////////////////
    }


    /**
     * Gets from the view the number of tiles that the current player wants to retrieve from the board
     * The method also sends to the view the list of lists of tiles that the user can choose
     *  in order to highlight them on the board.
     * @param chosenNumber is the input from the user
     */
    public void getFromViewNTiles(int chosenNumber){
        try{
        this.playerList.get(currentPlayer).choosableTiles(chosenNumber);
        }catch (IndexOutOfBoundsException | WrongListException e){
            //messsaggio alla view per far scegliere un altro valore
        }
        //mandare alla view la lista di liste di tile che sono prendibili
        ///highlightView(playerList.get(currentPlayer).choosableTiles(chosenNumber));
    }


    /**
     * Method to get the list of tiles the user has selected from the board,
     * The method also sends to the view the list of 'empty-enough' columns in which the chosen
     *  tiles might be put in.
     * @param chosenList is the list of selected tiles
     * @throws InvalidTileException exception used when a wrong tile is selected
     */
    public void getTilesListFromView(List<Tile> chosenList){

        for(Tile t: chosenList){
            currentTilesList.add(new Tile(t.getRow(), t.getColumn(), PossibleColors.valueOf(t.getColor())));
        }
        //dico ad user che tile sono state scelte
        try {
            boolean[] possibleColumns = playerList.get(currentPlayer).chooseSelectedTiles(currentTilesList);
            possibleColumnArray = possibleColumns;
        }catch (InvalidTileException e){
            //messaggio a view per far scegliere altre tiles
        }


        //passa alla view le possibleColumns, dato che sono state eliminate dalla board
    }


    /**
     * Method that gets from the view the column that the player wants to put his chosen tiles and
     *  in which order he wants the tiles to be.
     *  The method also calls the view to display the new points acquired by the player
     * @param col is the column of the shelfie where to put the tiles in
     * @param orderedTiles is the list of chosen tiles, ordered as the player wants them to be in his shelfie

     * @throws InvalidTileException exception used when a wrong tile is selected
     */
    //oltre a scegliere la colonna dove metterle, ordina le Tile per come le vuole nella colonna
    public void getColumnFromView(int col, List<Tile> orderedTiles) throws InvalidIDException {
        if(possibleColumnArray[col] != true){
           System.out.println("Invalid column choice"); //da far vedere a view
        }

        if (new HashSet<>(orderedTiles).containsAll(currentTilesList)){
             System.out.println("Different tiles from before"); //da far fare a view
        }
        try{
        endGame = playerList.get(currentPlayer).insertTile(col, orderedTiles);
        }catch (IndexOutOfBoundsException e){
            //messaggio per la view
        }

        game.pointCount(playerList.get(currentPlayer));

        if(this.endGame == true){
            game.endGame(playerList.get(currentPlayer));
        }



        // manda alla view l'immagine della nuova shelfie da visualizzare e i nuovi punteggi, col referimento all'user

        turnDealer();
    }


    /**
     * Method used to initialize the parameters of the game, called before the firs turn of the game
     * Calls the view in order to display the initial board and the cards that have been extracted randomly
     */
    public void firstTurnStarter(){
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
}
