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

import java.util.HashSet;
import java.util.List;

public class GameController {

    /**
     * Is the total number of players in the game
     */
    int numOfPlayers;

    /**
     * Univocal ID for the current game
     */
    int gameID;

    /**
     * Index of the playerList, represents the player who is able
     * to make a move this turn
     */
    int currentPlayer=0;

    /**
     * Flag to avoid continuing the game if somebody has already finished
     */
    boolean endGame=false;

    /**
     * List of the players, with a maximum size of 4
     * The players contained in the list are already ordered in the same way as they move during the game
     */
    List<User> playerList;


    /**
     * Reference to the Lobby, used to destroy the game, when this ends
     */
    Lobby lobby;


    /**
     * Reference to the Game
     */
    Game game;


    /**
     * List of tiles chosen in the current turn by the current player
     */
    List<Tile> currentTilesList;


    /**
     * Max number of tiles take-able in a certain turn
     */
    int maxTile;


    /**
     * Constructor method
     * @param game is the game which this controller references to
     * @param lobbyIn is the reference to the external lobby
     */
    public GameController(Game game, Lobby lobbyIn){
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
    void turnDealer() throws WrongListException, IllegalValueException, InvalidTileException, InvalidIDException {

        if(endGame){
            //manda alla view i punteggi finali

            lobby.notifyEndGame(gameID);
        }

        if(currentPlayer!=game.getNumplayers()) {
            currentPlayer++;
        }else{
            currentPlayer=0;
        }
        firstOperation();
            //faccio tutte le cose del turno,
            //aggiorno i punti

    }


    /**
     * Method to get the maximum possible number of tiles that can be selected from the board
     * Calls the view to ask the payer about how many tiles the player wants to retrieve
     * @throws WrongListException exception used when an illegal list of tiles is selected
     * @throws IllegalValueException exception used when an illegal value is given in input
     * @throws InvalidTileException exception used when a wrong tile is selected
     */
    void firstOperation() throws IllegalValueException, WrongListException, InvalidTileException {

        //faccio i controlli su qual è il massimo num di tile prendibili
        maxTile = playerList.get(currentPlayer).maxValueOfTiles();

        //passo alla view, faccio scegliere il numero che vuole l'utente
        int chosenNumber = 0;
        askFromView(maxTile);/////////////////////////////
    }


    /**
     * Gets from the view the number of tiles that the current player wants to retrieve from the board
     * The method also sends to the view the list of lists of tiles that the user can choose
     *  in order to highlight them on the board.
     * @param chosenNumber is the input from the user
     * @throws WrongListException exception used when an illegal list of tiles is selected
     * @throws IllegalValueException exception used when an illegal value is given in input
     */
    void getFromViewNTiles(int chosenNumber) throws IllegalValueException, WrongListException {
        if (chosenNumber <= 0 || chosenNumber > maxTile) {
            throw new IllegalValueException("How can you be so dumb...");
        }

        //mandare alla view la lista di liste di tile che sono prendibili
        highlightView(playerList.get(currentPlayer).choosableTiles(chosenNumber));
    }


    /**
     * Method to get the list o tiles the user has selected from the board,
     * The method also sends to the view the list of 'empty-enough' columns in which the chosen
     *  tiles might be put in.
     * @param chosenList is the list of selected tiles
     * @throws InvalidTileException exception used when a wrong tile is selected
     */
    void getTilesListFromView(List<Tile> chosenList) throws InvalidTileException {

        for(Tile t: chosenList){
            currentTilesList.add(new Tile(t.getRow(), t.getColumn(), PossibleColors.valueOf(t.getColor())));
        }
        //dico ad user che tile sono state scelte
        boolean[] possibleColumns = playerList.get(currentPlayer).chooseSelectedTiles(currentTilesList);


        //passa alla view le possibleColumns, dato che sono state eliminate dalla board
    }


    /**
     * Method that gets from the view the column that the player wants to put his chosen tiles and
     *  in which order he wants the tiles to be.
     *  The method also calls the view to display the new points acquired by the player
     * @param col is the column of the shelfie where to put the tiles in
     * @param orderedTiles is the list of chosen tiles, ordered as the player wants them to be in his shelfie
     * @throws IllegalValueException exception used when an illegal value is given in input
     * @throws WrongTileException exception used when the list of tiles is somehow changed from before
     * @throws WrongListException exception used when an illegal list of tiles is selected
     * @throws InvalidTileException exception used when a wrong tile is selected
     */
    //oltre a scegliere la colonna dove metterle, ordina le Tile per come le vuole nella colonna
    void getColumnFromView(int col, List<Tile> orderedTiles) throws IllegalValueException, WrongTileException, WrongListException, InvalidTileException {
        if(col<0 || col>4){
            throw new IllegalValueException("Invalid column choice");
        }

        if (new HashSet<>(orderedTiles).containsAll(currentTilesList)){
             throw new WrongTileException("Different tiles from before");
        }


        game.pointCount();

        List<Integer> commonP1 = game.get...();
        List<Integer> commonP2 = game.get...();
        //boolean finished = playerList.get(currentPlayer).getShelfie().checkEnd(); // cambiarlo in metodo get
        boolean finished = game.getEndTokenX();

        if(finished){
            endGame=true;
        }

        // manda alla view l'immagine della nuova shelfie da visualizzare e i nuovi punteggi, col referimento all'user

        turnDealer();
    }


    /**
     * Method used to initialize the parameters of the game, called before the firs turn of the game
     * Calls the view in order to display the initial board and the cards that have been extracted randomly
     * @throws WrongListException exception used when an illegal list of tiles is selected
     * @throws IllegalValueException exception used when an illegal value is given in input
     * @throws InvalidTileException exception used when a wrong tile is selected
     */
    void firstTurnStarter() throws WrongListException, IllegalValueException, InvalidTileException {
        //pesca le carte, dispone i giocatori

        playerList = game.getPlayerList();//sto salvando la lista di giocatori già ordinata come deve essere
        //view : dare la sedia al primo giocatore

        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }

        game.drawCommonCrads();
        //view: mostro alla view
        //view: mostro alla view chi ha il sofà d'inizio
        game.callNextPlayers();


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
}
