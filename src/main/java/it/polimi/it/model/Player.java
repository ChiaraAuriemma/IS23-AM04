package it.polimi.it.model;

import it.polimi.it.model.Board.Board;
import it.polimi.it.model.Tiles.Tile;

import java.util.List;

public class Player implements UserType {

    private int score;
    private Board board;

    private Game game;

    private Shelfie shelf;

    private int tilesNumber;

    @Override
    public List<List<Tile>> choosableTiles(int tilesNum) {

        tilesNumber = tilesNum;

        return board.choosableTiles(tilesNum);
    }

    @Override
    public boolean[] chooseSelectedTiles(List<Tile> choosen) {

        board.removeTiles(choosen);

        return shelf.chooseColumn(tilesNumber);
    }

    @Override
    public void inserTile(int column, List<Tile> choosen) {

        if(column < 0 || column > 4){

            throw new IndexOutOfBoundsException();

        }else {

            shelf.addTile(column, choosen);
        }

        board.refill();
    }

    @Override
    public boolean checkInGame(User user) {

        //vedere quando crasha
        return true;
    }

    @Override
    public void createGame(String Nickname, int playerNumber, User user) {

    }

    @Override
    public void joinGame(String Nickname, User User) {

    }

    @Override
    public Shelfie createShelfie(int personalCardID) {

        this.board = game.getBoard();
        return this.shelf = new Shelfie(personalCardID);
    }

    @Override
    public int maxValueofTiles() throws IndexOutOfBoundsException{

        int max = shelf.possibleTiles();

        if(max < 0 || max > 3){
            throw new IndexOutOfBoundsException();
        }

        return board.findMaxAdjacent(max);
    }

    @Override
    public Shelfie getShelfie(User user){
        return this.shelf;
    }

    @Override
    public Board getBoard(User user){
        return this.board;
    }
}
