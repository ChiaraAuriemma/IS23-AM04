package it.polimi.it.model;

import it.polimi.it.model.Board.Board;
import it.polimi.it.model.Exceptions.IsNotGuestException;
import it.polimi.it.model.Exceptions.IsNotPlayerException;

import java.util.ArrayList;

public class User {

    private static String Nickname;

    private final int Score;

    private final boolean InGame;

    private String Type;

    int tilesNumber;

    private Game game;

    private Shelfie shelf;

    private Board board;


    public User() {

       // Nickname = Lobby.setNickname(); -> serve controller/view che fa impostare nickname a user dentro la lobby

        this.Score = 0;

        this.InGame = true;

        this.Type = "GUEST";

    }

    int MaxValueOfTiles() throws IsNotPlayerException {

        if (Type.equals("PLAYER")){

           int max = shelf.PossibleTiles();

           if(max < 0 || max > 3){
               throw new IndexOutOfBoundsException();
           }

           return board.findMaxAdjacent(max);

        } else{
            throw new IsNotPlayerException(Type);
        }
    }

    ArrayList ChoosableTiles(int tilesValue) throws IsNotPlayerException {

        if (Type.equals("PLAYER")){

            tilesNumber = tilesValue;

            return board.ChoosableTiles(tilesValue); //struttura dati

        } else{

            throw new IsNotPlayerException(Type);
        }
    }

    boolean[] ChooseSelectedTiles(int x1, int x2, int x3, int y1, int y2, int y3, String col1, String col2, String col3) throws IsNotPlayerException {

        if (Type.equals("PLAYER")){

             board.RemoveTiles(x1, x2, x3, y1, y2, y3);

            return shelf.ChooseColumn(tilesNumber);

        } else{

            throw new IsNotPlayerException(Type);
        }
    }

    void InsertTile(int column, String[] colorOrder) throws IsNotPlayerException, IndexOutOfBoundsException {

        if (Type.equals("PLAYER")){
            if(column < 0 || column > 4){
                throw new IndexOutOfBoundsException();
            }else {

                shelf.AddTile(column, colorOrder, tilesNumber);
            }

            board.refill();

        } else{

            throw new IsNotPlayerException(Type);
        }
    }

    public static String getNickname() {
        return Nickname;
    }


    public int getScore() {
        return Score;
    }

    public boolean checkInGame() {
        //check se crasha

        return InGame;
    }

    void CreateGame(String Nickname, int playerNumber) throws IsNotGuestException {
        if(Type.equals("GUEST")) {

            Type = "Player";

            Lobby.PickGuest();

            this.game = new Game(playerNumber, this);

        }else{
            throw new IsNotGuestException(Type);
        }
    }

    void JoinGame(String Nickname, this) throws IsNotGuestException {
        if(Type.equals("GUEST")) {
            Type = "Player";

            Lobby.PickGuest();
        }else{
            throw new IsNotGuestException(Type);
        }
    }

    void CreateShelfie(int PersonalCardID){
        this.shelf = new Shelfie(PersonalCardID);
        this.board = game.getBoard();
    }

}
