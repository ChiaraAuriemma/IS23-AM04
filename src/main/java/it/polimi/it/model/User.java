package it.polimi.it.model;

import it.polimi.it.model.Board.Board;
import it.polimi.it.Exceptions.IllegalValueException;
import it.polimi.it.Exceptions.WrongTileException;
import it.polimi.it.Exceptions.WrongListException;
import it.polimi.it.model.Tiles.PossibleColors;
import it.polimi.it.model.Tiles.Tile;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

public class User implements Serializable {
    private static final long serialVersionUID = 8107849055941758371L;
    private Board board;
    private Shelfie shelf;
    private Game game;
    private int tilesNumber;
    private final String nickname;
    private boolean inGame;

    public User(String nickname){

        this.nickname = nickname;

        this.shelf=new Shelfie(this);
        this.inGame = true;
    }

    public int maxValueOfTiles() throws IllegalValueException, IOException {

        if(inGame==false){
            return -1;
        }

        int max = shelf.possibleTiles();
        if(max < 1 || max > 3){
            throw new IllegalValueException("Wrong tiles number");
        }
        max = board.findMaxAdjacent(max);
        System.out.println("Asking to " + nickname + "the tiles num \n");
        game.getVirtualView().startTurn(this.getNickname(),max);
        return max;
    }


    public List<List<Tile>> choosableTiles(int tilesNum) throws IllegalValueException, RemoteException {

        if(tilesNum < 1 || tilesNum > 3){
            throw new IllegalValueException("Wrong tiles number");
        }

        tilesNumber = tilesNum;

        List<List<Tile>> choosableList = board.choosableTiles(tilesNum);


        game.getVirtualView().takeableTiles(this.getNickname(),choosableList, tilesNum);
        return choosableList;
    }

    public boolean[] chooseSelectedTiles(List<Tile> chosen) throws WrongTileException, IOException {

        for(Tile t : chosen){
            if(t.getColor().equals("XTILE") || t.getColor().equals("DEFAULT")){
                throw new WrongTileException("Tiles of this type can't be chosen");
            }
        }

        //ho spostato questo metodo in insertTile per più ragioni:
        //- più comodo in caso di disconnessione (la shelfie e la board si aggiornano dopo l'ultima operazione)
        //- più comodo per inviare gli aggiornamenti su board e shelfie a tutti i giocatori a fine turno
        //board.removeTiles(chosen);

        boolean [] columns = shelf.chooseColumn(tilesNumber);
        game.getVirtualView().possibleColumns(this.getNickname(),columns);
        return columns;
    }

    public boolean insertTile(int column, List<Tile> chosen) throws IllegalValueException, RemoteException {

        boolean isEnd;
        if(column < 0 || column > 4){

            throw new IllegalValueException("The given column value does not exist");

        }else {
            isEnd = this.getShelfie().addTile(column, chosen);
            game.getVirtualView().shelfieUpdate(this);
        }

        board.removeTiles(chosen);
        boolean refill = board.refill();
        if(refill){
            game.getVirtualView().boardRefill();
        }

        game.getVirtualView().boardUpdate(board.getMatrix());
        return isEnd;
    }
    public Shelfie createShelfie() {

        this.board = this.game.getBoard();
        return this.shelf = new Shelfie(this);
    }

    public Shelfie getShelfie(){
        return this.shelf;
    }

    public Board getBoard(){
        return this.board;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public Game getGame(){
        return this.game;
    }

    public boolean getInGame() {
        return this.inGame;
    }

    public void setInGame(boolean state) {
        inGame=state;
    }

    public void setShelfie(Shelfie shelfie) {
        this.shelf = shelfie;
    }

    public void setBoard(Board board) {
        this.board=board;
    }
}
