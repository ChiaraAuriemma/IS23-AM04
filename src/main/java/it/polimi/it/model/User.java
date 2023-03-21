package it.polimi.it.model;

import it.polimi.it.model.Board.Board;

import java.util.ArrayList;

public class User {

    private final String Nickname;

    private final int Score;

    private final boolean InGame;

    private String Type;

    int tilesNumber;


    public User() {

        this.Nickname = Lobby.setNickname();

        this.Score = 0;

        this.InGame = true;

        this.Type = "GUEST";

    }

    int MaxValueOfTiles() {

        int max = Shelfie.PossibleTiles();

        return Board.FindMaxAdjacent(max);
    }

    ArrayList ChoosableTiles(int tilesValue) {

        tilesNumber = tilesValue;

        return Board.ChoosableTiles(tilesValue); //struttura dati

    }

    int[] ChooseSelectedTiles(int x1, int x2, int x3, int y1, int y2, int y3, String col1, String col2, String col3) {

        Board.RemoveTiles(x1, x2, x3, y1, y2, y3);

        return Shelfie.ChooseColumn(tilesNumber);
    }

    void InsertTile(int column, String[] colorOrder) {

        Shelfie.AddTile(column, colorOrder, tilesNumber);

        Board.Refill();
    }



    public String getNickname() {
        return Nickname;
    }


    public int getScore() {
        return Score;
    }

    public boolean checkInGame() {
        //check se crasha

        return InGame;
    }

    void CreateGame(String Nickname, int playerNumber){
        Type = "Player";

        Game g = new Game(playerNumber);
    }

    void JoinGame(String Nickname){
        Type = "Player";

        //TODO
    }



}
