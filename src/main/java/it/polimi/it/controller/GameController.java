package it.polimi.it.controller;

import it.polimi.it.controller.Exceptions.IllegalValueException;
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

    int numOfPlayers;
    int gameID;

    int currentPlayer=0;
    boolean endGame=false;
    List<User> playerList;

    Lobby lobby;

    Game game;


    List<Tile> currentTilesList;


    int maxTile;


    public GameController(Game game, Lobby lobbyIn){
        this.game=game;
        numOfPlayers = game.getNumplayers();
        gameID = game.getGameid();
        lobby = lobbyIn;
    }

    //suppongo che la partita sia già stata startata con 4 giocatori

    void turnDealer() throws WrongListException, IllegalValueException, InvalidTileException {

        if(endGame){
            //manda alla view i punteggi finali

            Lobby.notifyEndGame(gameID);
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

    void firstOperation() throws IllegalValueException, WrongListException, InvalidTileException {

        //faccio i controlli su qual è il massimo num di tile prendibili
        maxTile = playerList.get(currentPlayer).maxValueOfTiles();

        //passo alla view, faccio scegliere il numero che vuole l'utente
        int chosenNumber = 0;
        askFromView(maxTile);/////////////////////////////
    }


    void getFromViewNTiles(int chosenNumber) throws IllegalValueException, WrongListException {
        if (chosenNumber <= 0 || chosenNumber > maxTile) {
            throw new IllegalValueException("How can you be so dumb...");
        }

        //mandare alla view la lista di liste di tile che sono prendibili
        highlightView(playerList.get(currentPlayer).choosableTiles(chosenNumber));
    }




    void getTilesListFromView(List<Tile> chosenList) throws InvalidTileException {

        for(Tile t: chosenList){
            currentTilesList.add(new Tile(t.getRow(), t.getColumn(), PossibleColors.valueOf(t.getColor())));
        }
        //dico ad user che tile sono state scelte
        boolean[] possibleColumns = playerList.get(currentPlayer).chooseSelectedTiles(currentTilesList);


        //passa alla view le possibleColumns, dato che sono state eliminate dalla board
    }


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
        firstOperation();
    }



    /****************************
     * Prendo il giocatore
     * Faccio i controlli di massima
     * chiedo il numero di tile che vuole
     ***************************/

    public Game getGame(){
        return this.game;
    }
}
