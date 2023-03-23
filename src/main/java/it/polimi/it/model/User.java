package it.polimi.it.model;

import it.polimi.it.model.Board.Board;
import it.polimi.it.model.Exceptions.IsNotGuestException;
import it.polimi.it.model.Exceptions.IsNotPlayerException;
import it.polimi.it.model.Tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class User {

    private static String nickname;

    private final int score;

    private final boolean inGame;

    private String type;

    int tilesNumber;

    private Game game;

    private Shelfie shelf;

    private Board board;


    public User() {

       // Nickname = Lobby.setNickname(); -> serve controller/view che fa impostare nickname a user dentro la lobby

        this.score = 0;

        this.inGame = true;

        this.type = "GUEST";

    }

    int maxValueOfTiles() throws IsNotPlayerException {

        if (type.equals("PLAYER")){

           int max = shelf.possibleTiles();

           if(max < 0 || max > 3){
               throw new IndexOutOfBoundsException();
           }

           return board.findMaxAdjacent(max);

        } else{
            throw new IsNotPlayerException("Sei un Guest, non puoi usare questo metodo");
        }
    }

    List<List<Tile>> choosableTiles(int tilesValue) throws IsNotPlayerException {

        if (type.equals("PLAYER")){

            tilesNumber = tilesValue;

            return board.choosableTiles(tilesValue);

        } else{

            throw new IsNotPlayerException("Sei un Guest, non puoi usare questo metodo");
        }
    }

    boolean[] chooseSelectedTiles(int x1, int x2, int x3, int y1, int y2, int y3, String col1, String col2, String col3) throws IsNotPlayerException {

        if (type.equals("PLAYER")){

             board.removeTiles(x1, x2, x3, y1, y2, y3);

            return shelf.chooseColumn(tilesNumber);

        } else{

            throw new IsNotPlayerException("Sei un Guest, non puoi usare questo metodo");
        }
    }

    void insertTile(int column, String[] colorOrder) throws IsNotPlayerException, IndexOutOfBoundsException {

        if (type.equals("PLAYER")){
            if(column < 0 || column > 4){
                throw new IndexOutOfBoundsException();
            }else {

                shelf.addTile(column, colorOrder, tilesNumber);
            }

            board.refill();

        } else{

            throw new IsNotPlayerException("Sei un Guest, non puoi usare questo metodo");
        }
    }

    public static String getNickname() {
        return nickname;
    }


    public int getScore() {
        return score;
    }

    public boolean checkInGame() {
        //check se crasha

        return inGame;
    }

    void createGame(String Nickname, int playerNumber) throws IsNotGuestException {
        if(type.equals("GUEST")) {

            type = "Player";

            Lobby.pickGuest();

            this.game = new Game(playerNumber, this);

        }else{
            throw new IsNotGuestException("Sei un Player, non puoi usare questo metodo");
        }
    }

    void joinGame(String Nickname, this) throws IsNotGuestException {
        if(type.equals("GUEST")) {
            type = "Player";

            Lobby.pickGuest();
        }else{
            throw new IsNotGuestException("Sei un Player, non puoi usare questo metodo");
        }
    }

    void createShelfie(int personalCardID){
        this.shelf = new Shelfie(personalCardID);
        this.board = game.getBoard();
    }

}
