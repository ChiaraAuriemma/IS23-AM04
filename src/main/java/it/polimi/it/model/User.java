package it.polimi.it.model;

import it.polimi.it.model.Board.Board;
import it.polimi.it.model.Exception.InvalidTileException;
import it.polimi.it.model.Exception.WrongListException;
import it.polimi.it.model.Tiles.Tile;

import java.util.List;

public class User {
    private Board board;

    private Shelfie shelf;

    private Game game;

    private int tilesNumber;
    private final String nickname;

    private final boolean inGame;

    public User(String nickname){

        this.nickname = nickname;

        this.inGame = true;
    }

    List<List<Tile>> choosableTiles(int tilesNum) throws WrongListException {

        tilesNumber = tilesNum;

        List<List<Tile>> choosableList = board.choosableTiles(tilesNum);
        if(choosableList == null || choosableList.size() == 0){
            throw new WrongListException("La lista di scelte possibili non è utilizzabile");
        }
        return choosableList;
    }

    boolean[] chooseSelectedTiles(List<Tile> chosen) throws InvalidTileException {

        for(Tile t : chosen){
            if(t.getColor().equals("XTILE") || t.getColor().equals("DEFAULt")){
                throw new InvalidTileException("Non è possibile avere questo tipo di tile nella scelta");
            }
        }

        board.removeTiles(chosen);

        return shelf.chooseColumn(tilesNumber);
    }

    void insertTile(int column, List<Tile> chosen) throws IndexOutOfBoundsException {

        if(column < 0 || column > 4){

            throw new IndexOutOfBoundsException();

        }else {
            shelf.addTile(column, chosen);
        }

        board.refill();
    }


    boolean checkInGame(User user) {

        //vedere quando crasha
        return true;
    }
    Shelfie createShelfie() {

        this.board = this.game.getBoard();
        return this.shelf = new Shelfie();
    }

    int maxValueOfTiles() throws IndexOutOfBoundsException{

        int max = shelf.possibleTiles();

        if(max < 0 || max > 3){
            throw new IndexOutOfBoundsException();
        }

        return board.findMaxAdjacent(max);
    }

    public Shelfie getShelfie(User user){
        return this.shelf;
    }

    public Board getBoard(User user){
        return this.board;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setGame(Game game){
        this.game = game;
    }
}
