package it.polimi.it.model;

import it.polimi.it.model.Board.Board;
import it.polimi.it.model.Tiles.Tile;

import java.util.List;

public class User {
    private Board board;

    private Shelfie shelf;

    private Game game;

    private int tilesNumber;
    private final String nickname;

    private int score;

    private final boolean inGame;

    public User(String nickname){

        this.nickname = nickname;

        this.score = 0;

        this.inGame = true;
    }

    public List<List<Tile>> choosableTiles(int tilesNum) {

        tilesNumber = tilesNum;

        return board.choosableTiles(tilesNum);
    }

    public boolean[] chooseSelectedTiles(List<Tile> choosen) {

        board.removeTiles(choosen);

        return shelf.chooseColumn(tilesNumber);
    }

    public void insertTile(int column, List<Tile> choosen) {

        if(column < 0 || column > 4){

            throw new IndexOutOfBoundsException();

        }else {

            shelf.addTile(column, choosen);
        }

        board.refill();
    }


    public boolean checkInGame(User user) {

        //vedere quando crasha
        return true;
    }

    public Shelfie createShelfie() {

        this.board = this.game.getBoard();
        return this.shelf = new Shelfie();
    }

    public int maxValueofTiles() throws IndexOutOfBoundsException{

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
