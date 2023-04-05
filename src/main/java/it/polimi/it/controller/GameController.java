package it.polimi.it.controller;

import it.polimi.it.controller.Exceptions.IllegalValueException;
import it.polimi.it.model.Exceptions.InvalidTileException;
import it.polimi.it.model.Exceptions.WrongListException;
import it.polimi.it.model.Game;
import it.polimi.it.model.Tiles.Tile;
import it.polimi.it.model.User;

import java.util.List;

public class GameController {

    int numOfPlayers;
    int gameID;

    int currentPlayer=0;
    boolean endGame=false;
    List<User> playerList;

    Game game;


    public GameController(Game game){
        this.game=game;
        numOfPlayers = game.getNumplayers();
        gameID = game.getGameid();
    }

    //suppongo che la partita sia già stata startata con 4 giocatori

    void turnDealer(){
        //for (int i = 0; i<game.getNumplayers() && !endGame; i++){
        if(currentPlayer!=game.getNumplayers()) {
            currentPlayer++;
        }else{
            currentPlayer=0;
        }
        turnOperator(currentPlayer);
            //faccio tutte le cose del turno,
            //aggiorno i punti
        if(endGame == true){
            //boh faccio le robe della fine della partita
        }
    }

    void turnOperator(int currentPlayerIndex) throws IllegalValueException, WrongListException, InvalidTileException {

        //faccio i controlli su qual è il massimo num di tile prendibili
        int maxTile = playerList.get(currentPlayerIndex).maxValueOfTiles();

        //passo alla view, faccio scegliere il numero che vuole l'utente
        int chosenNumber=0;
        askFromView(maxTile);/////////////////////////////
        chosenNumber=getFromViewNTiles();
        if (chosenNumber<=0 || chosenNumber >maxTile){
            throw new IllegalValueException("How can you be so dumb...");
        }

        //mandare alla view la lista di liste di tile che sono prendibili

        highlightView(playerList.get(currentPlayerIndex).choosableTiles(chosenNumber));
        List<Tile> chosenList = getTilesListFromView();


        //dico ad user che tile sono state scelte
        playerList.get(currentPlayerIndex).chooseSelectedTiles(chosenList);


    }

    void firstTurnStarter(){
        //pesca le carte, dispone i giocatori

        playerList = game.getPlayerList();//sto salvando la lista di giocatori già ordinata come deve essere
        //view : dare la sedia al primo giocatore


        //game.randomPlayers();
        for(int i=0; i < game.getNumplayers(); i++){
            game.drawPersonalCard();
            game.getPlayer(i).createShelfie();
        }

        game.drawCommonCrads();
        //view: mostro alla view
        //view: mostro alla view chi ha il sofà d'inizio
        game.callNextPlayers();


        //ho ottenuto l'ordine dei giocatori, inizio i turni effettivi
        turnOperator(0);
    }

 /*   void turnEnder(){

        //aggiungo le tile nella shelfie, do i punti, aggiornare i punti per la view(boh)
        this.turnStarter();
        //aggiorno i punti

        // a fine turno controllo che il gioco non sia finito!!!!!
        //view: game finisce mostro a video la classifica finale

        }*/

    /****************************
     * Prendo il giocatore
     * Faccio i controlli di massima
     * chiedo il numero di tile che vuole
     ***************************/
}
